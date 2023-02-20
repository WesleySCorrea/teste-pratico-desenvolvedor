package teste.pratico.vr.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import teste.pratico.vr.server.model.Pedidos;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {

    @Query(value = "select * from pedido p where p.data_pedido >= :dataFature", nativeQuery = true)
    List<Pedidos> findByPedidoWithDataFatura (@RequestParam(name = "dataFature") LocalDate dataFature);

    @Query(value = "select * from pedido p where p.data_pedido >= :dataFature and p.cliente_id = :id", nativeQuery = true)
    List<Pedidos> findByPedidoWithDataFaturaAndCliente (@RequestParam(name = "dataFature") LocalDate dataFature, @RequestParam(name = "id") Long id);
}
