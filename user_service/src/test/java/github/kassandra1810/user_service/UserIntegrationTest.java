package github.kassandra1810.user_service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.cloud.stream.test.matcher.MessageQueueMatcher;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = UserServiceApplication.class)
class UserIntegrationTest {

    String port = "9090";
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private Source channels;

    @Autowired
    private MessageCollector collector;

    @Test
    void deleteUserSendsMessage() {
        restTemplate.delete("http://localhost:" + port + "/users/0");
        BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());
        assertThat(messages, MessageQueueMatcher.receivesPayloadThat(is("deleted user 0")));
    }




}
