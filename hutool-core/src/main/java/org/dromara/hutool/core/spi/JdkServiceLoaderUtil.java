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

package org.dromara.hutool.core.spi;

import org.dromara.hutool.core.classloader.ClassLoaderUtil;
import org.dromara.hutool.core.collection.ListUtil;
import org.dromara.hutool.core.util.ObjUtil;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * SPI机制中的服务加载工具类，流程如下
 *
 * <pre>
 *     1、创建接口，并创建实现类
 *     2、ClassPath/META-INF/services下创建与接口全限定类名相同的文件
 *     3、文件内容填写实现类的全限定类名
 * </pre>
 * 相关介绍见：<a href="https://www.jianshu.com/p/3a3edbcd8f24">https://www.jianshu.com/p/3a3edbcd8f24</a>
 *
 * @author Looly
 * @since 5.1.6
 */
public class JdkServiceLoaderUtil {

	/**
	 * 。加载第一个可用服务，如果用户定义了多个接口实现类，只获取第一个不报错的服务
	 *
	 * @param <T>   接口类型
	 * @param clazz 服务接口
	 * @return 第一个服务接口实现对象，无实现返回{@code null}
	 */
	public static <T> T loadFirstAvailable(final Class<T> clazz) {
		final Iterator<T> iterator = load(clazz).iterator();
		while (iterator.hasNext()) {
			try {
				return iterator.next();
			} catch (final ServiceConfigurationError ignore) {
				// ignore
			}
		}
		return null;
	}

	/**
	 * 加载第一个服务，如果用户定义了多个接口实现类，只获取第一个。
	 *
	 * @param <T>   接口类型
	 * @param clazz 服务接口
	 * @return 第一个服务接口实现对象，无实现返回{@code null}
	 */
	public static <T> T loadFirst(final Class<T> clazz) {
		final Iterator<T> iterator = load(clazz).iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}

	/**
	 * 加载服务
	 *
	 * @param <T>   接口类型
	 * @param clazz 服务接口
	 * @return 服务接口实现列表
	 */
	public static <T> ServiceLoader<T> load(final Class<T> clazz) {
		return load(clazz, null);
	}

	/**
	 * 加载服务
	 *
	 * @param <T>    接口类型
	 * @param clazz  服务接口
	 * @param loader {@link ClassLoader}
	 * @return 服务接口实现列表
	 */
	public static <T> ServiceLoader<T> load(final Class<T> clazz, final ClassLoader loader) {
		return ServiceLoader.load(clazz, ObjUtil.defaultIfNull(loader, ClassLoaderUtil::getClassLoader));
	}

	/**
	 * 加载服务 并已list列表返回
	 *
	 * @param <T>   接口类型
	 * @param clazz 服务接口
	 * @return 服务接口实现列表
	 * @since 5.4.2
	 */
	public static <T> List<T> loadList(final Class<T> clazz) {
		return loadList(clazz, null);
	}

	/**
	 * 加载服务 并已list列表返回
	 *
	 * @param <T>    接口类型
	 * @param clazz  服务接口
	 * @param loader {@link ClassLoader}
	 * @return 服务接口实现列表
	 * @since 5.4.2
	 */
	public static <T> List<T> loadList(final Class<T> clazz, final ClassLoader loader) {
		return ListUtil.of(false, load(clazz, loader));
	}
}
