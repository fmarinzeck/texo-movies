package com.texoit.movies.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerAwardIntervalView {

  private String producer;

  private Integer interval;

  private Boolean previousWin;

  private Boolean followingWin;

}
