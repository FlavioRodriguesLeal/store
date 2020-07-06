package br.com.service.store.config.error;

import br.com.service.store.envelop.JsonEnvelop;
import br.com.service.store.envelop.JsonError;
import br.com.service.store.exceptions.FaultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler({ FaultException.class })
	public ResponseEntity<JsonEnvelop<Object>> handleFaultException(FaultException ex, WebRequest request) {

		ex.printStackTrace();
		return new JsonEnvelop.Builder<>()
				.statusAndStatusCode(ex.getCode())
				.error(
						new JsonError.Builder()
								.code(ex.getCode())
								.message(ex.getMessage())
								.build())
				.build()
				.response();

	}
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<JsonEnvelop<Object>> handle(Exception ex, WebRequest request) {

    	ex.printStackTrace();
    	return new JsonEnvelop.Builder<>()
				.statusAndStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.error(
					new JsonError.Builder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR)
						.messageAndStacktrace(ex)
						.stacktrace(ex)
						.build())
				.build()
				.response();
    }
    
}
