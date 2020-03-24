package pw.stas.projects.zarandom.controller;

import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayloadParser;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.stas.projects.zarandom.service.SlackService;
import pw.stas.projects.zarandom.service.WinnerService;

@RestController
@RequestMapping("/api/slack")
public class ZaRandomController {

  private final WinnerService winnerService;
  private final SlackService slackService;
  private final SlashCommandPayloadParser payloadParser;

  @Autowired
  public ZaRandomController(WinnerService winnerService, SlackService slackService, SlashCommandPayloadParser payloadParser) {
    this.winnerService = winnerService;
    this.slackService = slackService;
    this.payloadParser = payloadParser;
  }

  @PostMapping("/zarandom")
  public SlashCommandResponse slackHandler(@RequestBody String payload) {
    SlashCommandPayload parsedPayload = payloadParser.parse(payload);
    String text = parsedPayload.getText();  // could be null if empty slash command send from Slack
    String winner = winnerService.getWinner(text);

    return slackService.getReply(winner);
  }
}
