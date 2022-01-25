package github.kassandra1810.order_service.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private List<UserPermission> permissions;
}
