package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.persistence.OrderRepository;
import github.kassandra1810.order_service.persistence.OrderDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelDAOIntegrationTest {
    // TODO add tests

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @AfterEach
    public void deleteTestDataAfterTest() {
        orderRepository.deleteAll();
    }
}
