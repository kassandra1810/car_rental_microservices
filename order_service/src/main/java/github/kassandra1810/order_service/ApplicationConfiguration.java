package github.kassandra1810.order_service;

import github.kassandra1810.order_service.persistence.OrderDAO;
import github.kassandra1810.order_service.services.CarService;
import github.kassandra1810.order_service.gateways.rest.CarServiceClient;
import github.kassandra1810.order_service.services.OrderService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CarService getCarService(CarServiceClient carServiceClient) {
        return new CarService(carServiceClient);
    }

    @Bean
    public OrderService getOrderService(OrderDAO orderDAO, CarService carService) {
        return new OrderService(orderDAO, carService);
    }
}
