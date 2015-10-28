package at.mikemitterer.bv;

import at.mikemitterer.bv.constraints.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    public void testEmptyList() throws Exception {
        final AreaCodes areayCodes = new AreaCodes();
        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        List<ViolationInfo<AreaCodes>> violationInfos = beanValidator.validate(areayCodes);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"List must not be empty");

        areayCodes.getCodes().add("6363");
        violationInfos = beanValidator.validate(areayCodes);
        assertEquals(0,violationInfos.size());
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
    @SuppressWarnings("Duplicates")
    public void testMinLenght() throws Exception {
        final User user = new User("Joe", "joe@test.com", "+43 4562 282872-4");

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"Name length must be at least 4 characters...");
    }

    @Test
    public void testAgeInAbstractBaseClass() throws Exception {
        final User user = new User(3,"Mike", "joe@test.com", "+43 4562 282872-4");

        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"Age must be between 5 and 99 years");
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
        assertEquals("City must be valid", violationInfos.get(0).getMessage());
    }

    @Test
    public void testUUID() throws Exception {
        final User user = new User("", "office@mikemitterer.at", "+43 4562 282872-4");
        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        final List<ViolationInfo<User>> violationInfos = beanValidator.validate(user);
        assertEquals(1,violationInfos.size());
        assertEquals(violationInfos.get(0).getMessage(),"Name must not be empty");

        user.userID = "123";

        final List<ViolationInfo<User>> violationInfosWithUUID = beanValidator.validate(user);
        assertEquals(2,violationInfosWithUUID.size());

        boolean found = false;
        for(final ViolationInfo<User> vi : violationInfosWithUUID) {
            if(vi.getMessage().compareTo("UserID must be a UUID") == 0) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testUsernamePassword() throws Exception {
        final BeanValidator beanValidator = BeanBeanValidatorImpl.getInstance();

        List<ViolationInfo<LoginPassword>> violationInfos;

        violationInfos = beanValidator.validate(new LoginPassword("office@mikemitterer.at","12345678aA#"));
        assertEquals(0,violationInfos.size());

        violationInfos = beanValidator.validate(new LoginPassword("office@mikemitterer.at","12345678aA@"));
        assertEquals(0,violationInfos.size());

        violationInfos = beanValidator.validate(new LoginPassword("office@mikemitterer.at","12345678aA;"));
        assertEquals(1,violationInfos.size());

        violationInfos = beanValidator.validate(new LoginPassword("office#mikemitterer.at","12345678aA;"));
        assertEquals(2,violationInfos.size());

        violationInfos = beanValidator.validate(new LoginPassword("office@mikemitterer.at","5678aA#"));
        assertEquals(1,violationInfos.size());

        violationInfos = beanValidator.validate(new LoginPassword("office@mikemitterer.at","12345678abcdefA#"));
        assertEquals(1,violationInfos.size());

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

    private static class AreaCodes {
        private final List<String> codes = new ArrayList<String>();

        @NotEmpty(message = "List must not be empty")
        public List<String> getCodes() {
            return codes;
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

    private static abstract class Person {
        private final int age;

        protected Person(final int age) {
            this.age = age;
        }

        // does not work - Annotations can not be inherited
        @Range(start = 5, end = 99, message = "Age must be between 5 and 99 years")
        public int getAge() {
            return age;
        }
    }

    private static class User extends Person {
        @Uuid(message = "UserID must be a UUID")
        public String userID;

        @NotEmpty(message = "Name must not be empty")
        @MinLength(value = 4, message = "Name length must be at least 4 characters...")
        public final String name;

        private final String eMail;

        @Phone(message = "%value% is not a valid phone number", value = "")
        public final String phone;

        private User(final int age, final String name, final String eMail, final String phone) {
            super(age);

            this.name = name;
            this.eMail = eMail;
            this.phone = phone;
            this.userID = "135ea20d-f57b-4960-b544-ceafde88d9b8";
        }

        private User(final String name, final String eMail, final String phone) {
            super(33);

            this.name = name;
            this.eMail = eMail;
            this.phone = phone;
            userID = "135ea20d-f57b-4960-b544-ceafde88d9b8";
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

        public String getUserID() {
            return userID;
        }

        //        @Override
//        @Range(start = 5, end = 99, message = "Age must be between 5 and 99 years")
//        public int getAge() {
//            return super.getAge();
//        }
    }

    private static class LoginPassword {

        private final String username;
        private final String password;

        public LoginPassword(final String username, final String password) {
            this.username = username;
            this.password = password;
        }

        @Email(message = "%value% is not a valid eMail address")
        public String getUsername() {
            return username;
        }

        @Password(message = "%value% is not a valid password")
        public String getPassword() {
            return password;
        }
    }
}
