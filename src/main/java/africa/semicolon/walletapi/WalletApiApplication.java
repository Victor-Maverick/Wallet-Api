package africa.semicolon.walletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"africa.semicolon.walletapi.infrastructure.adapter", "africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper"})
public class WalletApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApiApplication.class, args);
    }

}
