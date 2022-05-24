package com.texoit.movies.services.impl;

import com.texoit.movies.dtos.ProducerAwardIntervalDTO;
import com.texoit.movies.dtos.ProducerWinDTO;
import com.texoit.movies.entities.Movie;
import com.texoit.movies.repositories.MovieRepository;
import com.texoit.movies.services.MovieService;
import com.texoit.movies.dtos.AwardIntervalDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MovieServiceImpl implements MovieService {

  @Autowired
  private MovieRepository movieRepository;

  @Override
  public AwardIntervalDTO getAwardInterval() {
    List<Movie> winners = movieRepository.findAllByWinnerIsTrue();
    List<ProducerWinDTO> loadDistinctWinners = loadDistinctProducerWinDTO(winners);
    loadDistinctWinners.sort(Comparator.comparing(ProducerWinDTO::getYear));
    List<ProducerAwardIntervalDTO> producersAwardIntervals = createAwardIntervals(
        loadDistinctWinners);

    return new AwardIntervalDTO(
        getMinProducerAwardIntervals(producersAwardIntervals),
        getMaxProducerAwardIntervals(producersAwardIntervals)
    );
  }

  private List<ProducerAwardIntervalDTO> getMinProducerAwardIntervals(
      List<ProducerAwardIntervalDTO> producersAwardIntervals) {
    producersAwardIntervals.sort(Comparator.comparing(ProducerAwardIntervalDTO::getInterval));
    List<ProducerAwardIntervalDTO> allMin = new ArrayList<>();
    AtomicReference<Integer> minInterval = new AtomicReference<>(99999);

    producersAwardIntervals.forEach(producerAwardIntervalDTO -> {
      if (producerAwardIntervalDTO.getInterval() > 0
          && producerAwardIntervalDTO.getInterval() <= minInterval.get()) {
        allMin.add(producerAwardIntervalDTO);
        minInterval.set(producerAwardIntervalDTO.getInterval());
      }
    });

    return allMin;
  }

  private List<ProducerAwardIntervalDTO> getMaxProducerAwardIntervals(
      List<ProducerAwardIntervalDTO> producersAwardIntervals) {
    producersAwardIntervals.sort((o1, o2) -> o2.getInterval().compareTo(o1.getInterval()));
    List<ProducerAwardIntervalDTO> allMax = new ArrayList<>();
    AtomicReference<Integer> maxInterval = new AtomicReference<>(0);

    producersAwardIntervals.forEach(producerAwardIntervalDTO -> {
      if (producerAwardIntervalDTO.getInterval() > 0
          && producerAwardIntervalDTO.getInterval() >= maxInterval.get()) {
        allMax.add(producerAwardIntervalDTO);
        maxInterval.set(producerAwardIntervalDTO.getInterval());
      }
    });

    return allMax;
  }

  private List<ProducerAwardIntervalDTO> createAwardIntervals(
      List<ProducerWinDTO> loadDistinctWinners) {
    List<ProducerAwardIntervalDTO> producersAwardIntervals = new ArrayList<>();

    loadDistinctWinners.forEach(producerWinDTO -> {
      createIntervalToEachProducer(loadDistinctWinners, producersAwardIntervals, producerWinDTO);
    });

    return producersAwardIntervals;
  }

  private void createIntervalToEachProducer(List<ProducerWinDTO> loadDistinctWinners,
      List<ProducerAwardIntervalDTO> producersAwardIntervals, ProducerWinDTO producerWinDTO) {
    loadDistinctWinners.forEach(producerWinSearch -> {
      if (producerWinDTO.getName().equals(producerWinSearch.getName())
          && producerWinDTO.getYear().compareTo(producerWinSearch.getYear()) != 0) {
        ProducerAwardIntervalDTO dto = new ProducerAwardIntervalDTO();
        dto.setProducer(producerWinDTO.getName());
        dto.setPreviousWin(producerWinSearch.getYear());
        dto.setFollowingWin(producerWinDTO.getYear());
        dto.setInterval(producerWinDTO.getYear() - producerWinSearch.getYear());
        producersAwardIntervals.add(dto);
      }
    });
  }

  private List<ProducerWinDTO> loadDistinctProducerWinDTO(List<Movie> winners) {
    if (CollectionUtils.isEmpty(winners)) {
      return new ArrayList<>();
    }

    List<ProducerWinDTO> allProductors = new ArrayList<>();
    winners.stream().forEach(movie -> allProductors.addAll(getProducersWinDTOFromMovie(movie)));
    return allProductors;
  }

  private List<ProducerWinDTO> getProducersWinDTOFromMovie(Movie movie) {
    String producers = movie.getProducers();
    producers = producers.replace(" and ", ",");
    producers = producers.replace(",,", ",");
    return Arrays.asList(producers.split(",")).stream()
        .map(producer -> new ProducerWinDTO(producer.trim(), movie.getYear())).collect(
            Collectors.toList());
  }
}
