package teste.pratico.vr.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.pratico.vr.server.dto.PedidosDTO;
import teste.pratico.vr.server.service.PedidosService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/pedidos")
public class PedidosController {

    private final PedidosService pedidosService;

    /**
     * Método para retornar uma Lista de Pedidos
     * @return Lista de PedidosDTO
     */
    @GetMapping
    public ResponseEntity<List<PedidosDTO>> findAll() {

        //Utilizar o método findALl na classe PedidosServices
        var listaPedidos = pedidosService.findAll();

        //Retornar uma Lista de Pedidos
        return ResponseEntity.ok().body(listaPedidos);
    }

    /**
     * Método para retornar um Pedido com Id Específico
     * @param id
     * @return PedidoDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidosDTO> findById(@PathVariable Long id) {

        //Utilizar o método findById na classe PedidosService
        var pedido = pedidosService.findById(id);

        //Retornar um Pedido com Id Específico
        return ResponseEntity.ok().body(pedido);
    }

    /**
     * Método para adicionar um Pedido ao banco de dados
     * @param pedidoDTO
     * @return PedidoDTO persistido
     */
    @PostMapping
    public ResponseEntity<PedidosDTO> insert(@RequestBody PedidosDTO pedidoDTO) {

        //Utilizar o método insert na classe PedidosService
        var response = pedidosService.insert(pedidoDTO);

        //Retornar o Pedido persistido
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Método para atualizar um Pedido no banco de dados
     * @param pedidoDTO
     * @return PedidoDTO atualizado
     */
    @PutMapping
    public ResponseEntity<PedidosDTO> update (@RequestBody PedidosDTO pedidoDTO) {

        //Utilizar o método update na classe PedidosService
        var response = pedidosService.update(pedidoDTO);

        //Retornar o Pedido atualizado
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para deletar um Pedido com Id Específico
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {

        //Utilizar o método delete na classe PedidosService
        pedidosService.delete(id);

        //Deletar um Pedido com Id Específico
        return ResponseEntity.noContent().build();
    }
}
