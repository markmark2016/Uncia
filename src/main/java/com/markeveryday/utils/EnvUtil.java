package com.markeveryday.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


/**
 * 系统常量util
 *
 * @author liming
 */
public class EnvUtil {

    private static Properties props = null;

    /**
     * 解析property的placeholder工具
     */
    private static PropertyPlaceholderHelper helper =
            new PropertyPlaceholderHelper(SystemPropertyUtils.PLACEHOLDER_PREFIX,
                    SystemPropertyUtils.PLACEHOLDER_SUFFIX, SystemPropertyUtils.VALUE_SEPARATOR, false);

    private static Locale defaultLocale;


    /**
     * 根据配置的key
     */
    public static String getConfigValueByKey(String configKey) {
        if (props == null) {
            return null;
        }
        return getProperty(configKey);
    }

    /**
     * 根据配置key得到配置文件中的值并转化为Int类型 如果没有值或者转化失败则返回默认值defaultValue
     */
    public static int getConfigIntValueByKey(String key, int defaultValue) {
        if (props == null) {
            return defaultValue;
        }
        try {
            Integer value = Integer.parseInt(getProperty(key));
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 根据给定的key返回对应的配置值
     *
     * @param defaultValue 默认值
     */
    public static String getConfigValueByKey(String key, String defaultValue) {
        String value = getConfigValueByKey(key);

        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }

        return value;
    }

    public static String getConfigValueByKey(String key, Properties properties) {
        if (props == null) {
            return null;
        }
        return getProperty(key, properties);
    }

    /**
     * 根据给定的值返回字符串数组 默认是分号(;)
     *
     * @param key 关键字
     */
    public static String[] getConfigValueArrayByKey(String key) {
        return getConfigValueArrayByKey(key, "\\;");
    }

    /**
     * 根据给定的值返回字符串数组
     *
     * @param key         关键字
     * @param splitSymbol 拆分字符串的特殊符号
     */
    public static String[] getConfigValueArrayByKey(String key, String splitSymbol) {
        String value = getConfigValueByKey(key);
        String[] valueArray = null;

        if (value != null) {
            valueArray = value.split(splitSymbol);
        }

        return valueArray;
    }

    /**
     * 根据给定的值返回字符串数组,并由iso8859转换成utf-8
     */
    public static String[] getConfigIsoValue2GbArrayByKey(String key, String splitSymbol) {
        String value = getConfigValueByKey(key);
        String[] valueArray = null;

        if (value != null) {
            valueArray = value.split(splitSymbol);
        }

        return valueArray;
    }

    /**
     * 根据给定的值返回ArrayList,默认是分号(;)
     *
     * @param key 关键字
     */
    public static List<String> getConfigValueListByKey(String key) {
        return getConfigValueListByKey(key, "\\;");
    }

    /**
     * 根据给定的值返回ArrayList
     *
     * @param key         关键字
     * @param splitSymbol 拆分字符串的特殊符号
     */
    public static List<String> getConfigValueListByKey(String key, String splitSymbol) {
        List<String> valueList = new ArrayList<String>();
        String[] strings = getConfigValueArrayByKey(key, splitSymbol);
        if (strings != null) {
            Collections.addAll(valueList, strings);
        }
        return valueList;
    }

    /**
     * 根据给定的值返回ArrayList,并将其中的iso转换成utf-8
     */
    public static List<String> getConfigIsoValue2GbListByKey(String key, String splitSymbol) {
        ArrayList<String> valueList = new ArrayList<String>();
        Collections.addAll(valueList, getConfigIsoValue2GbArrayByKey(key, splitSymbol));

        return valueList;
    }

    private static String getProperty(String key, Properties properties) {
        return helper.replacePlaceholders(props.getProperty(key), properties);
    }

    private static String getProperty(String key) {
        return helper.replacePlaceholders(props.getProperty(key), props);
    }


    /*---------------------------------------------------------------------------------------------------------*/
    public static Locale getDefaultLocale() {
        if (defaultLocale == null) {
            defaultLocale = Locale.SIMPLIFIED_CHINESE;
        }
        return defaultLocale;
    }


    public void setProps(List<Properties> props) {
        Properties properties = new Properties();
        for (Properties p : props) {
            properties.putAll(p);
        }
        EnvUtil.props = properties;
    }

}
