package at.mikemitterer.bv;

/**
 * User: mikemitterer, Date: 25.10.13, Time: 09:58
 */
public interface ViolationInfo<T> {
    public String getMessag();

    public Object getInvalidValue();

    public String getFieldName();

    public T getRootBean();

}
