/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jiren
 */
public class ViolationInfoHandler<T> {

    private final List<ViolationInfo<T>> violationInfos = new ArrayList<>();
    private final T rootBean;

    public ViolationInfoHandler(final T rootBean) {
        this.rootBean = rootBean;
    }

    public void addMessage(final AnnotationMetaData method, final String message, final Object invalidValue) {
        violationInfos.add(new ViolationInfoImpl<>(rootBean, method.getMethod().getName(), message, invalidValue));
    }

    public void addMessage(final String methodName, final String message,final Object invalidValue) {
        violationInfos.add(new ViolationInfoImpl<>(rootBean,methodName, message, invalidValue));
    }

    public void addMessageForNullPointer(final AnnotationMetaData method, final String message) {
        violationInfos.add(new ViolationInfoImpl<>(rootBean,method.getMethod().getName(), message, null ));
    }

    public List<ViolationInfo<T>> getViolationInfos() {
        return violationInfos;
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
