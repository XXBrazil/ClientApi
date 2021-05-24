package io.platform.client.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.platform.client.dto.ClientDTO;
import io.platform.client.dto.LoginDTO;
import io.platform.client.dto.UserDTO;
import io.platform.client.model.Client;
import io.platform.client.model.Login;
import io.platform.client.model.User;
import io.platform.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
