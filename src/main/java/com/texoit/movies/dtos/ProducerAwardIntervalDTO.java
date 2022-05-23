package com.texoit.movies.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerAwardIntervalDTO {

  private String producer;

  private Integer interval;

  private Integer previousWin;

  private Integer followingWin;

}
