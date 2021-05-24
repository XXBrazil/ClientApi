package io.platform.client.service;

import io.platform.client.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

	User save(User entity);

	Optional<User> getById(Long id);

	void delete(User user);

	User update(User user);

	Page<User> find( User filter, Pageable pageRequest );

}
