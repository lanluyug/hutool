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

package org.dromara.hutool.poi.excel.sax;

import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.poi.POIException;

import java.io.File;
import java.io.InputStream;

/**
 * Sax方式读取Excel接口，提供一些共用方法
 * @author Looly
 *
 * @param <T> 子对象类型，用于标记返回值this
 * @since 3.2.0
 */
public interface ExcelSaxReader<T> {

	/**
	 * sheet r:Id前缀
	 */
	String RID_PREFIX = "rId";
	/**
	 * sheet name前缀
	 */
	String SHEET_NAME_PREFIX = "sheetName:";

	/**
	 * 开始读取Excel
	 *
	 * @param file Excel文件
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名称，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(File file, String idOrRidOrSheetName) throws POIException;

	/**
	 * 开始读取Excel，读取结束后并不关闭流
	 *
	 * @param in Excel流
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	T read(InputStream in, String idOrRidOrSheetName) throws POIException;

	/**
	 * 开始读取Excel，读取所有sheet
	 *
	 * @param path Excel文件路径
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final String path) throws POIException {
		return read(FileUtil.file(path));
	}

	/**
	 * 开始读取Excel，读取所有sheet
	 *
	 * @param file Excel文件
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final File file) throws POIException {
		return read(file, -1);
	}

	/**
	 * 开始读取Excel，读取所有sheet，读取结束后并不关闭流
	 *
	 * @param in Excel包流
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final InputStream in) throws POIException {
		return read(in, -1);
	}

	/**
	 * 开始读取Excel
	 *
	 * @param path 文件路径
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名称，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final String path, final int idOrRidOrSheetName) throws POIException {
		return read(FileUtil.file(path), idOrRidOrSheetName);
	}

	/**
	 * 开始读取Excel
	 *
	 * @param path 文件路径
	 * @param idOrRidOrSheetName Excel中的sheet id或者rid编号或sheet名称，rid必须加rId前缀，例如rId1，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final String path, final String idOrRidOrSheetName) throws POIException {
		return read(FileUtil.file(path), idOrRidOrSheetName);
	}

	/**
	 * 开始读取Excel
	 *
	 * @param file Excel文件
	 * @param rid Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final File file, final int rid) throws POIException{
		return read(file, String.valueOf(rid));
	}

	/**
	 * 开始读取Excel，读取结束后并不关闭流
	 *
	 * @param in Excel流
	 * @param rid Excel中的sheet rid编号，如果为-1处理所有编号的sheet
	 * @return this
	 * @throws POIException POI异常
	 */
	default T read(final InputStream in, final int rid) throws POIException{
		return read(in, String.valueOf(rid));
	}
}
