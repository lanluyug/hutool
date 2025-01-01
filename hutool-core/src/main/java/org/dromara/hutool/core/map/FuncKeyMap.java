/*
 * Copyright (c) 2013-2025 Hutool Team and hutool.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hutool.core.map;

import java.util.Map;
import java.util.function.Function;

/**
 * 自定义函数Key风格的Map
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Looly
 * @since 5.6.0
 */
public class FuncKeyMap<K, V> extends CustomKeyMap<K, V> {
	private static final long serialVersionUID = 1L;

	private final Function<Object, K> keyFunc;

	// ------------------------------------------------------------------------- Constructor start

	/**
	 * 构造<br>
	 * 注意提供的Map中不能有键值对，否则可能导致自定义key失效
	 *
	 * @param emptyMap       Map，提供的空map
	 * @param keyFunc 自定义KEY的函数
	 */
	public FuncKeyMap(final Map<K, V> emptyMap, final Function<Object, K> keyFunc) {
		super(emptyMap);
		this.keyFunc = keyFunc;
	}
	// ------------------------------------------------------------------------- Constructor end

	/**
	 * 根据函数自定义键
	 *
	 * @param key KEY
	 * @return 驼峰Key
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected K customKey(final Object key) {
		if (null != this.keyFunc) {
			return keyFunc.apply(key);
		}
		return (K)key;
	}
}
