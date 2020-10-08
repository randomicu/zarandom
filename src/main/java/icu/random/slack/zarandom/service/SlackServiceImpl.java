package icu.random.slack.zarandom.service;

import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import java.util.List;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SlackServiceImpl implements SlackService {

  private final static String RESPONSE_TO_CHANNEL = "in_channel";
  private final static String RESPONSE_TO_PERSON = "ephemeral";
  private final static String INVALID_INPUT_HELP_MESSAGE = "Передай боту список слов, разделённых запятой или пробелом";

  @Override
  public SlashCommandResponse getReply(@Nullable String winner) {
    if (!validateText(winner)) {
      List<LayoutBlock> blocks = List.of(SectionBlock.builder()
          .text(PlainTextObject.builder()
              .text(INVALID_INPUT_HELP_MESSAGE)
              .build())
          .build());
      return getResponse(blocks, RESPONSE_TO_PERSON);
    }

    List<LayoutBlock> blocks = List.of(
        SectionBlock.builder()
            .text(PlainTextObject.builder()
                .text(winner)
                .build())
            .build());

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
