package com.edatwhite.smkd.controller;


import com.edatwhite.smkd.entity.Division;
import com.edatwhite.smkd.entity.ERole;
import com.edatwhite.smkd.entity.Roles;
import com.edatwhite.smkd.entity.Users;
import com.edatwhite.smkd.payload.request.LoginRequest;
import com.edatwhite.smkd.payload.request.SignupRequest;
import com.edatwhite.smkd.payload.response.JwtResponse;
import com.edatwhite.smkd.payload.response.MessageResponse;
import com.edatwhite.smkd.repository.DivisionRepository;
import com.edatwhite.smkd.repository.RoleRepository;
import com.edatwhite.smkd.repository.UserRepository;
import com.edatwhite.smkd.security.jwt.JwtUtils;
import com.edatwhite.smkd.service.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	DivisionRepository divisionRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {



		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getFio(),
												 userDetails.getUsername(),
												 roles,
												 userDetails.getDivisions()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		// Create new user's account
		Users user = new Users(signUpRequest.getFio(),
							 signUpRequest.getUsername(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
//		userRepository.save(user);

		Set<Long> strDivisions = signUpRequest.getDivisions();
		Set<Division> divisions = new HashSet<>();

//		if (strRoles == null) {
//			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//
//			Division userDivision = divisionRepository.findById()
//		} else {
		strDivisions.forEach(division -> {
			Division userDivision = divisionRepository.findById(division)
					.orElseThrow(() -> new RuntimeException("Error: Division is not found."));
			divisions.add(userDivision);
			});
//		}

		user.setDivisions(divisions);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
