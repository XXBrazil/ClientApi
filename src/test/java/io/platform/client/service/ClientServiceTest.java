package io.platform.client.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.platform.client.model.Client;
import io.platform.client.repository.ClientRepository;
import io.platform.exception.BusinessException;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClientServiceTest {


    private static final LocalDate CLIENT_BIRTH = LocalDate.of(2010, 11, 1);

    private static final String CLIENT_SEX = "male";

    private static final String CLIENT_NAME = "Client Rest Test";

    ClientService service;

    @MockBean
    ClientRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new ClientServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save a client")
    public void saveClientTest() {
        Client client = createValidClient();
        when(repository.existsByNome(Mockito.anyString()) ).thenReturn(false);
        when(repository.save(client)).thenReturn(
                Client.builder().id(1l)
                .nome(CLIENT_NAME)
                .build()
        );

        Client savedClient = service.save(client);

        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getNome()).isEqualTo(CLIENT_NAME);
    }

    private Client createValidClient() {
        return Client.builder().nome(CLIENT_NAME).build();
    }

    @Test
    @DisplayName("Souldn't save a client with duplicated name")
    public void shouldNotSaveAClientWithDuplicatedName(){
        Client client = createValidClient();
        when( repository.existsByNome(Mockito.anyString()) ).thenReturn(true);

        Throwable exception = Assertions.catchThrowable(() -> service.save(client));

        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Client already exists.");

        Mockito.verify(repository, Mockito.never()).save(client);

    }

    @Test
    @DisplayName("Should retrieve a client by id")
    public void getByIdTest(){
        Long id = 1l;
        Client client = createValidClient();
        client.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(client));

        Optional<Client> foundClient = service.getById(id);

        assertThat( foundClient.isPresent() ).isTrue();
        assertThat( foundClient.get().getId()).isEqualTo(id);
        assertThat( foundClient.get().getNome()).isEqualTo(client.getNome());
    }

    @Test
    @DisplayName("Should return empty when client doesn't exists")
    public void clientNotFoundByIdTest(){
        Long id = 1l;
        when( repository.findById(id) ).thenReturn(Optional.empty());
        Optional<Client> client = service.getById(id);
        assertThat( client.isPresent() ).isFalse();
    }

    @Test
    @DisplayName("Should delete a client")
    public void deleteClientTest(){
        Client client = Client.builder().id(1l).build();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.delete(client) );

        Mockito.verify(repository, Mockito.times(1)).delete(client);
    }

    @Test
    @DisplayName("Should throw error on delete nonexistent client.")
    public void deleteInvalidClientTest(){
        Client client = new Client();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(client));

        Mockito.verify( repository, Mockito.never() ).delete(client);
    }

    @Test
    @DisplayName("Should throw error on update nonexistent client.")
    public void updateInvalidClientTest(){
        Client client = new Client();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(client));

        Mockito.verify( repository, Mockito.never() ).save(client);
    }

    @Test
    @DisplayName("Should update client")
    public void updateClientTest(){
        long id = 1l;

        Client updatingClient = Client.builder().id(id).build();

        Client updatedClient = createValidClient();
        updatedClient.setId(id);
        when(repository.save(updatingClient)).thenReturn(updatedClient);

        Client client = service.update(updatingClient);

        assertThat(client.getId()).isEqualTo(updatedClient.getId());
        assertThat(client.getNome()).isEqualTo(updatedClient.getNome());

    }

    @Test
    @DisplayName("Should find a client using a filter")
    public void findClientTest(){
        Client client = createValidClient();

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Client> lista = Arrays.asList(client);
        Page<Client> page = new PageImpl<Client>(lista, pageRequest, 1);
        when( repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<Client> result = service.find(client, pageRequest);


        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(lista);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }


}
