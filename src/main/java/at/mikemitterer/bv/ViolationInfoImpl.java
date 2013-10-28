package at.mikemitterer.bv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikemitterer, Date: 25.10.13, Time: 10:50
 */
class ViolationInfoImpl<T> implements ViolationInfo<T> {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ViolationInfoImpl.class.getSimpleName());

    private final String methodName;
    private final String message;
    private final Object invalidValue;
    private final T      rootBean;

    public ViolationInfoImpl(final T rootBean, final String methodName, final String message, final Object invalidValue) {
        this.methodName = methodName;
        this.message = message;
        this.invalidValue = invalidValue;
        this.rootBean = rootBean;
    }

    @Override
    public String getMessag() {
        return message.replace("%value%", value());
    }

    @Override
    public String getMessageTemplate() {
        return message;
    }

    @Override
    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String getFieldName() {
        return methodName;
    }

    @Override
    public T getRootBean() {
        return rootBean;
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------

    private String value() {
        return invalidValue != null ? invalidValue.toString() : "null";
    }
}
