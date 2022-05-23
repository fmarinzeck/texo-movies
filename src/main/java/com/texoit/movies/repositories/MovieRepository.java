package com.texoit.movies.repositories;

import com.texoit.movies.entities.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {


  List<Movie> findAllByWinnerIsTrue();
}
