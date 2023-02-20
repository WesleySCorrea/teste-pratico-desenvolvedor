package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teste.pratico.vr.server.dto.ProdutoDTO;
import teste.pratico.vr.server.dto.ProdutoPedidoDTO;
import teste.pratico.vr.server.exception.runtime.ObjectNotFoundException;
import teste.pratico.vr.server.exception.runtime.PersistFailedException;
import teste.pratico.vr.server.model.ProdutoPedido;
import teste.pratico.vr.server.repositories.ProdutoPedidoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoPedidoService {


    private final ModelMapper mapper;
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ProdutoService produtoService;

    /**
     * Método para retornar uma Lista de ProdutoPedido
     * @return Lista de ProdutoPedidoDTO
     */
    public List<ProdutoPedidoDTO> findAll() {

        //Utilizar o método findAll na camada ProdutoPedidoRepository
        var listaProdutoPedido = produtoPedidoRepository.findAll();

        //Converter lista de ProdutoPedido para ProdutoPedidoDTO e retornar a lista
        return listaProdutoPedido.stream()
                .map(produtoPedido -> new ModelMapper().map(produtoPedido, ProdutoPedidoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar um ProdutoPedido com Id Específico
     * @param id
     * @return ProdutoPedidoDTO
     */
    public ProdutoPedidoDTO findById(Long id) {

        //Utilizar o método findById na camada ProdutoPedidoRepository, caso não encontrar, lançar a exception
        var produtoPedido = produtoPedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto de ID: " + id + " não encontrado"));
        return mapper.map(produtoPedido, ProdutoPedidoDTO.class);
    }

    /**
     * Método para adicionar um ProdutoPedido ao banco de dados
     * @param produtoPedidoDTO
     * @return ProdutoPedidoDTO persistido
     */
    public ProdutoPedidoDTO insert(ProdutoPedidoDTO produtoPedidoDTO) {

        ProdutoPedido produtoPedido;

        //Setar automaticamente o atributo valorPedido do produtoPedidoDTO
        produtoPedidoDTO.setValorPedido(valorTotalProdutoPedido(produtoPedidoDTO));

        //Converter ProdutoPedidoDTO em ProdutoPedido
        var request = mapper.map(produtoPedidoDTO, ProdutoPedido.class);

        //Salvar o novo produtoPedido utilizando a ProdutoPedidoRepository
        try {
            produtoPedido = produtoPedidoRepository.save(request);
        } catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }

        //Converter o novo produtoPedido em ProdutoPedidoDTO novamente e retornar
        return mapper.map(produtoPedido, ProdutoPedidoDTO.class);
    }

    /**
     * Método para atualizar um ProdutoPedido no banco de dados
     * @param produtoPedidoDTO
     * @return produtoPedido atualizado
     */
    public ProdutoPedidoDTO update(ProdutoPedidoDTO produtoPedidoDTO) {

        //Verificação se existe um ID no produtoPedidoDTO, caso não tenha, lançar uma exception
        if (produtoPedidoDTO.getId() == null) {
            throw new ObjectNotFoundException("ID: " + produtoPedidoDTO.getId() + " não encontrado");
        }

        //Configurar mapper, para converter apenas os atributos que não estão nulos
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Buscar o produtoPedido persistido utilizando ProdutoPedidoRepository
        var produtoPedidoPersistido = this.findById(produtoPedidoDTO.getId());

        //Converter as atualizações do produtoPedidoDTO para o ProdutoPedido
        mapper.map(produtoPedidoDTO, produtoPedidoPersistido);

        //Salvar o produtoPedido atualizado utilizando a ProdutoPedidoRepository
        try {
            var novoProdutoPedidoPersistido = this.insert(produtoPedidoPersistido);

            //Converter o novo produto em ProdutoDTO novamente e retornar
            return mapper.map(novoProdutoPedidoPersistido,ProdutoPedidoDTO.class);
        }catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }
    }

    /**
     * Método para deletar um ProdutoPedido com Id Específico
     * @param id
     */
    public void delete(Long id) {

        //Deletar o produtoPedido utilizando a ProdutoPedidoRepository, caso não exista, lançar uma exception
        try {
            produtoPedidoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("ID: " + id + " not found");
        }
    }

    private Double valorTotalProdutoPedido(ProdutoPedidoDTO produtoPedidoDTO) {

        //Buscar o produto persistido no banco de dados para coletar informaçoes sobre o produto
        ProdutoDTO produtoDTO = produtoService.findById(produtoPedidoDTO.getProduto().getId());

        //Somar o valor total do produtoPedido
        Double valor = produtoDTO.getPreco() * produtoPedidoDTO.getQuantidade();

        //retornar o valor total
        return valor;
    }
}
