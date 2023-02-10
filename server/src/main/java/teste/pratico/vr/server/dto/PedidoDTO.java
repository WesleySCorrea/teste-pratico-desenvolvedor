package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.pratico.vr.server.model.Pedido;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidoDTO {

    private Long id;

    private ClienteDTO cliente;

    private List<ProdutoDTO> produtos = new ArrayList<>();

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();

        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedido.getCliente().getId());
        this.cliente = clienteDTO;

        List<ProdutoDTO> listaDeProdutosDTO = new ArrayList<>();
        pedido.getProdutos().forEach(produtos1 -> listaDeProdutosDTO.add(new ProdutoDTO(produtos1)));
        this.produtos = listaDeProdutosDTO;
    }
}
