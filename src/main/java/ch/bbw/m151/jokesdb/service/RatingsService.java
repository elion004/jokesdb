package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.RatingEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingsService {

    private final RatingsRepository ratingsRepository;
    private final JokesRepository jokesRepository;

    public RatingEntity addRatingForJokeById(int jokeId, int rating) throws NullPointerException {
        var joke = jokesRepository.findById(jokeId);

        if (joke.isEmpty()) {
            log.error("no joke found to add rating for");
            throw new NullPointerException();
        }

        var newRating = new RatingEntity();
        newRating.setJokeId(jokeId);
        newRating.setRating(rating);

        return ratingsRepository.save(newRating);
    }

    public RatingEntity updateRatingById(int ratingId, int newRating) throws NullPointerException {

        Optional<RatingEntity> actualRating = ratingsRepository.findById(ratingId);

        if (actualRating.isEmpty()) {
            log.error("No rating to update found");
            throw new NullPointerException();
        }

        var updatedRating = actualRating.get();
        updatedRating.setRating(newRating);

        log.info("updating rating finished");

        return updatedRating;
    }

}
