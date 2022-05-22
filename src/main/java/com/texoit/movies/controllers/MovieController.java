package com.texoit.movies.controllers;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.repositories.MovieRepository;
import com.texoit.movies.services.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

  @Autowired
  private MovieService movieService;


  @GetMapping(value = "/list")
  @ResponseBody
  private ResponseEntity<List<Movie>> listAll() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(movieService.listAll());
  }
}
