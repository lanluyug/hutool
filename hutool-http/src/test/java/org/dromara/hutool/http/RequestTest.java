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

package org.dromara.hutool.http;

import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.date.StopWatch;
import org.dromara.hutool.core.lang.Console;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.net.url.UrlBuilder;
import org.dromara.hutool.core.util.CharsetUtil;
import org.dromara.hutool.http.client.Request;
import org.dromara.hutool.http.client.Response;
import org.dromara.hutool.http.meta.HeaderName;
import org.dromara.hutool.http.meta.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Request}单元测试
 *
 * @author Looly
 */
@SuppressWarnings("resource")
public class RequestTest {
	final String url = "http://photo.qzone.qq.com/fcgi-bin/fcg_list_album?uin=88888&outstyle=2";

	@Test
	@Disabled
	public void getHttpsThenTest() {
		Request.of("https://hutool.cn")
				.send()
				.then(response -> Console.log(response.body()));
	}

	@Test
	@Disabled
	public void getCookiesTest() {
		// 检查在Connection关闭情况下Cookie是否可以正常获取
		final Response res = Request.of("https://www.oschina.net/").send();
		final String body = res.bodyStr();
		Console.log(res.getCookieStr());
		Console.log(body);
	}

	@Test
	@Disabled
	public void toStringTest() {
		final String url = "http://gc.ditu.aliyun.com/geocoding?ccc=你好";

		final Request request = Request.of(url).body("a=乌海");
		Console.log(request.toString());
	}

	@Test
	@Disabled
	public void asyncHeadTest() {
		final Response response = Request.of(url).method(Method.HEAD).send();
		final Map<String, List<String>> headers = response.headers();
		Console.log(headers);
		Console.log(response.body());
	}

	@Test
	@Disabled
	public void asyncGetTest() {
		final StopWatch timer = DateUtil.createStopWatch();
		timer.start();
		final Response body = Request.of(url).charset(CharsetUtil.GBK).send();
		timer.stop();
		final long interval = timer.getLastTaskTimeMillis();
		timer.start();
		Console.log(body.body());
		timer.stop();
		final long interval2 = timer.getLastTaskTimeMillis();
		Console.log("Async response spend {}ms, body spend {}ms", interval, interval2);
	}

	@Test
	@Disabled
	public void syncGetTest() {
		final StopWatch timer = DateUtil.createStopWatch();
		timer.start();
		final Response body = Request.of(url).charset(CharsetUtil.GBK).send();
		timer.stop();
		final long interval = timer.getLastTaskTimeMillis();

		timer.start();
		Console.log(body.body());
		timer.stop();
		final long interval2 = timer.getLastTaskTimeMillis();
		Console.log("Async response spend {}ms, body spend {}ms", interval, interval2);
	}

	@Test
	@Disabled
	public void getDeflateTest() {
		final Response res = Request.of("https://comment.bilibili.com/67573272.xml")
				.header(HeaderName.ACCEPT_ENCODING, "deflate")
				.send();
		Console.log(res.header(HeaderName.CONTENT_ENCODING));
		Console.log(res.body());
	}

	@Test
	@Disabled
	public void bodyTest() {
		final String ddddd1 = Request.of("https://baijiahao.baidu.com/s").body("id=1625528941695652600").send().bodyStr();
		Console.log(ddddd1);
	}

	/**
	 * 测试GET请求附带body体是否会变更为POST
	 */
	@Test
	@Disabled
	public void getLocalTest() {
		final List<String> list = new ArrayList<>();
		list.add("hhhhh");
		list.add("sssss");

		final Map<String, Object> map = new HashMap<>(16);
		map.put("recordId", "12321321");
		map.put("page", "1");
		map.put("size", "2");
		map.put("sizes", list);

		Request
				.of("http://localhost:8888/get")
				.form(map).send()
				.then(resp -> Console.log(resp.body()));
	}

