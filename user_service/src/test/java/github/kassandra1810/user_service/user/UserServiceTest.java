package github.kassandra1810.user_service.user;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    Source mockMessageSource = Mockito.mock(Source.class);
    final UserService userService = new UserService(mockMessageSource);

    @Test
    public void deleteUser() {
        MessageChannel mockMessageChannel = Mockito.mock(MessageChannel.class);
        Mockito.when(mockMessageSource.output()).thenReturn(mockMessageChannel);
        userService.deleteUser("1");
        final Optional<User> retrievedUser = userService.getUserById("1");
        assertFalse(retrievedUser.isPresent());
        Mockito.verify(mockMessageChannel).send(Mockito.any(Message.class));
    }

}