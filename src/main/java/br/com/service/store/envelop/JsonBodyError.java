package br.com.service.store.envelop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(Include.NON_DEFAULT)
public class JsonBodyError implements Serializable {
	
	private static final long serialVersionUID = 5943383924313310218L;

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("name")
	private String name;
	
	public JsonBodyError() {
		
	}
	
	private JsonBodyError(Builder builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.name = builder.name;
	}
	
	public static class Builder {
		
		private String code;
		private String message;
		private String name;
		
		public Builder code(String code) {
			this.code = code;
			return this;
		}
		
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public JsonBodyError build() {
			return new JsonBodyError(this);
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
}

