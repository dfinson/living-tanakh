package living.tanach.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import dev.sanda.datafi.service.DataManager;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {

  @Autowired
  private EnvironmentVariablesService env;

  @Bean
  @SuppressWarnings("rawtypes")
  public DataSource getDataSource() {
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.url(env.getDbUrl());
    dataSourceBuilder.username(env.getDbUsername());
    dataSourceBuilder.password(env.getDbPassword());
    return dataSourceBuilder.build();
  }

  @PostConstruct
  private void initUniRest() {
    Unirest.setObjectMapper(
      new ObjectMapper() {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        @Override
        public <T> T readValue(String s, Class<T> aClass) {
          try {
            return mapper.readValue(s, aClass);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public String writeValue(Object o) {
          try {
            return mapper.writeValueAsString(o);
          } catch (JsonProcessingException e) {
            throw new RuntimeException();
          }
        }
      }
    );
  }
}
