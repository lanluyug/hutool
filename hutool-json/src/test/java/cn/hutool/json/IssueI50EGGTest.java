package cn.hutool.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IssueI50EGGTest {

	@Test
	public void toBeanTest(){
		String data = "{\"return_code\": 1, \"return_msg\": \"成功\", \"return_data\" : null}";
		final ApiResult<?> apiResult = JSONUtil.toBean(data, JSONConfig.create().setIgnoreCase(true), ApiResult.class);
		assertEquals(1, apiResult.getReturn_code());
	}

	@Data
	@AllArgsConstructor
	static class ApiResult<T>{
		private long Return_code;
		private String Return_msg;
		private T Return_data;
	}
}
