package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teste.pratico.vr.server.model.Pedidos;
import teste.pratico.vr.server.model.ProdutoPedido;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoPedidoDTO {

    private Long id;

    private Integer quantidade;

    private ProdutoDTO produto;

    private Double valorPedido;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PedidosDTO pedido;

//    private List<PedidosDTO> pedidos = new ArrayList<>();

    public ProdutoPedidoDTO(ProdutoPedido produtoPedido) {
        this.id = produtoPedido.getId();
        this.quantidade = produtoPedido.getQuantidade();
        this.valorPedido = produtoPedido.getValorPedido();

        var produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produtoPedido.getProduto().getId());
        this.produto = produtoDTO;

        var pedidoDTO = new PedidosDTO();
        pedidoDTO.setId(produtoPedido.getPedido().getId());
        this.pedido = pedidoDTO;

//        List<PedidosDTO> listaDePedidoDTO = new ArrayList<>();
//        produtoPedido.getPedidos().forEach(pedidos1 -> listaDePedidoDTO.add(new PedidosDTO(pedidos1)));
//        this.pedidos =listaDePedidoDTO;
    }
}
