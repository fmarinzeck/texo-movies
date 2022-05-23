package com.texoit.movies.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/test-data.sql"})
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
  public void get_award_interval_OK() throws Exception {
//    when(movieRepositoryMock.findAllByWinnerIsTrue()).thenReturn(
//        Arrays.asList(MovieMock.createDefaultMovieValue(1), MovieMock.createDefaultMovieValue(2)));

    mockMvc.perform(get("/movie/award-intervals"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.min", isA(List.class)))
        .andExpect(jsonPath("$.max", isA(List.class)))
        .andExpect(jsonPath("$.min", is(Collections.EMPTY_LIST)));

//    verify(movieRepositoryMock, times(1)).findAllByWinnerIsTrue();
  }


}
