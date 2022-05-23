package com.texoit.movies.services;

import com.texoit.movies.dtos.AwardIntervalDTO;
import com.texoit.movies.entities.Movie;
import com.texoit.movies.dtos.ProducerAwardIntervalDTO;
import java.util.List;

public interface ProducerAwardIntervalService {

  AwardIntervalDTO getAwardInterval(List<Movie> winners);

}
