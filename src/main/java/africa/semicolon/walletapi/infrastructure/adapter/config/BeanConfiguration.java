package africa.semicolon.walletapi.infrastructure.adapter.config;

import africa.semicolon.walletapi.application.ports.output.*;
import africa.semicolon.walletapi.domain.services.*;
import africa.semicolon.walletapi.infrastructure.adapter.*;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.TransactionPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.TransactionRepository;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.UserRepository;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.WalletRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class BeanConfiguration {
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.secretKey}")
    private String secretKey;
     @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;


    @Value("${cloud.api.name}")
    private String cloudName;
    @Value("${cloud.api.key}")
    private String cloudApiKey;
    @Value("${cloud.api.secret}")
    private String cloudApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<?, ?> map = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", cloudApiKey,
                "api_secret", cloudApiSecret
        );
        return new Cloudinary(map);
    }

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret(secretKey)
                .clientId(clientId)
                .grantType("client_credentials")
                .realm(realm)
                .serverUrl(serverUrl)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MonnifyService monnifyService(final RestTemplate restTemplate, final UserOutputPort userOutputPort){
        return new MonnifyService(restTemplate, userOutputPort);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(final TransactionRepository transactionRepository, final TransactionPersistenceMapper mapper, final UserOutputPort userOutputPort, final UserPersistenceMapper userPersistenceMapper){
        return new TransactionPersistenceAdapter(transactionRepository, mapper, userOutputPort, userPersistenceMapper);
    }

    @Bean
    public TransactionService transactionService(final TransactionOutputPort transactionOutputPort){
        return new TransactionService(transactionOutputPort);
    }

    @Bean
    public PaystackAdapter paystackAdapter(final RestTemplate restTemplate){
        return new PaystackAdapter(restTemplate);
    }

    @Bean
    public WalletService walletService(final WalletOutputPort walletOutputPort,final PaymentPort paymentPort, final TransactionOutputPort transactionOutputPort){
        return new WalletService(walletOutputPort, paymentPort,transactionOutputPort);
    }

    @Bean
    public WalletPersistenceAdapter persistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper, final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public UserService userService(final PasswordEncoder passwordEncoder, final UserOutputPort userOutputPort, final WalletOutputPort walletOutputPort, final IdentityManagementPort identityManagementPort, final IdentityVerificationPort identityVerificationPort){
        return new UserService(passwordEncoder,userOutputPort,walletOutputPort,identityManagementPort,identityVerificationPort);
    }

    @Bean
    public PremblyAdapter identityVerificationAdapter(Cloudinary cloudinary, UserOutputPort userOutputPort){
        return new PremblyAdapter(cloudinary,userOutputPort);
    }

    @Bean
    public KeycloakAdapter identityManagementAdapter(final Keycloak keycloak){
        return new KeycloakAdapter(keycloak);
    }

}

