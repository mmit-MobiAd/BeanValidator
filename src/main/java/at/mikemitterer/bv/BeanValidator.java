package at.mikemitterer.bv;

import java.util.List;

/**
 * User: mikemitterer, Date: 25.10.13, Time: 09:25
 */
public interface BeanValidator {
    public  <T> List<ViolationInfo<T>> validate(final T obj);
}
