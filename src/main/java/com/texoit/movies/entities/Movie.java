package com.texoit.movies.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movie {

  @Id
  private Long id;

  @Column(name = "NOMINATED_YEAR")
  private Integer year;

  private String title;

  private String studios;

  private String producers;

  private Boolean winner;

}
