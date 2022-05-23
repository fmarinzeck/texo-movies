package com.texoit.movies.services.impl;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.services.MovieService;
import com.texoit.movies.services.ProducerAwardIntervalService;
import com.texoit.movies.view.ProducerAwardIntervalView;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProducerAwardIntervalServiceImpl implements ProducerAwardIntervalService {

  @Override
  public List<ProducerAwardIntervalView> getMinProducerAwardIntervals(List<Movie> winners) {
    ProducerAwardIntervalView result = new ProducerAwardIntervalView();
    result.setProducer(winners.get(0).getProducers());
    result.setInterval(1900);

    return List.of(result);
  }

  @Override
  public List<ProducerAwardIntervalView> getMaxProducerAwardIntervals(List<Movie> winners) {
    ProducerAwardIntervalView result = new ProducerAwardIntervalView();
    result.setProducer("Aqui é o MAx pai");
    result.setInterval(1900);

    return List.of(result);
  }
}
