/*
 * Copyright (c) 2025 Hutool Team and hutool.cn
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

package org.dromara.hutool.swing.qrcode.render;

import com.google.zxing.common.BitMatrix;

import java.io.OutputStream;

/**
 * BitMatrix渲染接口
 *
 * @author Looly
 * @since 6.0.0
 */
public interface BitMatrixRender {

	/**
	 * 渲染
	 *
	 * @param matrix 二维码矩阵
	 * @param out       输出流
	 */
	void render(BitMatrix matrix, OutputStream out);
}
