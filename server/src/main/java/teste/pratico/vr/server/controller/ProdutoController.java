package teste.pratico.vr.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.pratico.vr.server.dto.ProdutoDTO;
import teste.pratico.vr.server.service.ProdutoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * Método para retornar uma Lista de Produtos
     * @return Lista de ProdutosDTO
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {

        //Utilizar o método findALl na classe ProdutoService
        var listaProduto = produtoService.findAll();

        //Retornar uma Lista de Produtos
        return ResponseEntity.ok().body(listaProduto);
    }

    /**
     * Método para retornar um Produto com Id Específico
     * @param id
     * @return ProdutoDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {

        //Utilizar o método findById na classe ProdutoService
        var produto = produtoService.findById(id);

        //Retornar um Produto com Id Específico
        return ResponseEntity.ok().body(produto);
    }

    /**
     * Método para adicionar um Produto ao banco de dados
     * @param produtoDTO
     * @return ProdutoDTO persistido
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> insert(@RequestBody ProdutoDTO produtoDTO) {

        //Utilizar o método insert na classe ProdutoService
        var response = produtoService.insert(produtoDTO);

        //Retornar o Produto persistido
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     * @param produtoDTO
     * @return ProdutoDTO atualizado
     */
    @PutMapping
    public ResponseEntity<ProdutoDTO> update (@RequestBody ProdutoDTO produtoDTO) {

        //Utilizar o método update na classe ProdutoService
        var response = produtoService.update(produtoDTO);

        //Retornar o Produto atualizado
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para deletar um Cliente com Id Específico
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {

        //Utilizar o método delete na classe ProdutoService
        produtoService.delete(id);

        //Deletar um Produto com Id Específico
        return ResponseEntity.noContent().build();
    }
}
