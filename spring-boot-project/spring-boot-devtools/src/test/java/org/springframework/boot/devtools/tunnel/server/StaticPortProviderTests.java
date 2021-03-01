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

package org.springframework.boot.devtools.tunnel.server;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link StaticPortProvider}.
 *
 * @author Phillip Webb
 */
public class StaticPortProviderTests {

	@Test
	public void portMustBePositive() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StaticPortProvider(0))
				.withMessageContaining("Port must be positive");
	}

	@Test
	public void getPort() {
		StaticPortProvider provider = new StaticPortProvider(123);
		assertThat(provider.getPort()).isEqualTo(123);
	}

}
