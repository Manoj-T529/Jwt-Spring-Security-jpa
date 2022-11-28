package com.SpringSecurityJwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurityJwt.Jwt.JwtUtil;
import com.SpringSecurityJwt.exceptions.AlreadyExistsException;
import com.SpringSecurityJwt.model.User;
import com.SpringSecurityJwt.model.UserRequest;
import com.SpringSecurityJwt.model.UserResponse;
import com.SpringSecurityJwt.model.loginDTO;
import com.SpringSecurityJwt.service.UserDetailService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserDetailService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> create(@Valid @RequestBody UserRequest request) throws Exception {

		User name = service.findByUsername(request.getUsername());

		if (name != null) {
			throw new AlreadyExistsException("User with " + name.getUsername() + " is already exists");

		}

		User phno = service.findByPhno(request.getPhno());

		if (phno != null) {
			throw new AlreadyExistsException(phno.getPhno() + " is already registered");

		}

		String pwd = request.getPassword();
		String encrypted = encoder.encode(pwd);

		User user = new User();

		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setPassword(encrypted);
		user.setAge(request.getAge());
		user.setPhno(request.getPhno());
		user.setGender(request.getGender());
		user.setDob(request.getDob());
		user.setAddress(request.getAddress());
		user.setRoles(request.getRoles());

		User user1 = service.save(user);

		if (user1 == null) {
			return new ResponseEntity<String>("User Cannot Be Created !!!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("User Created Successfully", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody loginDTO request) {
		User user = service.findByUsername(request.getUsername());

		if (user == null) {
			return new ResponseEntity<>("User does not Exist", HttpStatus.NOT_FOUND);
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String token = jwtUtil.generateToken(request.getUsername());

		return ResponseEntity.ok(new UserResponse(token));
	}

	@GetMapping("/getdata/{user_id}")
	public ResponseEntity<?> getData(@PathVariable(value = "user_id") Long user_id) {
		User user = service.findById(user_id);

		return ResponseEntity.accepted().body(user);
	}

	@PutMapping("/updatedata/{user_id}")
	public ResponseEntity<?> updateData(@PathVariable(value = "user_id") Long user_id,
			@Valid @RequestBody UserRequest request) {

		User existeduser = service.findById(user_id);

		if (existeduser != null) {
			String pwd = request.getPassword();
			String encrypted = encoder.encode(pwd);

			existeduser.setName(request.getName());
			existeduser.setUsername(request.getUsername());
			existeduser.setPassword(encrypted);
			existeduser.setAge(request.getAge());
			existeduser.setPhno(request.getPhno());
			existeduser.setGender(request.getGender());
			existeduser.setDob(request.getDob());
			existeduser.setAddress(request.getAddress());
			existeduser.setRoles(request.getRoles());

			User user1 = service.save(existeduser);

			if (user1 == null) {
				return new ResponseEntity<String>("User Cannot Be Modified !!!", HttpStatus.NOT_MODIFIED);
			}

		}

		return new ResponseEntity<>("User Data Updated", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deletedata/{user_id}")
	public ResponseEntity<?> deleteData(@PathVariable(value = "user_id") Long user_id) {

		User user = service.findById(user_id);

		if (user != null) {
			service.deleteById(user_id);
		}

		return ResponseEntity.ok("User Deleted Successfully");
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			return ResponseEntity.ok("Logged Out");
		}
		return new ResponseEntity<>("User Not Logged In", HttpStatus.NOT_FOUND);
	}

}
