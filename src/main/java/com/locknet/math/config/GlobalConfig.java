package com.locknet.math.config;

import java.util.Iterator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;

@Configuration
public class GlobalConfig {
	
	/*
	 * here is defined the bean configuration for Javaluator, which is the library used to
	 * parse expressions, in this case is not enough to call the default constructor since
	 * doesn't support "sqrt" (square root) expression, so it's added here
	 */

	@Bean
	public DoubleEvaluator initialize() {

		Function sqrt = new Function("sqrt", 1);
		Parameters params = DoubleEvaluator.getDefaultParameters();
		params.add(sqrt);
		
		return new DoubleEvaluator(params) {
			
			@Override
			protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
				if (function == sqrt) {
					return Math.sqrt(arguments.next());
				} else {
					return super.evaluate(function, arguments, evaluationContext);
				}
			}
		};
	}
}
