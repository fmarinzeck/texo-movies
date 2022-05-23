package com.texoit.movies.services;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.view.ProducerAwardIntervalView;
import java.util.List;

public interface ProducerAwardIntervalService {

  List<ProducerAwardIntervalView> getMinProducerAwardIntervals(List<Movie> winners);

  List<ProducerAwardIntervalView> getMaxProducerAwardIntervals(List<Movie> winners);

}
