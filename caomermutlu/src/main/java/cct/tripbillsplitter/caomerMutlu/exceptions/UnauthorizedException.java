package cct.tripbillsplitter.caomerMutlu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3527726000920161116L;

	public UnauthorizedException(String message) {
		super(message);
	}
}
