package cct.tripbillsplitter.caomerMutlu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {



	/**
	 * 
	 */
	private static final long serialVersionUID = 814235571890965569L;

	public NoContentException(String message) {
        super(message);
	}
	
}
