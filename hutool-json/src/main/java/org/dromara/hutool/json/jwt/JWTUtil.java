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

package org.dromara.hutool.json.jwt;

import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.json.jwt.signers.JWTSigner;

import java.util.Map;

/**
 * JSON Web Token (JWT)工具类
 */
public class JWTUtil {

	/**
	 * 创建HS256(HmacSHA256) JWT Token
	 *
	 * @param payload 荷载信息
	 * @param key     HS256(HmacSHA256)密钥
	 * @return JWT Token
	 */
	public static String createToken(final Map<String, ?> payload, final byte[] key) {
		return createToken(MapUtil.of(JWTHeader.TYPE, "JWT"), payload, key);
	}

	/**
	 * 创建HS256(HmacSHA256) JWT Token
	 *
	 * @param headers 头信息
	 * @param payload 荷载信息
	 * @param key     HS256(HmacSHA256)密钥
	 * @return JWT Token
	 */
	public static String createToken(final Map<String, ?> headers, final Map<String, ?> payload, final byte[] key) {
		return JWT.of()
				.addHeaders(headers)
				.addPayloads(payload)
				.setKey(key)
				.sign();
	}

	/**
	 * 创建JWT Token
	 *
	 * @param payload 荷载信息
	 * @param signer  签名算法
	 * @return JWT Token
	 */
	public static String createToken(final Map<String, Object> payload, final JWTSigner signer) {
		return createToken(null, payload, signer);
	}

	/**
	 * 创建JWT Token
	 *
	 * @param headers 头信息
	 * @param payload 荷载信息
	 * @param signer  签名算法
	 * @return JWT Token
	 */
	public static String createToken(final Map<String, Object> headers, final Map<String, Object> payload, final JWTSigner signer) {
		return JWT.of()
				.addHeaders(headers)
				.addPayloads(payload)
				.setSigner(signer)
				.sign();
	}

	/**
	 * 解析JWT Token
	 *
	 * @param token token
	 * @return {@link JWT}
	 */
	public static JWT parseToken(final String token) {
		return JWT.of(token);
	}

	/**
	 * 验证JWT Token有效性
	 *
	 * @param token JWT Token
	 * @param key   HS256(HmacSHA256)密钥
	 * @return 是否有效
	 */
	public static boolean verify(final String token, final byte[] key) {
		return JWT.of(token).setKey(key).verify();
	}

	/**
	 * 验证JWT Token有效性
	 *
	 * @param token  JWT Token
	 * @param signer 签名器
	 * @return 是否有效
	 */
	public static boolean verify(final String token, final JWTSigner signer) {
		return JWT.of(token).verify(signer);
	}
}
