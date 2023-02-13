package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import teste.pratico.vr.server.model.Produto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoDTO {

    private Long id;

    private String descricao;

    private Double preco;

//    private List<ProdutoPedidoDTO> listaProdutoPedido = new ArrayList<>();

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();

//        List<ProdutoPedidoDTO> listaPedidoPedidoDTO = new ArrayList<>();
//        produto.getListaProdutoPedido().forEach(produtoPedido -> listaPedidoPedidoDTO.add(new ProdutoPedidoDTO(produtoPedido)));
//        this.listaProdutoPedido = listaPedidoPedidoDTO;
    }
}
