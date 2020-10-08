package icu.random.slack.zarandom.config;

import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayloadParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZaRandomConfig {

  @Bean
  public SlashCommandPayloadParser getSlackPayloadParser() {
    return new SlashCommandPayloadParser();
  }
}
