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

    final List<ViolationInfo<T>> violationInfos = new ArrayList<ViolationInfo<T>>();
    final T rootBean;

    public ViolationInfoHandler(final T rootBean) {
        this.rootBean = rootBean;
    }

    public void addMessage(final AnnotationMetaData method, final String message,final Object invalidValue) {
        violationInfos.add(new ViolationInfoImpl<T>(rootBean,method.getMethod().getName(), message, invalidValue));
    }

    public void addMessage(final String methodName, final String message,final Object invalidValue) {
        violationInfos.add(new ViolationInfoImpl<T>(rootBean,methodName, message, invalidValue));
    }

    public List<ViolationInfo<T>> getViolationInfos() {
        return violationInfos;
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------
}
