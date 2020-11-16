package icu.random.slack.zarandom.service;

import icu.random.slack.zarandom.entity.Shuffle;

public interface ShuffleService {
  Shuffle getShuffle(String text);
}
