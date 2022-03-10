package com.adrianomenezes.test_eis_persistence.controller;

import com.adrianomenezes.test_eis_persistence.DTO.WordDTO;
import com.adrianomenezes.test_eis_persistence.entity.WordEntity;
import com.adrianomenezes.test_eis_persistence.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@CrossOrigin(
        methods = {POST, GET, OPTIONS, PUT, DELETE, PATCH},
        maxAge = 3600,
        allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
        origins = "*"
)
@RequestMapping("/api/v1/words/")
public class WordController {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    private WordsRepository repo;

    public WordController(WordsRepository todoRepo) {
        this.repo = todoRepo;
    }

    @GetMapping
    public Stream<WordDTO> findAll(HttpServletRequest req) {
        return repo.findAll().stream()
                .map(WordController::mapAsWord)
//                .map(w -> w.setUrl(req))
                ;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<WordDTO> findById(HttpServletRequest req, @PathVariable(value = "uid") String uid) {
        Optional<WordEntity> e = repo.findById(UUID.fromString(uid));
        if (e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapAsWord(e.get()));
    }

    @GetMapping("/word/{wordFind}")
    public ResponseEntity<List<Optional<WordEntity>>> findByWord(HttpServletRequest req, @PathVariable(value = "wordFind") String wordFind) {
        List<Optional<WordEntity>> e = repo.findByWordSentence(wordFind);
        if (e.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e);
//        return ResponseEntity.ok(mapAsWord(e.get()));
    }

    @PostMapping
    public ResponseEntity<WordDTO> create(HttpServletRequest req, @RequestBody WordDTO wordReq)
            throws URISyntaxException {
        WordEntity wr = mapAsWordEntity(wordReq);
        repo.save(wr);
        wordReq.setUuid(wr.getUid());
        return ResponseEntity.ok(wordReq);
    }

    @PostMapping("/{word}")
    public ResponseEntity<WordDTO> created(@PathVariable(value = "word") String word)
            throws URISyntaxException {
        WordDTO wordRet = new WordDTO();
        wordRet.setWord(word.replaceAll("\"",""));
        WordEntity wr = mapAsWordEntity(wordRet);
        repo.save(wr);

        wordRet.setUuid(wr.getUid());
        return ResponseEntity.ok(wordRet);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "uid") String uid) {
        if (!repo.existsById(UUID.fromString(uid))) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(UUID.fromString(uid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(HttpServletRequest request) {
        repo.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static WordDTO mapAsWord(WordEntity we) {
        WordDTO word = new WordDTO();
        word.setWord(we.getWordSentence());
        word.setUuid(we.getUid());
        return word;
    }

    private static WordEntity mapAsWordEntity(WordDTO wr) {
        WordEntity word = new WordEntity();
        if (null != wr.getUuid()) {
            word.setUid(wr.getUuid());
        } else {
            word.setUid(UUID.randomUUID());
        }
        word.setWordSentence(wr.getWord());

        return word;
    }

}