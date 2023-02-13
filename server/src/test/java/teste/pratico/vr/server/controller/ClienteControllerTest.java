package teste.pratico.vr.server.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import teste.pratico.vr.server.dto.ClienteDTO;
import teste.pratico.vr.server.dto.PedidosDTO;
import teste.pratico.vr.server.dto.ProdutoDTO;
import teste.pratico.vr.server.dto.ProdutoPedidoDTO;
import teste.pratico.vr.server.service.ClienteService;
import teste.pratico.vr.server.utils.StaticObjets;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService mockClienteService;

    @Test
    void testFindAll() throws Exception {
        // Setup
        // Configure ClienteService.findAll(...).
        final List<ClienteDTO> clienteDTOS = List.of(new ClienteDTO(0L, "nome", 0.0, 0,
                List.of(new PedidosDTO(0L, 0.0, null,
                        List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null))))));
        when(mockClienteService.findAll()).thenReturn(clienteDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(clienteDTOS));
    }

    @Test
    void testFindAll_ClienteServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockClienteService.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testFindById() throws Exception {
        // Setup
        // Configure ClienteService.findById(...).
        final ClienteDTO clienteDTO = new ClienteDTO(0L, "nome", 0.0, 0, List.of(new PedidosDTO(0L, 0.0, null,
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)))));
        when(mockClienteService.findById(0L)).thenReturn(clienteDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(clienteDTO));
    }

    @Test
    void testInsert() throws Exception {
        // Setup
        // Configure ClienteService.insert(...).
        final ClienteDTO clienteDTO = new ClienteDTO(0L, "nome", 0.0, 0, List.of(new PedidosDTO(0L, 0.0, null,
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)))));
        when(mockClienteService.insert(any(ClienteDTO.class))).thenReturn(clienteDTO);



        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/clientes")
                        .content("{\"id\": 10,\"nome\": \"Cliente 4\",\"limiteDeCompra\": 5000,\"diaDeFechamentoDaFatura\": 20}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(clienteDTO));
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure ClienteService.update(...).
        final ClienteDTO clienteDTO = new ClienteDTO(0L, "nome", 0.0, 0, List.of(new PedidosDTO(0L, 0.0, null,
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)))));
        when(mockClienteService.update(any(ClienteDTO.class))).thenReturn(clienteDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/clientes")
                        .content("{\"id\": 10,\"nome\": \"Cliente 4\",\"limiteDeCompra\": 5000,\"diaDeFechamentoDaFatura\": 20}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(clienteDTO));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/clientes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockClienteService).delete(0L);
    }
}
