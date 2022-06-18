package com.locknet.math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.locknet.math.expressions.ports.Routes;

@SpringBootTest
@AutoConfigureMockMvc
class MathApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void calculateSqrt_shouldReturn200() throws UnsupportedEncodingException, Exception {

		String expression = "sqrt(2)";
		Integer precision = 3;

		String response = mockMvc
				.perform(get(Routes.CALCULATE_URL).contentType(MediaType.ALL).param("expression", expression)
						.param("precision", precision.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualTo("1.414");

	}

	@Test
	void calculateComplexExpression_shouldReturn200() throws UnsupportedEncodingException, Exception {

		String expression = "(5*6)/(2/3)*sqrt(2)^2";
		Integer precision = 1;

		String response = mockMvc
				.perform(post(Routes.CALCULATE_URL).contentType(MediaType.ALL).param("expression", expression)
						.param("precision", precision.toString()))
				.andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

		assertThat(response).isEqualTo("90.0");

	}

	@Test
	void calculateWrongExpression_shouldReturn400() throws UnsupportedEncodingException, Exception {

		String expression = "sqrt(2*5/";

		mockMvc.perform(get(Routes.CALCULATE_URL).contentType(MediaType.ALL).param("expression", expression))
				.andExpect(status().isBadRequest()).andDo(print());

	}

}
