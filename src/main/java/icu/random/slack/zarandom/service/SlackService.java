package icu.random.slack.zarandom.service;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import icu.random.slack.zarandom.entity.Shuffle;

public interface SlackService {
  SlashCommandResponse getReply(Shuffle shuffle);
}
