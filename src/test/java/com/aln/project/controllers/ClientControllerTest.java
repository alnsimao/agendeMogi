package com.aln.project.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aln.project.exceptions.ConflictException;
import com.aln.project.exceptions.CpfDuplicateException;
import com.aln.project.exceptions.CpfInvalidException;
import com.aln.project.exceptions.NotFoundException;
import com.aln.project.model.Client;
import com.aln.project.repository.ClientRepository;
import com.aln.project.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClientController.class) 
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepo; 
    
   
    private Client clientExemplo() {
        Client client = new Client();
        client.setId(1L);
        client.setName("João Teste");
        client.setCpf("12345678901");
        client.setEmail("joao@teste.com");
        client.setNumber("999999999");
        return client;
    }

  
    @Test
    void test_newClient() throws Exception {
        Client newClient = clientExemplo();
        String clientJson = objectMapper.writeValueAsString(newClient);

        when(clientService.createClient(any(Client.class))).thenReturn(newClient);

        mockMvc.perform(post("/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L)); 
    }

  
    @Test
    void test_ClientList() throws Exception {
        Client client1 = clientExemplo();
        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Maria Teste");
        
        List<Client> clientList = Arrays.asList(client1, client2);

        when(clientRepo.findAll()).thenReturn(clientList);

        mockMvc.perform(get("/client/list")
                .contentType(MediaType.APPLICATION_JSON))
                
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); 
    }
    

    @Test
    void test_ClientFindById() throws Exception {
        Client client = clientExemplo();
        Long id = 1L;

        when(clientService.findById(id)).thenReturn(client);

        mockMvc.perform(get("/client/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }
    
    @Test
    void test_ClientNotFound() throws Exception {
        Long id = 99L;
        
       
        doThrow(new NotFoundException("Cliente não encontrado")).when(clientService).findById(id);

        mockMvc.perform(get("/client/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                
              
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado")); 
    }


    @Test
    void test_clientPut() throws Exception {
        Long id = 1L;
        Client clienteAtualizado = new Client();
        clienteAtualizado.setName("João Atualizado");
        clienteAtualizado.setEmail("joao.atualizado@teste.com");
        
        
        Client clienteEsperado = clientExemplo();
        clienteEsperado.setName("João Atualizado");
        clienteEsperado.setEmail("joao.atualizado@teste.com");

        String clienteAtualizadoJson = objectMapper.writeValueAsString(clienteAtualizado);

       
        when(clientService.editClient(eq(id), any(Client.class))).thenReturn(clienteEsperado);

        mockMvc.perform(put("/client/edit/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteAtualizadoJson))
                
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("João Atualizado"));
    }

  
    @Test
    void test_DeleteClient() throws Exception {
        Long id = 1L;
        
      
        doNothing().when(clientService).deleteClient(id);

        mockMvc.perform(delete("/client/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                
                .andExpect(status().isOk()); 
                
        
        verify(clientService).deleteClient(id);
    }
    
    @Test
    void test_NotFoundCient() throws Exception {
        Long id = 99L;
        
        
        doThrow(new NotFoundException("Cliente não encontrado")).when(clientService).deleteClient(id);

        mockMvc.perform(delete("/client/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                
                
                .andExpect(status().isNotFound());
    }
    @Test
    void test_duplicateClient() throws Exception {
        Client duplicateClient = clientExemplo();
        String clientJson = objectMapper.writeValueAsString(duplicateClient);

        
        doThrow(new CpfDuplicateException("CPF já cadastrado!!!"))
            .when(clientService).createClient(any(Client.class));

        mockMvc.perform(post("/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                
               
                .andExpect(status().isConflict()) 
               
                .andExpect(jsonPath("$.message").value("CPF já cadastrado!!!")); 
    }
    @Test
    void test_CpfInvalid() throws Exception {
        Client invalidCpfClient = clientExemplo();
        invalidCpfClient.setCpf("1234"); 
        String clientJson = objectMapper.writeValueAsString(invalidCpfClient);

      
        doThrow(new CpfInvalidException("CPF inválido"))
            .when(clientService).createClient(any(Client.class));

        mockMvc.perform(post("/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                
               
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF inválido"));
    }
    @Test
    void teste_EmailExist() throws Exception {
        Long idDoClienteEditado = 1L;
        Client clienteComEmailDuplicado = clientExemplo();
        clienteComEmailDuplicado.setEmail("email.duplicado@teste.com");
        
        String clientJson = objectMapper.writeValueAsString(clienteComEmailDuplicado);

       
        doThrow(new ConflictException("Email já cadastrado"))
            .when(clientService).editClient(eq(idDoClienteEditado), any(Client.class));

        mockMvc.perform(put("/client/edit/{id}", idDoClienteEditado)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                
                
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email já cadastrado"));
    }
}