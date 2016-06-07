package gwt.material.design.chat.server;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.bus.server.annotations.Service;

/**
 * Created by Mark Kevin on 6/6/2016.
 */
@Service
public class HelloWorldService implements MessageCallback {


    @Override
    public void callback(Message message) {
        System.out.print("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
    }
}
