package com.texoit.movies.services.impl;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.repositories.MovieRepository;
import com.texoit.movies.services.MovieService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

  @Autowired
  private MovieRepository movieRepository;


  @Override
  public List<Movie> listAll() {
    return movieRepository.findAll();
  }
}
