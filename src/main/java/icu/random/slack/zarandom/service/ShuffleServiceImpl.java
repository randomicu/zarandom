package icu.random.slack.zarandom.service;

import icu.random.slack.zarandom.entity.Shuffle;
import icu.random.slack.zarandom.repository.ShuffleRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class ShuffleServiceImpl implements ShuffleService {

  private final static String SPLIT_ELEMENTS_REGEX = "\\s*(,|\\s)\\s*";
  private final static int WINNER_POSITION = 0;

  @Value("${randomicu.zarandom.shuffle-count}")
  private int shuffleCount;

  private final ShuffleRepository repository;

  public ShuffleServiceImpl(ShuffleRepository repository) {
    this.repository = repository;
  }


  @Override
  public Shuffle getShuffle(@Nullable String text) {
    if (!validateText(text)) {
      return null;
    }

    Shuffle shuffle = new Shuffle();

    List<String> items = getMixedItems(text, shuffle);

    String winner = items.get(WINNER_POSITION);
    shuffle.setWinner(winner);
    shuffle.setItems(items);
    repository.save(shuffle);

    return shuffle;
  }

  private List<String> getMixedItems(String text, Shuffle shuffle) {
    List<String> items = Arrays.asList(text.split(SPLIT_ELEMENTS_REGEX));
    log.info("Initial elements before shuffling: {}", items);

    IntStream.rangeClosed(1, shuffleCount)
        .forEach(i -> {
              log.info("Shuffle step {} of {}", i, shuffleCount);

              Collections.shuffle(items);
              shuffle.addLog(new ArrayList<>(items));

              log.info("Shuffle result: {}", items);
              log.info("Shuffle log content: {}", shuffle.getShuffleLog());
            }
        );

    return items;
  }

  private boolean validateText(String text) {
    return StringUtils.hasText(text);
  }
}
