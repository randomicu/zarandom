package icu.random.slack.zarandom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;

public interface InteractiveService {

  ActionResponse getInteractiveReply(String payload) throws JsonProcessingException;

}
