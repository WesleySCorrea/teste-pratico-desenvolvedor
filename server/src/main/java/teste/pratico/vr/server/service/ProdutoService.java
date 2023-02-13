package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teste.pratico.vr.server.dto.ProdutoDTO;
import teste.pratico.vr.server.exception.runtime.ObjectNotFoundException;
import teste.pratico.vr.server.exception.runtime.PersistFailedException;
import teste.pratico.vr.server.model.Produto;
import teste.pratico.vr.server.repositories.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ModelMapper mapper;
    private final ProdutoRepository produtoRepository;

    /**
     * Método para retornar uma Lista de Produtos
     * @return Lista de ProdutosDTO
     */
    public List<ProdutoDTO> findAll() {

        //Utilizar o método findAll na camada ProdutoRepository
        var listaProduto = produtoRepository.findAll();

        //Converter lista de Produto para ProdutoDTO e retornar a lista
        return listaProduto.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar um Produto com Id Específico
     * @param id
     * @return ProdutoDTO
     */
    public ProdutoDTO findById(Long id) {

        //Utilizar o método findById na camada ProdutoRepository, caso não encontrar, lançar a exception
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto de ID: " + id + " não encontrado"));
        return mapper.map(produto, ProdutoDTO.class);
    }

    /**
     * Método para adicionar um Produto ao banco de dados
     * @param produtoDTO
     * @return ProdutoDTO persistido
     */
    public ProdutoDTO insert(ProdutoDTO produtoDTO) {
        Produto produto;

        //SetId no produtoDTO, para evitar que o produto seja atualizado nesse método
        produtoDTO.setId(null);

        //Converter ProdutoDTO em Produto
        var request = mapper.map(produtoDTO, Produto.class);

        //Salvar o novo produto utilizando a ProdutoRepository
        try {
            produto = produtoRepository.save(request);
        } catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }

        //Converter o novo produto em ProdutoDTO novamente e retornar
        return mapper.map(produto, ProdutoDTO.class);
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     * @param produtoDTO
     * @return ProdutoDTO atualizado
     */
    public ProdutoDTO update(ProdutoDTO produtoDTO) {

        //Verificação se existe um ID no produtoDTO, caso não tenha, lançar uma exception
        if (produtoDTO.getId() == null) {
            throw new ObjectNotFoundException("ID: " + produtoDTO.getId() + " não encontrado");
        }

        //Configurar mapper, para converter apenas os atributos que não estão nulos
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Buscar o produto persistido utilizando ProdutoRepository
        var produtoPersistido = this.findById(produtoDTO.getId());

        //Converter as atualizações do produtoDTO para o Produto
        mapper.map(produtoDTO, produtoPersistido);

        //Salvar o produto atualizado utilizando a ProdutoRepository
        try {
            var novoProdutoPersistido = produtoRepository.save(mapper.map(produtoPersistido,Produto.class));

            //Converter o novo produto em ProdutoDTO novamente e retornar
            return mapper.map(novoProdutoPersistido,ProdutoDTO.class);
        }catch (Exception e) {
            throw new PersistFailedException("Falha ao persistir o objeto");
        }
    }

    /**
     * Método para deletar um Produto com Id Específico
     * @param id
     */
    public void delete(Long id) {

        //Deletar o produto utilizando a ProdutoRepository, caso não exista, lançar uma exception
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ObjectNotFoundException("ID: " + id + " not found");
        }
    }
}
