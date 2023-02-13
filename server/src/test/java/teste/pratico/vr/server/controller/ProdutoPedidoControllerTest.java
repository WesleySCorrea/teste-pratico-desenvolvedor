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
import teste.pratico.vr.server.dto.ProdutoDTO;
import teste.pratico.vr.server.dto.ProdutoPedidoDTO;
import teste.pratico.vr.server.service.ProdutoPedidoService;
import teste.pratico.vr.server.utils.StaticObjets;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProdutoPedidoController.class)
class ProdutoPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoPedidoService mockProdutoPedidoService;

    @Test
    void testFindAll() throws Exception {
        // Setup
        // Configure ProdutoPedidoService.findAll(...).
        final List<ProdutoPedidoDTO> produtoPedidoDTOS = List.of(
                new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0), 0.0, null));
        when(mockProdutoPedidoService.findAll()).thenReturn(produtoPedidoDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produtopedido")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoPedidoDTOS));
    }

    @Test
    void testFindAll_ProdutoPedidoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProdutoPedidoService.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produtopedido")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testFindById() throws Exception {
        // Setup
        // Configure ProdutoPedidoService.findById(...).
        final ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0),
                0.0, null);
        when(mockProdutoPedidoService.findById(0L)).thenReturn(produtoPedidoDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produtopedido/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoPedidoDTO));
    }

    @Test
    void testInsert() throws Exception {
        // Setup
        // Configure ProdutoPedidoService.insert(...).
        final ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0),
                0.0, null);
        when(mockProdutoPedidoService.insert(any(ProdutoPedidoDTO.class))).thenReturn(produtoPedidoDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/produtopedido")
                        .content("{\"quantidade\":5,\"pedido\":{\"id\":1},\"produto\":{\"id\":2}}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoPedidoDTO));
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure ProdutoPedidoService.update(...).
        final ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(0L, 0, new ProdutoDTO(0L, "descricao", 0.0),
                0.0, null);
        when(mockProdutoPedidoService.update(any(ProdutoPedidoDTO.class))).thenReturn(produtoPedidoDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/produtopedido")
                        .content("{\"quantidade\":5,\"pedido\":{\"id\":1},\"produto\":{\"id\":2}}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoPedidoDTO));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/produtopedido/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockProdutoPedidoService).delete(0L);
    }
}
