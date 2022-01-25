package github.kassandra1810.order_service.controller;

import github.kassandra1810.order_service.loadbalancer.RibbonConfiguration;
import github.kassandra1810.order_service.model.Order;
import github.kassandra1810.order_service.services.OrderService;
import github.kassandra1810.order_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orders")
@RibbonClient(
        name = "car-service",
        configuration = RibbonConfiguration.class)
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(final OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("test")
    public ResponseEntity<List<String>> test() {
        orderService.init();
            return new ResponseEntity<>(Arrays.asList("Ala ma kota", "Kasia ma psa", "Robert ma konia"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader(value = "userJWT") String userJWT) {
        boolean actionAllowed = userService.checkGetOrderPermission(userJWT);
        if(actionAllowed) {
            return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@RequestHeader(value = "userJWT") String userJWT, @PathVariable final String id) {
        boolean actionAllowed = userService.checkGetOrderPermission(userJWT);
        if(actionAllowed) {
            Optional<Order> order = orderService.getOrderById(id);
            return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader(value = "userJWT") String userJWT,
                                             @RequestBody @Validated Order orderDetails) {
        boolean actionAllowed = userService.checkGetOrderPermission(userJWT);
        if(actionAllowed) {
            final Order createdOrder = orderService.createOrder(orderDetails);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@RequestHeader(value = "userJWT") String userJWT, @PathVariable String id, @RequestBody @Validated Order orderDetails) {
        boolean actionAllowed = userService.checkGetOrderPermission(userJWT);
        if(actionAllowed) {
            Optional<Order> order = orderService.updateOrder(id, orderDetails);
                return order.map(value -> new ResponseEntity<>(value, HttpStatus.NO_CONTENT))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@RequestHeader(value = "userJWT") String userJWT, @PathVariable String id) {
        boolean actionAllowed = userService.checkGetOrderPermission(userJWT);
        if(actionAllowed) {
            if (orderService.cancelOrder(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
