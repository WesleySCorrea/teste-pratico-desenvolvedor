package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teste.pratico.vr.server.dto.ClienteDTO;
import teste.pratico.vr.server.exception.runtime.ValidationNotPermissionException;
import teste.pratico.vr.server.exception.runtime.ObjectNotFoundException;
import teste.pratico.vr.server.exception.runtime.PersistFailedException;
import teste.pratico.vr.server.model.Cliente;
import teste.pratico.vr.server.repositories.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ModelMapper mapper;
    private final ClienteRepository clienteRepository;

    /**
     * Método para retornar uma Lista de Clientes
     *
     * @return Lista de ClientesDTO
     */
    public List<ClienteDTO> findAll() {

        var listaCliente = clienteRepository.findAll();
        return listaCliente.stream()
                .map(cliente -> new ModelMapper().map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar um Cliente com Id Específico
     *
     * @param id
     * @return ClienteDTO
     */
    public ClienteDTO findById(Long id) {

        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente ID: " + id + " não encontrado"));
        return mapper.map(cliente, ClienteDTO.class);
    }


    /**
     * Método para adicionar um Cliente ao banco de dados
     *
     * @param clienteDTO
     * @return ClienteDTO persistido
     */
    public ClienteDTO insert(ClienteDTO clienteDTO) {
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);

        if (clienteDTO.getDiaDeFechamentoDaFatura() > 31 || clienteDTO.getDiaDeFechamentoDaFatura() < 1) {
            throw new ValidationNotPermissionException("dia " + clienteDTO.getDiaDeFechamentoDaFatura() + " para o fechamento da fatura não é valido!");
        }

        cliente.setId(null);

        try {
            clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }

        clienteDTO.setId(cliente.getId());

        return clienteDTO;
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     *
     * @param clienteDTO
     * @return ClienteDTO atualizado
     */
    public ClienteDTO update(ClienteDTO clienteDTO) {

        if (clienteDTO.getDiaDeFechamentoDaFatura() > 31 || clienteDTO.getDiaDeFechamentoDaFatura() < 1) {
            throw new ValidationNotPermissionException("dia " + clienteDTO.getDiaDeFechamentoDaFatura() + " para o fechamento da fatura não é valido!");
        }
        if (clienteDTO.getId() == null) {
            throw new ObjectNotFoundException("ID: " + clienteDTO.getId() + " não encontrado");
        }

        if (this.clienteRepository.findById(clienteDTO.getId()).isPresent()) {

            var clientePersistido = this.findById(clienteDTO.getId());

            var request = mapper.map(clienteDTO, Cliente.class);

            Cliente cliente = clienteRepository.save(request);

            var clienteAtualizado = mapper.map(cliente, ClienteDTO.class);

            return clienteAtualizado;
        } else {
            throw new ObjectNotFoundException("ID: " + clienteDTO.getId() + " não encontrado");
        }
    }

    /**
     * Método para deletar um Cliente com Id Específico
     *
     * @param id
     */
    public void delete(Long id) {

        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("ID: " + id + " not found");
        }
    }
}
