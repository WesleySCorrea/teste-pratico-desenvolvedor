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
import teste.pratico.vr.server.service.ProdutoService;
import teste.pratico.vr.server.utils.StaticObjets;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService mockProdutoService;

    @Test
    void testFindAll() throws Exception {
        // Setup
        // Configure ProdutoService.findAll(...).
        final List<ProdutoDTO> produtoDTOS = List.of(new ProdutoDTO(0L, "descricao", 0.0));
        when(mockProdutoService.findAll()).thenReturn(produtoDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produto")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoDTOS));
    }

    @Test
    void testFindAll_ProdutoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProdutoService.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produto")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testFindById() throws Exception {
        // Setup
        // Configure ProdutoService.findAll(...).
        final ProdutoDTO produtoDTO = new ProdutoDTO(0L, "descricao", 0.0);
        when(mockProdutoService.findById(0L)).thenReturn(new ProdutoDTO(0L, "descricao", 0.0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/produto/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoDTO));
    }

    @Test
    void testInsert() throws Exception {
        // Setup
        // Configure ProdutoService.findAll(...).
        final ProdutoDTO produtoDTO = new ProdutoDTO(0L, "descricao", 0.0);
        when(mockProdutoService.insert(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/produto")
                        .content("{\"id\":1,\"descricao\":\"Produto 1\",\"preco\":10}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoDTO));
    }


    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure ProdutoService.findAll(...).
        final ProdutoDTO produtoDTO = new ProdutoDTO(0L, "descricao", 0.0);
        when(mockProdutoService.update(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/produto")
                        .content("{\"id\":1,\"descricao\":\"Produto 1\",\"preco\":10}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(StaticObjets.toJson(produtoDTO));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/produto/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockProdutoService).delete(0L);
    }
}
