package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.gateways.rest.CarServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {
    // TODO Add actual connection to car service implementation

    CarServiceClient carServiceClient;

    @Autowired
    public CarService(CarServiceClient carServiceClient) {
        this.carServiceClient = carServiceClient;
    }

    public boolean bookCar(String carId, LocalDateTime startDate, LocalDateTime endDate) {
        return true;
    }

    public boolean unbookCar(String carId, LocalDateTime startDate, LocalDateTime endDate) {
        return true;
    }
}
