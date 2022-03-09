package com.adrianomenezes.test_eis_persistence.repository;

import com.adrianomenezes.test_eis_persistence.entity.WordEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WordsRepository extends CassandraRepository<WordEntity, UUID> {

    @Query("select * from word where word_sentence = ?0")
    Iterable<WordEntity> findByWordSentence(String word_sentence);
}

