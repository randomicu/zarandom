package icu.random.slack.zarandom.config;

import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZaRandomConfig {

  @Bean
  public SlashCommandPayloadParser getSlackPayloadParser() {
    return new SlashCommandPayloadParser();
  }
}
