package com.texoit.movies.controllers;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.services.MovieService;
import com.texoit.movies.dtos.AwardIntervalDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @Autowired
  private MovieService movieService;


  @GetMapping(value = "/award-intervals")
  @ResponseBody
  private ResponseEntity<AwardIntervalDTO> getAwardIntervals() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(movieService.getAwardInterval());
  }
}
