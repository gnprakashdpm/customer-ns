package com.customerservice.mongo.sequence;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class AddressSequenceGeneratorService {

	@Autowired
	private MongoOperations mongo;

	public long generateSequence(String seqName) {
		AddressDatabaseSequence counter = mongo.findAndModify(query(where("_addressId").is(seqName)), new Update().inc("seq", 1),
				options().returnNew(true).upsert(true), AddressDatabaseSequence.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}
