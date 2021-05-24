package io.platform.client.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.platform.client.dto.LoginDTO;
import io.platform.client.model.Login;
import io.platform.client.model.User;
import io.platform.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private UserService service;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper objectMapper;

	@CrossOrigin
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public User loginProcess(
			@RequestBody
			@Valid LoginDTO login) {

		return service.validateUser(mapRequest(login));
	}

	private Login mapRequest(LoginDTO dto) {
		return mapper.map(dto, Login.class);
	}

}
