package com.texoit.movies.services;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.dtos.AwardIntervalDTO;
import java.util.List;

public interface MovieService {


  List<Movie> listAll();

  AwardIntervalDTO getAwardIntervals();

}
