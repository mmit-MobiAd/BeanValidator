/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv;


import at.mikemitterer.bv.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * Bean Validator main class.
 * @author Jiren
 */
public class BeanBeanValidatorImpl implements BeanValidator {
    private static Logger logger = LoggerFactory.getLogger(BeanBeanValidatorImpl.class.getSimpleName());

    private static final Object[] EMPTY_CLASS_ARRAY = new Object[0];

//  private static HashMap<Class<? extends Annotation>, IValidator> validatorsMap = new HashMap<Class<? extends Annotation>, IValidator>();
//
//    /**
//     * Register Validator Annotation and related Validator Class
//     * @param annotation
//     */
//    public static void registerValidator(Class<? extends Annotation> annotation) {
//
//        if (!ValidatorsCache.isAValidation(annotation)) {
//            throw new IllegalArgumentException(annotation.toString() + " has not valid Validator Annotation() ");
//        }
//
//        if (!validatorsMap.containsKey(annotation)) {
//
//            AValidation iAnnotation = (AValidation) annotation.getAnnotation(AValidation.class);
//            try {
//                IValidator validate = (IValidator) iAnnotation.validator().newInstance();
//                validatorsMap.put(annotation, validate);
//                return;
//            } catch (InstantiationException ex) {
//                Logger.getLogger(BeanValidator.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalAccessException ex) {
//                Logger.getLogger(BeanValidator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        throw new IllegalArgumentException(annotation.toString() + " is Already register.");
//    }

    private BeanBeanValidatorImpl() {
    }

    public static BeanValidator getInstance() {
        return new BeanBeanValidatorImpl();
    }

    /**
     * Validate object fields.
     * If some field has multiple validation annotation. Which ever first
     * annoation validation beark remaining annotation are not going to
     * validate.
     * @param obj
     * @return
     */

    @Override
    public<T> List<ViolationInfo<T>> validate(final T obj) {
        final ViolationInfoHandler<T> validationMsg = new ViolationInfoHandler<T>(obj);

        AnnotationMetaData[] annotationMetaDatas = ValidatorCache.getFields(obj);
        if (annotationMetaDatas == null) {
            return validationMsg.getViolationInfos();
        }

        for (AnnotationMetaData annotationMetaData : annotationMetaDatas) {

            Annotation[] annotations = annotationMetaData.getAnnotation();

            Object valueObj = null;
            try {
                valueObj = annotationMetaData.getMethod().invoke(obj, EMPTY_CLASS_ARRAY);
            } catch (IllegalAccessException ex) {
                logger.error("validate failed, Error: {}", ex.toString());

            } catch (IllegalArgumentException ex) {
                logger.error("validate failed, Error: {}", ex.toString());

            } catch (InvocationTargetException ex) {
                logger.error("validate failed, Error: {}", ex.toString());

            } catch (Exception ex) {
                logger.error("validate failed, Error: {}", ex.toString());
            }

            for (Annotation annotation : annotations) {

                Validate iValidate = ValidatorCache.getValidator(annotation);

                if (iValidate != null) {
                    try {

                        if (!iValidate.validate(annotationMetaData, valueObj, annotation, validationMsg)) {
                            break;
                        }


                    } catch (Exception ex) {
                        logger.error("validate failed, Error: {}", ex.toString());
                    }
                } else {
                    logger.warn("Validator not registered for: {}", annotation);
                }

            }
        }

        return validationMsg.getViolationInfos();
    }

    // --------------------------------------------------------------------------------------------
    // innerclasses
    // --------------------------------------------------------------------------------------------

}
