package africa.semicolon.walletapi.infrastructure.adapter.config;

import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.services.PaystackService;
import africa.semicolon.walletapi.domain.services.UserService;
import africa.semicolon.walletapi.domain.services.WalletService;
import africa.semicolon.walletapi.infrastructure.adapter.UserPersistenceAdapter;
import africa.semicolon.walletapi.infrastructure.adapter.WalletPersistenceAdapter;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.UserRepository;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaystackService paystackService() {
        return new PaystackService();
    }

    @Bean
    public WalletService walletService(final WalletOutputPort walletOutputPort, final PaystackService paystackService) {
        return new WalletService(walletOutputPort,paystackService);
    }

    @Bean
    public WalletPersistenceAdapter persistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper, final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper, walletRepository, walletPersistenceMapper);
    }
    @Bean
    public UserService userService(final UserOutputPort userOutputPort, final WalletOutputPort walletOutputPort){
        return new UserService(userOutputPort, walletOutputPort);
    }
}

