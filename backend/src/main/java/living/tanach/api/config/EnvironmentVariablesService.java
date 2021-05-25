package living.tanach.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component @Getter
public class EnvironmentVariablesService {
    @Value("${DB_URL}")
    private String dbUrl;
    @Value("${DB_PASSWORD}")
    private String dbPassword;
    @Value("${DB_USERNAME}")
    private String dbUsername;
}
