package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.viewmodel.JokesVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
@Slf4j
public class JokesService {

    private final JokesRepository jokesRepository;

    public JokesService(JokesRepository jokesRepository) {
        this.jokesRepository = jokesRepository;
    }

    public JokesVM getOneJoke() {
        var client = WebClient.builder()
                .baseUrl("https://v2.jokeapi.dev/joke/")
                .build();
        return client.get()
                .uri("Any?lang=de&type=single")
                .retrieve()
                .bodyToMono(JokesVM.class)
                .block();
    }

    public boolean isJokeExisting(int id) {
        return jokesRepository.existsById(id);
    }

    /*public JokesVM getAllJokes(Pageable pageable){
        var allJokes = jokesRepository.findAll(pageable).getContent();
        var mappedJokes = new ArrayList<JokesVM>();
        for (var joke:allJokes) {
            var mappedJoke = new JokesVM();
            mappedJoke.setJoke();
        }

        return (JokesVM) jokesRepository.findAll(pageable).getContent();
    }

     */

//    @EventListener(ContextRefreshedEvent.class)
//    public void preloadDatabase() {
//        if (jokesRepository.count() != 0) {
//            log.info("database already contains data...");
//            return;
//        }
//
//        log.info("will load jokes from classpath...");
//        try (var lineStream = Files.lines(new ClassPathResource("chucknorris.txt").getFile()
//                .toPath(), StandardCharsets.UTF_8)) {
//            var jokes = lineStream.filter(x -> !x.isEmpty())
//                    .map(x -> new JokesEntity().setJoke(x))
//                    .toList();
//            jokesRepository.saveAll(jokes);
//        } catch (IOException e) {
//            throw new RuntimeException("failed reading jokes from classpath", e);
//        }
//    }
}
