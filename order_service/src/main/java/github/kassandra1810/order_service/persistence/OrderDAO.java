package github.kassandra1810.order_service.persistence;

import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.model.OrderStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order save(@Valid Order order);
    Optional<Order> findById(String id);
    List<Order> findOrdersByStatus(OrderStatus status);
    Iterable<Order> getOrders();
    Optional<Order> updateOrder(String id, final Order leaseDetails);
}
