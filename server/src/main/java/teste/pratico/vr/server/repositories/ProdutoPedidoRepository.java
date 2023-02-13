package teste.pratico.vr.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.pratico.vr.server.model.ProdutoPedido;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {
}
