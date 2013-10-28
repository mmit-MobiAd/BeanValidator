/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mikemitterer.bv;


import at.mikemitterer.bv.constraints.Constraint;
import at.mikemitterer.bv.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Valdaitors registration and Cache.
 * Bean Fields methods and validation annotation cache.
 *
 * @author Jiren
 */
final class ValidatorCache<P> {
    private static Logger logger = LoggerFactory.getLogger(ValidatorCache.class.getSimpleName());

    /**
     * Getter method prefix for all primitive and non-premitive datatype except
     * 'boolean' primitive data type
     */
    private static String GETTER_METHOD_PREFIX   = "get";
    /**
     * Getter method prefix for 'boolean' datatype.
     */
    private static String GETTER_METHOD_PREFIX_B = "is";

    /**
     * Map of Class name to  fileds annotations and their reflected getter method array.
     */
    private static HashMap<String, AnnotationMetaData[]> classMethodsMap = new HashMap<String, AnnotationMetaData[]>();

    /**
     * Annotation to Custome validator instance map.
     */
    private static HashMap<Class<? extends Annotation>, Validate> validatorsMap = new HashMap<Class<? extends Annotation>, Validate>();


    private ValidatorCache() {
    }

    /**
     * Get Class fields annotation and getter method object array.
     * - If Class is not present into cache than it add it else it return from
     * the cache.
     */
    public static AnnotationMetaData[] getFields(Object object) {

        String className = object.getClass().getName();
        if (!classMethodsMap.containsKey(className)) {
            addClass(object.getClass());
        }
        return classMethodsMap.get(className);
    }


    /**
     * Check the annotation is AValidation type.
     *
     * @param annotation
     * @return
     */
    public static boolean isAValidation(Class<? extends Annotation> annotation) {
        if (annotation.isAnnotationPresent(Constraint.class)) {
            return true;
        }
        return false;
    }


    /**
     * Get Validator from the map.
     */
    public static Validate getValidator(Annotation vAnnotation) {
        return validatorsMap.get(vAnnotation.annotationType());
    }

    // --------------------------------------------------------------------------------------------
    // private
    // --------------------------------------------------------------------------------------------

    /**
     * Get field Get Method name.
     *
     * @param fieldName
     * @return
     */
    private static String getGetMethod(String fieldName) {
        return GETTER_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * Get field Get Method name.
     */
    private static String getGetMethod(Field field) {

        String prefix = null;
        String fieldName = field.getName();

        if (field.getType() != boolean.class) {
            prefix = GETTER_METHOD_PREFIX;
        } else {
            prefix = GETTER_METHOD_PREFIX_B;
        }
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1).toLowerCase();
    }

    /**
     * Get Annotation with AValidation.
     */
    private static Annotation[] getAValidates(Field field) {
        return filterConstraintAnnotations(field.getDeclaredAnnotations());
    }

    private static Annotation[] getAValidates(Method method) {
        return filterConstraintAnnotations(method.getDeclaredAnnotations());
    }

    private static Annotation[] filterConstraintAnnotations(final Annotation[] annotations) {
        ArrayList<Annotation> list = new ArrayList<Annotation>();

        for (int i = 0; i < annotations.length; i++) {

            Annotation annotation = annotations[i];
            if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
                list.add(annotation);
            }
        }

        return list.toArray(new Annotation[list.size()]);
    }

    /**
     * This method register validator.
     * - Get @AValidation annotation from the validation annotation.
     * - create instance of Validator object and add it to validators map.
     *
     * @param annotation
     */
    private static void registerValidator(Annotation annotation) {


        Class vAnnotationClass = annotation.annotationType();
        try {
            Constraint constraint = (Constraint) vAnnotationClass.getAnnotation(Constraint.class);

            validatorsMap.put(vAnnotationClass, constraint.validator().newInstance());

            logger.debug("Registered Validator {}", annotation.toString());

        } catch (InstantiationException ex) {
            logger.error("registerValidator failed, Error: {}", ex.toString());

        } catch (IllegalAccessException ex) {
            logger.error("registerValidator failed, Error: {}", ex.toString());
        }

    }

    /**
     * Add class fields 'get'(Getter) Methods and field annotation in Map.
     * - This method check annotation is annotated by 'AValidation' annotation.
     * Using this check at run time checking the annotation is for validation
     * or not.
     * <p/>
     * - This method add field into cache only if it's getter method has public
     * modifier.
     * <p/>
     * - This method check that validator already exist in validators map. If not
     * exist in it than register that validator.So no need to register validator
     * separately it loaded on demand basis.
     */
    private static void addClass(final Class klass) {

        try {
            Map<String, AnnotationMetaData> methodMap = new HashMap<String, AnnotationMetaData>();

            //List<AnnotationMetaData> methodList = new ArrayList<AnnotationMetaData>();

            addMethodsToMethodList(klass, methodMap);
            addFieldsToMethodList(klass, methodMap);

            if (!methodMap.isEmpty()) {

                //Convert list into array and store in to map.
                classMethodsMap.put(klass.getName(), methodMap.values().toArray(new AnnotationMetaData[methodMap.size()]));
            }
        } catch (Exception ex) {
            logger.error("addClass failed, Error: {}", ex.toString());
        }

    }


    private static void addMethodsToMethodList(final Class klass, final Map<String, AnnotationMetaData> methodMap) {
        Method[] methods = klass.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            final Annotation[] annotations = getAValidates(method);

            if (annotations.length != 0) {

                for (int j = 0; j < annotations.length; j++) {
                    if (!validatorsMap.containsKey(annotations[j].annotationType())) {
                        registerValidator(annotations[j]);
                    }
                }

                if (Modifier.isPublic(method.getModifiers())) {
                    methodMap.put(method.getName(), new AnnotationMetaData(method, annotations));
                } else {
                    logger.warn("Getter {} is not public...", method.getName());
                }

            }
        }
    }

    private static void addFieldsToMethodList(final Class klass, Map<String, AnnotationMetaData> methodMap) {
        Field[] fields = klass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];
            final Annotation[] annotations = getAValidates(field);

            if (annotations.length != 0) {

                for (int j = 0; j < annotations.length; j++) {
                    if (!validatorsMap.containsKey(annotations[j].annotationType())) {
                        registerValidator(annotations[j]);
                    }
                }

                try {

                    final String methodName = getGetMethod(field);
                    Method method = klass.getDeclaredMethod(methodName);

                    if (Modifier.isPublic(method.getModifiers())) {
                        if (!methodMap.containsKey(methodName)) {
                            methodMap.put(methodName, new AnnotationMetaData(field, method, annotations));
                        } else {
                            logger.warn("Field annotation for {} is already in defined with it's getter {}", field.getName(),methodName);
                        }
                    } else {
                        logger.warn("Field getter method has not public modifier: {}", field.getName());
                    }

                } catch (NoSuchMethodException ex) {
                    logger.error("addClass failed, Error: {}", ex.toString());
                }
            }
        }
    }

}
