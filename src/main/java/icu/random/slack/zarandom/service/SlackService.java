package icu.random.slack.zarandom.service;

import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;

public interface SlackService {
  SlashCommandResponse getReply(String winner);
}
