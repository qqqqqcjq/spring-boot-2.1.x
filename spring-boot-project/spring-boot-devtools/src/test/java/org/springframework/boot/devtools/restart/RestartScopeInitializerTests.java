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

package org.springframework.boot.devtools.restart;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RestartScopeInitializer}.
 *
 * @author Phillip Webb
 */
public class RestartScopeInitializerTests {

	private static AtomicInteger createCount;

	private static AtomicInteger refreshCount;

	@Test
	public void restartScope() {
		createCount = new AtomicInteger();
		refreshCount = new AtomicInteger();
		ConfigurableApplicationContext context = runApplication();
		context.close();
		context = runApplication();
		context.close();
		assertThat(createCount.get()).isEqualTo(1);
		assertThat(refreshCount.get()).isEqualTo(2);
	}

	private ConfigurableApplicationContext runApplication() {
		SpringApplication application = new SpringApplication(Config.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		return application.run();
	}

	@Configuration
	public static class Config {

		@Bean
		@RestartScope
		public ScopeTestBean scopeTestBean() {
			return new ScopeTestBean();
		}

	}

	public static class ScopeTestBean implements ApplicationListener<ContextRefreshedEvent> {

		public ScopeTestBean() {
			createCount.incrementAndGet();
		}

		@Override
		public void onApplicationEvent(ContextRefreshedEvent event) {
			refreshCount.incrementAndGet();
		}

	}

}
