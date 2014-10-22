package com.cf.utils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.*;

/**
 * Created by aliaksandr.krasitski on 9/17/2014.
 */
public class CustomMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();

        return properties;
    }

    public Properties getAllProperties() {
        clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = getMergedProperties(Locale.getDefault());
        Properties properties = propertiesHolder.getProperties();

        return properties;
    }

    public Map<String, String> getPropertiesByTags(List<String> tags) {
        Properties properties = getAllProperties();
        Set<String> propertyNames = properties.stringPropertyNames();

        Map<String, String> resultMap = new HashMap<>();
        for (String tag : tags) {
            for (String name : propertyNames) {
                if (name.startsWith(tag)) {
                    resultMap.put(name, properties.getProperty(name));
                }
            }
        }
        return resultMap;
    }
}
