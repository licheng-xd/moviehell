package com.lc.moviehell.util;

import java.util.regex.Pattern;

public class RegexUtil {

    private static final Pattern fixedPhonePattern = Pattern
        .compile("^(\\+?0*86-?)?(0+)?([0-9]{2,3})?([2-8][0-9]{6,7})([0-9]{1,4})?$");

    private static final Pattern mobilePattern = Pattern
        .compile("^\\+\\d{1,4}\\-\\d+|1[3,4,5,7,8]\\d{9}");

    private static final Pattern domesticMobilePattern = Pattern
        .compile("^1[3,4,5,7,8]\\d{9}");

    private static final Pattern internationMobilePattern = Pattern
        .compile("^\\+\\d{1,4}\\-\\d+");

    private static final Pattern foreignerMobilePattern = Pattern
        .compile("^\\d{5,}$");

    private static final Pattern emailPattern = Pattern
        .compile("^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+");

    private static final Pattern mobileEmailPattern = Pattern
        .compile("^1[3,4,5,7,8]\\d{9}@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+");

    private static final Pattern yidPattern = Pattern
        .compile("[a-zA-Z][a-zA-Z0-9_]{5,19}"); // 易信号：以字母开头的字母+数字+下划线，6-20位

    private static final Pattern yidPatternForFindUser = Pattern
        .compile("[a-zA-Z][a-zA-Z0-9_]{4,19}"); // 易信号：以字母开头的字母+数字+下划线，5-20位

    private static final Pattern ecpidPattern = Pattern
        .compile(".+@ecplive\\.com");
    
    private static final String ipDigitPattern = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";

    private static final Pattern accidPattern = Pattern
        .compile("^([A-Za-z0-9_@\\.\\-])*$");

    private static final Pattern ipPattern = Pattern.compile(String.format(
        "%s(\\.%s){3}", ipDigitPattern, ipDigitPattern));

    private static final Pattern internalIpPattern = Pattern
        .compile(String
            .format(
                "(10(\\.%s){3})|(172\\.(1[6-9]|2\\d|3[01])(\\.%s){2})|(192\\.168(\\.%s){2})|(127\\.0\\.0\\.%s)",
                ipDigitPattern, ipDigitPattern, ipDigitPattern, ipDigitPattern));

    private static final Pattern numbersStringPattern = Pattern.compile("\\d+");

    public static boolean isNumbersString(String str) {
        return numbersStringPattern.matcher(str).matches();
    }

    public static boolean isFixedPhone(String str) {
        return fixedPhonePattern.matcher(str).matches();
    }

    public static boolean isMobile(String str) {
        return mobilePattern.matcher(str).matches();
    }

    public static boolean isDomesticMobile(String str) {
        return domesticMobilePattern.matcher(str).matches();
    }

    public static boolean isInternationMobile(String str) {
        return internationMobilePattern.matcher(str).matches();
    }

    public static boolean isForeignerMobile(String str) {
        return foreignerMobilePattern.matcher(str).matches();
    }

    public static boolean isEmail(String str) {
        return emailPattern.matcher(str).matches();
    }

    public static boolean isMobileEmail(String str) {
        return mobileEmailPattern.matcher(str).matches();
    }

    public static boolean isYid(String str) {
        return yidPattern.matcher(str).matches();
    }

    public static boolean isYidForFindUser(String str) {
        return yidPatternForFindUser.matcher(str).matches();
    }

    public static boolean isEcpid(String str) {
        return ecpidPattern.matcher(str).matches();
    }

    public static boolean isInternalIp(String str) {
        return internalIpPattern.matcher(str).matches();
    }

    public static boolean isIp(String str) {
        return ipPattern.matcher(str).matches();
    }

    public static boolean isAccid(String accid) {
        return accidPattern.matcher(accid).matches();
    }
}
