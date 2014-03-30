package com.loopnet.crawler;

import com.loopnet.crawler.provider.BizBuySell;

/**
 * Runs the parser
 *
 */
public class Runner {
  
  public static void main(String argv[]) throws Exception {
	new BizBuySell().loopListingCategory("http://www.bizbuysell.com", "gas-stations-for-sale");
  }
  
}
