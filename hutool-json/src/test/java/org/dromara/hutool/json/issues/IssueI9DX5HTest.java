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

package org.dromara.hutool.json.issues;

import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.json.JSONConfig;
import org.dromara.hutool.json.JSONFactory;
import org.dromara.hutool.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueI9DX5HTest {

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	@Test
	void xmlToJSONTest() {
		final String xml = "<GoodMsg>你好</GoodMsg>";
		final JSONFactory factory = JSONFactory.of(JSONConfig.of(), entry -> {
			entry.setKey(StrUtil.toUnderlineCase((CharSequence) entry.getKey()));
			return true;
		});
		final JSONObject jsonObject = factory.parseObj(xml);

		Assertions.assertEquals("{\"good_msg\":\"你好\"}", jsonObject.toString());
	}
}
