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

package org.dromara.hutool.core.text.split;

import org.dromara.hutool.core.text.finder.CharFinder;
import org.dromara.hutool.core.text.finder.LengthFinder;
import org.dromara.hutool.core.text.finder.PatternFinder;
import org.dromara.hutool.core.text.finder.StrFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

public class SplitIterTest {

	@Test
	public void splitByCharTest(){
		final String str1 = "a, ,,efedsfs,   ddf,";

		//不忽略""
		final SplitIter splitIter = new SplitIter(str1,
				new CharFinder(',', false),
				Integer.MAX_VALUE,
				false
		);
		Assertions.assertEquals(6, splitIter.toList(false).size());
	}

	@Test
	public void splitByCharIgnoreCaseTest(){
		final String str1 = "a, ,,eAedsas,   ddf,";

		//不忽略""
		final SplitIter splitIter = new SplitIter(str1,
				new CharFinder('a', true),
				Integer.MAX_VALUE,
				false
		);
		Assertions.assertEquals(4, splitIter.toList(false).size());
	}

	@Test
	public void splitByCharIgnoreEmptyTest(){
		final String str1 = "a, ,,efedsfs,   ddf,";

		final SplitIter splitIter = new SplitIter(str1,
				new CharFinder(',', false),
				Integer.MAX_VALUE,
				true
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(4, strings.size());
	}

	@Test
	public void splitByCharTrimTest(){
		final String str1 = "a, ,,efedsfs,   ddf,";

		final SplitIter splitIter = new SplitIter(str1,
				new CharFinder(',', false),
				Integer.MAX_VALUE,
				true
		);

		final List<String> strings = splitIter.toList(true);
		Assertions.assertEquals(3, strings.size());
		Assertions.assertEquals("a", strings.get(0));
		Assertions.assertEquals("efedsfs", strings.get(1));
		Assertions.assertEquals("ddf", strings.get(2));
	}

	@Test
	public void splitByStrTest(){
		final String str1 = "a, ,,efedsfs,   ddf,";

		final SplitIter splitIter = new SplitIter(str1,
				StrFinder.of("e", false),
				Integer.MAX_VALUE,
				true
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(3, strings.size());
	}

	@Test
	public void splitByPatternTest(){
		final String str1 = "a, ,,efedsfs,   ddf,";

		final SplitIter splitIter = new SplitIter(str1,
				new PatternFinder(Pattern.compile("\\s")),
				Integer.MAX_VALUE,
				true
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(3, strings.size());
	}

	@Test
	public void splitByLengthTest(){
		final String text = "1234123412341234";
		final SplitIter splitIter = new SplitIter(text,
				new LengthFinder(4),
				Integer.MAX_VALUE,
				false
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(4, strings.size());
	}

	@Test
	public void splitLimitTest(){
		final String text = "55:02:18";
		final SplitIter splitIter = new SplitIter(text,
				new CharFinder(':'),
				3,
				false
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(3, strings.size());
	}

	@Test
	public void splitToSingleTest(){
		final String text = "";
		final SplitIter splitIter = new SplitIter(text,
				new CharFinder(':'),
				3,
				false
		);

		final List<String> strings = splitIter.toList(false);
		Assertions.assertEquals(1, strings.size());
	}

	// 切割字符串是空字符串时报错
	@Test
	public void splitByEmptyTest(){
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			final String text = "aa,bb,cc";
			final SplitIter splitIter = new SplitIter(text,
				StrFinder.of("", false),
				3,
				false
			);

			final List<String> strings = splitIter.toList(false);
			Assertions.assertEquals(1, strings.size());
		});
	}
}
