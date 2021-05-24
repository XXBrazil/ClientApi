package io.platform.client.repository;

import io.platform.client.model.Client;
import io.platform.client.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUser( String user );

}
