package at.mikemitterer.bv;

/**
 * User: mikemitterer, Date: 25.10.13, Time: 09:58
 */
public interface ViolationInfo<T> {
    public String getMessage();

    public String getMessageTemplate();

    public Object getInvalidValue();

    public String getMethodName();

    public T getRootBean();

}
