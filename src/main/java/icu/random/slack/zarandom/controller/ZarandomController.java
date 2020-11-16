package icu.random.slack.zarandom.controller;

import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import static icu.random.slack.zarandom.api.Paths.SLACK_ROOT_ENDPOINT;
import static icu.random.slack.zarandom.api.Paths.SLACK_ZARANDOM_ENDPOINT;
import icu.random.slack.zarandom.entity.Shuffle;
import icu.random.slack.zarandom.service.ShuffleService;
import icu.random.slack.zarandom.service.SlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(SLACK_ROOT_ENDPOINT)
public class ZarandomController {

  private final ShuffleService shuffleService;
  private final SlackService slackService;
  private final SlashCommandPayloadParser payloadParser;

  @Autowired
  public ZarandomController(ShuffleService shuffleService, SlackService slackService,
                            SlashCommandPayloadParser payloadParser) {
    this.shuffleService = shuffleService;
    this.slackService = slackService;
    this.payloadParser = payloadParser;
  }

  @PostMapping(SLACK_ZARANDOM_ENDPOINT)
  public ResponseEntity<SlashCommandResponse> slackHandler(@RequestBody String payload) {
    log.info("Zarandom controller invoked with path: {}", SLACK_ZARANDOM_ENDPOINT);

    SlashCommandPayload payloadObj = payloadParser.parse(payload);
    logPayloadContent(payloadObj);

    String text = payloadParser.parse(payload).getText(); // could be null if empty slash command send from Slack
    Shuffle shuffle = shuffleService.getShuffle(text);

    var reply = slackService.getReply(shuffle);
    return new ResponseEntity<>(reply, HttpStatus.OK);
  }

  private void logPayloadContent(SlashCommandPayload payloadObj) {
    log.debug("PayloadObj ApiAppId: {}", payloadObj.getApiAppId());
    log.debug("PayloadObj ChannelId: {}", payloadObj.getChannelId());
    log.debug("PayloadObj ChannelName: {}", payloadObj.getChannelName());
    log.debug("PayloadObj Command: {}", payloadObj.getCommand());
    log.debug("PayloadObj EnterpriseId: {}", payloadObj.getEnterpriseId());
    log.debug("PayloadObj EnterpriseName: {}", payloadObj.getEnterpriseName());
    log.debug("PayloadObj ResponseUrl: {}", payloadObj.getResponseUrl());
    log.debug("PayloadObj TeamDomain: {}", payloadObj.getTeamDomain());
    log.debug("PayloadObj TeamId: {}", payloadObj.getTeamId());
    log.debug("PayloadObj Text: {}", payloadObj.getText());
    log.debug("PayloadObj Token: {}", payloadObj.getToken());
    log.debug("PayloadObj TriggerId: {}", payloadObj.getTriggerId());
    log.debug("PayloadObj UserId: {}", payloadObj.getUserId());
    log.debug("PayloadObj UserName: {}", payloadObj.getUserName());
  }
}
