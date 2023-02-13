package teste.pratico.vr.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto_pedido")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProdutoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_produtos")
    private Integer quantidade;

    @Column(name = "valor_pedido")
    private Double valorPedido;

//    @UniqueConstraint()*****************

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido")
    private Pedidos pedido;
}
