package com.example.finance;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class SimpleStream {
	static ArrayList<String> tweetDeck = new ArrayList<String>();

    static String[] anewWords = {"time", "man", "people", "good", "world", "life", "house", "home", "thought", "part", "war", "water", "hand", "city", "young", "father", "social", "present", "face", "white", "church", "power", "family", "interest", "mind", "god", "kind", "door", "name", "history", "death", "body", "car", "field", "money", "free", "air", "office", "moment", "street", "boy", "board", "love", "wife", "girl", "mother", "music", "party", "education", "child", "art", "black", "hard", "strong", "peace", "table", "red", "idea", "alone", "book", "cut", "nature", "fire", "pressure", "dark", "space", "spirit", "hope", "person", "material", "dead", "heart", "lost", "industry", "cold", "river", "building", "paper", "natural", "market", "answer", "earth", "fall", "food", "knowledge", "letter", "square", "blue", "method", "moral", "color", "trouble", "friend", "army", "chance", "month", "theory", "freedom", "spring", "beautiful", "bed", "fear", "hotel", "truth", "plant", "respect", "easy", "farm", "manner", "game", "couple", "radio", "progress", "window", "gun", "horse", "serious", "green", "hit", "corner", "justice", "quality", "plane", "sun", "hospital", "pretty", "stress", "agreement", "health", "machine", "unit", "news", "doctor", "glass", "happy", "fight", "opinion", "hell", "arm", "success", "dinner", "failure", "famous", "key", "king", "pain", "poetry", "bright", "patient", "learn", "sex", "ship", "youth", "bar", "crisis", "watch", "event", "teacher", "desire", "favor", "knife", "memory", "quiet", "bottle", "nice", "rock", "dog", "leader", "writer", "advantage", "brother", "phase", "village", "wine", "detail", "evil", "beauty", "column", "rain", "song", "danger", "foot", "quick", "dress", "honor", "capable", "chair", "sleep", "achievement", "cell", "dream", "powerful", "rifle", "broken", "pleasure", "save", "baby", "imagine", "metal", "soft", "travel", "victory", "beach", "coast", "eat", "friendly", "orchestra", "slow", "circle", "fat", "garden", "lie", "snow", "taste", "tree", "safe", "sky", "surprised", "useful", "exercise", "truck", "afraid", "alive", "hat", "loved", "vision", "identity", "wagon", "win", "yellow", "lake", "item", "seat", "sin", "grass", "trust", "gold", "sick", "devoted", "proud", "engine", "milk", "passage", "tragedy", "acceptance", "plain", "anger", "destroy", "honest", "humor", "mail", "vacation"};
    static Double[] valenceMean = {5.31, 6.73, 7.33, 7.47, 6.5, 7.27, 7.26, 7.91, 6.39, 5.11, 2.08, 6.61, 5.95, 6.03, 6.89, 7.08, 6.88, 6.95, 6.39, 6.47, 6.28, 6.54, 7.65, 6.97, 6.68, 8.15, 7.59, 5.13, 5.55, 5.24, 1.61, 5.55, 7.73, 6.2, 7.59, 8.26, 6.34, 5.24, 5.76, 5.22, 6.32, 4.82, 8.72, 6.33, 6.87, 8.39, 8.13, 7.86, 6.69, 7.08, 6.68, 5.39, 5.22, 7.11, 7.72, 5.22, 6.41, 7.0, 2.41, 5.72, 3.64, 7.65, 3.22, 3.38, 4.71, 6.78, 7.0, 7.05, 6.32, 5.26, 1.94, 7.39, 2.82, 5.3, 4.02, 6.85, 5.29, 5.2, 6.59, 5.66, 6.63, 7.15, 4.09, 7.65, 7.58, 6.61, 4.74, 6.76, 5.56, 6.2, 7.02, 3.03, 7.74, 4.72, 6.02, 5.15, 5.3, 7.58, 7.76, 7.6, 7.51, 2.76, 6.0, 7.8, 5.98, 7.64, 7.1, 5.53, 5.64, 6.98, 7.41, 6.73, 7.73, 5.91, 3.47, 5.89, 5.08, 6.18, 4.33, 4.36, 7.78, 6.25, 6.43, 7.55, 5.04, 7.75, 2.09, 7.08, 6.81, 5.09, 5.59, 5.3, 5.2, 4.75, 8.21, 3.76, 6.28, 2.24, 5.34, 8.29, 7.16, 1.7, 6.98, 5.68, 7.26, 2.13, 5.86, 7.5, 5.29, 7.15, 8.05, 5.55, 6.75, 6.42, 2.74, 5.78, 6.21, 5.68, 7.69, 6.46, 3.62, 6.62, 5.58, 6.15, 6.55, 5.56, 7.57, 7.63, 5.52, 6.95, 7.11, 5.17, 5.92, 5.95, 5.55, 3.23, 7.82, 5.17, 5.08, 7.1, 2.95, 5.02, 6.64, 6.41, 7.66, 7.16, 5.08, 7.2, 7.89, 3.82, 6.73, 6.84, 4.02, 3.05, 8.28, 6.45, 8.22, 7.32, 4.95, 7.12, 7.1, 8.32, 8.03, 5.98, 7.47, 8.43, 6.02, 3.93, 5.67, 2.28, 6.71, 2.79, 7.08, 6.66, 6.32, 7.07, 7.37, 7.47, 7.14, 7.13, 5.47, 2.0, 7.25, 5.46, 8.64, 6.62, 6.57, 5.37, 8.38, 5.61, 6.82, 5.26, 4.95, 2.8, 6.12, 6.68, 7.54, 1.9, 7.41, 8.03, 5.2, 5.95, 5.28, 1.78, 7.98, 4.39, 2.34, 2.64, 7.7, 8.56, 6.88, 8.16};
    static Double[] valenceSD = {2.02, 1.7, 1.7, 1.45, 2.03, 1.88, 1.72, 1.63, 1.58, 1.78, 1.91, 1.78, 1.38, 1.37, 2.12, 2.2, 1.82, 1.85, 1.6, 1.59, 2.31, 2.21, 1.55, 1.53, 1.84, 1.27, 1.67, 1.44, 2.24, 2.01, 1.4, 2.37, 1.63, 1.37, 1.4, 1.31, 1.56, 1.59, 1.65, 0.72, 1.6, 1.23, 0.7, 1.97, 1.64, 1.15, 1.09, 1.83, 1.77, 1.98, 2.1, 1.8, 1.82, 1.48, 1.75, 0.72, 1.61, 1.34, 1.77, 1.54, 2.08, 1.37, 2.06, 1.61, 2.36, 1.66, 1.32, 1.96, 1.74, 1.29, 1.76, 1.53, 1.83, 1.61, 1.99, 1.69, 1.15, 1.21, 1.57, 1.02, 1.68, 1.67, 2.21, 1.37, 1.32, 1.59, 1.02, 1.78, 1.76, 1.85, 1.57, 2.09, 1.24, 1.75, 1.77, 1.09, 1.49, 2.04, 1.51, 1.64, 1.38, 2.12, 1.77, 1.29, 1.83, 1.29, 1.91, 1.85, 1.34, 1.97, 1.97, 1.47, 1.34, 1.38, 2.48, 1.55, 1.59, 2.05, 2.35, 1.21, 1.35, 1.59, 1.98, 1.85, 2.45, 1.26, 1.41, 1.59, 1.88, 1.67, 1.87, 1.67, 2.54, 1.38, 1.82, 2.63, 1.45, 1.62, 1.82, 0.93, 1.5, 1.07, 2.07, 1.62, 1.67, 1.81, 1.91, 1.55, 1.89, 1.49, 1.53, 1.4, 2.29, 2.05, 2.23, 1.51, 1.63, 2.12, 1.39, 1.52, 2.18, 1.5, 1.83, 1.49, 2.44, 1.38, 1.66, 1.59, 1.9, 1.85, 2.17, 0.79, 1.34, 2.19, 1.58, 2.64, 1.16, 0.85, 2.51, 1.97, 2.22, 0.93, 1.61, 1.34, 1.24, 1.39, 0.98, 1.77, 1.38, 1.7, 1.75, 1.8, 2.76, 1.92, 0.92, 1.93, 1.2, 1.52, 1.17, 1.34, 2.0, 1.16, 1.59, 1.86, 1.73, 1.08, 1.89, 1.6, 1.26, 1.92, 1.74, 1.92, 1.83, 1.57, 1.56, 1.9, 1.4, 1.56, 1.6, 1.58, 1.88, 1.28, 2.22, 1.36, 0.71, 1.84, 1.99, 0.97, 0.92, 1.94, 1.54, 0.86, 0.98, 1.67, 1.44, 2.71, 1.63, 1.14, 1.37, 1.56, 1.18, 2.16, 1.44, 1.31, 1.42, 1.46, 1.32, 2.03, 1.43, 0.81, 1.74, 1.36};
    static Double[] arousalMean = {4.64, 5.24, 5.94, 5.43, 5.32, 6.02, 4.56, 4.21, 4.83, 3.82, 7.49, 4.97, 4.40, 5.24, 5.64, 5.92, 4.98, 5.12, 5.04, 4.37, 4.34, 6.67, 4.80, 5.66, 5.00, 5.95, 4.46, 3.80, 4.25, 3.93, 4.59, 5.52, 6.24, 4.08, 5.70, 5.15, 4.12, 4.08, 3.83, 3.39, 4.58, 3.36, 6.44, 4.93, 4.29, 6.13, 5.32, 6.69, 5.74, 5.55, 4.86, 4.61, 5.12, 5.92, 2.95, 2.92, 5.29, 5.86, 4.83, 4.17, 5.00, 4.37, 7.17, 6.07, 4.28, 5.14, 5.56, 5.44, 4.19, 4.05, 5.73, 6.34, 5.82, 4.47, 5.19, 4.51, 3.92, 2.50, 4.09, 4.12, 5.41, 4.24, 4.70, 5.92, 5.92, 4.90, 3.18, 4.31, 3.85, 4.49, 4.73, 6.85, 5.74, 5.03, 5.38, 4.03, 4.62, 5.52, 5.67, 6.17, 3.61, 6.96, 4.80, 5.00, 3.62, 5.19, 4.48, 3.90, 4.56, 5.89, 6.39, 4.78, 6.02, 3.97, 7.02, 3.89, 4.00, 4.28, 5.73, 3.91, 5.47, 4.48, 6.14, 5.04, 5.98, 6.03, 7.45, 5.02, 5.13, 3.82, 3.75, 5.17, 5.86, 4.27, 6.49, 7.15, 4.89, 5.38, 3.59, 6.11, 5.43, 4.95, 5.73, 3.70, 5.51, 6.50, 4.00, 5.40, 4.21, 5.39, 7.36, 4.38, 5.67, 5.00, 5.44, 4.10, 5.10, 4.05, 7.35, 4.54, 5.80, 5.42, 2.82, 4.79, 4.38, 4.52, 5.76, 6.27, 4.33, 4.76, 4.71, 3.98, 4.08, 4.78, 4.10, 6.39, 4.95, 3.62, 3.65, 6.07, 7.32, 3.27, 6.57, 4.05, 5.90, 5.08, 3.15, 2.80, 5.53, 4.08, 4.53, 5.83, 6.35, 5.43, 5.74, 4.95, 5.53, 5.98, 3.79, 4.63, 6.21, 6.63, 5.53, 4.59, 5.69, 5.11, 3.52, 3.39, 3.86, 4.81, 4.39, 5.96, 5.75, 5.22, 3.42, 3.86, 4.27, 7.47, 4.26, 6.84, 4.84, 6.67, 5.50, 4.10, 6.38, 4.66, 4.95, 3.98, 7.72, 4.43, 3.95, 3.24, 2.95, 5.78, 4.14, 5.30, 5.76, 4.29, 5.23, 5.56, 3.98, 3.68, 4.36, 6.24, 5.40, 3.52, 7.63, 6.83, 5.32, 5.50, 5.63, 5.64};
    static Double[] arousalSD = {2.75, 2.31, 2.09, 2.85, 2.39, 2.62, 2.41, 2.94, 2.46, 2.24, 2.16, 2.49, 2.07, 2.53, 2.51, 2.60, 2.59, 2.39, 2.18, 2.14, 2.45, 1.87, 2.71, 2.26, 2.68, 2.84, 2.55, 2.29, 2.47, 2.29, 3.07, 2.63, 2.04, 2.41, 2.66, 3.04, 2.30, 1.92, 2.29, 1.87, 2.37, 2.12, 3.35, 2.22, 2.69, 2.71, 3.19, 2.84, 2.46, 2.29, 2.88, 2.24, 2.19, 2.28, 2.55, 2.16, 2.04, 1.81, 2.66, 2.49, 2.32, 2.51, 2.06, 2.26, 2.21, 2.54, 2.62, 2.47, 2.45, 2.34, 2.73, 2.25, 2.62, 2.43, 2.23, 2.42, 1.94, 1.85, 2.37, 1.83, 2.43, 2.49, 2.48, 2.11, 2.32, 2.37, 1.76, 2.20, 2.58, 2.28, 2.64, 2.03, 2.57, 2.03, 2.58, 1.77, 1.94, 2.72, 2.51, 2.34, 2.56, 2.17, 2.53, 2.77, 2.25, 2.39, 2.82, 1.95, 1.78, 2.37, 2.31, 2.82, 2.58, 2.01, 1.84, 2.17, 1.87, 2.46, 2.09, 1.92, 2.54, 2.12, 2.39, 2.66, 2.54, 2.22, 2.38, 2.24, 2.35, 2.40, 2.49, 2.11, 2.70, 2.07, 2.77, 2.19, 2.46, 2.62, 2.40, 2.65, 2.14, 2.81, 2.68, 2.18, 2.77, 2.49, 2.85, 2.33, 2.37, 2.22, 1.91, 2.29, 2.52, 2.83, 3.07, 2.12, 2.40, 2.61, 1.76, 1.86, 2.00, 2.25, 2.13, 2.44, 2.69, 2.37, 2.50, 2.18, 2.45, 2.18, 2.68, 1.82, 1.87, 2.34, 2.24, 2.44, 2.57, 1.91, 2.35, 2.42, 2.07, 1.98, 1.78, 1.89, 1.83, 2.07, 1.77, 2.66, 2.81, 2.19, 2.72, 2.69, 2.04, 2.42, 2.81, 2.19, 2.80, 2.14, 1.96, 2.61, 2.51, 2.84, 3.07, 2.31, 2.51, 2.96, 2.29, 2.22, 2.13, 2.80, 2.35, 2.63, 2.47, 2.38, 2.21, 2.72, 2.17, 2.09, 2.47, 2.06, 2.17, 2.54, 2.74, 2.00, 2.68, 2.43, 2.24, 2.04, 2.16, 2.05, 2.44, 2.08, 1.72, 2.21, 2.11, 2.66, 2.79, 2.45, 2.21, 3.01, 2.33, 2.57, 2.13, 2.64, 2.70, 2.05, 1.91, 2.38, 1.92, 2.91, 2.36, 2.99};
    
	static ArrayList<Double> valenceOverall = new ArrayList<Double>();
	static ArrayList<Double> arousalOverall = new ArrayList<Double>();

	private int socialIndex;
	public SimpleStream(String ticker, String companyName) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("HBJcc3xGwdOFQXCB5ut14KpMt");
		cb.setOAuthConsumerSecret("CG4GKWd5uAuviWgE6Kd6KUHmmCiIhHgjmvyY9H5FLepE82actg");
		cb.setOAuthAccessToken("152786450-wWYItMcQhzH1RjUQzkAMGK6aOIb5h0mVvtwqvM7c");
		cb.setOAuthAccessTokenSecret("QW3OnrDbF0AXLRjROXEyun6H7g0gBkQHd2bXEzvrINk78");


		String stx = "($"+ticker+") OR ("+companyName+")";
		//System.out.println(stx);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {
			Query query = new Query(stx);
			query.setResultType(Query.MIXED);
			query.setCount(100);
			QueryResult result = twitter.search(query);
			do{
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					tweetDeck.add(tweet.getText());
				}
				query=result.nextQuery();
				if(query!=null)
					result=twitter.search(query);
			}while(query!=null);
			findMatches();
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	




	public void findMatches() {
		//System.out.println(tweetDeck.size());
		for (int i = 0; i < tweetDeck.size(); i++) {
			ArrayList<Double> valenceTweetMeans = new ArrayList<Double>();
			ArrayList<Double> valenceTweetSDS = new ArrayList<Double>();
			ArrayList<Double> arousalTweetMeans = new ArrayList<Double>();
			ArrayList<Double> arousalTweetSDS = new ArrayList<Double>();
			String[] words = tweetDeck.get(i).replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			//System.out.println(words.length);
			for (int j = 0; j < words.length; j++) { //searches through each word in each tweet
				for (int k = 0; k < anewWords.length; k++) { //searches through the words in ANEW
					//System.out.println(anewWords.get(k));
					
					if (words[j].trim().replaceAll("#","").equals(anewWords[k])) { //if the word in the sentence is equal to the word in ANEW
						valenceTweetMeans.add(valenceMean[k]);
						valenceTweetSDS.add(valenceSD[k]);
						arousalTweetMeans.add(arousalMean[k]);
						arousalTweetSDS.add(arousalSD[k]);
					}
				}
			} //finished searching through the tweet
			

			double bigValenceDivisor=0;
			double bigValenceNumerator=0;
			double bigArousalDivisor=0;
			double bigArousalNumerator=0;

			for (int x = 0; x < valenceTweetMeans.size(); x++) {
				bigValenceDivisor += (1 / valenceTweetSDS.get(x));
				bigArousalDivisor += (1 / arousalTweetSDS.get(x));
				bigValenceNumerator += (valenceTweetMeans.get(x) / valenceTweetSDS.get(x));
				bigArousalNumerator += (arousalTweetMeans.get(x) / arousalTweetSDS.get(x));
			}

			double Valence=0.0;
			double Arousal=0.0;

			Valence = bigValenceNumerator / bigValenceDivisor;
			Arousal = bigArousalNumerator / bigArousalDivisor;
			
			int comp = (int) Valence;
			//System.out.println(comp);
			
			if (comp != 0) {
				valenceOverall.add(Valence);
				//System.out.println("The Valence for Tweet #" + (i+1) + " is "  + Valence);
				arousalOverall.add(Arousal);
				//System.out.println("The Arousal for Tweet #" + (i+1) + " is "  + Arousal);
			}


		}
		//System.out.println(valenceOverall.size());
		
		DescriptiveStatistics valence1 = new DescriptiveStatistics();
		DescriptiveStatistics valence2 = new DescriptiveStatistics();
		DescriptiveStatistics valence3 = new DescriptiveStatistics();
		DescriptiveStatistics valence4 = new DescriptiveStatistics();
		DescriptiveStatistics arousal1 = new DescriptiveStatistics();
		DescriptiveStatistics arousal2 = new DescriptiveStatistics();
		DescriptiveStatistics arousal3 = new DescriptiveStatistics();
		DescriptiveStatistics arousal4 = new DescriptiveStatistics();
		int v1=0;
		int v2=0;
		int v3=0;
		int v4=0;
		int a1=0;
		int a2=0;
		int a3=0;
		int a4=0;
		for (int i = 0; i < valenceOverall.size(); i++) {
			if (valenceOverall.get(i) > 4.5 && arousalOverall.get(i) > 4.5) {
				valence1.addValue(valenceOverall.get(i));
				v1++;
				arousal1.addValue(arousalOverall.get(i));
				a1++;
			}
			if (valenceOverall.get(i) < 4.5 && arousalOverall.get(i) < 4.5) {
				valence3.addValue(valenceOverall.get(i));
				v3++;
				arousal3.addValue(arousalOverall.get(i));
				a3++;
			}
			if (valenceOverall.get(i) > 4.5 && arousalOverall.get(i) < 4.5) {
				valence2.addValue(valenceOverall.get(i));
				v2++;
				arousal2.addValue(arousalOverall.get(i));
				a2++;
			}
			if (valenceOverall.get(i) < 4.5 && arousalOverall.get(i) > 4.5) {
				valence4.addValue(valenceOverall.get(i));
				v4++;
				arousal4.addValue(arousalOverall.get(i));
				a4++;
			}
		}
		/*System.out.println("Valence1 Average: " + valence1.getMean());
		System.out.println(v1);
		System.out.println("Arousal1 Average: " + arousal1.getMean());
		System.out.println(a1);
		System.out.println("Valence2 Average: " + valence2.getMean());
		System.out.println(v2);
		System.out.println("Arousal2 Average: " + arousal2.getMean());
		System.out.println(a2);
		System.out.println("Valence3 Average: " + valence3.getMean());
		System.out.println(v3);
		System.out.println("Arousal3 Average: " + arousal3.getMean());
		System.out.println(a3);
		System.out.println("Valence4 Average: " + valence4.getMean());
		System.out.println(v4);
		System.out.println("Arousal4 Average: " + arousal4.getMean());
		System.out.println(a4);*/
		
		SimpleRegression full = new SimpleRegression();
		for (int i = 0; i < valenceOverall.size(); i++) {
			full.addData(valenceOverall.get(i), arousalOverall.get(i));
		}
		
		double indexV;
		double indexA;
		double vM1 = valence1.getMean();
		double vM2 = valence2.getMean();
		double vM3 = valence3.getMean();
		double vM4 = valence4.getMean();
		double aM1 = arousal1.getMean();
		double aM2 = arousal2.getMean();
		double aM3 = arousal3.getMean();
		double aM4 = arousal4.getMean();
		
		if (Double.isNaN(vM1)) vM1=0.0;
		if (Double.isNaN(vM2)) vM2=0.0;
		if (Double.isNaN(vM3)) vM3=0.0;
		if (Double.isNaN(vM4)) vM4=0.0;
		if (Double.isNaN(aM1)) aM1=0.0;
		if (Double.isNaN(aM2)) aM2=0.0;
		if (Double.isNaN(aM3)) aM3=0.0;
		if (Double.isNaN(aM4)) aM4=0.0;
		
		indexV = ((vM1*v1+vM2*v2+vM3*v3+vM4*v4)/(v1+v2+v3+v4));
		indexA = (((aM1*a1+aM2*a2+aM3*a3+aM4*a4)/(a1+a2+a3+a4)))-4.5;
		//System.out.println(indexV);
		//System.out.println(indexA);
		double combInd = indexV * indexA;
		System.out.println(combInd);
		setSocialIndex((int) (combInd * 15));
		
		//System.out.println("Flumedex: " + flumedex);
		
		
	}






	public int getSocialIndex() {
		return socialIndex;
	}






	public void setSocialIndex(int socialIndex) {
		this.socialIndex = socialIndex;
	}
}