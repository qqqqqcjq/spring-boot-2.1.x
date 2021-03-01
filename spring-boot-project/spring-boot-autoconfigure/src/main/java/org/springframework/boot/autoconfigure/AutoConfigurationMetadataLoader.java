/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

/**
 * Internal utility used to load {@link AutoConfigurationMetadata}.
 *
 * @author Phillip Webb
 */
// 用于加载{@link AutoConfigurationMetadata}的内部工具
final class AutoConfigurationMetadataLoader {

    // spring boot编译的时候会使用注解处理器AutoConfigureAnnotationProcessor,收集OnBeanCondition/OnClassCondition/OnWebApplicationCondition类型的注解
    // 然后在类路径下生成一个META-INF/spring-autoconfigure-metadata.properties文件，保存这些信息，
    // 在AutoConfigurationImportSelector#selectImports
    //  ==>AutoConfigurationImportSelector#getAutoConfigurationEntry
    //  ==>AutoConfigurationImportSelector#filter
    //  ==>AutoConfigurationImportFilter#match这个流程里判断是否引入这个bean，
    // 而不必等到执行ConditionEvaluator.shouldSkip()再判断是否引入这个bean。
    //
    // 可以改善应用的启动时间。

        // 要使用这个功能注意要在pom文件中引入下面的依赖
    // <dependency>
    // <groupId>org.springframework.boot</groupId>
    // <artifactId>spring-boot-autoconfigure-processor</artifactId>
    // <optional>true</optional>
    // </dependency>
	protected static final String PATH = "META-INF/spring-autoconfigure-metadata.properties";

	private AutoConfigurationMetadataLoader() {
	}

	public static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
		return loadMetadata(classLoader, PATH);
	}

	static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
		try {
			Enumeration<URL> urls = (classLoader != null) ? classLoader.getResources(path)
					: ClassLoader.getSystemResources(path);
			Properties properties = new Properties();
			while (urls.hasMoreElements()) {
				properties.putAll(PropertiesLoaderUtils.loadProperties(new UrlResource(urls.nextElement())));
			}
			return loadMetadata(properties);
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load @ConditionalOnClass location [" + path + "]", ex);
		}
	}

	static AutoConfigurationMetadata loadMetadata(Properties properties) {
		return new PropertiesAutoConfigurationMetadata(properties);
	}

	/**
	 * {@link AutoConfigurationMetadata} implementation backed by a properties file.
	 */
	// 从META-INF/spring-autoconfigure-metadata.properties加载的信息会保存在AutoConfigurationMetadata对象中
	// AutoConfigurationMetadata只有一个实现类，就是PropertiesAutoConfigurationMetadata
	private static class PropertiesAutoConfigurationMetadata implements AutoConfigurationMetadata {

		private final Properties properties;

		PropertiesAutoConfigurationMetadata(Properties properties) {
			this.properties = properties;
		}

		@Override
		public boolean wasProcessed(String className) {
			return this.properties.containsKey(className);
		}

		@Override
		public Integer getInteger(String className, String key) {
			return getInteger(className, key, null);
		}

		@Override
		public Integer getInteger(String className, String key, Integer defaultValue) {
			String value = get(className, key);
			return (value != null) ? Integer.valueOf(value) : defaultValue;
		}

		@Override
		public Set<String> getSet(String className, String key) {
			return getSet(className, key, null);
		}

		@Override
		public Set<String> getSet(String className, String key, Set<String> defaultValue) {
			String value = get(className, key);
			return (value != null) ? StringUtils.commaDelimitedListToSet(value) : defaultValue;
		}

		@Override
		public String get(String className, String key) {
			return get(className, key, null);
		}

		@Override
		public String get(String className, String key, String defaultValue) {
			String value = this.properties.getProperty(className + "." + key);
			return (value != null) ? value : defaultValue;
		}

	}

}
