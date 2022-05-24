package com.texoit.movies.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
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
        .andExpect(jsonPath("$.max[0].interval", is(1)))

        .andExpect(jsonPath("$.min", hasSize(1)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }

  @Test
  public void get_award_interval_with_min_and_max_distinct() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).year(2000).winner(Boolean.TRUE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(1).year(2001).winner(Boolean.TRUE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(2).year(2002).winner(Boolean.TRUE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(2).year(2004).winner(Boolean.TRUE).build();

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

        .andExpect(jsonPath("$.max[0].producer", is("Producer 2")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2002)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2004)))
        .andExpect(jsonPath("$.max[0].interval", is(2)))

        .andExpect(jsonPath("$.min", hasSize(1)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }

  @Test
  public void get_award_interval_with_draw_min_value() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).year(2000).winner(Boolean.TRUE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(1).year(2001).winner(Boolean.TRUE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(2).year(2002).winner(Boolean.TRUE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(2).year(2003).winner(Boolean.TRUE).build();
    Movie movie5 = MovieLoader.createDefaultMovie(3).year(2004).winner(Boolean.TRUE).build();
    Movie movie6 = MovieLoader.createDefaultMovie(3).year(2010).winner(Boolean.TRUE).build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);
    movieRepository.save(movie5);
    movieRepository.save(movie6);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2001)))
        .andExpect(jsonPath("$.min[0].interval", is(1)))

        .andExpect(jsonPath("$.min[1].producer", is("Producer 2")))
        .andExpect(jsonPath("$.min[1].previousWin", is(2002)))
        .andExpect(jsonPath("$.min[1].followingWin", is(2003)))
        .andExpect(jsonPath("$.min[1].interval", is(1)))

        .andExpect(jsonPath("$.max[0].producer", is("Producer 3")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2004)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2010)))
        .andExpect(jsonPath("$.max[0].interval", is(6)))

        .andExpect(jsonPath("$.min", hasSize(2)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }

  @Test
  public void get_award_interval_with_draw_max_value() throws Exception {
    Movie movie1 = MovieLoader.createDefaultMovie(1).year(2000).winner(Boolean.TRUE).build();
    Movie movie2 = MovieLoader.createDefaultMovie(1).year(2005).winner(Boolean.TRUE).build();
    Movie movie3 = MovieLoader.createDefaultMovie(2).year(2001).winner(Boolean.TRUE).build();
    Movie movie4 = MovieLoader.createDefaultMovie(2).year(2006).winner(Boolean.TRUE).build();
    Movie movie5 = MovieLoader.createDefaultMovie(3).year(2010).winner(Boolean.TRUE).build();
    Movie movie6 = MovieLoader.createDefaultMovie(3).year(2011).winner(Boolean.TRUE).build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);
    movieRepository.save(movie5);
    movieRepository.save(movie6);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))

        .andExpect(jsonPath("$.min[0].producer", is("Producer 3")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2010)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2011)))
        .andExpect(jsonPath("$.min[0].interval", is(1)))

        .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2005)))
        .andExpect(jsonPath("$.max[0].interval", is(5)))

        .andExpect(jsonPath("$.max[1].producer", is("Producer 2")))
        .andExpect(jsonPath("$.max[1].previousWin", is(2001)))
        .andExpect(jsonPath("$.max[1].followingWin", is(2006)))
        .andExpect(jsonPath("$.max[1].interval", is(5)))

        .andExpect(jsonPath("$.min", hasSize(1)))
        .andExpect(jsonPath("$.max", hasSize(2)));
  }

  @Test
  public void get_award_interval_with_collab_producing_min() throws Exception {
    Movie movie1 = Movie.builder().year(2000).winner(Boolean.TRUE).producers("Producer 1").build();
    Movie movie2 = Movie.builder().year(2001).winner(Boolean.TRUE).producers("Producer 2").build();
    Movie movie3 = Movie.builder().year(2002).winner(Boolean.TRUE)
        .producers("Producer 3 and Producer 1").build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))

        .andExpect(jsonPath("$.min[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2002)))
        .andExpect(jsonPath("$.min[0].interval", is(2)))

        .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2002)))
        .andExpect(jsonPath("$.max[0].interval", is(2)))

        .andExpect(jsonPath("$.min", hasSize(1)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }

  @Test
  public void get_award_interval_with_collab_producing_min_and_max() throws Exception {
    Movie movie1 = Movie.builder().year(2000).winner(Boolean.TRUE).producers("Producer 1").build();
    Movie movie2 = Movie.builder().year(2001).winner(Boolean.TRUE).producers("Producer 2").build();
    Movie movie3 = Movie.builder().year(2002).winner(Boolean.TRUE).producers("Producer 3").build();
    Movie movie4 = Movie.builder().year(2003).winner(Boolean.TRUE).producers("Producer 2").build();
    Movie movie5 = Movie.builder().year(2004).winner(Boolean.TRUE).producers("Producer 3").build();
    Movie movie6 = Movie.builder().year(2005).winner(Boolean.TRUE)
        .producers("Producer 1, Producer 2 and Producer 3").build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);
    movieRepository.save(movie5);
    movieRepository.save(movie6);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))

        .andExpect(jsonPath("$.min[0].producer", is("Producer 3")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2004)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2005)))
        .andExpect(jsonPath("$.min[0].interval", is(1)))

        .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2005)))
        .andExpect(jsonPath("$.max[0].interval", is(5)))

        .andExpect(jsonPath("$.min", hasSize(1)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }

  @Test
  public void get_award_interval_with_collab_producing_min_and_max_and_draw() throws Exception {
    Movie movie1 = Movie.builder().year(2000).winner(Boolean.TRUE).producers("Producer 1").build();
    Movie movie2 = Movie.builder().year(2001).winner(Boolean.TRUE).producers("Producer 2").build();
    Movie movie3 = Movie.builder().year(2002).winner(Boolean.TRUE).producers("Producer 3").build();
    Movie movie4 = Movie.builder().year(2003).winner(Boolean.TRUE).producers("Producer 2").build();
    Movie movie5 = Movie.builder().year(2004).winner(Boolean.TRUE).producers("Producer 3").build();
    Movie movie6 = Movie.builder().year(2010).winner(Boolean.TRUE)
        .producers("Producer 1, Producer 2 and Producer 3").build();

    movieRepository.save(movie1);
    movieRepository.save(movie2);
    movieRepository.save(movie3);
    movieRepository.save(movie4);
    movieRepository.save(movie5);
    movieRepository.save(movie6);

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))

        .andExpect(jsonPath("$.min[0].producer", is("Producer 2")))
        .andExpect(jsonPath("$.min[0].previousWin", is(2001)))
        .andExpect(jsonPath("$.min[0].followingWin", is(2003)))
        .andExpect(jsonPath("$.min[0].interval", is(2)))

        .andExpect(jsonPath("$.min[1].producer", is("Producer 3")))
        .andExpect(jsonPath("$.min[1].previousWin", is(2002)))
        .andExpect(jsonPath("$.min[1].followingWin", is(2004)))
        .andExpect(jsonPath("$.min[1].interval", is(2)))

        .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
        .andExpect(jsonPath("$.max[0].previousWin", is(2000)))
        .andExpect(jsonPath("$.max[0].followingWin", is(2010)))
        .andExpect(jsonPath("$.max[0].interval", is(10)))

        .andExpect(jsonPath("$.min", hasSize(2)))
        .andExpect(jsonPath("$.max", hasSize(1)));
  }


}
