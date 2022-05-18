package com.shopstyle.catalog.model.DBSequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_sequences")
public class DbSequence {
    @Id
    private String id;
    private int seq;
}
