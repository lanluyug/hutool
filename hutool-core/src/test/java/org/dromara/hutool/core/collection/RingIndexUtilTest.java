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

package org.dromara.hutool.core.collection;

import org.dromara.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 集合索引环形获取工具类测试类
 *
 * @author ZhouChuGang
 */
public class RingIndexUtilTest {

	private final List<String> strList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

	/**
	 * 观察输出的打印为不重复的
	 */
	@Test
	public void ringNextIntByObjTest() {
		final AtomicInteger atomicInteger = new AtomicInteger();
		// 开启并发测试，每个线程获取到的元素都是唯一的
		ThreadUtil.concurrencyTest(strList.size(), () -> {
			final int index = RingIndexUtil.ringNextIntByObj(strList, atomicInteger);
			final String s = strList.get(index);
			Assertions.assertNotNull(s);
		});
	}

}
