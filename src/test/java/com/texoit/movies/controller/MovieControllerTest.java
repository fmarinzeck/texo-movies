package com.texoit.movies.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.texoit.movies.entities.Movie;
import com.texoit.movies.loader.MovieLoader;
import com.texoit.movies.repositories.MovieRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/before-test-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/after-test-data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MovieRepository movieRepository;

//  @MockBean
//  private MovieRepository movieRepositoryMock;

//  @MockBean
//  private MovieRepository movieRepositoryMock;

//  @Test
//  public void get_award_interval_OK() throws Exception {
//    when(movieServiceMock.getAwardInterval()).thenReturn(AwardIntervalDTOMock.getDefaultAwardInterval());
//
//    mockMvc.perform(get("/movie/award-intervals"))
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.min", isA(List.class)))
//        .andExpect(jsonPath("$.max", isA(List.class)))
//        .andExpect(jsonPath("$.min[0].producer", is("Default Min Producer")))
//        .andExpect(jsonPath("$.min[0].previousWin", is(2020)))
//        .andExpect(jsonPath("$.min[0].followingWin", is(2021)))
//        .andExpect(jsonPath("$.min[0].interval", is(1)))
//        .andExpect(jsonPath("$.max[0].producer", is("Default Max Producer")))
//        .andExpect(jsonPath("$.max[0].previousWin", is(2010)))
//        .andExpect(jsonPath("$.max[0].followingWin", is(2020)))
//        .andExpect(jsonPath("$.max[0].interval", is(10)));
//
//    verify(movieServiceMock, times(1)).getAwardInterval();
//  }

  @Test
  public void get_award_interval_all_empty() throws Exception {
    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min", is(Collections.EMPTY_LIST)))
        .andExpect(jsonPath("$.max", is(Collections.EMPTY_LIST)));
  }

  @Test
  public void get_award_interval_without_winners() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).winner(Boolean.FALSE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(2).winner(Boolean.FALSE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(3).winner(Boolean.FALSE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(4).winner(Boolean.FALSE).build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min", is(Collections.EMPTY_LIST)))
        .andExpect(jsonPath("$.max", is(Collections.EMPTY_LIST)));
  }

  @Test
  public void get_award_interval_with_winners_without_recurrence() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).year(2000).winner(Boolean.TRUE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(2).year(2001).winner(Boolean.TRUE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(3).year(2002).winner(Boolean.TRUE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(4).year(2003).winner(Boolean.TRUE).build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min", is(Collections.EMPTY_LIST)))
        .andExpect(jsonPath("$.max", is(Collections.EMPTY_LIST)));
  }

  @Test
  public void get_award_interval_with_min_and_max_same_winner() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).year(2000).winner(Boolean.TRUE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(1).year(2001).winner(Boolean.TRUE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(3).year(2002).winner(Boolean.TRUE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(4).year(2003).winner(Boolean.TRUE).build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2001)))
        .andExpect(jsonPath("$.min[0].interval", is(1)))
        .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2001)))
        .andExpect(jsonPath("$.max[0].interval", is(1)));
  }


}
