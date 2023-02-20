package teste.pratico.vr.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select * from pedido p where p.cliente_id  = :id", nativeQuery = true)
    List<Pedidos> findByCliente(@RequestParam(name = "id") Long id);

    @Query(value = "select * from pedido p  where p.data_pedido  >= :dataInicio and p.data_pedido  < :dataFinal", nativeQuery = true)
    List<Pedidos> findEntryDate (@RequestParam(name = "dataInicio") LocalDate dataInicio, @RequestParam(name = "dataFinal") LocalDate dataFinal);

    @Query(value = "select pedido.id, data_pedido, valor_total , cliente_id from pedido left join produto_pedido on pedido.id = produto_pedido.pedido where produto_pedido.produto_id = :x", nativeQuery = true)
    List<Pedidos> findByProdutos(@Param("x") Long id);
}
