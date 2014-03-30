package com.loopnet.crawler.dao;

import java.net.UnknownHostException;


import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.loopnet.crawler.data.Listing;
import com.mongodb.Mongo;

public class ListingDao {
	
	public static final String DB_NAME = "marketplace";
	private Datastore datastore;
	
	private ListingDao(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public static ListingDao listingDao() throws UnknownHostException {
		Mongo mongo = new Mongo("localhost", 27017);
		Datastore ds = new Morphia()
    		.map(Listing.class)
    		.createDatastore(mongo, DB_NAME);
		ds.ensureIndexes();
		return new ListingDao(ds);
	}
	
	public void insert(Listing listing) {
		datastore.save(listing);
	}
	
}
