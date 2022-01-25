package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.model.OrderStatus;
import github.kassandra1810.order_service.persistence.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private OrderDAO orderDAO;
    private CarService carService;

    @Autowired
    public OrderService(OrderDAO orderDAO, CarService carService) {
        this.orderDAO = orderDAO;
        this.carService = carService;
    }

    public Order createOrder(final Order orderDetails) {
        final String orderId = UUID.randomUUID().toString();
        final LocalDateTime orderDate = LocalDateTime.now();
        final String carId = orderDetails.getCarId();
        carService.bookCar(carId, orderDetails.getBookStartDate(), orderDetails.getBookEndDate());

        final Order order = new Order(orderId, carId, orderDate, orderDetails.getBookStartDate(), orderDetails.getBookEndDate(), OrderStatus.CREATED);

        orderDAO.save(order);

        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Iterable<Order> ordersFromDAO = orderDAO.getOrders();
        ordersFromDAO.forEach(orders::add);
        return orders;
    }

    public Optional<Order> getOrderById(final String orderId) {
        return orderDAO.findById(orderId);
    }

    public Optional<Order> updateOrder(final String orderId, final Order orderDetails) {
        return orderDAO.updateOrder(orderId,orderDetails);
    }

    public boolean cancelOrder(final String orderId) {
        Optional<Order> orderToCancel = orderDAO.findById(orderId);
        if (orderToCancel.isPresent()) {
            Order order = orderToCancel.get();
            order.setOrderStatus(OrderStatus.CANCELLED);
            final String carId = order.getCarId();
            carService.unbookCar(carId, order.getBookStartDate(), order.getBookEndDate());
            return true;
        }
        return false;
    }
}
