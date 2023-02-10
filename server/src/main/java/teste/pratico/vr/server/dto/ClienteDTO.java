package teste.pratico.vr.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.pratico.vr.server.model.Cliente;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClienteDTO {

    private Long id;

    private String nome;

    private BigDecimal limiteDeCompra;

    @Min(value = 1)
    @Max(value = 31)
    private Integer diaDeFechamentoDaFatura;

    private List<PedidoDTO> pedidos = new ArrayList<>();

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.limiteDeCompra = cliente.getLimiteDeCompra();
        this.diaDeFechamentoDaFatura = cliente.getDiaDeFechamentoDaFatura();

        List<PedidoDTO> listaDePedidosDTO = new ArrayList<>();
        cliente.getPedidos().forEach(pedidos1 -> listaDePedidosDTO.add(new PedidoDTO(pedidos1)));
        this.pedidos = listaDePedidosDTO;
    }
}
