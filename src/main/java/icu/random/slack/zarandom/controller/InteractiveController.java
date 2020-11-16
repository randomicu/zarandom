package icu.random.slack.zarandom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import static icu.random.slack.zarandom.api.Paths.SLACK_INTERACTIVE_ENDPOINT;
import icu.random.slack.zarandom.service.InteractiveService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/slack")
public class InteractiveController {

  private final ObjectMapper mapper;
  private final RestTemplate restTemplate;
  private final InteractiveService service;

  private final static String SLACK_PAYLOAD = "payload";

  public InteractiveController(ObjectMapper mapper, RestTemplate restTemplate, InteractiveService service) {
    this.mapper = mapper;
    this.restTemplate = restTemplate;
    this.service = service;
  }

  @PostMapping(SLACK_INTERACTIVE_ENDPOINT)
  public HttpStatus interactiveHandler(@RequestParam MultiValueMap<String, String> payload) throws IOException {
    log.info("Interactive controller invoked with path: {}", SLACK_INTERACTIVE_ENDPOINT);
    log.info("Slack payload content: {}", payload.getFirst(SLACK_PAYLOAD));

    var attachmentActionPayload = mapper.readValue(payload.getFirst(SLACK_PAYLOAD), AttachmentActionPayload.class);
    String responseUrl = attachmentActionPayload.getResponseUrl();
    var actionResponse = service.getInteractiveReply(payload.getFirst(SLACK_PAYLOAD));
    ResponseEntity<Object> result = restTemplate.exchange(responseUrl, HttpMethod.POST, new HttpEntity<>(actionResponse), Object.class);

    log.info("Result body {}", result.getBody());

    return HttpStatus.NO_CONTENT;
  }
}
