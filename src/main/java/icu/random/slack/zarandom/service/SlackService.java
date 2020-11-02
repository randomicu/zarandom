package icu.random.slack.zarandom.service;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;

public interface SlackService {
  SlashCommandResponse getReply(String winner);
}
