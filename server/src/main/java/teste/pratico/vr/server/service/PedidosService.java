package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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

        //Definindo a data do pedido
        pedidoDTO.setDataPedido(LocalDate.now());

        //Definindo o valor total do pedido
        pedidoDTO.setValorTotal(this.valorTotalPedido(pedidoDTO.getProdutosPedidos()));

        LocalDate dataInicioDaFatura = this.valorDataDoInicioDaFatura(pedidoDTO);
        LocalDate dataFechamentoDaFatura = this.valorDataDoFimDaFatura(dataInicioDaFatura);

        //Verificação de crédito
        if (verificacaoDeCredito(pedidoDTO, dataInicioDaFatura) != Boolean.TRUE) {
            throw new PersistFailedException("Verificação de credito falhou: " +
                    "O limite de crédito do cliente é de R$" + this.creditoDisponivel(pedidoDTO, dataInicioDaFatura)
            + " e a data de fechamento da fatura é dia " + dataFechamentoDaFatura);
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

    public List<PedidosDTO> findByPedidoWithDataFatura(LocalDate dateFatura) {

        //Utilizar o método findByPedidoWithData na camada PedidoRepository
        var listaPedidoComData = pedidosRepository.findByPedidoWithDataFatura(dateFatura);

        //Converter lista de Pedido para PedidoDTO e retornar a lista
        return listaPedidoComData.stream()
                .map(pedidos -> new ModelMapper().map(pedidos, PedidosDTO.class))
                .collect(Collectors.toList());
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

    private Boolean verificacaoDeCredito(PedidosDTO pedidoDTO, LocalDate dataInicioDaFatura) {

        //Verificando credito do cliente
        var creditoDisponivel = this.creditoDisponivel(pedidoDTO, dataInicioDaFatura);

        //Validando a verificação de crédito com a compra atual
        return creditoDisponivel > pedidoDTO.getValorTotal();
    }

    private Double creditoDisponivel(PedidosDTO pedidoDTO, LocalDate dataDaFatura) {

        //Buscando o limite de crédito do cliente
        var cliente = clienteRepository.findById(pedidoDTO.getCliente().getId());

        //Validando a fatura
//        LocalDate dataDaFatura = this.valorDataDoInicioDaFatura(pedidoDTO, dataD);

        //Listando todas as compras do cliente em relação a fatura
        var pedidosPorData = pedidosRepository.findByPedidoWithDataFaturaAndCliente(dataDaFatura, pedidoDTO.getCliente().getId());

        //Calculando o valor total da fatura
        var valorTotalFatura = 0;

        for (Pedidos pedidos: pedidosPorData) {
            valorTotalFatura += pedidos.getValorTotal();
        }

        var valorTotalDeCredito = cliente.get().getLimiteDeCompra() - valorTotalFatura;

            return valorTotalDeCredito;
    }

    private LocalDate valorDataDoInicioDaFatura (PedidosDTO pedidosDTO) {

        var cliente = clienteRepository.findById(pedidosDTO.getCliente().getId());
        var diaDaFatura = cliente.get().getDiaDeFechamentoDaFatura();
        LocalDate dataDaFatura;

        //Validando o dia da fatura
        if (diaDaFatura >= LocalDate.now().lengthOfMonth()) {
            dataDaFatura = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        } else {
            dataDaFatura = LocalDate.now().withDayOfMonth(diaDaFatura);
        }

        //Validando o mes da fatura
        if (dataDaFatura.isAfter(LocalDate.now()) && dataDaFatura.getMonthValue() == 1) {
            dataDaFatura = dataDaFatura.withMonth(12).minusYears(1);
        } else if (dataDaFatura.isAfter(LocalDate.now())) {
            dataDaFatura = dataDaFatura.minusMonths(1);
        }

        return dataDaFatura;
    }

    private  LocalDate valorDataDoFimDaFatura (LocalDate dataInicioFatura) {

        LocalDate dataFimFatura;

        if (dataInicioFatura.getMonthValue() == 12) {
            dataFimFatura = dataInicioFatura.withMonth(1).plusYears(1);
        } else {
            dataFimFatura = dataInicioFatura.plusMonths(1);
        }

        return dataFimFatura;
    }
}
