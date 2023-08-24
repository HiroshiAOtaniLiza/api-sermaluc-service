package com.sermaluc.service.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private Integer id;
	
	private String name;
	
	private String lastname;
	
	private String email;
	
	private String password;
	
	private Integer featureId;
	
}
