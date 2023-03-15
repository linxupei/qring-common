package com.qring.common.spring.i18n;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Qring
 * @Description 自定义信息源
 * @Date 2023/3/15 22:51
 * @Version 1.0
 */
public class CustomMessageSource extends AbstractResourceBasedMessageSource implements ResourceLoaderAware {
    private final ConcurrentMap<String, Map<Locale, List<String>>> cachedFilenames = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, PropertiesHolder> cachedProperties = new ConcurrentHashMap<>();
    private final ConcurrentMap<Locale, PropertiesHolder> cachedMergedProperties = new ConcurrentHashMap<>();
    private Properties fileEncodings;
    private boolean concurrentRefresh = true;
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
    private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    public void setFileEncodings(Properties fileEncodings) {
        this.fileEncodings = fileEncodings;
    }

    public void setConcurrentRefresh(boolean concurrentRefresh) {
        this.concurrentRefresh = concurrentRefresh;
    }

    public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
        this.propertiesPersister = (propertiesPersister != null) ? propertiesPersister : new DefaultPropertiesPersister();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null) ? resourceLoader : new DefaultResourceLoader();
    }

    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        if (getCacheMillis() < 0L) {
            PropertiesHolder propHolder = getMergedProperties(locale);
            String result = propHolder.getProperty(code);
            if (result != null) {
                return result;
            }
        } else {
            for (String basename : getBasenameSet()) {
                List<String> filenames = calculateAllFilenames(basename, locale);
                for (String filename : filenames) {
                    PropertiesHolder propHolder = getProperties(filename);
                    String result = propHolder.getProperty(code);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    protected MessageFormat resolveCode(String code, Locale locale) {
        if (getCacheMillis() < 0L) {
            PropertiesHolder propHolder = getMergedProperties(locale);
            MessageFormat result = propHolder.getMessageFormat(code, locale);
            if (result != null)
                return result;
        } else {
            for (String basename : getBasenameSet()) {
                List<String> filenames = calculateAllFilenames(basename, locale);
                for (String filename : filenames) {
                    PropertiesHolder propHolder = getProperties(filename);
                    MessageFormat result = propHolder.getMessageFormat(code, locale);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    protected PropertiesHolder getMergedProperties(Locale locale) {
        PropertiesHolder mergedHolder = this.cachedMergedProperties.get(locale);
        if (mergedHolder != null)
            return mergedHolder;
        Properties mergedProps = newProperties();
        long latestTimestamp = -1L;
        String[] basenames = StringUtils.toStringArray(getBasenameSet());
        for (int i = basenames.length - 1; i >= 0; i--) {
            List<String> filenames = calculateAllFilenames(basenames[i], locale);
            for (int j = filenames.size() - 1; j >= 0; j--) {
                String filename = filenames.get(j);
                PropertiesHolder propHolder = getProperties(filename);
                if (propHolder.getProperties() != null) {
                    mergedProps.putAll(propHolder.getProperties());
                    if (propHolder.getFileTimestamp() > latestTimestamp)
                        latestTimestamp = propHolder.getFileTimestamp();
                }
            }
        }
        mergedHolder = new PropertiesHolder(mergedProps, latestTimestamp);
        PropertiesHolder existing = this.cachedMergedProperties.putIfAbsent(locale, mergedHolder);
        if (existing != null)
            mergedHolder = existing;
        return mergedHolder;
    }

    protected List<String> calculateAllFilenames(String basename, Locale locale) {
        Map<Locale, List<String>> localeMap = this.cachedFilenames.get(basename);
        if (localeMap != null) {
            List<String> list = localeMap.get(locale);
            if (list != null)
                return list;
        }
        List<String> filenames = new ArrayList<>(7);
        filenames.addAll(calculateFilenamesForLocale(basename, locale));
        Locale defaultLocale = getDefaultLocale();
        if (defaultLocale != null && !defaultLocale.equals(locale)) {
            List<String> fallbackFilenames = calculateFilenamesForLocale(basename, defaultLocale);
            for (String fallbackFilename : fallbackFilenames) {
                if (!filenames.contains(fallbackFilename))
                    filenames.add(fallbackFilename);
            }
        }
        filenames.add(basename);
        if (localeMap == null) {
            localeMap = new ConcurrentHashMap<>();
            Map<Locale, List<String>> existing = this.cachedFilenames.putIfAbsent(basename, localeMap);
            if (existing != null)
                localeMap = existing;
        }
        localeMap.put(locale, filenames);
        return filenames;
    }

    protected List<String> calculateFilenamesForLocale(String basename, Locale locale) {
        List<String> result = new ArrayList<>(3);
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        StringBuilder temp = new StringBuilder(basename);
        temp.append('_');
        if (language.length() > 0) {
            temp.append(language);
            result.add(0, temp.toString());
        }
        temp.append('_');
        if (country.length() > 0) {
            temp.append(country);
            result.add(0, temp.toString());
        }
        if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
            temp.append('_').append(variant);
            result.add(0, temp.toString());
        }
        return result;
    }

    protected PropertiesHolder getProperties(String filename) {
        PropertiesHolder propHolder = this.cachedProperties.get(filename);
        long originalTimestamp = -2L;
        if (propHolder != null) {
            originalTimestamp = propHolder.getRefreshTimestamp();
            if (originalTimestamp == -1L || originalTimestamp >
                    System.currentTimeMillis() - getCacheMillis()) {
                return propHolder;
            }
        } else {
            propHolder = new PropertiesHolder();
            PropertiesHolder existingHolder = this.cachedProperties.putIfAbsent(filename, propHolder);
            if (existingHolder != null) {
                propHolder = existingHolder;
            }
        }
        try {
            if (this.concurrentRefresh && propHolder.getRefreshTimestamp() >= 0L) {
                if (!propHolder.refreshLock.tryLock()) {
                    return propHolder;
                }
            } else {
                propHolder.refreshLock.lock();
            }
            PropertiesHolder existingHolder = this.cachedProperties.get(filename);
            if (existingHolder != null && existingHolder
                    .getRefreshTimestamp() > originalTimestamp) {
                return existingHolder;
            }
            return refreshProperties(filename, propHolder);
        } finally {
            propHolder.refreshLock.unlock();
        }
    }

    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        long refreshTimestamp = (getCacheMillis() < 0L) ? -1L : System.currentTimeMillis();
        Resource[] resourceList = this.resolver.getResources(filename + ".properties");
        for (Resource resource : resourceList) {
            if (resource.exists()) {
                long fileTimestamp = -1L;
                if (getCacheMillis() >= 0L)
                    try {
                        fileTimestamp = resource.lastModified();
                        if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
                            if (this.logger.isDebugEnabled())
                                this.logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
                            propHolder.setRefreshTimestamp(refreshTimestamp);
                            return propHolder;
                        }
                    } catch (IOException ex) {
                        if (this.logger.isDebugEnabled())
                            this.logger.debug(resource + " could not be resolved in the file system - assuming that it hasn't changed", ex);
                        fileTimestamp = -1L;
                    }
                try {
                    Properties props = loadProperties(resource, filename);
                    propHolder = new PropertiesHolder(props, fileTimestamp);
                } catch (IOException ex) {
                    if (this.logger.isWarnEnabled()) {
                        this.logger.warn("Could not parse properties file [" + resource
                                .getFilename() + "]", ex);
                    }
                    propHolder = new PropertiesHolder();
                }
            } else {
                if (this.logger.isDebugEnabled())
                    this.logger.debug("No properties file found for [" + filename + "] - neither plain properties nor XML");
                propHolder = new PropertiesHolder();
            }
            propHolder.setRefreshTimestamp(refreshTimestamp);
            PropertiesHolder propertiesHolder = this.cachedProperties.get(filename);
            if (propertiesHolder.properties != null) {
                propertiesHolder.properties.putAll(propHolder.getProperties());
            } else {
                this.cachedProperties.put(filename, propHolder);
            }
        }
        return this.cachedProperties.get(filename);
    }

    protected Properties loadProperties(Resource resource, String filename) throws IOException {
        Properties props = newProperties();
        try (InputStream is = resource.getInputStream()) {
            String resourceFilename = resource.getFilename();
            if (resourceFilename != null && resourceFilename.endsWith(".xml")) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Loading properties [" + resource.getFilename() + "]");
                }
                this.propertiesPersister.loadFromXml(props, is);
            } else {
                String encoding = null;
                if (this.fileEncodings != null) {
                    encoding = this.fileEncodings.getProperty(filename);
                }
                if (encoding == null) {
                    encoding = getDefaultEncoding();
                }
                if (encoding != null) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Loading properties [" + resource
                                .getFilename() + "] with encoding '" + encoding + "'");
                    }
                    this.propertiesPersister.load(props, new InputStreamReader(is, encoding));
                } else {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Loading properties [" + resource.getFilename() + "]");
                    }
                    this.propertiesPersister.load(props, is);
                }
            }
            return props;
        }
    }

    protected Properties newProperties() {
        return new Properties();
    }

    public void clearCache() {
        this.logger.debug("Clearing entire resource bundle cache");
        this.cachedProperties.clear();
        this.cachedMergedProperties.clear();
    }

    public void clearCacheIncludingAncestors() {
        clearCache();
        if (getParentMessageSource() instanceof CustomMessageSource)
            ((CustomMessageSource) getParentMessageSource()).clearCacheIncludingAncestors();
    }

    public String toString() {
        return getClass().getName() + ": basenames=" + getBasenameSet();
    }

    protected class PropertiesHolder {

        private final Properties properties;

        private final long fileTimestamp;
        private final ReentrantLock refreshLock = new ReentrantLock();
        private final ConcurrentMap<String, Map<Locale, MessageFormat>> cachedMessageFormats = new ConcurrentHashMap<>();
        private volatile long refreshTimestamp = -2L;

        public PropertiesHolder() {
            this.properties = null;
            this.fileTimestamp = -1L;
        }

        public PropertiesHolder(Properties properties, long fileTimestamp) {
            this.properties = properties;
            this.fileTimestamp = fileTimestamp;
        }

        public Properties getProperties() {
            return this.properties;
        }

        public long getFileTimestamp() {
            return this.fileTimestamp;
        }

        public long getRefreshTimestamp() {
            return this.refreshTimestamp;
        }

        public void setRefreshTimestamp(long refreshTimestamp) {
            this.refreshTimestamp = refreshTimestamp;
        }

        public String getProperty(String code) {
            if (this.properties == null) {
                return null;
            }
            return this.properties.getProperty(code);
        }

        public MessageFormat getMessageFormat(String code, Locale locale) {
            if (this.properties == null) {
                return null;
            }
            Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats.get(code);
            if (localeMap != null) {
                MessageFormat result = localeMap.get(locale);
                if (result != null) {
                    return result;
                }
            }
            String msg = this.properties.getProperty(code);
            if (msg != null) {
                if (localeMap == null) {
                    localeMap = new ConcurrentHashMap<>();
                    Map<Locale, MessageFormat> existing = this.cachedMessageFormats.putIfAbsent(code, localeMap);
                    if (existing != null) {
                        localeMap = existing;
                    }
                }
                MessageFormat result = CustomMessageSource.this.createMessageFormat(msg, locale);
                localeMap.put(locale, result);
                return result;
            }
            return null;
        }
    }
}
