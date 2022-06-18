package com.locknet.math.expressions.ports.controllers;

import java.util.Optional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.locknet.math.expressions.domain.exceptions.ExpressionFormatException;
import com.locknet.math.expressions.domain.usecase.OperationService;
import com.locknet.math.expressions.ports.Routes;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Routes.CALCULATE_URL)
@CrossOrigin
@RequiredArgsConstructor
public class ExpressionController {

	private final OperationService operationService;

	@GetMapping()
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> calculate(@RequestParam(name = "expression", required = true) String expressionParam,
			@RequestParam(name = "precision", required = false) Optional<Integer> precision) {

		if (Base64.isBase64(expressionParam)) {
			byte[] decodedBytes = Base64.decodeBase64(expressionParam);
			expressionParam = new String(decodedBytes);
		}

		try {
			Number calculatedExpression = operationService.parseAndCalculate(expressionParam, precision);
			return new ResponseEntity<>(calculatedExpression, HttpStatus.OK);
		} catch (ExpressionFormatException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
