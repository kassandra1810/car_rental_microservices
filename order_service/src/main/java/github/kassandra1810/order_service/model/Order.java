package github.kassandra1810.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Order {
    @Id
    private String id;
    @NotNull
    @Field
    private String carId;
    @NotNull
    @Field
    private LocalDateTime orderDate;
    @NotNull
    @Field
    private LocalDateTime bookStartDate;
    @NotNull
    @Field
    private LocalDateTime bookEndDate;
    @Field
    private OrderStatus orderStatus;

    public Order(String carId, LocalDateTime leaseDate, LocalDateTime bookStartDate, LocalDateTime bookEndDate) {
        this.carId = carId;
        this.orderDate = leaseDate;
        this.orderStatus = OrderStatus.CREATED;
        this.bookStartDate = bookStartDate;
        this.bookEndDate = bookEndDate;
    }
}
