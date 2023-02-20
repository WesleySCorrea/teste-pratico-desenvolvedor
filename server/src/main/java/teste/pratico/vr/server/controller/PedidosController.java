package teste.pratico.vr.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.pratico.vr.server.dto.PedidosDTO;
import teste.pratico.vr.server.service.PedidosService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @GetMapping(value = "/data/{dataStringFatura}")
    public ResponseEntity<List<PedidosDTO>> findByPedidoWithData (@PathVariable String dataStringFatura) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataFatura = LocalDate.parse(dataStringFatura,formatter);

        //Utilizar o método findByPedidoWithDataFatura na classe PedidosService
        var response = pedidosService.findByPedidoWithDataFatura(dataFatura);

        //Retornar uma Lista de Pedidos a partir da data da fatura
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para retornar uma Lista de Pedidos de um cliente específico
     * @param id
     * @return Lista de PedidosDTO
     */
    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<List<PedidosDTO>> findByCliente (@PathVariable Long id) {

        //Utilizar o método findByCliente na classe PedidosService
        var response = pedidosService.findByCliente(id);

        //Retornar uma Lista de Pedidos de um cliente com id específico
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para retornar uma Lista de Pedidos de entre as datas especificas
     * @param dataStringInicio
     * @param dataStringFinal
     * @return Lista de PedidosDTO
     */
    @GetMapping(value = "/entredatas/{dataStringInicio}/{dataStringFinal}")
    public ResponseEntity<List<PedidosDTO>> findByCliente (@PathVariable String dataStringInicio, @PathVariable String dataStringFinal) {

        //Formatar o String data que veio na url e passar para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataInicio = LocalDate.parse(dataStringInicio,formatter);
        LocalDate dataFinal = LocalDate.parse(dataStringFinal,formatter);

        //Utilizar o método findByDate na classe PedidosService
        var response = pedidosService.findByDate(dataInicio, dataFinal);


        //Retornar uma Lista de Pedidos realizados entra as datas
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para retornar uma Lista de Pedidos de que contenham o id do produto específico
     * @param id
     * @return Lista de PedidosDTO
     */
    @GetMapping(value = "/produtos/{id}")
    public ResponseEntity<List<PedidosDTO>> findByProdutos (@PathVariable Long id) {

        //Utilizar o método findByProduto na classe PedidosService
        var response = pedidosService.findByProduto(id);

        //Retornar uma Lista de Pedidos que contém o produto com id específico
        return ResponseEntity.ok().body(response);
    }
}
