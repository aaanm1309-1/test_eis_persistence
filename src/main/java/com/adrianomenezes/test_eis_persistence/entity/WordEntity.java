package com.adrianomenezes.test_eis_persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.UUID;

@Table(value = WordEntity.TABLENAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordEntity {
    public static final String TABLENAME = "words";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_SENTENCE = "wordSentence";


    @PrimaryKey
    @Column(COLUMN_UID)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID uid;

    @Column(COLUMN_SENTENCE)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String wordSentence;

    public WordEntity(String word) {
        this(UUID.randomUUID(), word);
    }

}
