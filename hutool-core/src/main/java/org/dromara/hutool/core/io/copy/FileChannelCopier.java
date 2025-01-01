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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * {@link FileChannel} 数据拷贝封装<br>
 *
 * <pre>{@code
 * FileChannel#transferTo 或 FileChannel#transferFrom 的实现是平台相关的，需要确保低版本平台的兼容性
 * 例如 android 7以下平台在使用 ZipInputStream 解压文件的过程中，
 * 通过 FileChannel#transferFrom 传输到文件时，其返回值可能小于 totalBytes，不处理将导致文件内容缺失
 *
 * // 错误写法，dstChannel.transferFrom 返回值小于 zipEntry.getSize()，导致解压后文件内容缺失
 * try (InputStream srcStream = zipFile.getInputStream(zipEntry);
 *     ReadableByteChannel srcChannel = Channels.newChannel(srcStream);
 *     FileOutputStream fos = new FileOutputStream(saveFile);
 *     FileChannel dstChannel = fos.getChannel()) {
 *     dstChannel.transferFrom(srcChannel, 0, zipEntry.getSize());
 *  }
 * }</pre>
 *
 * @author z8g
 */
public class FileChannelCopier extends IoCopier<FileChannel, FileChannel> {

	/**
	 * 创建{@link FileChannel} 拷贝器
	 *
	 * @return FileChannelCopier
	 */
	public static FileChannelCopier of() {
		return of(-1);
	}

	/**
	 * 创建{@link FileChannel} 拷贝器
	 *
	 * @param count 拷贝总数，-1表示无限制
	 * @return FileChannelCopier
	 */
	public static FileChannelCopier of(final long count) {
		return new FileChannelCopier(count);
	}

	/**
	 * 构造
	 *
	 * @param count 拷贝总数，-1表示无限制
	 */
	public FileChannelCopier(final long count) {
		super(-1, count, null);
	}

	/**
	 * 拷贝文件流，使用NIO
	 *
	 * @param in  输入
	 * @param out 输出
	 * @return 拷贝的字节数
	 * @throws IORuntimeException IO异常
	 */
	public long copy(final FileInputStream in, final FileOutputStream out) throws IORuntimeException {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = in.getChannel();
			outChannel = out.getChannel();
			return copy(inChannel, outChannel);
		} finally {
			IoUtil.closeQuietly(outChannel);
			IoUtil.closeQuietly(inChannel);
		}
	}

	@Override
	public long copy(final FileChannel source, final FileChannel target) {
		try {
			return doCopySafely(source, target);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * 文件拷贝实现
	 *
	 * <pre>
	 * FileChannel#transferTo 或 FileChannel#transferFrom 的实现是平台相关的，需要确保低版本平台的兼容性
	 * 例如 android 7以下平台在使用 ZipInputStream 解压文件的过程中，
	 * 通过 FileChannel#transferFrom 传输到文件时，其返回值可能小于 totalBytes，不处理将导致文件内容缺失
	 *
	 * // 错误写法，dstChannel.transferFrom 返回值小于 zipEntry.getSize()，导致解压后文件内容缺失
	 * try (InputStream srcStream = zipFile.getInputStream(zipEntry);
	 * 		ReadableByteChannel srcChannel = Channels.newChannel(srcStream);
	 * 		FileOutputStream fos = new FileOutputStream(saveFile);
	 * 		FileChannel dstChannel = fos.getChannel()) {
	 * 		dstChannel.transferFrom(srcChannel, 0, zipEntry.getSize());
	 *  }
	 * </pre>
	 *
	 * @param inChannel  输入通道
	 * @param outChannel 输出通道
	 * @return 输入通道的字节数
	 * @throws IOException 发生IO错误
	 * @link http://androidxref.com/6.0.1_r10/xref/libcore/luni/src/main/java/java/nio/FileChannelImpl.java
	 * @link http://androidxref.com/7.0.0_r1/xref/libcore/ojluni/src/main/java/sun/nio/ch/FileChannelImpl.java
	 * @link http://androidxref.com/7.0.0_r1/xref/libcore/ojluni/src/main/native/FileChannelImpl.c
	 * @author z8g
	 */
	private long doCopySafely(final FileChannel inChannel, final FileChannel outChannel) throws IOException {
		long totalBytes = inChannel.size();
		if (this.count > 0 && this.count < totalBytes) {
			// 限制拷贝总数
			totalBytes = count;
		}
		for (long pos = 0, remaining = totalBytes; remaining > 0; ) { // 确保文件内容不会缺失
			final long writeBytes = inChannel.transferTo(pos, remaining, outChannel); // 实际传输的字节数
			pos += writeBytes;
			remaining -= writeBytes;
		}
		return totalBytes;
	}
}
