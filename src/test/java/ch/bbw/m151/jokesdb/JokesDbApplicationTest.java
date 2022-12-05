package ch.bbw.m151.jokesdb;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.service.JokesService;
import ch.bbw.m151.jokesdb.service.RatingsService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assert;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class JokesDbApplicationTest implements WithAssertions {

    @Autowired
    JokesRepository jokesRepository;

    @Autowired
    JokesService jokesService;

    @Autowired
    RatingsService ratingsService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void jokesAreLoadedAtStartup() {
        var jokes = jokesRepository.findAll();
        assertThat(jokes).hasSizeGreaterThan(100)
                .allSatisfy(x -> assertThat(x.getJoke()).isNotEmpty());
    }

    @Test
    void jokesCanBeRetrievedViaHttpGet() {
        var pageSize = 5;
        webTestClient.get()
                .uri("/jokes?page={page}&size={size}", 1, pageSize)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(JokesEntity.class)
                .hasSize(pageSize);
    }

    @Test
    void testWebClient() {
        var joke = jokesService.getOneJoke();
        assertThat(joke).isNotNull();
    }

    @Test
    void getAllJokesTest() {
        given().when().get("http://localhost:8080/jokes").then().statusCode(200);
    }

    @Test
    public void addOneRating() {
        //jokeId must be an existing jokeId
        var rating = ratingsService.addRatingForJokeById(14, 5);

        assertThat(rating).isNotNull();
    }

    @Test
    public void updateOneRating() {
        //ratingId must be an existing rating
        var rating = ratingsService.updateRatingById(1, 2);

        assertThat(rating.getRating()).isEqualTo(2);
    }
}
