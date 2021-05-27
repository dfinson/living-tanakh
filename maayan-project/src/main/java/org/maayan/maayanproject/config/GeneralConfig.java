package org.maayan.maayanproject.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
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
