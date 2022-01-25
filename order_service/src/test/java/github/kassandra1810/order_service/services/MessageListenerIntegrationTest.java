package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.OrderServiceApplication;
import github.kassandra1810.order_service.gateways.messaging.MessageListenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
class MessageListenerIntegrationTest {

    @Autowired
    private Sink channels;

    @SpyBean
    private MessageListenerService listenerService;

    Logger mockLogger = Mockito.mock(Logger.class);

    @BeforeEach
    public void testSetup() {
        listenerService.setLogger(mockLogger);
    }

    @Test
    void testDeleteMessageDeleteLeases() {
        channels.input().send(new GenericMessage<>("deleted user 3"));
        Mockito.verify(mockLogger).info("Order Service Received Message: {0}", "deleted user 3");
        Mockito.verify(mockLogger).info("All orders of user # {0} should be deleted", 3);
    }

    @Test
    void testDeleteMessageNoDeleteLeases() {
        channels.input().send(new GenericMessage<>("some other message"));
        Mockito.verify(mockLogger).info("Order Service Received Message: {0}", "some other message");
        Mockito.verifyNoMoreInteractions(mockLogger);
    }

}
