package com.locknet.math.expressions.domain.usecase;

import java.util.Optional;

import com.locknet.math.expressions.domain.exceptions.ExpressionFormatException;

public interface OperationService {

	/**
	 * 
	 * This method parse and calculate the expression, first it will call
	 * {@link #validateExpression(String)} to check if the expression is
	 * semantically correct
	 * 
	 * @author Santiago Pulido
	 */

	Number parseAndCalculate(String expression, Optional<Integer> precision) throws ExpressionFormatException;
}
