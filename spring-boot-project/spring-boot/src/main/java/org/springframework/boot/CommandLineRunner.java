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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Interface used to indicate that a bean should <em>run</em> when it is contained within
 * a {@link SpringApplication}. Multiple {@link CommandLineRunner} beans can be defined
 * within the same application context and can be ordered using the {@link Ordered}
 * interface or {@link Order @Order} annotation.
 * <p>
 * If you need access to {@link ApplicationArguments} instead of the raw String array
 * consider using {@link ApplicationRunner}.
 *
 * @author Dave Syer
 * @since 1.0.0
 * @see ApplicationRunner
 */
// ApplicationRunner CommandLineRunner
// Spring Boot如何解决项目启动时初始化资源，在我们实际工作中，总会遇到这样需求，在项目启动的时候需要做一些初始化的操作，比如初始化线程池，获取加密数字证书等。
// 为了达到这个目的，我们需要使用CommandLineRunner或ApplicationRunner接口创建bean，spring
// boot会自动监测到它们。这两个接口都有一个run()方法，在实现接口时需要覆盖该方法，并使用@Component注解使其成为bean。

@FunctionalInterface
public interface CommandLineRunner {

	/**
	 * Callback used to run the bean.
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	void run(String... args) throws Exception;

}
