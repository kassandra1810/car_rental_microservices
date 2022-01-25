package github.kassandra1810.order_service.persistence;

import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.model.OrderStatus;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;

import java.util.List;

public interface OrderRepository extends CouchbaseRepository<Order, String> {

    List<Order> findOrderByOrderStatus(OrderStatus status);

    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter}")
    List<Order> findAll();

}
