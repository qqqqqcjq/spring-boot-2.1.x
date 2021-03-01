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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

/**
 * Import and apply the specified auto-configuration classes. Applies the same ordering
 * rules as {@code @EnableAutoConfiguration} but restricts the auto-configuration classes
 * to the specified set, rather than consulting {@code spring.factories}.
 * <p>
 * Can also be used to {@link #exclude()} specific auto-configuration classes such that
 * they will never be applied.
 * <p>
 * Generally, {@code @EnableAutoConfiguration} should be used in preference to this
 * annotation, however, {@code @ImportAutoConfiguration} can be useful in some situations
 * and especially when writing tests.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.3.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ImportAutoConfigurationImportSelector.class)

// 我们可以使用@ImportAutoConfiguration注解手工导入和应用指定的自动配置类。比如我们可以重写自动配置类，然后使用@ImportAutoConfiguration导入。这个注解也可以用于排除指定的自动配置类。
// @ImportAutoConfiguration和@EnableAutoConfiguration注解的功能相同的地方很多，一般来说，应该优先使用@EnableAutoConfiguration，但是，{@code
// @ImportAutoConfiguration}在某些情况下可能很有用，特别是在编写测试时。

// 使用实例如下 ：
// @ImportAutoConfiguration({WebMvcAutoConfiguration.class
// , DispatcherServletAutoConfiguration.class
// , EmbeddedServletContainerAutoConfiguration.class
// , ServerPropertiesAutoConfiguration.class
// , HttpMessageConvertersAutoConfiguration.class})
public @interface ImportAutoConfiguration {

	/**
	 * The auto-configuration classes that should be imported. This is an alias for
	 * {@link #classes()}.
	 * @return the classes to import
	 */
	@AliasFor("classes")
	// 应该导入的自动配置类
	Class<?>[] value() default {};

	/**
	 * The auto-configuration classes that should be imported. When empty, the classes are
	 * specified using an entry in {@code META-INF/spring.factories} where the key is the
	 * fully-qualified name of the annotated class.
	 * @return the classes to import
	 */
	@AliasFor("value")
	// 应该导入的自动配置类
	Class<?>[] classes() default {};

	/**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
	// 排除特定的自动配置类，使它们永远不会被应用。
	Class<?>[] exclude() default {};

}
