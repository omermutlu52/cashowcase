package cct.tripbillsplitter.caomerMutlu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus( code=HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2799575381428311983L;

	public UnprocessableEntityException(String message) {
		super(message);
	}
}
