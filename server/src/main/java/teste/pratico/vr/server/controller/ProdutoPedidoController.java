package teste.pratico.vr.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.pratico.vr.server.dto.ProdutoPedidoDTO;
import teste.pratico.vr.server.service.ProdutoPedidoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/produtopedido")
public class ProdutoPedidoController {

    private final ProdutoPedidoService produtoPedidoService;

    /**
     * Método para retornar uma Lista de ProdutosPedido
     * @return Lista de ProdutosPedidoDTO
     */
    @GetMapping
    public ResponseEntity<List<ProdutoPedidoDTO>> findAll() {

        //Utilizar o método findALl na classe ProdutoPedidoService
        var listaProdutoPedido = produtoPedidoService.findAll();

        //Retornar uma Lista de ProdutosPedido
        return ResponseEntity.ok().body(listaProdutoPedido);
    }

    /**
     * Método para retornar um ProdutoPedido com Id Específico
     * @param id
     * @return ProdutoPedidoDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoPedidoDTO> findById(@PathVariable Long id) {

        //Utilizar o método findById na classe ProdutoPedidoService
        var produtoPedido = produtoPedidoService.findById(id);

        //Retornar um ProdutosPedido com Id Específico
        return ResponseEntity.ok().body(produtoPedido);
    }

    /**
     * Método para adicionar um ProdutoPedido ao banco de dados
     * @param produtoPedidoDTO
     * @return ProdutoPedidoDTO persistido
     */
    @PostMapping
    public ResponseEntity<ProdutoPedidoDTO> insert(@RequestBody ProdutoPedidoDTO produtoPedidoDTO) {

        //Utilizar o método insert na classe ProdutoPedidoService
        var response = produtoPedidoService.insert(produtoPedidoDTO);

        //Retornar o ProdutosPedido persistido
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     * @param produtoPedidoDTO
     * @return ProdutoDTO atualizado
     */
    @PutMapping
    public ResponseEntity<ProdutoPedidoDTO> update (@RequestBody ProdutoPedidoDTO produtoPedidoDTO) {

        //Utilizar o método update na classe ProdutoPedidoService
        var response = produtoPedidoService.update(produtoPedidoDTO);

        //Retornar o ProdutosPedido atualizado
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para deletar um ProdutosPedido com Id Específico
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {

        //Utilizar o método delete na classe ProdutoPedidoService
        produtoPedidoService.delete(id);

        //Deletar um ProdutosPedido com Id Específico
        return ResponseEntity.noContent().build();
    }
}
