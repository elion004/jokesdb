package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.RatingEntity;
import ch.bbw.m151.jokesdb.service.RatingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingsController {

    private final RatingsService ratingsService;

    @PostMapping("/rating")
    public ResponseEntity<RatingEntity> setRatingForJokeById(@Param("jokeId") int jokeId, @Param("rating") int rating) {
        log.info("add rating");
        return new ResponseEntity<>(ratingsService.addRatingForJokeById(jokeId, rating), HttpStatus.OK);
    }

    @PutMapping("/update-rating")
    public ResponseEntity<RatingEntity> updateRatingById(@Param("ratingId") int ratingId, @Param("newRating") int newRating) {
        log.info("update rating");
        return new ResponseEntity<>(ratingsService.updateRatingById(ratingId, newRating), HttpStatus.OK);
    }

}
