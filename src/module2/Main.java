package module2;

import java.io.File;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import org.jsoup.parser.*;


/* Name: Jack Oporto
 * Class: CEN 3024C
 * Professor Dhrgam Al Kafaf
 * Assignment: Collections
 * Date completed: June 17, 2020
 * 
 * Summary:
 * This program utilizes jsoup to extract a poem from a URL and relay the most used word
 * along with their frequency
 */


public class Main {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String myURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String poem = getData(myURL);
		String webData[] = poem.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
		HashMap<String,Integer> poemHash = new HashMap<String,Integer>();
		
		poemHash = wordCount(webData);
		
		
		
		
		
//		List<String> poemLIST = new ArrayList<String>();
//		poemLIST = Arrays.asList(webData);
		
		
		
	}
	
	public static String getData(String myURL) throws IOException {
		System.out.println("Connecting...");
		Document doc = Jsoup.connect(myURL).get();
		System.out.println("Connected!");
		
		//I get elements in this order because the website separates them.
		//Spaces added where necessary
		String poem = doc.body().getElementsByTag("h1").text() + " " + doc.body().getElementsByTag("h4").text() + " " + doc.body().getElementsByTag("h3").text()  + " " + doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	public static HashMap<String, Integer> wordCount(String[] strings) {
		  HashMap<String, Integer> map = new HashMap<String, Integer> ();
		  for (String s:strings) {
		    
		    if (!map.containsKey(s)) {
		      map.put(s, 1);
		    }
		    else {
		      int count = map.get(s);
		      map.put(s, count + 1);
		    }
		  }
		  return map;
		}
	
	
}
