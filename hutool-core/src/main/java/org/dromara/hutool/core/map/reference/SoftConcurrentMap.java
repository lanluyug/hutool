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

package org.dromara.hutool.core.map.reference;

import org.dromara.hutool.core.lang.ref.Ref;
import org.dromara.hutool.core.lang.ref.SoftObj;
import org.dromara.hutool.core.map.concurrent.SafeConcurrentHashMap;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * 线程安全的SoftMap实现<br>
 * 键和值都为Soft引用，即，在GC报告内存不足时会被GC回收
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Looly
 * @since 6.0.0
 */
public class SoftConcurrentMap<K, V> extends ReferenceConcurrentMap<K, V> {
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 */
	public SoftConcurrentMap() {
		this(new SafeConcurrentHashMap<>());
	}

	/**
	 * 构造
	 *
	 * @param raw {@link ConcurrentMap}实现
	 */
	public SoftConcurrentMap(final ConcurrentMap<Ref<K>, Ref<V>> raw) {
		super(raw);
	}

	@Override
	Ref<K> wrapKey(final K key, final ReferenceQueue<? super K> queue) {
		return new SoftObj<>(key, queue);
	}

	@Override
	Ref<V> wrapValue(final V value, final ReferenceQueue<? super V> queue) {
		return new SoftObj<>(value, queue);
	}
}
