package github.kassandra1810.order_service.gateways.messaging;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Data
public class MessageListenerService {
    //TODO Add actual implementation
    private Logger logger = LoggerFactory.getLogger(MessageListenerService.class);
    private Pattern pattern = Pattern.compile("deleted user (\\d+)");

    public MessageListenerService() {
        logger.info("********** Starting Lease Service Message Listener *********");
    }

    // Spring will call this function each time a message arrives on the configured Kafka topic, with a string value such as "deleted user 1"
    @StreamListener(Sink.INPUT)
    public void consume(String message) {
        logger.info("Order Service Received Message: {0}", message);
        Matcher matcher = pattern.matcher(message);
        if(matcher.matches()) {
            int userId = Integer.parseInt(matcher.group(1));
            logger.info("All orders of user # {0} should be deleted", userId);
        }
    }
}
