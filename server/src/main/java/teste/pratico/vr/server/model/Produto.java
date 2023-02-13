package teste.pratico.vr.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Produto {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", unique=true)
    private String descricao;

    @Column(name = "preco")
    private Double preco;

//    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
//    private List<ProdutoPedido> ListaProdutoPedido = new ArrayList<>();
}