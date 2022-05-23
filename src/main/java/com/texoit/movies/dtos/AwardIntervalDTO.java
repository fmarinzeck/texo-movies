package com.texoit.movies.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AwardIntervalDTO {

  private List<ProducerAwardIntervalDTO> min;

  private List<ProducerAwardIntervalDTO> max;
}
