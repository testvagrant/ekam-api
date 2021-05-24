package com.testvagrant.ekam.api.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDos {
  private String userId;
  private String id;
  private String title;
  private boolean completed;

  @Override
  public String toString() {
    return "ToDos{"
        + "userId='"
        + userId
        + '\''
        + ", id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", completed="
        + completed
        + '}';
  }
}
