package com.loopnet.crawler.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.loopnet.crawler.dao.ListingDao;
import com.loopnet.crawler.data.Listing;


public class BizBuySell {
	
	private final int RETRY_LIMIT = 3;
	private final int PAGE_LIMIT = 3;
	private final Pattern URLPattern = Pattern.compile("<a href=\"(.*?)\"");
	
	private final Pattern titlePattern = Pattern.compile("<h1 style=\"font-size: 30px; margin-bottom: 0;");
	private final Pattern continuationPattern = Pattern.compile("\\s*(.*?)<");
	private final Pattern locationPattern = Pattern.compile("<h2 class=\"gray\" style=\"font-size: 22px\">");
	
	private final Pattern askingPricePattern = Pattern.compile("Asking Price:");
	private final Pattern grossIncomePattern = Pattern.compile("Gross Income:");
	private final Pattern cashFlowPattern = Pattern.compile("Cash Flow:");
	private final Pattern ebitdaPattern = Pattern.compile("EBITDA:");
	private final Pattern ffePattern = Pattern.compile("FF&amp;E:");
	private final Pattern boldPattern = Pattern.compile("\\s*<b>(.*?)<");
	
	private final Pattern inventoryPattern = Pattern.compile("<strong>Inventory:");
	private final Pattern furniturePattern = Pattern.compile("<strong>Furniture");
	private final Pattern facilitiesPattern = Pattern.compile("<strong>Facilities:");
	private final Pattern competitionPattern = Pattern.compile("<strong>Competition:");
	private final Pattern growthPattern = Pattern.compile("<strong>Growth");
	private final Pattern financingPattern = Pattern.compile("<strong>Financing");
	private final Pattern supportPattern = Pattern.compile("<strong>Support");
	private final Pattern reasonPattern = Pattern.compile("<strong>Reason");	
	private final Pattern ddPattern = Pattern.compile("\\s*<dd>\\s*(.*?)<");
	
	private final Pattern nextPagePattern = Pattern.compile("\\s*<a title=\"Next Page");

	public void loopListingCategory(String urlString, String category) throws Exception {
		loopListingCategory(urlString, category, 1);
	}
	
    public void loopListingCategory(String urlString, String category, int page) throws Exception {
    	List<String> detailPageUrls = new ArrayList<String>();
		URLConnection connection = prepareUrlConnection(urlString + "/" + category + "/" + page);
		connection.setConnectTimeout(30000);
        connection.connect();
        InputStream istr = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(istr));
        String line;
        while ((line = reader.readLine()) != null) {
        	Matcher urlPattern = URLPattern.matcher(line);
            if (urlPattern.find()) {
            	String urlMatch = urlPattern.group(1);
            	if (urlMatch.contains("Business-Opportunity")) {
            		detailPageUrls.add("http://www.bizbuysell.com/" + urlMatch);
            	}
            }
            if (page < PAGE_LIMIT) {
	            Matcher m = nextPagePattern.matcher(line);
	            if (m.find()) {
	            	loopListingCategory(urlString, category, page + 1);
	            }
            }
        }
        istr.close();
        for (String url : detailPageUrls) {
        	int retry = RETRY_LIMIT;
        	while (true) {
        		try {
        			if (retry == 0) {
        				break;
        			}
        			getListingDetails(url);
        			break;
        		} catch (Throwable t) {
        			t.printStackTrace();
        			retry--;
        		}
        	}
        }
    }

    public void getListingDetails(String urlString) throws Exception {
    	ListingDao dao = ListingDao.listingDao();
    	URLConnection connection = prepareUrlConnection(urlString);
    	connection.setConnectTimeout(3000);
        connection.connect();
        InputStream istr = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(istr));
        String line;
        Listing listing = new Listing();
        while ((line = reader.readLine()) != null) {
        	if (titlePattern.matcher(line).find()) {
        		line = reader.readLine();
        		Matcher m = continuationPattern.matcher(line);
        		if (m.find()) {
        			listing.setTitle(m.group(1));
        		}
        		
        	} else if (locationPattern.matcher(line).find()) {
        		line = reader.readLine();
        		Matcher m = continuationPattern.matcher(line);
        		if (m.find()) {
        			listing.setLocation(m.group(1));
        		}
        	}  else if (askingPricePattern.matcher(line).find()) {
        		line = reader.readLine();
        		Matcher m = boldPattern.matcher(line);
        		if (m.find()) {
        			listing.setAskingPrice(m.group(1));
        		}
        	}  else if (grossIncomePattern.matcher(line).find()) {
        		line = reader.readLine();
        		Matcher m = boldPattern.matcher(line);
        		if (m.find()) {
        			listing.setGrossIncome(m.group(1));
        		}
        	} else if (inventoryPattern.matcher(line).find()) {
        		line = reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setInventory(m.group(1));
        		}
        	} else if (furniturePattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setFfe(m.group(1));
        		}
        	} else if (facilitiesPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setFacilities(m.group(1));
        		}
        	} else if (competitionPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setCompetition(m.group(1));
        		}
        	} else if (growthPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setGrowth(m.group(1));
        		}
        	} else if (financingPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setFinancing(m.group(1));
        		}
        	} else if (supportPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setTraining(m.group(1));
        		}
        	} else if (reasonPattern.matcher(line).find()) {
        		line = reader.readLine();
        		line += reader.readLine();
        		Matcher m = ddPattern.matcher(line);
        		if (m.find()) {
        			listing.setSellingReason(m.group(1));
        		}
        	}
        }
        dao.insert(listing);
        System.out.println("Crawled " +  urlString);
        istr.close();
    }
    
    private static URLConnection prepareUrlConnection(String urlString) 
	    throws MalformedURLException, IOException {
		URL url = new URL(urlString);
        URLConnection connection = null;
        connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla//4.0");
		return connection;
	}

}
