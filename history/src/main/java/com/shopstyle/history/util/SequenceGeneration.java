package com.shopstyle.history.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class SequenceGeneration {
    @Autowired
    private MongoOperations mongoOperations;

    public long getSequenceNumber(String sequenceName) {
        //get sequence number
        Query query = new Query (Criteria.where("id").is(sequenceName));
        // update the sequence number
        Update update = new Update().inc("seq",1);
        // modify in document
        DbSequence dbSequence = mongoOperations.findAndModify(query,update,
                options().returnNew(true).upsert(true),
                DbSequence.class);
        return !Objects.isNull(dbSequence)  ? dbSequence.getSeq() : 1;
    }
}
