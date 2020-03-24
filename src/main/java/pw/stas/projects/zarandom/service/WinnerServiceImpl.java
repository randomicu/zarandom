package pw.stas.projects.zarandom.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WinnerServiceImpl implements WinnerService {

  private final static String SPLIT_ELEMENTS_REGEX = "\\s*(,|\\s)\\s*";
  private final static int SHUFFLE_COUNT = 3;
  private final static int WINNER_IN_LIST = 0;

  @Override
  public String getWinner(@Nullable String text) {
    if (!validateText(text)) {
      return null;
    }

    List<String> items = getMixedItems(text);

    return items.get(WINNER_IN_LIST);
  }

  private List<String> getMixedItems(String text) {
    List<String> items = Arrays.asList(text.split(SPLIT_ELEMENTS_REGEX));

    IntStream.rangeClosed(0, SHUFFLE_COUNT)
        .forEach(i -> Collections.shuffle(items));

    return items;
  }

  private boolean validateText(String text) {
    return StringUtils.hasText(text);
  }
}
