package org.winter.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author wang
 * @Description
 * @Date 2020/12/31
 */
public final class ArrayUtil {

    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
