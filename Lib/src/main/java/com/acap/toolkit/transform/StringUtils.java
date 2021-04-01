package com.acap.toolkit.transform;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Tip:
 *      String类相关工具
 * Function:
 *
 * Created by ACap on 2018/6/29.
 * </pre>
 */
public class StringUtils {

    /**
     * 去掉首位无意义,判断是否为空或者空数据
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 去掉首位无意义,判断非空并且非空数据
     */
    public static boolean isNotTrimEmpty(final String s) {
        return !isTrimEmpty(s);
    }


    /**
     * 比较两个字符串是否相等,忽略大小写
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 空对象字符转为空字符
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 安全判断字符长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 字符串反转
     */
    public static String reverse(final String s) {
        if (s == null) return "";
        int len = s.length();
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 字符串转半角
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 字符串转全角
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }


    /**
     * 从String中查询某个字符的下标集
     */
    public static final List<Integer> findIndex(String str, String findstr) {
        return findIndex(str, findstr, -1);
    }

    /**
     * 从String中查询某个字符的下标集
     */
    public static final List<Integer> findIndex(String str, String findstr, int startIndex) {
        List<Integer> indexs = new ArrayList<>();
        int fromIndex = startIndex;
        while ((fromIndex = str.indexOf(findstr, fromIndex + 1)) != -1) {
            indexs.add(fromIndex);
        }
        return indexs;
    }


    /**
     * 格式化String到一列
     */
    public static final String format2Column(String str) {
        String[] array = new String[str.length()];
        for (int i = 0; i < str.length(); i++) {
            array[i] = str.substring(i, i + 1);
        }
        String join = ArraysUtils.join(array, "\n");
        return join;
    }

}
