package teste.pratico.vr.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descrição", unique=true)
    private String descricao;

    @Column(name = "preço")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}