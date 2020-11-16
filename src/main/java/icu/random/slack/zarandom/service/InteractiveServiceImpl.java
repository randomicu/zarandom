package icu.random.slack.zarandom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import static icu.random.slack.zarandom.api.Constants.FALLBACK_SOMETHING_WRONG;
import static icu.random.slack.zarandom.api.Constants.RESPONSE_TO_CHANNEL;
import icu.random.slack.zarandom.repository.ShuffleRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InteractiveServiceImpl implements InteractiveService {

  private final ObjectMapper mapper;
  private final ShuffleRepository repository;

  @Autowired
  public InteractiveServiceImpl(ObjectMapper mapper, ShuffleRepository repository) {
    this.mapper = mapper;
    this.repository = repository;
  }

  @Override
  public ActionResponse getInteractiveReply(String payload) throws JsonProcessingException {
    AttachmentActionPayload actionPayload = mapper.readValue(payload, AttachmentActionPayload.class);

    log.info("Response url: {}", actionPayload.getResponseUrl());
    log.info("Shuffle uuid from Slack API: {}", actionPayload.getActions().get(0).getValue());
    log.debug("Raw AttachmentActionPayload after serialization: {}", mapper.writeValueAsString(actionPayload));

    var shuffleUuid = actionPayload.getActions().get(0).getValue();
    var shuffle = repository.findById(shuffleUuid).orElseThrow();

    log.info("Shuffle uuid from repository: {}", shuffle.getUuid());

    List<List<String>> shuffleLog = shuffle.getShuffleLog();
    var elements = shuffleLog.stream().map(Objects::toString).collect(Collectors.joining(", "));
    var winnner = shuffle.getWinner();

    return ActionResponse.builder()
        .deleteOriginal(false)
        .replaceOriginal(true)
        .responseType(RESPONSE_TO_CHANNEL)
        .text(FALLBACK_SOMETHING_WRONG)
        .blocks(
            List.of(
                SectionBlock.builder().text(
                    MarkdownTextObject.builder().text(
                        String.format("*Winner:* %s", winnner)).build()
                ).build(),
                SectionBlock.builder().text(
                    MarkdownTextObject.builder().text(
                        String.format("*Shuffle log:* %s", elements)).build()
                ).build())
        )
        .build();
  }
}
