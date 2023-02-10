package teste.pratico.vr.server.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import teste.pratico.vr.server.dto.ProdutoDTO;
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
    public List<ProdutoDTO> findAll(){

        var listaProduto = produtoRepository.findAll();
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

        var produto =  produtoRepository.findById(id);

        return mapper.map(produto, ProdutoDTO.class);
    }

    /**
     * Método para adicionar um Produto ao banco de dados
     * @param produtoDTO
     * @return ProdutoDTO persistido
     */
    public ProdutoDTO insert(ProdutoDTO produtoDTO) {
        Produto produto = mapper.map(produtoDTO, Produto.class);

        produtoRepository.save(produto);

        produtoDTO.setId(produto.getId());

        return produtoDTO;
    }

    /**
     * Método para atualizar um Cliente no banco de dados
     * @param produtoDTO
     * @return ProdutoDTO atualizado
     */
    public ProdutoDTO update(ProdutoDTO produtoDTO) {

        var produtoPersistido = this.findById(produtoDTO.getId());

        mapper.map(produtoDTO, produtoPersistido);

        var produtoAtualizado = this.insert(produtoPersistido);

        return produtoAtualizado;
    }

    /**
     * Método para deletar um Cliente com Id Específico
     * @param id
     */
    public void delete (Long id) {

        produtoRepository.deleteById(id);
    }
}
