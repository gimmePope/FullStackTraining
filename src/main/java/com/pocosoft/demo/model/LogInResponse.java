package com.pocosoft.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInResponse {
	
	private String responseCode;
	String responseMessage;
	String username;
	

}
