package icu.random.slack.zarandom.service;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ButtonElement;
import static icu.random.slack.zarandom.api.Constants.INVALID_INPUT_HELP_MESSAGE;
import static icu.random.slack.zarandom.api.Constants.RESPONSE_TO_CHANNEL;
import static icu.random.slack.zarandom.api.Constants.RESPONSE_TO_PERSON;
import static icu.random.slack.zarandom.api.Constants.VIEW_LOG;
import icu.random.slack.zarandom.entity.Shuffle;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SlackServiceImpl implements SlackService {

  @Override
  public SlashCommandResponse getReply(Shuffle shuffle) {
    if (!validateText(shuffle.getWinner())) {
      List<LayoutBlock> blocks = List.of(SectionBlock.builder()
          .text(PlainTextObject.builder()
              .text(INVALID_INPUT_HELP_MESSAGE)
              .build())
          .build());
      return getResponse(blocks, RESPONSE_TO_PERSON);
    }

    String shuffleUuid = shuffle.getUuid();

    List<LayoutBlock> blocks = List.of(
        SectionBlock.builder()
            .text(PlainTextObject.builder()
                .text(shuffle.getWinner())
                .build())
            .build(),
        ActionsBlock.builder().elements(
            List.of(
                ButtonElement.builder().text(
                    PlainTextObject.builder().text(VIEW_LOG).build())
                    .value(shuffleUuid)
                    .build()))
            .build()
    );

    return getResponse(blocks, RESPONSE_TO_CHANNEL);
  }

  private SlashCommandResponse getResponse(List<LayoutBlock> blocks, String responseType) {
    return SlashCommandResponse.builder()
        .blocks(blocks)
        .responseType(responseType)
        .build();
  }

  private boolean validateText(String text) {
    return StringUtils.hasText(text);
  }
}
