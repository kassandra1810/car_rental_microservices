package github.kassandra1810.order_service.gateways.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(value = "car-service")
public interface CarServiceClient {

//    @PostMapping (value = "/reservation")
//    boolean bookCar(@RequestBody String carId, LocalDateTime dateFrom, LocalDateTime dateTo);

//    @PutMapping(value = "/reservation/{id}")
//    boolean unbookCar(@PathVariable String id, @RequestBody LocalDateTime dateFrom, LocalDateTime dateTo);
}
