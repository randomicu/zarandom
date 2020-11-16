package icu.random.slack.zarandom.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@KeySpace(value = "shuffle")
public class Shuffle {

  @Id
  private String uuid = UUID.randomUUID().toString();

  private List<List<String>> shuffleLog = new ArrayList<>();

  private List<String> items = new ArrayList<>();

  private String winner;

  public void addLog(List<String> logEntry) {
    shuffleLog.add(logEntry);
  }
}
