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

package org.dromara.hutool.core.io.copy;

import org.dromara.hutool.core.io.IORuntimeException;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.io.StreamProgress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * {@link ReadableByteChannel} 向 {@link WritableByteChannel} 拷贝
 *
 * @author Looly
 * @since 5.7.8
 */
public class ChannelCopier extends IoCopier<ReadableByteChannel, WritableByteChannel> {

	/**
	 * 构造
	 */
	public ChannelCopier() {
		this(IoUtil.DEFAULT_BUFFER_SIZE);
	}

	/**
	 * 构造
	 *
	 * @param bufferSize 缓存大小
	 */
	public ChannelCopier(final int bufferSize) {
		this(bufferSize, -1);
	}

	/**
	 * 构造
	 *
	 * @param bufferSize 缓存大小
	 * @param count      拷贝总数
	 */
	public ChannelCopier(final int bufferSize, final long count) {
		this(bufferSize, count, null);
	}

	/**
	 * 构造
	 *
	 * @param bufferSize 缓存大小
	 * @param count      拷贝总数
	 * @param progress   进度条
	 */
	public ChannelCopier(final int bufferSize, final long count, final StreamProgress progress) {
		super(bufferSize, count, progress);
	}

	@Override
	public long copy(final ReadableByteChannel source, final WritableByteChannel target) {
		final StreamProgress progress = this.progress;
		if (null != progress) {
			progress.start();
		}
		final long size;
		try {
			size = doCopy(source, target, ByteBuffer.allocate(bufferSize(this.count)), progress);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}

		if (null != progress) {
			progress.finish();
		}
		return size;
	}

	/**
	 * 执行拷贝，如果限制最大长度，则按照最大长度读取，否则一直读取直到遇到-1
	 *
	 * @param source   {@link InputStream}
	 * @param target   {@link OutputStream}
	 * @param buffer   缓存
	 * @param progress 进度条
	 * @return 拷贝总长度
	 * @throws IOException IO异常
	 */
	private long doCopy(final ReadableByteChannel source, final WritableByteChannel target, final ByteBuffer buffer, final StreamProgress progress) throws IOException {
		long numToRead = this.count > 0 ? this.count : Long.MAX_VALUE;
		long total = 0;

		int read;
		while (numToRead > 0) {
			read = source.read(buffer);
			if (read < 0) {
				// 提前读取到末尾
				break;
			}
			buffer.flip();// 写转读
			target.write(buffer);
			buffer.clear();

			numToRead -= read;
			total += read;
			if (null != progress) {
				// 总长度未知的情况下，-1表示未知
				progress.progress(this.count < Long.MAX_VALUE ? this.count : -1, total);
			}
		}

		return total;
	}
}
