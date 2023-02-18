package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teste.pratico.vr.server.dto.ClienteDTO;
import teste.pratico.vr.server.dto.PedidosDTO;
import teste.pratico.vr.server.dto.ProdutoPedidoDTO;
import teste.pratico.vr.server.exception.runtime.ObjectNotFoundException;
import teste.pratico.vr.server.exception.runtime.PersistFailedException;
import teste.pratico.vr.server.model.Pedidos;
import teste.pratico.vr.server.model.ProdutoPedido;
import teste.pratico.vr.server.repositories.ClienteRepository;
import teste.pratico.vr.server.repositories.PedidosRepository;
import teste.pratico.vr.server.repositories.ProdutoPedidoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidosService {

    private final ModelMapper mapper;

    private final ClienteRepository clienteRepository;

    private final PedidosRepository pedidosRepository;

    private final ProdutoPedidoRepository produtoPedidoRepository;


    /**
     * Método para retornar uma Lista de Pedidos
     * @return Lista de PedidosDTO
     */
    public List<PedidosDTO> findAll() {

        //Utilizar o método findAll na camada PedidoRepository
        var listaPedidos = pedidosRepository.findAll();

        //Converter lista de Pedido para PedidoDTO e retornar a lista
        return listaPedidos.stream()
                .map(pedidos -> new ModelMapper().map(pedidos, PedidosDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar um Pedido com Id Específico
     * @param id
     * @return PedidoDTO
     */
    public PedidosDTO findById(Long id) {

        //Utilizar o método findById na camada PedidoRepository, caso não encontrar, lançar a exception
        var pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido de ID: " + id + " não encontrado"));
        return mapper.map(pedido, PedidosDTO.class);
    }

    /**
     * Método para adicionar um Pedido ao banco de dados
     * @param pedidoDTO
     * @return PediddoDTO persistido
     */
    public PedidosDTO insert(PedidosDTO pedidoDTO) {
        Pedidos pedido;

        //SetId no PedidoDTO, para evitar que o cliente seja atualizado nesse método
        pedidoDTO.setId(null);

        LocalDate dataPedido = LocalDate.now();
        pedidoDTO.setDataPedido(dataPedido);

        //Definindo o valor total do pedido
        pedidoDTO.setValorTotal(valorTotalPedido(pedidoDTO.getProdutosPedidos()));

        //Verificação de crédito
        if (verificacaoDeCredito(pedidoDTO) != Boolean.TRUE) {
            throw new PersistFailedException("Verificação de credito falhou");
        }

        //Converter PedidoDTO em cliente
        var request = mapper.map(pedidoDTO, Pedidos.class);

        //Salvar o novo pedido utilizando a PedidoRepository
        try {
            pedido = pedidosRepository.save(request);
        } catch (
                Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }

        //Atribuindo valores para a lista de produtoPedido
        for (ProdutoPedidoDTO pedProd : pedidoDTO.getProdutosPedidos()) {

            var prodPedPers = produtoPedidoRepository.findById(pedProd.getId());
            mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            mapper.map(prodPedPers, ProdutoPedidoDTO.class);

            prodPedPers.get().setPedido(pedido);

            produtoPedidoRepository.save(mapper.map(prodPedPers, ProdutoPedido.class));
        }

        //Converter o novo pedido em PedidoeDTO novamente e retornar
        return mapper.map(pedido, PedidosDTO.class);
    }

    /**
     * Método para atualizar um Pedido no banco de dados
     * @param pedidoDTO
     * @return PedidoDTO atualizado
     */
    public PedidosDTO update(PedidosDTO pedidoDTO) {

        //Verificação se existe um ID no pedidoDTO, caso não tenha, lançar uma exception
        if (pedidoDTO.getId() == null) {
            throw new ObjectNotFoundException("ID: " + pedidoDTO.getId() + " não encontrado");
        }

        //Configurar mapper, para converter apenas os atributos que não estão nulos
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Buscar o pedido persistido utilizando PedidoRepository
        var pedidoPersistido = this.findById(pedidoDTO.getId());

        //Converter as atualizações do pedidoDTO para o Pedido
        mapper.map(pedidoDTO, pedidoPersistido);

        //Salvar o pedido atualizado utilizando a PedidoRepository
        try {
            var novoProdutoPersistido = pedidosRepository.save(mapper.map(pedidoPersistido, Pedidos.class));
            return mapper.map(novoProdutoPersistido, PedidosDTO.class);

            //Converter o novo pedido em PedidoDTO novamente e retornar
        } catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }
    }

    /**
     * Método para deletar um Pedido com Id Específico
     * @param id
     */
    public void delete(Long id) {

        //Deletar o pedido utilizando a PedidoRepository, caso não exista, lançar uma exception
        try {
            pedidosRepository.deleteById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("ID: " + id + " not found");
        }
    }

    private Double valorTotalPedido(List<ProdutoPedidoDTO> produtoPedidoDTO) {

        Double valorTotalPedido = 0.00;

        //Utilizando foreach para verificar cada item da lista de produtoPedidoDTO
        for (ProdutoPedidoDTO x : produtoPedidoDTO) {

            var produtoPedidoDTO1 = produtoPedidoRepository.findById(x.getId());

            //Somando o valore de cada pedido em uma variável
            valorTotalPedido += produtoPedidoDTO1.get().getValorPedido();
        }

        //Retornando o valor total do pedido
        return valorTotalPedido;
    }

    private Boolean verificacaoDeCredito(PedidosDTO pedidoDTO) {

        //Buscando o limite de crédito do cliente
        var cliente = clienteRepository.findById(pedidoDTO.getCliente().getId());

        //Validando a verificação de crédito
        return (cliente.get().getLimiteDeCompra()) > pedidoDTO.getValorTotal();
    }
}
