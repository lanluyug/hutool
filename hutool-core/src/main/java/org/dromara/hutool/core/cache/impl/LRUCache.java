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

package org.dromara.hutool.core.cache.impl;

import org.dromara.hutool.core.lang.mutable.Mutable;
import org.dromara.hutool.core.map.FixedLinkedHashMap;

import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LRU (least recently used)最近最久未使用缓存<br>
 * 根据使用时间来判定对象是否被持续缓存<br>
 * 当对象被访问时放入缓存，当缓存满了，最久未被使用的对象将被移除。<br>
 * 此缓存基于LinkedHashMap，因此当被缓存的对象每被访问一次，这个对象的key就到链表头部。<br>
 * 这个算法简单并且非常快，他比FIFO有一个显著优势是经常使用的对象不太可能被移除缓存。<br>
 * 缺点是当缓存满时，不能被很快的访问。
 * @author Looly,jodd
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class LRUCache<K, V> extends LockedCache<K, V> {
	private static final long serialVersionUID = 1L;

	/**
	 * 构造<br>
	 * 默认无超时
	 * @param capacity 容量
	 */
	public LRUCache(final int capacity) {
		this(capacity, 0);
	}

	/**
	 * 构造
	 * @param capacity 容量
	 * @param timeout 默认超时时间，单位：毫秒
	 */
	public LRUCache(int capacity, final long timeout) {
		if(Integer.MAX_VALUE == capacity) {
			capacity -= 1;
		}

		this.capacity = capacity;
		this.timeout = timeout;

		//链表key按照访问顺序排序，调用get方法后，会将这次访问的元素移至头部
		final FixedLinkedHashMap<Mutable<K>, CacheObj<K, V>> fixedLinkedHashMap = new FixedLinkedHashMap<>(capacity);
		fixedLinkedHashMap.setRemoveListener(entry -> {
			if(null != listener){
				listener.onRemove(entry.getKey().get(), entry.getValue().getValue());
			}
		});
		lock = new ReentrantLock();
		cacheMap = fixedLinkedHashMap;
	}

	// ---------------------------------------------------------------- prune

	/**
	 * 只清理超时对象，LRU的实现会交给{@code LinkedHashMap}
	 */
	@Override
	protected int pruneCache() {
		if (isPruneExpiredActive() == false) {
			return 0;
		}
		int count = 0;
		final Iterator<CacheObj<K, V>> values = cacheObjIter();
		CacheObj<K, V> co;
		while (values.hasNext()) {
			co = values.next();
			if (co.isExpired()) {
				values.remove();
				onRemove(co.key, co.obj);
				count++;
			}
		}
		return count;
	}
}
