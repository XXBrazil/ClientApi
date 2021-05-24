package io.platform.client.service;

import io.platform.client.model.Login;
import io.platform.client.model.User;
import io.platform.client.repository.UserRepository;
import io.platform.exception.BusinessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User entity) {
        if(repository.existsByUser(entity.getUser())) {
           throw new BusinessException("User already exists.");
        }
        return this.repository.save(entity);
    }

    @Override
    public Optional<User> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        this.repository.delete(user);
    }

    @Override
    public User update(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        return this.repository.save(user);
    }

    @Override
    public Page<User> find(User filter, Pageable pageRequest) {
        Example<User> example = Example.of(filter, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example, pageRequest);
    }

    @Override
    public User validateUser(Login login) {
       if (repository.existsByPassword(login.getPassword()) && repository.existsByUser(login.getUser())) {
           return repository.findByUser(login.getUser());
       } else {
           return new User();
       }
    }

}
