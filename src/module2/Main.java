package module2;

import java.io.File;
import java.io.IOException;
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
		String data[] = poem.split(" ");
		List<String> poemLIST = new ArrayList<String>();
		poemLIST = Arrays.asList(data);
		
		//WORDS SEPERATED NOW, FIND A WAY TO TALLY UP FREQUENCY AND OUTPUT TOP 20 WORDS WITH FREQUENCIES
		for(String s: poemLIST) {
			System.out.println(s);
		}
		
		
	}
	
	public static String getData(String myURL) throws IOException {
		Document doc = Jsoup.connect(myURL).get();
		
		String poem = doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	
}
