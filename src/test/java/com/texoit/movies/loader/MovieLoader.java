package com.texoit.movies.loader;

import com.texoit.movies.entities.Movie;

public class MovieLoader {

  public static Movie.MovieBuilder createDefaultMovie(Integer value) {
    return Movie.builder().producers("Producer " + value).studios("Studios " + value)
        .title("Title " + value);
  }
}
