package com.locknet.math.expressions.domain.usecase.impl;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.locknet.math.expressions.domain.exceptions.ExpressionFormatException;
import com.locknet.math.expressions.domain.usecase.OperationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

	private final DoubleEvaluator evaluator;

	@Override
	public Number parseAndCalculate(String expression, Optional<Integer> precision) throws ExpressionFormatException {

		validateExpression(expression);

		/*
		 * in this statement first check if precision has been passed in the arguments
		 * then if present, it will use BigDecimal class to apply the truncation if not,
		 * it will just return the original number
		 */

		Double result = evaluator.evaluate(expression);

		if (precision.isPresent()) {
			try {
				return BigDecimal.valueOf(result).setScale(precision.get(), BigDecimal.ROUND_DOWN).doubleValue();
			} catch (NumberFormatException e) {
				throw new ExpressionFormatException("Division by zero is undefined");
			}
		} else {
			return result;
		}
	}

	/*
	 * This method validates the expression correctness, if it's malformed it will
	 * thrown a ExpressionFormatException
	 */

	private void validateExpression(String expression) throws ExpressionFormatException {

		try {
			evaluator.evaluate(expression);
		} catch (IllegalArgumentException e) {
			throw new ExpressionFormatException(String.format("The expression %s is malformed", expression));
		}
	}
}
