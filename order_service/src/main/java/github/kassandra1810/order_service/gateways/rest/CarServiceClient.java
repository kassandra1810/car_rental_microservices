package github.kassandra1810.order_service.gateways.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@FeignClient(value = "car-service")
public interface CarServiceClient {

    @PostMapping (value = "/reservation")
    boolean bookCar(@RequestBody String carId, LocalDateTime dateFrom, LocalDateTime dateTo);

    @DeleteMapping(value = "/reservation")
    boolean unbookCar(@RequestBody String carId, LocalDateTime dateFrom, LocalDateTime dateTo);
}
