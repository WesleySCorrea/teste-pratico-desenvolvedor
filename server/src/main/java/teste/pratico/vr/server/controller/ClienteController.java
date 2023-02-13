package teste.pratico.vr.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.pratico.vr.server.dto.ClienteDTO;
import teste.pratico.vr.server.service.ClienteService;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Método para retornar uma Lista de Clientes
     * @return Lista de ClientesDTO
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {

        //Utilizar o método findALl na classe ClienteService
        var listaCliente = clienteService.findAll();

        //Retornar uma Lista de CLientes
        return ResponseEntity.ok().body(listaCliente);
    }

    /**
     * Método para retornar um Cliente com Id Específico
     * @param id
     * @return ClienteDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {

        //Utilizar o método findById na classe ClienteService
        var cliente = clienteService.findById(id);

        //Retornar um CLiente com Id Específico
        return ResponseEntity.ok().body(cliente);
    }

    /**
     * Método para adicionar um Cliente ao banco de dados
     * @param clienteDTO
     * @return ClienteDTO persistido
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteDTO clienteDTO) {

        //Utilizar o método insert na classe ClienteService
        var response = clienteService.insert(clienteDTO);

        //Retornar o CLiente persistido
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Método para atualizar um Cliente no banco de dados
     * @param clienteDTO
     * @return ClienteDTO atualizado
     */
    @PutMapping
    public ResponseEntity<ClienteDTO> update (@Valid @RequestBody ClienteDTO clienteDTO) {

        //Utilizar o método update na classe ClienteService
        var response = clienteService.update(clienteDTO);

        //Retornar o CLiente atualizado
        return ResponseEntity.ok().body(response);
    }

    /**
     * Método para deletar um Cliente com Id Específico
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {

        //Utilizar o método delete na classe ClienteService
        clienteService.delete(id);

        //Deletar um CLiente com Id Específico
        return ResponseEntity.noContent().build();
    }
}
