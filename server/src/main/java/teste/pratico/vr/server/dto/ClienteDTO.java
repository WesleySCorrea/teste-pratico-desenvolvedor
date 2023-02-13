package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import teste.pratico.vr.server.model.Cliente;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClienteDTO {

    private Long id;

    private String nome;

    private Double limiteDeCompra;

    @Min(value = 1)
    @Max(value = 31)
    private Integer diaDeFechamentoDaFatura;

    private List<PedidosDTO> listaPedidos = new ArrayList<>();

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.limiteDeCompra = cliente.getLimiteDeCompra();
        this.diaDeFechamentoDaFatura = cliente.getDiaDeFechamentoDaFatura();

        List<PedidosDTO> listaPedidoDTO = new ArrayList<>();
        cliente.getListaPedidos().forEach(pedidos -> listaPedidoDTO.add(new PedidosDTO(pedidos)));
        this.listaPedidos = listaPedidoDTO;
    }
}
