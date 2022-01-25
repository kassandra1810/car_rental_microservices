package github.kassandra1810.order_service.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean checkGetOrderPermission(String userJWT) {
        return true;
    }
}
