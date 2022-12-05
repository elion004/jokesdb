package ch.bbw.m151.jokesdb.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.service.JokesService;
import ch.bbw.m151.jokesdb.viewmodel.JokesVM;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.transaction.Transactional;

@Slf4j
@Data
@CrossOrigin
@Transactional
@RestController
@RequiredArgsConstructor
public class JokesController {

    private final JokesRepository jokesRepository;
    private final JokesService jokesService;

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    WebClient client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

    WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
    WebClient.RequestBodySpec bodySpec = uriSpec.uri("/resource");

    /**
     * @param pageable to be called with params `?page=3&size=5`
     * @return hilarious content
     */

    @GetMapping("joke")
    public ResponseEntity<JokesEntity> getOneJoke(Pageable pageable) {
        log.info("Get joke");

        JokesVM joke = jokesService.getOneJoke();

        JokesEntity newJoke = new JokesEntity();
        newJoke.setId(joke.getId());
        newJoke.setJoke(joke.getJoke());

        if (!jokesService.isJokeExisting(newJoke.getId())) {
            jokesRepository.save(newJoke);
        }

        return new ResponseEntity<>(newJoke, HttpStatus.OK);
    }

    @GetMapping("jokes")
    public ResponseEntity<List<JokesEntity>> getJokes(Pageable pageable) {
        log.info("Get jokes");
        return new ResponseEntity<>(jokesRepository.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
