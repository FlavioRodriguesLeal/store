package br.com.service.store.envelop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

@JsonInclude(Include.NON_DEFAULT)
public class JsonError implements Serializable {

	private static final long serialVersionUID = -5266207846972101032L;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("stacktrace")
	private String stacktrace;
	
	@JsonProperty("body")
	private JsonBodyError body;
	
	public JsonError() {
		
	}
	
	private JsonError(Builder builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.stacktrace = builder.stacktrace;
		this.body = builder.body;
	}
	
	public static String extractStacktrace(Exception exception) {
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	public static class Builder {
		
		private String code;
		private String message;
		private String stacktrace;
		private JsonBodyError body;
		
		public Builder code(HttpStatus code) {
			this.code = String.valueOf(code.value());
			return this;
		}
		
		public Builder message(Exception exception) {
			this.message = exception.getMessage();
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}
		
		public Builder stacktrace(Exception exception) {
			this.stacktrace = extractStacktrace(exception);
			return this;
		}
		
		public Builder stacktrace(String stacktrace) {
			this.stacktrace = stacktrace;
			return this;
		}
		
		public Builder messageAndStacktrace(Exception exception) {
			if(this.message == null)
				this.message = exception.getMessage();
			this.stacktrace = extractStacktrace(exception);
			return this;
		}
		
		public Builder body(JsonBodyError body) {
			this.body = body;
			return this;
		}



		public JsonError build() {
			return new JsonError(this);
		}

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JsonBodyError getBody() {
		return body;
	}

	public void setBody(JsonBodyError body) {
		this.body = body;
	}
}

