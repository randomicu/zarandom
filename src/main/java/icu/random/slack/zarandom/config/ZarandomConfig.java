package icu.random.slack.zarandom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ZarandomConfig {

  private final ObjectMapper mapper;

  @Autowired
  public ZarandomConfig(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Bean
  public SlashCommandPayloadParser getSlackPayloadParser() {
    return new SlashCommandPayloadParser();
  }

  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(mapper);
    restTemplate.getMessageConverters().add(0, converter);

    return restTemplate;
  }
}
