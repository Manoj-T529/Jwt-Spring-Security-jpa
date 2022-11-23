package com.SpringSecurityJwt.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginDTO {

	
	@Email(message="Email is Your UserName")
	@NotNull(message="Username Cannot Be Empty")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$",message="Please Enter a Proper Email Format (Ex: 'A@gmail.com')")
	private String username;
	
	
	
	@Size(min=8, message="Password should contain minimum 8 characters")
    @NotBlank(message="Password Cannot Be Empty")
	private String password;
	
	
}
