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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ReflectionUtils;

/**
 * A collection of {@link SpringApplicationRunListener}.
 *
 * @author Phillip Webb
 */
// SpringApplicationRunListener的集合
class SpringApplicationRunListeners {

	private final Log log;

	private final List<SpringApplicationRunListener> listeners;

	SpringApplicationRunListeners(Log log, Collection<? extends SpringApplicationRunListener> listeners) {
		this.log = log;
		this.listeners = new ArrayList<>(listeners);
	}

    // 发布事件1 ： ApplicationStartingEvent 应用开始启动
	public void starting() {
		for (SpringApplicationRunListener listener : this.listeners) {
            //广播应用启动事件
			listener.starting();
		}
	}

    // 发布事件2：ApplicationEnvironmentPreparedEvent 系统的环境enviroment已经准备好
	public void environmentPrepared(ConfigurableEnvironment environment) {
		for (SpringApplicationRunListener listener : this.listeners) {
		    //广播Enviroment已经准备好的事件(后续处理@PropertySource等也可以继续往里面添加相关属性)
			listener.environmentPrepared(environment);
		}
	}

    // 发布事件3 ：ApplicationContextInitializedEvent
    // ioc容器上下文初始化，这里说的初始化只是设置容器实例相关的初始化，比如设置系统环境等，并不是指完成扫描的实例化bean
	public void contextPrepared(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
		    //广播容器初始化事件
			listener.contextPrepared(context);
		}
	}

    // 发布事件4 ： ApplicationPreparedEvent ioc容器上下文已经准备好, 可以开始进行扫描bean
	public void contextLoaded(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
		    //广播ioc容器上下文已经准备好, 可以开始进行扫描bean事件
			listener.contextLoaded(context);
		}
	}

    // 发布事件5 ：ApplicationStartedEvent spring容器已经完成该做的所有事情, 应用已经启动成功
	public void started(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
		    //广播应用已经启动成功事件
			listener.started(context);
		}
	}

	public void running(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.running(context);
		}
	}

	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		for (SpringApplicationRunListener listener : this.listeners) {
			callFailedListener(listener, context, exception);
		}
	}

	private void callFailedListener(SpringApplicationRunListener listener, ConfigurableApplicationContext context,
			Throwable exception) {
		try {
			listener.failed(context, exception);
		}
		catch (Throwable ex) {
			if (exception == null) {
				ReflectionUtils.rethrowRuntimeException(ex);
			}
			if (this.log.isDebugEnabled()) {
				this.log.error("Error handling failed", ex);
			}
			else {
				String message = ex.getMessage();
				message = (message != null) ? message : "no error message";
				this.log.warn("Error handling failed (" + message + ")");
			}
		}
	}

}
