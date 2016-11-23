package com.osman.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.osman.model.User;

/*This Repository has responsible for controlling CRUD actions on mongoDb */
@Repository
public class UserRepository {
	/*Our Document(DB Table name*/
	public static final String COLLECTION_NAME = "User";
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public void addIssuer(User issuer) {
       /* if (!mongoTemplate.collectionExists(COLLECTION_NAME)) {
            mongoTemplate.createCollection(COLLECTION_NAME);
        }*/     
        mongoTemplate.insert(issuer, COLLECTION_NAME);
    }
	
	public User getIssuerByTicker(String ticker) {
		/*Getting one document with this query*/
	    return mongoTemplate.findOne(
	    		Query.query(Criteria.where("id").is(ticker)), User.class, COLLECTION_NAME);
	}
	
	public List<User> getAllIssuers() {
		/*Getting all documents from User Table*/
        return mongoTemplate.findAll(User.class, COLLECTION_NAME);
    }
     
    public User deleteIssuer(String ticker) {
    	/*Getting User that will be deleted.And delete it finally.*/
    	User issuer = mongoTemplate.findOne(
	    		Query.query(Criteria.where("id").is(ticker)), User.class, COLLECTION_NAME);
        mongoTemplate.remove(issuer, COLLECTION_NAME);
        
        return issuer;
    }
     
    public User updateIssuer(String ticker, User issuer) {
    	/*Firstly get User that will be updated.Then set variables for User document.Finally save document.*/
    	Query query = new Query();
		query.addCriteria(Criteria.where("id").is(ticker));
		
		User issuernew = mongoTemplate.findOne(query,User.class,COLLECTION_NAME);
		System.out.println(issuernew);
		issuernew.setName(issuer.getName());
		issuernew.setSurname(issuer.getSurname());
		issuernew.setTelephone(issuer.getTelephone());
		mongoTemplate.save(issuernew, COLLECTION_NAME);
		/*Update update = new Update();
		update.set("name", issuer.getName());
		update.set("surname", issuer.getSurname());
		update.set("telephone", issuer.getTelephone());
 
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);*/
        
        return issuer;
    }
}

