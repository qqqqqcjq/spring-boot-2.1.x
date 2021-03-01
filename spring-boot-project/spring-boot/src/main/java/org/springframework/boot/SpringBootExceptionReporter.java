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

package org.springframework.boot;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * Callback interface used to support custom reporting of {@link SpringApplication}
 * startup errors. {@link SpringBootExceptionReporter reporters} are loaded via the
 * {@link SpringFactoriesLoader} and must declare a public constructor with a single
 * {@link ConfigurableApplicationContext} parameter.
 *
 * @author Phillip Webb
 * @since 2.0.0
 * @see ApplicationContextAware
 */
@FunctionalInterface
// 用户实现这个接口， 实现类要求必须有一个带有ConfigurableApplicationContext参数的构造函数
// spring使用SpringFactoriesLoader来获取用户实现子类(子类需要配置在一个spring.factories文件中)，
// 在springapplication启动的过程中有错误的话就使用这些实现类报告错误
// spi编程思想和api编程思想一样随处可见
public interface SpringBootExceptionReporter {

	/**
	 * Report a startup failure to the user.
	 * @param failure the source failure
	 * @return {@code true} if the failure was reported or {@code false} if default
	 * reporting should occur.
	 */
	boolean reportException(Throwable failure);

}
