package at.mikemitterer.bv;

import at.mikemitterer.bv.constraints.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: mikemitterer, Date: 28.10.13, Time: 09:02
 */
public class BeanValidatorTest extends Assert {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(BeanValidatorTest.class.getSimpleName());

    @Test
    public void testNotEmpty() throws Exception {
        final User user = new User("", "office@mikemitterer.at", "+43 4562 282872-4");
        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"Name must not be empty");
    }

    @Test
    public void testPhoneNumber() throws Exception {
        final User user = new User("Mike", "office@mikemitterer.at", "+43 (0) 4562 282872-4");

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"+43 (0) 4562 282872-4 is not a valid phone number");
        assertEquals(violationInfos.get(0).getMessageTemplate(),"%value% is not a valid phone number");
    }

    @Test
    public void testMinLenght() throws Exception {
        final User user = new User("Joe", "joe@test.com", "+43 4562 282872-4");

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"Name lenght must be at least 4 characters...");
    }

    @Test
    public void testCity() throws Exception {
        final City city = new City("", null);

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<City>> violationInfos = beanValidator.validate(city);
        assertEquals(2,violationInfos.size());
    }

    @Test
    public void testUserInCity() throws Exception {
        final UserInCity userInCity = new UserInCity(new City("6363", "Westendorf"), new User("Joe", "office@mikemitterer.at", "+43 56676 22322"));

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<UserInCity>> violationInfos = beanValidator.validate(userInCity);
        assertEquals(1,violationInfos.size());
        logger.debug("UserInCityError: {}, was: {}",violationInfos.get(0).getMessage(),violationInfos.get(0).getInvalidValue());
    }

    @Test
    public void testUserInCityWithNull() throws Exception {
        final UserInCity userInCity = new UserInCity(null, new User("Mike", "office@mikemitterer.at", "+43 56676 22322"));

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<UserInCity>> violationInfos = beanValidator.validate(userInCity);
        assertEquals(1,violationInfos.size());
        assertEquals("City must be valid",violationInfos.get(0).getMessage());
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------

    private static class UserInCity {
        private final City city;
        private final User user;

        private UserInCity(final City city, final User user) {
            this.city = city;
            this.user = user;
        }

        @VObject(message = "City must be valid")
        public City getCity() {
            return city;
        }

        @VObject(message = "User must be valid")
        public User getUser() {
            return user;
        }
    }

    private static class City {
        private final String zip;
        private final String name;

        private City(final String zip, final String name) {
            this.zip = zip;
            this.name = name;
        }

        @NotEmpty(message = "ZIP-Code must not be empty")
        public String getZip() {
            return zip;
        }

        @NotEmpty(message = "Cityname must not be empty")
        public String getName() {
            return name;
        }
    }

    private static class User {

        @NotEmpty(message = "Name must not be empty")
        @MinLength(value = 4, message = "Name lenght must be at least 4 characters...")
        private final String name;

        private final String eMail;

        @Phone(message = "%value% is not a valid phone number", value = "")
        private final String phone;

        private User(final String name, final String eMail, final String phone) {
            this.name = name;
            this.eMail = eMail;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        @Email(message = "%value% is not a valid eMail address")
        public String getEmail() {
            return eMail;
        }

        public String getPhone() {
            return phone;
        }
    }
}
