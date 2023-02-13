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
import teste.pratico.vr.server.service.PedidosService;
import teste.pratico.vr.server.utils.StaticObjets;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PedidosController.class)
class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidosService mockPedidosService;

    @Test
    void testFindAll() throws Exception {
        // Setup
        // Configure PedidosService.findAll(...).
        final List<PedidosDTO> pedidosDTOS = List.of(
                new PedidosDTO(0L, 0.0, new ClienteDTO(0L, "nome", 0.0, 0, List.of()),
                        List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null))));
        when(mockPedidosService.findAll()).thenReturn(pedidosDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/pedidos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(pedidosDTOS));
    }

    @Test
    void testFindAll_PedidosServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPedidosService.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/pedidos")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testFindById() throws Exception {
        // Setup
        // Configure PedidosService.findById(...).
        final PedidosDTO pedidosDTO = new PedidosDTO(0L, 0.0, new ClienteDTO(0L, "nome", 0.0, 0, List.of()),
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)));
        when(mockPedidosService.findById(0L)).thenReturn(pedidosDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/pedidos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(pedidosDTO));
    }

    @Test
    void testInsert() throws Exception {
        // Setup
        // Configure PedidosService.insert(...).
        final PedidosDTO pedidosDTO = new PedidosDTO(0L, 0.0, new ClienteDTO(0L, "nome", 0.0, 0, List.of()),
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)));
        when(mockPedidosService.insert(any(PedidosDTO.class))).thenReturn(pedidosDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/pedidos")
                        .content("{\"cliente\":{\"id\":1},\"produtosPedidos\":[{\"id\":3},{\"id\":2}]}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(pedidosDTO));
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure PedidosService.update(...).
        final PedidosDTO pedidosDTO = new PedidosDTO(0L, 0.0, new ClienteDTO(0L, "nome", 0.0, 0, List.of()),
                List.of(new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null)));
        when(mockPedidosService.update(any(PedidosDTO.class))).thenReturn(pedidosDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/pedidos")
                        .content("{\"cliente\":{\"id\":1},\"produtosPedidos\":[{\"id\":3},{\"id\":2}]}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(pedidosDTO));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/pedidos/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockPedidosService).delete(0L);
    }
}
