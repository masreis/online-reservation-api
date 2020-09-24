package net.onlinereservation.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Response<T> {

	private T data;

	private List<ResponseError> errors = new ArrayList<>();

	public void addError(String message) {
		errors.add(new ResponseError(message));
	}

}
