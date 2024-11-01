package africa.semicolon.walletapi.infrastructure.adapter.config;

import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.services.*;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.TransactionPersistenceAdapter;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.UserPersistenceAdapter;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.WalletPersistenceAdapter;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.TransactionPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.TransactionRepository;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.UserRepository;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.WalletRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityManager;
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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthService authService(final Keycloak keycloak){
        return new AuthService(keycloak);
    }

    @Bean
    public PaystackService paystackService(final RestTemplate restTemplate) {
        return new PaystackService(restTemplate);
    }

    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(final TransactionRepository transactionRepository, final TransactionPersistenceMapper mapper, final UserOutputPort userOutputPort, final UserPersistenceMapper userPersistenceMapper){
        return new TransactionPersistenceAdapter(transactionRepository, mapper, userOutputPort, userPersistenceMapper);
    }

    @Bean
    public TransactionService transactionService(final TransactionOutputPort transactionOutputPort, TransactionPersistenceMapper mapper){
        return new TransactionService(transactionOutputPort, mapper);
    }

    @Bean
    public MonnifyService monnifyService(final RestTemplate restTemplate){
        return new MonnifyService(restTemplate);
    }

    @Bean
    public WalletService walletService(final WalletOutputPort walletOutputPort, final PaystackService paystackService, final TransactionOutputPort transactionOutputPort) {
        return new WalletService(walletOutputPort,paystackService, transactionOutputPort);
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
    public UserService userService(final PasswordEncoder passwordEncoder, final UserOutputPort userOutputPort, final WalletOutputPort walletOutputPort, final AuthService authService){
        return new UserService(passwordEncoder,userOutputPort, walletOutputPort, authService);
    }
    @Bean
    public PremblyUserService premblyUserService(final Cloudinary cloudinary, final UserOutputPort userOutputPort, final UserPersistenceMapper userPersistenceMapper) {
        return new PremblyUserService(cloudinary,userOutputPort);
    }
}

