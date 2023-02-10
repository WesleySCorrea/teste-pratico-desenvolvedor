package teste.pratico.vr.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "limite_de_compra")
    private BigDecimal limiteDeCompra;

    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "dia_de_fechamento_da_fatura")
    private Integer diaDeFechamentoDaFatura;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

}