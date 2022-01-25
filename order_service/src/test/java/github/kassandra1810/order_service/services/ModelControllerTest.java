package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.controller.OrderController;
import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ModelControllerTest {

    OrderService orderService = Mockito.mock(OrderService.class);
    UserService userService = Mockito.mock(UserService.class);
    OrderController controller = new OrderController(orderService, userService);

    @Test
    void getAllOrders_With_Ok_Response() {
        final List<Order> mockOrders = new ArrayList<>();
        final Order order1 = new Order("1", "1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        final Order order2 = new Order("2", "3", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        mockOrders.add(order1);
        mockOrders.add(order2);
        Mockito.when(orderService.getAllOrders()).thenReturn(mockOrders);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<List<Order>> responseEntity = controller.getAllOrders("1");

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(mockOrders, responseEntity.getBody());

    }

    @Test
    void getAllOrders_unauthorized() {
        final List<Order> mockOrders = new ArrayList<>();
        final Order order1 = new Order("1", "1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        final Order order2 = new Order("2", "3", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        mockOrders.add(order1);
        mockOrders.add(order2);
        Mockito.when(orderService.getAllOrders()).thenReturn(mockOrders);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(false);

        final ResponseEntity<List<Order>> responseEntity = controller.getAllOrders("jwt1");

        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

    }

    @Test
    void getOrderById_Match() {
        Mockito.when(orderService.getOrderById(Mockito.anyString())).thenReturn(Optional
                .of(new Order("1", "carId", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED)));
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<Order> responseEntity = controller.getOrderById("123dee", "1");

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getOrderById_NoMatch() {
        Mockito.when(orderService.getOrderById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<Order> responseEntity = controller.getOrderById("jwt", "1");

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void getOrderById_Unauthorized() {
        Mockito.when(orderService.getOrderById(Mockito.anyString())).thenReturn(Optional
                .of(new Order("1", "carId", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED)));
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(false);

        final ResponseEntity<Order> responseEntity = controller.getOrderById("jwt", "1");

        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void createOrder() {
        final Order orderDetails = new Order("2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        final Order order = new Order("1", "2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(order);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<Order> responseEntity = controller.createOrder("jwt", orderDetails);

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(order, responseEntity.getBody());
    }

    @Test
    void createOrder_Unauthorized() {
        final Order orderDetails = new Order("2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        final Order order = new Order("1", "2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(order);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(false);

        final ResponseEntity<Order> responseEntity = controller.createOrder("jwt", orderDetails);

        assertEquals(401, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }


    @Test
    void updateUpdate_Match() {
        final Order leaseDetails = new Order("2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        Order order = new Order("1", "2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        Mockito.when(orderService.updateOrder(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<Order> responseEntity = controller.updateOrder("1234dff", "1", leaseDetails);

        assertEquals(204, responseEntity.getStatusCodeValue());
        assertEquals(order, responseEntity.getBody());
    }

    @Test
    void updateOrder_NoMatch() {
        final Order leaseDetails = new Order("2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(orderService.updateOrder(Mockito.anyString(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity<Order> responseEntity = controller.updateOrder("1234dff", "1", leaseDetails);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    void updateUpdate_Unauthorized() {
        final Order leaseDetails = new Order("2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        Order order = new Order("1", "2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), OrderStatus.CREATED);
        Mockito.when(orderService.updateOrder(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(false);

        final ResponseEntity<Order> responseEntity = controller.updateOrder("1234dff", "1", leaseDetails);

        assertEquals(401, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteOrder_Match() {
        Mockito.when(orderService.cancelOrder(Mockito.anyString())).thenReturn(true);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity responseEntity = controller.cancelOrder("1234dff", "1");

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteOrder_NoMatch() {
        Mockito.when(orderService.cancelOrder(Mockito.anyString())).thenReturn(false);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(true);

        final ResponseEntity responseEntity = controller.cancelOrder("123jwt", "1");

        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteOrder_Unauthorized() {
        Mockito.when(orderService.cancelOrder(Mockito.anyString())).thenReturn(true);
        Mockito.when(userService.checkGetOrderPermission(Mockito.anyString())).thenReturn(false);

        final ResponseEntity responseEntity = controller.cancelOrder("1234dff", "1");

        assertEquals(401, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

}