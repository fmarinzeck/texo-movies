package com.texoit.movies.services.impl;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.repositories.MovieRepository;
import com.texoit.movies.services.MovieService;
import com.texoit.movies.services.ProducerAwardIntervalService;
import com.texoit.movies.dtos.AwardIntervalDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private ProducerAwardIntervalService producerAwardIntervalService;


  @Override
  public List<Movie> listAll() {
    return movieRepository.findAll();
  }

  @Override
  public AwardIntervalDTO getAwardIntervals() {
    final List<Movie> vencedores = movieRepository.findAllByWinnerIsTrue();

    return producerAwardIntervalService.getAwardInterval(vencedores);
  }
}
