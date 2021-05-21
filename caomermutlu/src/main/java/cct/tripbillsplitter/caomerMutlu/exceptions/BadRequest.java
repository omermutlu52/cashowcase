package cct.tripbillsplitter.caomerMutlu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5659625114614686387L;

	public BadRequest(String message) {
		super(message);
	}
	
}
