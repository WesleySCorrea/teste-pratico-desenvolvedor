package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.pratico.vr.server.model.Produto;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoDTO {

    private Long id;

    private String descricao;

    private BigDecimal preco;

    private PedidoDTO pedido;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();

        var pedidoDto = new PedidoDTO();
        pedidoDto.setId(pedido.getCliente().getId());
        this.pedido = pedidoDto;
    }
}
