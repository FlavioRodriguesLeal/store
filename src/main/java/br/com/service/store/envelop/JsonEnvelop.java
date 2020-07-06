package br.com.service.store.envelop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonEnvelop<T> implements Serializable {

	private static final long serialVersionUID = 5540316958211092037L;
	
	@JsonProperty("code")
	private Integer statusCode;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("data")
	private T data;

	@JsonProperty("error")
	private List<JsonError> error;
	
	private JsonEnvelop(Builder<?> builder) {
		this.statusCode = builder.statusCode;
		this.status = builder.status;
		this.data = (T) builder.data;
		this.error = builder.error;
	}
	
	public void addError(String error) {
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		jsonError.setMessage(error);
		this.error.add(jsonError);
	}
	
	public void addError(String code , String error) {
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		jsonError.setCode(code);
		jsonError.setMessage(error);
		this.error.add(jsonError);
	}
	
	public void addError(HttpStatus code , String error) {
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		jsonError.setCode(String.valueOf(code.value()));
		jsonError.setMessage(error);
		this.error.add(jsonError);
	}

	public void addError(JsonError error) {
		if(this.error == null) this.error = new ArrayList<JsonError>();
		this.error.add(error);
	}
	
	public void addError(HttpStatus code , String error, Exception exception){
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		this.error.add(jsonError);
		jsonError.setCode(String.valueOf(code.value()));
		jsonError.setMessage(error);
	}
	
	public void addError(String error, Exception exception){
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		this.error.add(jsonError);
		jsonError.setMessage(error);
	}
	
	public void addError(Exception exception){
		if(this.error == null) this.error = new ArrayList<JsonError>();
		JsonError jsonError = new JsonError();
		this.error.add(jsonError);
	}
	
	public ResponseEntity<JsonEnvelop<T>> response() {
		return new ResponseEntity<JsonEnvelop<T>>(this, HttpStatus.valueOf(this.statusCode));
	}
	
	public static class Builder<T> {
		
		private Integer statusCode;
		
		private String status;
		
		private T data;

		private List<JsonError> error;
		
		private JsonEnvelop<T> instance;
		
		public Builder<T> statusCode(HttpStatus statusCode) {
			this.statusCode = statusCode.value();
			return this;
		}
		
		public Builder<T> status(String status) {
			this.status = status;
			return this;
		}
		
		public Builder<T> statusAndStatusCode(HttpStatus status) {
			this.statusCode = status.value();
			this.status = status.getReasonPhrase();
			return this;
		}
		
		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}
		
		public Builder<T> error(JsonError error) {
			if(this.error == null) this.error = new ArrayList<JsonError>();
			this.error.add(error);
			return this;
		}
		
		public JsonEnvelop<T> build() {
			if(instance == null) instance = new JsonEnvelop<T>(this);
			return instance;
		}
		
	}
	
}
