package com.data.dataproducer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局唯一编码生成器
 *
 * @author danny
 *
 * @date 2019-04-04
 *
 */
public class UCodeUtil {

    /**
     * 自定义进制字符。
     * 包括：
     * 数字、小写字母、大写字母
     */
    private final static char[] base = {
            '0','1','2','3','4',
            '5','6','7','8','9',

            'a','b','c','d','e',
            'f','g','h','i','j',
            'k','l','m','n','o',
            'p','q','r','s','t',
            'u','v','w','x','y',
            'z',

            'A','B','C','D','E',
            'F','G','H','I','J',
            'K','L','M','N','O',
            'P','Q','R','S','T',
            'U','V','W','X','Y',
            'Z'
    };

    /**
     * 1970-01-01距离今毫秒数缩减基数
     * 49 * 365天  ≈ 49年
     */
    private final static long BASE_REDUCED_MS = 1000L * 60 *60 * 24 * 365 * 49;

    /**
     * 自定义36进制
     * 仅小写字母和数字
     */
    private final static int DECIMAL36 = 36;

    /**
     * 自定义62进制
     */
    private final static int DECIMAL62 = 62;

    /**
     * 自定义进制最大值
     */
    private final static int MAX = 62;

    /**
     * 自定义进制最小值
     */
    private final static int MIN = 2;

    /**
     * 小写字母长度
     */
    private final static int LOWER_CASE_LEN = 26;

    /**
     * 小写字母起始位置
     */
    private final static int LOWER_CASE_START_INDEX = 10;

    /**
     * 自定义字符的10进制含义字典
     */
    private final static Map<Character, Integer> map = new HashMap<>(MAX);

    /**
     * 初始化自定义字符10进制值
     */
    static {
        for (int i = 0; i < base.length; i++) {
            map.put(base[i], i);
        }
    }

    /**
     * 每毫秒内计数器
     * key: 毫秒
     * val:当前毫秒下的计数器的值
     */
    private final static Map<Long, Integer> counterMap = new HashMap<>(1);

    /**
     * 计数器起始值
     */
    private final static int COUNTER_START = 0;

    /**
     * 自定义进制中的0
     */
    private final static String ZERO = "0";

    /**
     * 获取计数
     * @param timeMs
     * @return
     */
    private static synchronized int counter (long timeMs) {
        Integer count = counterMap.get(timeMs);
        counterMap.clear();
        if (null == count) {
            counterMap.put(timeMs, COUNTER_START);
            return COUNTER_START;
        }
        counterMap.put(timeMs, ++count);
        return count.intValue();
    }

    /**
     * 数值转换为自定义进度数值
     *
     * @param num
     * @param x [2-62]
     * @param startWithLetter
     * @return
     */
    public static String code (long num, int x, boolean startWithLetter) {
        StringBuffer buffer = new StringBuffer();
        //Start with character
        if (startWithLetter) {
            //First character. Character start at index of 10.
            long fx = x > LOWER_CASE_LEN ? LOWER_CASE_LEN : x;
            //Add the number base len. Confirm start with character
            int firstBit = LOWER_CASE_START_INDEX + (int) (num % fx);
            buffer.append(base[firstBit]);
            num /= fx;
        }

        //Other character
        for (; num > 0; num /= x) {
            buffer.append(base[(int) (num % x)]);
        }

        if (startWithLetter) {
            return buffer.toString();
        }

        if (buffer.length() == 0) {
            buffer.append(base[0]);
        }
        return buffer.reverse().toString();
    }

    /**
     * 数值转换为自定义进度数值 , 重载
     * @param num
     * @param x
     * @param startWithLetter
     * @return
     */
    public static String code (int num, int x, boolean startWithLetter) {
        return code ((long) num, x, startWithLetter);
    }

    /**
     * 根据数值生成唯一字符串编码，小写字母+数字
     * 小写字母开头
     * @param num
     * @param x [2-62]
     * @return
     */
    public static String code (long num, int x) {
        return code(num, x, true);
    }

    /**
     * 根据数值生成唯一字符串编码，小写字母+数字， 重载
     * 小写字母开头
     * @param num
     * @param x [2-62]
     * @return
     */
    public static String code (int num, int x) {
        return code((long) num, x);
    }

