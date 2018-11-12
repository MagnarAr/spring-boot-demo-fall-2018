package ee.ut.cs.wad2018.fall.springbootdemo.security.smartid;

import ee.sk.smartid.AuthenticationResponseValidator;
import ee.sk.smartid.SmartIdClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartIdClientConfig {

    @Bean
    SmartIdClient smartIdClient() {
        SmartIdClient client = new SmartIdClient();
        client.setRelyingPartyUUID("00000000-0000-0000-0000-000000000000");
        client.setRelyingPartyName("DEMO");
        client.setHostUrl("https://sid.demo.sk.ee/smart-id-rp/v1/");
        return client;
    }

    @Bean
    AuthenticationResponseValidator authenticationResponseValidator() {
        return new AuthenticationResponseValidator();
    }

}
