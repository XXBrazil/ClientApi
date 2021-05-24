package io.platform.client.repository;

import io.platform.client.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    boolean existsByTelefone( String telefone );

}
