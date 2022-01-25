package github.kassandra1810.order_service.services;

import github.kassandra1810.order_service.gateways.messaging.MessageListenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class MessageListenerServiceTest {

    Logger mockLogger = Mockito.mock(Logger.class);
    MessageListenerService listenerService = new MessageListenerService();

    @BeforeEach
    public void testSetup() {
        listenerService.setLogger(mockLogger);
    }

    @Test
    void testRegExpressionMatchDeleteOrders() {
        listenerService.consume("deleted user 3");
        Mockito.verify(mockLogger).info("Order Service Received Message: {0}", "deleted user 3");
        Mockito.verify(mockLogger).info("All orders of user # {0} should be deleted", 3);
    }

    @Test
    void testRegExpressionMatchNoDeleteOrders() {
        listenerService.consume("some other message");
        Mockito.verify(mockLogger).info("Order Service Received Message: {0}", "some other message");
        Mockito.verifyNoMoreInteractions(mockLogger);
    }

}