package at.mikemitterer.bv;

import at.mikemitterer.bv.constraints.Email;
import at.mikemitterer.bv.constraints.MinLength;
import at.mikemitterer.bv.constraints.NotEmpty;
import at.mikemitterer.bv.constraints.Phone;
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

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------

    private static class User {

        @NotEmpty(message = "Name must not be empty")
        @MinLength(value = 4,message = "Name lenght must be at least 4 characters...")
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
