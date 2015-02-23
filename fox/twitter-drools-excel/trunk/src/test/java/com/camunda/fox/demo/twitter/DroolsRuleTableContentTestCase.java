package com.camunda.fox.demo.twitter;

import junit.framework.TestCase;

public class DroolsRuleTableContentTestCase extends TestCase {
	
	private static final String TWEET_REJECTION_RULES_XLS = "/TweetRejectionRules.xls";

	public void testDrools1() throws Exception {
		Tweet tweet = new Tweet();
		tweet.setContent("We will never see this content on Twitter, because it is about John Doe and from Jakob...");
		tweet.setEmail("jakob.freund@camunda.com");

		executeDroolsExcelRuleTable(tweet);
		assertEquals(false, tweet.isApproved());
	}

	public void testDrools2() throws Exception {
		Tweet tweet = new Tweet();
		tweet.setContent("We will see this content on Twitter as it is from MS...");
		tweet.setEmail("ms@camunda.com");

		executeDroolsExcelRuleTable(tweet);
		assertEquals(true, tweet.isApproved());

	}

	public void testDrools3() throws Exception {
		Tweet tweet = new Tweet();
		tweet.setContent("We will never see this content on Twitter, because it is about Jon Doe...");
		tweet.setEmail("ms@camunda.com");
		executeDroolsExcelRuleTable(tweet);
		assertEquals(false, tweet.isApproved());

	}
	
	private void executeDroolsExcelRuleTable(Tweet tweet) throws Exception {
		String decisionTable = TWEET_REJECTION_RULES_XLS;
		DroolsUtil d = new DroolsUtil();

		d.loadDecisionTable(decisionTable);
		d.executeDrools(tweet);
		printResult(tweet);
	}	

	public void testDrools4() throws Exception {
		Tweet tweet = new Tweet();
		tweet.setContent("We will never see this content on Twitter, because it is about Jon Doe and from Jakob...");
		tweet.setEmail("jakob.freund@camunda.com");

		Tweet tweet1 = new Tweet();
		tweet1.setContent("We will see this content on Twitter as it is from MS...");
		tweet1.setEmail("ms@camunda.com");

		Tweet[] tweets = new Tweet[2];
		tweets[0] = tweet;
		tweets[1] = tweet1;

		DroolsUtil d = new DroolsUtil();
		d.loadDecisionTable(TWEET_REJECTION_RULES_XLS);
		d.executeDrools(tweets);

		printResult(tweets[0]);
		assertEquals(false, tweets[0].isApproved());

		printResult(tweets[1]);
		assertEquals(true, tweets[1].isApproved());
	}

	private void printResult(Tweet tweet) {
		System.out.println("");
		System.out.println(tweet.getEmail());
		System.out.println(tweet.getContent());
		System.out.println("tweet: is the tweet approved? -> "
				+ tweet.isApproved());
		if (tweet.isApproved() == false)
			System.out.println("rejection comment: "
					+ tweet.getRejectionComment());
	}
}