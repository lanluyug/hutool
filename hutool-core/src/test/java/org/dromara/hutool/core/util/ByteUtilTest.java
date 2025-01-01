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

package org.dromara.hutool.core.util;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteUtilTest {
	@Test
	public void intAndBytesLittleEndianTest() {
		// 测试 int 转小端序 byte 数组
		final int int1 = RandomUtil.randomInt((Integer.MAX_VALUE));

		final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(int1);
		final byte[] bytesIntFromBuffer = buffer.array();

		final byte[] bytesInt = ByteUtil.toBytes(int1, ByteOrder.LITTLE_ENDIAN);
		assertArrayEquals(bytesIntFromBuffer, bytesInt);

		final int int2 = ByteUtil.toInt(bytesInt, ByteOrder.LITTLE_ENDIAN);
		assertEquals(int1, int2);

		final byte[] bytesInt2 = ByteUtil.toBytes(int1, ByteOrder.LITTLE_ENDIAN);
		final int int3 = ByteUtil.toInt(bytesInt2, ByteOrder.LITTLE_ENDIAN);
		assertEquals(int1, int3);

		final byte[] bytesInt3 = ByteUtil.toBytes(int1, ByteOrder.LITTLE_ENDIAN);
		final int int4 = ByteUtil.toInt(bytesInt3, ByteOrder.LITTLE_ENDIAN);
		assertEquals(int1, int4);
	}

	@Test
	public void intAndBytesBigEndianTest() {
		// 测试 int 转大端序 byte 数组
		final int int2 = RandomUtil.randomInt(Integer.MAX_VALUE);

		final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(int2);
		final byte[] bytesIntFromBuffer = buffer.array();

		final byte[] bytesInt = ByteUtil.toBytes(int2, ByteOrder.BIG_ENDIAN);
		assertArrayEquals(bytesIntFromBuffer, bytesInt);

		// 测试大端序 byte 数组转 int
		final int int3 = ByteUtil.toInt(bytesInt, ByteOrder.BIG_ENDIAN);
		assertEquals(int2, int3);
	}

	@Test
	public void longAndBytesLittleEndianTest() {
		// 测试 long 转 byte 数组
		final long long1 = RandomUtil.randomLong(Long.MAX_VALUE);

		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(long1);
		final byte[] bytesLongFromBuffer = buffer.array();

		final byte[] bytesLong = ByteUtil.toBytes(long1, ByteOrder.LITTLE_ENDIAN);
		assertArrayEquals(bytesLongFromBuffer, bytesLong);

		final long long2 = ByteUtil.toLong(bytesLong, ByteOrder.LITTLE_ENDIAN);
		assertEquals(long1, long2);

		final byte[] bytesLong2 = ByteUtil.toBytes(long1);
		final long long3 = ByteUtil.toLong(bytesLong2, ByteOrder.LITTLE_ENDIAN);
		assertEquals(long1, long3);

		final byte[] bytesLong3 = ByteUtil.toBytes(long1, ByteOrder.LITTLE_ENDIAN);
		final long long4 = ByteUtil.toLong(bytesLong3);
		assertEquals(long1, long4);
	}

	@Test
	public void longAndBytesBigEndianTest() {
		// 测试大端序 long 转 byte 数组
		final long long1 = RandomUtil.randomLong(Long.MAX_VALUE);

		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(long1);
		final byte[] bytesLongFromBuffer = buffer.array();

		final byte[] bytesLong = ByteUtil.toBytes(long1, ByteOrder.BIG_ENDIAN);
		assertArrayEquals(bytesLongFromBuffer, bytesLong);

		final long long2 = ByteUtil.toLong(bytesLong, ByteOrder.BIG_ENDIAN);
		assertEquals(long1, long2);
	}

	@Test
	public void floatAndBytesLittleEndianTest() {
		// 测试 long 转 byte 数组
		final float f1 = (float) RandomUtil.randomDouble();

		final byte[] bytesLong = ByteUtil.toBytes(f1, ByteOrder.LITTLE_ENDIAN);
		final float f2 = ByteUtil.toFloat(bytesLong, ByteOrder.LITTLE_ENDIAN);
		assertEquals(f1, f2, 0);
	}

	@Test
	public void floatAndBytesBigEndianTest() {
		// 测试大端序 long 转 byte 数组
		final float f1 = (float) RandomUtil.randomDouble();

		final byte[] bytesLong = ByteUtil.toBytes(f1, ByteOrder.BIG_ENDIAN);
		final float f2 = ByteUtil.toFloat(bytesLong, ByteOrder.BIG_ENDIAN);

		assertEquals(f1, f2, 0);
	}

	@Test
	public void shortAndBytesLittleEndianTest() {
		final short short1 = (short) RandomUtil.randomInt();

		final byte[] bytes = ByteUtil.toBytes(short1, ByteOrder.LITTLE_ENDIAN);
		final short short2 = ByteUtil.toShort(bytes, ByteOrder.LITTLE_ENDIAN);
		assertEquals(short2, short1);

		final byte[] bytes2 = ByteUtil.toBytes(short1);
		final short short3 = ByteUtil.toShort(bytes2, ByteOrder.LITTLE_ENDIAN);
		assertEquals(short3, short1);

		final byte[] bytes3 = ByteUtil.toBytes(short1, ByteOrder.LITTLE_ENDIAN);
		final short short4 = ByteUtil.toShort(bytes3);
		assertEquals(short4, short1);
	}

	@Test
	public void shortAndBytesBigEndianTest() {
		final short short1 = 122;
		final byte[] bytes = ByteUtil.toBytes(short1, ByteOrder.BIG_ENDIAN);
		final short short2 = ByteUtil.toShort(bytes, ByteOrder.BIG_ENDIAN);

		assertEquals(short2, short1);
	}

	@Test
	public void bytesToLongTest(){
		final long a = RandomUtil.randomLong(0, Long.MAX_VALUE);
		ByteBuffer wrap = ByteBuffer.wrap(ByteUtil.toBytes(a));
		wrap.order(ByteOrder.LITTLE_ENDIAN);
		long aLong = wrap.getLong();
		assertEquals(a, aLong);

		wrap = ByteBuffer.wrap(ByteUtil.toBytes(a, ByteOrder.BIG_ENDIAN));
		wrap.order(ByteOrder.BIG_ENDIAN);
		aLong = wrap.getLong();
		assertEquals(a, aLong);
	}

	@Test
	public void bytesToIntTest(){
		final int a = RandomUtil.randomInt(0, Integer.MAX_VALUE);
		ByteBuffer wrap = ByteBuffer.wrap(ByteUtil.toBytes(a));
		wrap.order(ByteOrder.LITTLE_ENDIAN);
		int aInt = wrap.getInt();
		assertEquals(a, aInt);

		wrap = ByteBuffer.wrap(ByteUtil.toBytes(a, ByteOrder.BIG_ENDIAN));
		wrap.order(ByteOrder.BIG_ENDIAN);
		aInt = wrap.getInt();
		assertEquals(a, aInt);
	}

	@Test
	public void bytesToShortTest(){
		final short a = (short) RandomUtil.randomInt(0, Short.MAX_VALUE);

		ByteBuffer wrap = ByteBuffer.wrap(ByteUtil.toBytes(a));
		wrap.order(ByteOrder.LITTLE_ENDIAN);
		short aShort = wrap.getShort();
		assertEquals(a, aShort);

		wrap = ByteBuffer.wrap(ByteUtil.toBytes(a, ByteOrder.BIG_ENDIAN));
		wrap.order(ByteOrder.BIG_ENDIAN);
		aShort = wrap.getShort();
		assertEquals(a, aShort);
	}

	@Test
	public void toUnsignedBitIndex() {
		final byte[] bytes = {0, 13, -64, -31, 101, 88, 47, -64};
		final List<Integer> list = ByteUtil.toUnsignedBitIndex(bytes);
		assertEquals("[12, 13, 15, 16, 17, 24, 25, 26, 31, 33, 34, 37, 39, 41, 43, 44, 50, 52, 53, 54, 55, 56, 57]", list.toString());
	}

	@Test
	public void bitCount() {
		final byte[] bytes = {0, 13, -64, -31, 101, 88, 47, -64};
		final int count = ByteUtil.bitCount(bytes);
		assertEquals(count, ByteUtil.toUnsignedBitIndex(bytes).size());
	}

	@Test
	public void testToIntWithLittleEndian() {
		// 小端序：低位字节在前，高位字节在后
		final byte[] bytes = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0x01, (byte) 0x00};
		final int expectedValue = 130815;
		final int actualValue = ByteUtil.toInt(bytes, 0, ByteOrder.LITTLE_ENDIAN);
		assertEquals(expectedValue, actualValue, "Failed to support bytes to int using LITTLE_ENDIAN");
	}

	@Test
	public void testToIntWithBigEndian() {
		// 大端序：高位字节在前，低位字节在后
		final byte[] bytes = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0x01, (byte) 0x00};
		final int expectedValue = -130816;
		final int actualValue = ByteUtil.toInt(bytes, 0, ByteOrder.BIG_ENDIAN);
		assertEquals(expectedValue, actualValue, "Failed to support bytes to int using BIG_ENDIAN");
	}
}
