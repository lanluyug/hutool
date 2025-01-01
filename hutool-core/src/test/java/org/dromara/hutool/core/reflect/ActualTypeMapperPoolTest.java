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

package org.dromara.hutool.core.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 见：<a href="https://gitee.com/dromara/hutool/pulls/447/files">https://gitee.com/dromara/hutool/pulls/447/files</a>
 * <p>
 * TODO 同时继承泛型和实现泛型接口需要解析，此处为F
 */
public class ActualTypeMapperPoolTest {

	@Test
	public void getTypeArgumentTest(){
		final Map<Type, Type> typeTypeMap = ActualTypeMapperPool.get(FinalClass.class);
		typeTypeMap.forEach((key, value)->{
			if("A".equals(key.getTypeName())){
				Assertions.assertEquals(Character.class, value);
			} else if("B".equals(key.getTypeName())){
				Assertions.assertEquals(Boolean.class, value);
			} else if("C".equals(key.getTypeName())){
				Assertions.assertEquals(String.class, value);
			} else if("D".equals(key.getTypeName())){
				Assertions.assertEquals(Double.class, value);
			} else if("E".equals(key.getTypeName())){
				Assertions.assertEquals(Integer.class, value);
			}
		});
	}

	@Test
	public void getTypeArgumentStrKeyTest(){
		final Map<String, Type> typeTypeMap = ActualTypeMapperPool.getStrKeyMap(FinalClass.class);
		typeTypeMap.forEach((key, value)->{
			if("A".equals(key)){
				Assertions.assertEquals(Character.class, value);
			} else if("B".equals(key)){
				Assertions.assertEquals(Boolean.class, value);
			} else if("C".equals(key)){
				Assertions.assertEquals(String.class, value);
			} else if("D".equals(key)){
				Assertions.assertEquals(Double.class, value);
			} else if("E".equals(key)){
				Assertions.assertEquals(Integer.class, value);
			}
		});
	}

	public interface BaseInterface<A, B, C> {}
	public interface FirstInterface<A, B, D, E> extends BaseInterface<A, B, String> {}
	public interface SecondInterface<A, B, F> extends BaseInterface<A, B, String> {}

	public static class BaseClass<A, D> implements FirstInterface<A, Boolean, D, Integer> {}
	public static class FirstClass extends BaseClass<Character, Double> implements SecondInterface<Character, Boolean, FirstClass> {}
	public static class SecondClass extends FirstClass {}
	public static class FinalClass extends SecondClass {}
}
