package net.sunnycore.intercontent.util;

import java.lang.reflect.ParameterizedType;


/**
 * 
 *
 * @author   Valiantsin Shukaila
 * @version  1.0
 * @created  8/6/08
 */
public class GenericUtils {

    /**
     * Gets the value of the parameter class property.
     *
     * @param   clazz
     *
     * @return  parameter class value
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Class<T> getParameterClass(Class clazz) {
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }

        return (Class<T>)
            ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
