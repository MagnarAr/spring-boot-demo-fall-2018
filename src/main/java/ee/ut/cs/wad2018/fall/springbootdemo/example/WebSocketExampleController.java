package ee.ut.cs.wad2018.fall.springbootdemo.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketExampleController {

    @GetMapping("/websocket-example")
    public String getWebsocketExamplePage() {
        return "websocket/subscription";
    }

}
