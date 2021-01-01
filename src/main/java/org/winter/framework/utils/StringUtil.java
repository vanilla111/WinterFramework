package org.winter.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wang
 * @Description 字符串工具类
 * @Date 2020/12/31
 */
public final class StringUtil {

    public static final String SEPARATOR = String.valueOf((char)29);

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
