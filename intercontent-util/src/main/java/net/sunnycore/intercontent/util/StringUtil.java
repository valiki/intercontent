package net.sunnycore.intercontent.util;

import java.util.Collection;
import java.util.Iterator;


/**
 * General util classes that provide string-operating and string-converting methods
 *
 * @author   Valiantsin Shukaila
 * @version  1.0
 * @created  10/1/08
 */
public class StringUtil
{

    /** stores COMMA*/
    private static final String COMMA = ",";

    /**
     * A method that provides converting Collection to string.<br>
     * If a Collection consists of user defined objects then <code>toString()</code> method should
     * be implemented<br>
     * Usually used is sql queries for <code>in</code> clauses
     *
     * @param   A  Collection of values to convert
     *
     * @return  A String of type <code>value1,value2,value3</code> where <code>value</code> is
     *          result of calling <code>String.valueOf(..)</code> method on collection elements
     */
    public static <T> String CollectionToString(Collection<T> values)
    {
        StringBuffer resultBuffer = new StringBuffer(values.size() * 3);
        Iterator<T> iterator = values.iterator();

        while (iterator.hasNext())
        {
            T value = iterator.next();
            resultBuffer.append(String.valueOf(value));

            if (iterator.hasNext())
            {
                resultBuffer.append(COMMA);
            }
        }

        return resultBuffer.toString();
    }
} // end class StringUtils
