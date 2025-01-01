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

package org.dromara.hutool.core.convert;

import org.dromara.hutool.core.map.BiMap;

import java.util.HashMap;
import java.util.Set;

/**
 * 基本变量类型的枚举<br>
 * 基本类型枚举包括原始类型和包装类型
 *
 * @author Looly
 */
public enum BasicType {
	/**
	 * byte
	 */
	BYTE,
	/**
	 * short
	 */
	SHORT,
	/**
	 * int
	 */
	INT,
	/**
	 * {@link Integer}
	 */
	INTEGER,
	/**
	 * long
	 */
	LONG,
	/**
	 * double
	 */
	DOUBLE,
	/**
	 * float
	 */
	FLOAT,
	/**
	 * boolean
	 */
	BOOLEAN,
	/**
	 * char
	 */
	CHAR,
	/**
	 * {@link Character}
	 */
	CHARACTER,
	/**
	 * {@link String}
	 */
	STRING;

	/**
	 * 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class.
	 */
	private static final BiMap<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP = new BiMap<>(new HashMap<>(8, 1));

	static {
		WRAPPER_PRIMITIVE_MAP.put(Boolean.class, boolean.class);
		WRAPPER_PRIMITIVE_MAP.put(Byte.class, byte.class);
		WRAPPER_PRIMITIVE_MAP.put(Character.class, char.class);
		WRAPPER_PRIMITIVE_MAP.put(Double.class, double.class);
		WRAPPER_PRIMITIVE_MAP.put(Float.class, float.class);
		WRAPPER_PRIMITIVE_MAP.put(Integer.class, int.class);
		WRAPPER_PRIMITIVE_MAP.put(Long.class, long.class);
		WRAPPER_PRIMITIVE_MAP.put(Short.class, short.class);
	}

	/**
	 * 原始类转为包装类，非原始类返回原类
	 *
	 * @param clazz 原始类
	 * @return 包装类
	 */
	public static Class<?> wrap(final Class<?> clazz) {
		return wrap(clazz, false);
	}

	/**
	 * 原始类转为包装类，非原始类返回原类
	 *
	 * @param clazz           原始类
	 * @param errorReturnNull 如果没有对应类的原始类型，是否返回{@code null}，{@code true}返回{@code null}，否则返回原class
	 * @return 包装类
	 */
	public static Class<?> wrap(final Class<?> clazz, final boolean errorReturnNull) {
		if (null == clazz || !clazz.isPrimitive()) {
			return clazz;
		}
		final Class<?> result = WRAPPER_PRIMITIVE_MAP.getInverse().get(clazz);
		return (null == result) ? errorReturnNull ? null : clazz : result;
	}

	/**
	 * 包装类转为原始类，非包装类返回原类
	 *
	 * @param clazz 包装类
	 * @return 原始类
	 */
	public static Class<?> unWrap(final Class<?> clazz) {
		if (null == clazz || clazz.isPrimitive()) {
			return clazz;
		}
		final Class<?> result = WRAPPER_PRIMITIVE_MAP.get(clazz);
		return (null == result) ? clazz : result;
	}

	/**
	 * 是否为包装类型
	 *
	 * @param clazz 类
	 * @return 是否为包装类型
	 */
	public static boolean isPrimitiveWrapper(final Class<?> clazz) {
		if (null == clazz) {
			return false;
		}
		return WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
	}

	/**
	 * 获取所有原始类型
	 *
	 * @return 所有原始类型
	 */
	public static Set<Class<?>> getPrimitiveSet() {
		return WRAPPER_PRIMITIVE_MAP.getInverse().keySet();
	}

	/**
	 * 获取所有原始类型
	 *
	 * @return 所有原始类型
	 */
	public static Set<Class<?>> getWrapperSet() {
		return WRAPPER_PRIMITIVE_MAP.keySet();
	}
}
