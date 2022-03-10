package com.adrianomenezes.test_eis_persistence.repository;

import com.adrianomenezes.test_eis_persistence.entity.WordEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface WordsRepository extends CassandraRepository<WordEntity, UUID> {

    @Query("select * from words where wordsentence = ?0")
    List<Optional<WordEntity>> findByWordSentence(String wordsentence);
}

