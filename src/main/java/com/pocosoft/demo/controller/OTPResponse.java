package com.pocosoft.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPResponse {
	
	private String responseCode;
	private String token;
	private String description;

}
