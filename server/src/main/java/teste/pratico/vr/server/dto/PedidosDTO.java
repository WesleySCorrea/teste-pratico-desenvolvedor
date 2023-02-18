package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teste.pratico.vr.server.model.Pedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PedidosDTO {

    private Long id;

    private Double valorTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    private LocalDate dataPedido;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;

    private List<ProdutoPedidoDTO> produtosPedidos = new ArrayList<>();


    public PedidosDTO(Pedidos pedidos) {
        this.id = pedidos.getId();
        this.valorTotal = pedidos.getValorTotal();
        this.dataPedido = pedidos.getDataPedido();

        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedidos.getCliente().getId());
        this.cliente = clienteDTO;

        List<ProdutoPedidoDTO> listaDeProdutosPedidoDTO = new ArrayList<>();
        pedidos.getProdutosPedidos().forEach(produtosPedidos1 -> listaDeProdutosPedidoDTO.add(new ProdutoPedidoDTO(produtosPedidos1)));
        this.produtosPedidos = listaDeProdutosPedidoDTO;
    }
}
