package teste.pratico.vr.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.pratico.vr.server.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
