package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
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
     * @return Lista de ClientesDTO
     */
    public List<ClienteDTO> findAll() {

        //Utilizar o método findAll na camada ClienteRepository
        var listaCliente = clienteRepository.findAll();

        //Converter lista de Cliente para ClienteDTO e retornar a lista
        return listaCliente.stream()
                .map(cliente -> new ModelMapper().map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar um Cliente com Id Específico
     * @param id
     * @return ClienteDTO
     */
    public ClienteDTO findById(Long id) {

        //Utilizar o método findById na camada ClienteRepository, caso não encontrar, lançar a exception
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente ID: " + id + " não encontrado"));
        return mapper.map(cliente, ClienteDTO.class);
    }

    /**
     * Método para adicionar um Cliente ao banco de dados
     * @param clienteDTO
     * @return ClienteDTO persistido
     */
    public ClienteDTO insert(ClienteDTO clienteDTO) {
        Cliente cliente;

        //SetId no clienteDTO, para evitar que o cliente seja atualizado nesse método
        clienteDTO.setId(null);

        //Validar o novo dia de fechamento da fatura
        if (clienteDTO.getDiaDeFechamentoDaFatura() > 31 || clienteDTO.getDiaDeFechamentoDaFatura() < 1) {
            throw new ValidationNotPermissionException("dia " + clienteDTO.getDiaDeFechamentoDaFatura() + " para o fechamento da fatura não é valido!");
        }

        //Converter ClienteDTO em cliente
        var request = mapper.map(clienteDTO, Cliente.class);

        //Salvar o novo cliente utilizando a ClienteRepository
        try {
            cliente = clienteRepository.save(request);
        } catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }

        //Converter o novo cliente em ClienteDTO novamente e retornar
        return mapper.map(cliente, ClienteDTO.class);
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     * @param clienteDTO
     * @return ClienteDTO atualizado
     */
    public ClienteDTO update(ClienteDTO clienteDTO) {

        //Verificação se existe um ID no clienteDTO, caso não tenha, lançar uma exception
        if (clienteDTO.getId() == null) {
            throw new ObjectNotFoundException("ID: " + clienteDTO.getId() + " não encontrado");
        }

        //Validar o novo dia de fechamento da fatura
        if (clienteDTO.getDiaDeFechamentoDaFatura() > 31 || clienteDTO.getDiaDeFechamentoDaFatura() < 1) {
            throw new ValidationNotPermissionException("dia " + clienteDTO.getDiaDeFechamentoDaFatura() + " para o fechamento da fatura não é valido!");
        }

        //Configurar mapper, para converter apenas os atributos que não estão nulos
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Buscar o cliente persistido utilizando ClienteRepository
        var clientePersistido = this.findById(clienteDTO.getId());

        //Converter as atualizações do clienteDTO para o Cliente
        mapper.map(clienteDTO, clientePersistido);

        //Salvar o cliente atualizado utilizando a ClienteRepository
        try {
            var novoClientePersistido = clienteRepository.save(mapper.map(clientePersistido,Cliente.class));

            //Converter o novo cliente em ClienteDTO novamente e retornar
            return mapper.map(novoClientePersistido,ClienteDTO.class);
        }catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }
    }

    /**
     * Método para deletar um Cliente com Id Específico
     * @param id
     */
    public void delete(Long id) {

        //Deletar o cliente utilizando a ClienteRepository, caso não exista, lançar uma exception
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("ID: " + id + " not found");
        }
    }
}
