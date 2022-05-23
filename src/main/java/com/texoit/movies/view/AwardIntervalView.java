package com.texoit.movies.view;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AwardIntervalView {

  private List<ProducerAwardIntervalView> min;

  private List<ProducerAwardIntervalView> max;
}
