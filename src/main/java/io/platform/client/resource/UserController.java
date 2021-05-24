package io.platform.client.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.platform.client.dto.UserDTO;
import io.platform.client.exceptions.ApiErrors;
import io.platform.client.model.Client;
import io.platform.client.model.User;
import io.platform.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO create(@RequestBody
	@Valid
			UserDTO dto) {
		log.info("creating new client");
		User savedUser = service.save(mapRequest(dto));

		log.info("user saved with id: {}", savedUser.getId());
		return mapper.map(savedUser, UserDTO.class);
	}

	@GetMapping("{id}")
	public UserDTO get(@PathVariable Long id) {
		log.info(" obtaining details for user id: {} ", id);
		return service.getById(id).map(this::mapResponse)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		log.info(" deleting user of id: {} ", id);
		User user = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(user);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
	public UserDTO updatePartialUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
		return service.getById(id).map(user -> {
			user = service.update(applyPatchToCustomer(patch, user));
			return mapResponse(user);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PutMapping("{id}")
	public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
		log.info(" updating client of id: {} ", id);
		return service.getById(id).map(user -> {
			user.setUser(dto.getUser());
			user = service.update(user);
			return mapResponse(user);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public Page<UserDTO> find(UserDTO dto, Pageable pageRequest) {
		User filter = mapRequest(dto);
		Page<User> result = service.find(filter, pageRequest);
		List<UserDTO> list = result.getContent().stream().map(this::mapResponse).collect(Collectors.toList());
		return new PageImpl<UserDTO>(list, pageRequest, result.getTotalElements());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return new ApiErrors(bindingResult);
	}

	private User mapRequest(UserDTO dto) {
		return mapper.map(dto, User.class);
	}

	private UserDTO mapResponse(User model) {
		return mapper.map(model, UserDTO.class);
	}

	private User applyPatchToCustomer(JsonPatch patch, User target) {
		try {
			JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
			return objectMapper.treeToValue(patched, User.class);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
