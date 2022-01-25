package github.kassandra1810.order_service.persistence;

import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDAOImpl implements OrderDAO {

    private OrderRepository orderRepository;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orderRepository.findOrderByOrderStatus(status);
    }

    @Override
    public Order save(@Valid Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> updateOrder(String orderId, final Order orderDetails) {
        Optional<Order> oldOrder = findById(orderId);
        if (oldOrder.isPresent()) {
            oldOrder.get().setOrderStatus(OrderStatus.CANCELLED);
            Order newOrder = new Order(orderId,orderDetails.getCarId(), LocalDateTime.now(), orderDetails.getBookStartDate(), orderDetails.getBookEndDate(), OrderStatus.CREATED);
            orderRepository.save(newOrder);
            return Optional.of(newOrder);
        } else {
            return Optional.empty();
        }
    }


}
