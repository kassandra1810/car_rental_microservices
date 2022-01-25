package github.kassandra1810.user_service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private Source messageSource;

    @Autowired
    public UserService(Source messageSource) {
        initUsers();
        this.messageSource = messageSource;
    }

    private void initUsers() {
        User user1 = new User("1", "John", "Wick", UserType.SUSPENDED, LocalDate.parse("1976-01-01"));
        User user2 = new User("2", "Lady", "Gaga", UserType.ACTIVE, LocalDate.parse("1980-01-01"));
        users.add(user1);
        users.add(user2);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User createUser(UserDetails userDetails) {
        final int newUserId = users.size();
        final User newUser = new User(String.valueOf(newUserId), userDetails);
        users.add(newUser);
        return newUser;
    }

    public User updateUser(final String userId, UserDetails userDetails) {
        users.removeIf(user -> user.getId().equals(userId));
        return new User(userId, userDetails);
    }

    public boolean deleteUser(final String userId) {
        if(getUserById(userId).isPresent()) {
            users.removeIf(user -> user.getId().equals(userId));
            messageSource.output().send(MessageBuilder.withPayload("deleted user " + userId).build());
            return true;
        } else {
            return false;
        }
    }


}
