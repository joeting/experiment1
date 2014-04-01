package com.loopnet.crawler.dao;

import java.net.UnknownHostException;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.loopnet.crawler.data.Listing;
import com.mongodb.Mongo;

public class ListingDao {

  public static final String DB_NAME = "marketplace";
  private Datastore datastore;
  private Geocoder geocoder;

  private ListingDao(Datastore datastore, Geocoder geocoder) {
  	this.datastore = datastore;
  	this.geocoder = geocoder;
  }

  public static ListingDao listingDao() throws UnknownHostException {
  	Mongo mongo = new Mongo("localhost", 27017);
  	Datastore ds = new Morphia()
  		.map(Listing.class)
  		.createDatastore(mongo, DB_NAME);
  	ds.ensureIndexes();
  	return new ListingDao(ds, new Geocoder());
  }

  public void insert(Listing listing) {
  	if (listing.getLocation() != null) {
    	GeocoderRequest request = new GeocoderRequestBuilder()
    		.setAddress(listing.getLocation())
    		.setLanguage("en")
    		.getGeocoderRequest();
    	GeocodeResponse response = geocoder.geocode(request);
    	
    	if (response.getResults() != null && response.getResults().size() > 0) {
    		double lat = response.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
    		double lng = response.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
    		double[] geo = new double[2];
    		geo[0] = lng;
    		geo[1] = lat;
    		listing.setGeolocation(geo);
    	}
  	}
  	datastore.save(listing);
  }
  
  public void find() {
  }

}