	@Test
	@Disabled
	public void getWithoutEncodeTest() {
		final String url = "https://img-cloud.voc.com.cn/140/2020/09/03/c3d41b93e0d32138574af8e8b50928b376ca5ba61599127028157.png?imageMogr2/auto-orient/thumbnail/500&pid=259848";
		final Request get = Request.of(url);
		Console.log(get.url());
		final Response execute = get.send();
		Console.log(execute.body());
	}

	@Test
	@Disabled
	public void followRedirectsTest() {
		// 从5.7.19开始关闭JDK的自动重定向功能，改为手动重定向
		// 当有多层重定向时，JDK的重定向会失效，或者说只有最后一个重定向有效，因此改为手动更易控制次数
		// 此链接有两次重定向，当设置次数为1时，表示最多执行一次重定向，即请求2次
		final String url = "http://api.rosysun.cn/sjtx/?type=2";
//		String url = "https://api.btstu.cn/sjtx/api.php?lx=b1";

		// 方式1：全局设置
		HttpGlobalConfig.setMaxRedirects(1);
		Response execute = Request.of(url).send();
		Console.log(execute.getStatus(), execute.header(HeaderName.LOCATION));

		// 方式2，单独设置
		execute = Request.of(url).setMaxRedirects(1).send();
		Console.log(execute.getStatus(), execute.header(HeaderName.LOCATION));
	}

	@Test
	@Disabled
	public void getWithFormTest(){
		final String url = "https://postman-echo.com/get";
		final Map<String, Object> map = new HashMap<>();
		map.put("aaa", "application+1@qqq.com");
		final Request request =Request.of(url).form(map);
		Console.log(request.send().body());
	}

	@Test
	@Disabled
	public void urlWithParamIfGetTest(){
		final UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setScheme("https").setHost("hutool.cn");

		final Request httpRequest = Request.of(urlBuilder);
		httpRequest.method(Method.GET).send();
	}

	@Test
	@Disabled
	public void getCookieTest(){
		final Response execute = Request.of("http://localhost:8888/getCookier").send();
		Console.log(execute.getCookieStr());
	}

	@Test
	public void optionsTest() {
		final Request options = Request.of("https://hutool.cn").method(Method.OPTIONS);
		Assertions.assertNotNull(options.toString());
	}

	@Test
	public void deleteTest() {
		final Request options = Request.of("https://hutool.cn").method(Method.DELETE);
		Assertions.assertNotNull(options.toString());
	}

	@Test
	public void traceTest() {
		final Request options = Request.of("https://hutool.cn").method(Method.TRACE);
		Assertions.assertNotNull(options.toString());
	}

	@Test
	public void getToStringTest() {
		final Request a = Request.of("https://hutool.cn/").form(MapUtil.of("a", 1));
		Assertions.assertNotNull(a.toString());
	}

	@Test
	public void postToStringTest() {
		final Request a = Request.of("https://hutool.cn/").method(Method.POST).form(MapUtil.of("a", 1));
		Console.log(a.toString());
	}

	@Test
	@Disabled
	public void issueI5Y68WTest() {
		final Response httpResponse = Request.of("http://82.157.17.173:8100/app/getAddress").send();
		Console.log(httpResponse.body());
	}

	@Test
	void percentTest() {
		// 此处URL有歧义
		// 如果用户需要传的a的值为`%`，则这个URL表示已经编码过了，此时需要解码后再重新编码，保持不变
		Request request = Request.of("http://localhost:9999/a?a=%25", CharsetUtil.UTF_8);
		assertEquals("http://localhost:9999/a?a=%25", request.handledUrl().toURL().toString());

		// 不传charset，则保留原样，不做任何处理
		request = Request.of("http://localhost:9999/a?a=%25", null);
		assertEquals("http://localhost:9999/a?a=%25", request.handledUrl().toURL().toString());

		// 如果用户需要传的a的值为`%25`，则这个URL表示未编码，不需要解码，需要对`%`再次编码
		request = Request.of("http://localhost:9999/a?a=%25", null);
		request.setEncodeUrl(true);
		assertEquals("http://localhost:9999/a?a=%2525", request.handledUrl().toURL().toString());
	}
}
