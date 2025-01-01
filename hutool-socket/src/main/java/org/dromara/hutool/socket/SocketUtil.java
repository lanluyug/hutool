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

package org.dromara.hutool.socket;

import org.dromara.hutool.core.io.IORuntimeException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;

/**
 * Socket相关工具类
 *
 * @author Looly
 * @since 4.5.0
 */
public class SocketUtil {

	/**
	 * 获取远程端的地址信息，包括host和端口<br>
	 * null表示channel为null或者远程主机未连接
	 *
	 * @param channel {@link AsynchronousSocketChannel}
	 * @return 远程端的地址信息，包括host和端口，null表示channel为null或者远程主机未连接
	 * @throws IORuntimeException IO异常
	 */
	public static SocketAddress getRemoteAddress(final AsynchronousSocketChannel channel) throws IORuntimeException {
		try {
			return (null == channel) ? null : channel.getRemoteAddress();
		} catch (final ClosedChannelException e) {
			// Channel未打开或已关闭，返回null表示未连接
			return null;
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * 远程主机是否处于连接状态<br>
	 * 通过判断远程地址获取成功与否判断
	 *
	 * @param channel {@link AsynchronousSocketChannel}
	 * @return 远程主机是否处于连接状态
	 * @throws IORuntimeException IO异常
	 */
	public static boolean isConnected(final AsynchronousSocketChannel channel) throws IORuntimeException{
		return null != getRemoteAddress(channel);
	}

	/**
	 * 创建Socket并连接到指定地址的服务器
	 *
	 * @param hostname 地址
	 * @param port     端口
	 * @return {@link Socket}
	 * @throws IORuntimeException IO异常
	 * @since 5.7.8
	 */
	public static Socket connect(final String hostname, final int port) throws IORuntimeException {
		return connect(hostname, port, -1);
	}

	/**
	 * 创建Socket并连接到指定地址的服务器
	 *
	 * @param hostname          地址
	 * @param port              端口
	 * @param connectionTimeout 连接超时
	 * @return {@link Socket}
	 * @throws IORuntimeException IO异常
	 * @since 5.7.8
	 */
	public static Socket connect(final String hostname, final int port, final int connectionTimeout) throws IORuntimeException {
		return connect(new InetSocketAddress(hostname, port), connectionTimeout);
	}

	/**
	 * 创建Socket并连接到指定地址的服务器
	 *
	 * @param address           地址
	 * @param connectionTimeout 连接超时
	 * @return {@link Socket}
	 * @throws IORuntimeException IO异常
	 * @since 5.7.8
	 */
	public static Socket connect(final InetSocketAddress address, final int connectionTimeout) throws IORuntimeException {
		final Socket socket = new Socket();
		try {
			if (connectionTimeout <= 0) {
				socket.connect(address);
			} else {
				socket.connect(address, connectionTimeout);
			}
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
		return socket;
	}
}