    /**
     * 自定义进制字符串转成10进制
     * @param code 自定义进制字符
     * @param x x进制
     * @param startWithLetter 是否以字母开头的生成法则
     * @return
     */
    public static long unCode (String code, int x, boolean startWithLetter) {
        int b = code.length();
        long rv = 0;

        if (!startWithLetter) {
            for (int i = 0; i < b; i++) {
                rv += map.get(code.charAt(i)).longValue() * (long) Math.pow(x, b - i - 1);
            }
            return rv;
        }

        //Start with letter
        for (int i = 1; i < b; i++) {
            rv += map.get(code.charAt(i)).longValue() * (long) Math.pow(x, i - 1);
        }

        rv *= (x > LOWER_CASE_LEN ? LOWER_CASE_LEN : x);

        //Add the first character 10 decimal value.
        return rv + map.get(code.charAt(0)).longValue() - LOWER_CASE_START_INDEX;
    }


    /**
     * 自定义进制字符串转成10进制， 重载
     * 默认字母开头的特殊进制规则
     * @param code
     * @param x
     * @return
     */
    public static long unCode (String code, int x) {
        return unCode(code, x, true);
    }

    /**
     * 将时间数值生成的36进制(小写字母和数字)的code
     * @param num
     * @return
     */
    public static String code36 (long num) {
        return code(num, DECIMAL36);
    }

    /**
     * 将时间数值生成的36进制(小写字母和数字)的code, 重载
     * @param num
     * @return
     */
    public static String code36 (int num) {
        return code(num, DECIMAL36);
    }

    /**
     * 将时间数值生成的36进制(小写字母和数字)的code
     * @param num
     * @param startWithLetter
     * @return
     */
    public static String code36 (long num, boolean startWithLetter) {
        return code(num, DECIMAL36, startWithLetter);
    }

    /**
     * 将时间数值生成的36进制(小写字母和数字)的code, 重载
     * @param num
     * @param startWithLetter
     * @return
     */
    public static String code36 (int num, boolean startWithLetter) {
        return code(num, DECIMAL36, startWithLetter);
    }

    /**
     * 将生成的36进制(小写字母和数字)的code解析成对应的long数值
     * @param code
     * @return
     */
    public static long unCode36 (String code) {
        return unCode(code, DECIMAL36);
    }

    /**
     * 将生成的36进制(小写字母和数字)的code解析成对应的long数值
     * @param code
     * @param startWithLetter
     * @return
     */
    public static long unCode36 (String code, boolean startWithLetter) {
        return unCode(code, DECIMAL36, startWithLetter);
    }


    /**
     * 将long数值生成对应的62进制的编码
     * @param num
     * @return
     */
    public static String code62 (long num) {
        return code(num, DECIMAL62);
    }

    /**
     * 将long数值生成对应的62进制的编码, 重载
     * @param num
     * @return
     */
    public static String code62 (int num) {
        return code(num, DECIMAL62);
    }

    /**
     * 将生成的62进制的code解析成对应的long数值
     * @param code
     * @return
     */
    public static long unCode62 (String code) {
        return unCode(code, DECIMAL62);
    }



    /**
     * 指定长度字符，不够长度首位补0
     * @param str
     * @param len
     * @return
     */
    public static String lpad(String str, int len) {
        str = str == null ? "" : str;
        int realLen = str.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < len - realLen; i++) {
            buffer.append(base[0]);
        }
        buffer.append(str);
        return buffer.toString();
    }


    /**
     * 编码生成器
     * @return
     */
    private static String produce (boolean startWithLetter) {
        long ms = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        //timestamp
        buffer.append(code36(ms - BASE_REDUCED_MS, startWithLetter));
        //counter, length = 2
        String counter = code36(counter(ms), false);
        if (ZERO.equals(counter)) {
            return buffer.toString();
        }
        buffer.append(counter);

        return  buffer.toString();
    }

    /**
     * 编码生成器，单服务唯一
     * @return
     */
    public static String produce () {
        return produce(true);
    }

    /**
     * 编码生成器，全局唯一
     * @param soaId 服务ID
     * @return
     */
    public static String produce (int soaId) {
        return code36(soaId) + produce(false);
    }


}
