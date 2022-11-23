package com.SpringSecurityJwt.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class UserRequest {

	
	private long user_id;
	
	
	@NotBlank(message="Name Cannot Be Empty")
	private String name;
	
	
	@Email(message="Email is Your UserName")
	@NotNull(message="Username Cannot Be Empty")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$",message="Please Enter a Proper Email Format (Ex: 'A@gmail.com')")
	private String username;
	
	
	
	@Size(min=8, message="Password should contain minimum 8 characters")
    @NotBlank(message="Password Cannot Be Empty")
	private String password;
	
	
	@NotNull(message="Age Cannot Be Empty")
	@Min(value=5,message="Age should be between 5 and 100")
	@Max(value=100,message="Age should be between 5 and 100")
	private int age;
	
	
	@NotNull(message="Phone Number Cannot Be Empty")
	@Size(min=10,max=10, message="Phone Number should contain 10 Digits")
	@Pattern(regexp="(0|91)?[6-9][0-9]{9}", message="Invalid Mobile Number")
	private String phno;
	
	
	@NotBlank(message="Enter Male or Female")
	private String gender;
	
	
	//@NotBlank(message="Enter Current Address")
	private String address;
	
	
	@NotBlank(message="Enter Current DOB")
	private String dob;
	
	
	private  Set<String> roles;

	
}
