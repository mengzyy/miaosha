package com.mzy.miaosha.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    /*正则匹配器*/
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    /**
     * 判断用户手机号合法性
     *
     * @param mobile 用户输入的手机号
     * @return true 合法 false 非法
     */
    public static Boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
