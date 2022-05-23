package com.texoit.movies.services;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.view.AwardIntervalView;
import java.util.List;

public interface MovieService {


  List<Movie> listAll();

  AwardIntervalView getAwardIntervals();

}
