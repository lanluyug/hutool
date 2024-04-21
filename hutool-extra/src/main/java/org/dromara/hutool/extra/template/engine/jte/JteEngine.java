/*
 * Copyright (c) 2023 looly(loolly@aliyun.com)
 * Hutool is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          https://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package org.dromara.hutool.extra.template.engine.jte;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.resolve.ResourceCodeResolver;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.template.Template;
import org.dromara.hutool.extra.template.TemplateConfig;
import org.dromara.hutool.extra.template.engine.TemplateEngine;

import java.nio.file.Paths;

/**
 * jte实现<br>
 * 见：<a href="https://jte.gg/">https://jte.gg/</a>
 *
 * @author dy
 */
public class JteEngine implements TemplateEngine {

	private final ContentType contentType = ContentType.Plain;
	private TemplateConfig config = TemplateConfig.DEFAULT;
	private gg.jte.TemplateEngine engine;

	// --------------------------------------------------------------------------------- Constructor start

	/**
	 * 默认构造
	 */
	public JteEngine() {
	}

	/**
	 * 构造
	 *
	 * @param config 模板配置
	 */
	public JteEngine(final TemplateConfig config) {
		this.config = config;
		createEngine();
	}

	/**
	 * 构造
	 *
	 * @param codeResolver {@link CodeResolver}
	 */
	public JteEngine(final CodeResolver codeResolver) {
		this.engine = createEngine(codeResolver);
	}
	// --------------------------------------------------------------------------------- Constructor end

	@Override
	public TemplateEngine init(final TemplateConfig config) {
		if (config != null) {
			this.config = config;
		}
		createEngine();
		return this;
	}

	@Override
	public Template getTemplate(final String resource) {
		if (TemplateConfig.ResourceMode.STRING.equals(config.getResourceMode())) {
			String path = config.getPath();
			if(!StrUtil.endWithAny(path, ".jte", ".kte")){
				path = StrUtil.addSuffixIfNot(StrUtil.defaultIfEmpty(path, "hutool"), ".jte");
			}
			return new JteTemplate(createEngine(
				new SimpleStringCodeResolver(MapUtil.of(path, resource)))
				, path, config.getCharset());
		} else {
			return new JteTemplate(engine, resource, config.getCharset());
		}
	}

	@Override
	public gg.jte.TemplateEngine getRaw() {
		return this.engine;
	}

	/**
	 * 创建引擎 {@link gg.jte.TemplateEngine }
	 */
	private void createEngine() {
		switch (config.getResourceMode()) {
			case CLASSPATH:
				this.engine = createEngine(new ResourceCodeResolver(config.getPath(), JteEngine.class.getClassLoader()));
				break;
			case FILE:
				this.engine = createEngine(new DirectoryCodeResolver(Paths.get(config.getPath())));
				break;
			case STRING:
				// 这里无法直接创建引擎
				break;
			default:
				break;
		}
	}

	/**
	 * 创建引擎 {@link gg.jte.TemplateEngine }
	 *
	 * @param codeResolver CodeResolver
	 */
	private gg.jte.TemplateEngine createEngine(final CodeResolver codeResolver) {
		return gg.jte.TemplateEngine.create(codeResolver, this.contentType);
	}
}
