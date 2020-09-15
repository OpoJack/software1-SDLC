package module2;

import java.io.File;
import java.io.*;
import java.text.ParseException;
import java.util.*;

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
		List<String> poemLIST = new ArrayList<String>();
		poemLIST = Arrays.asList(webData);
		
		for(String s: poemLIST) {
			System.out.println(s);
		}
		System.out.println(poemLIST.get(0));
		
		
	}
	
	public static String getData(String myURL) throws IOException {
		System.out.println("Connecting...");
		Document doc = Jsoup.connect(myURL).get();
		
		//I get elements in this order because the website separates them.
		//Spaces added where necessary
		String poem = doc.body().getElementsByTag("h1").text() + " " + doc.body().getElementsByTag("h4").text() + " " + doc.body().getElementsByTag("h3").text()  + " " + doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	
}
