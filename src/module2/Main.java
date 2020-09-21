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
 * Assignment: Module 2, SDLC
 * Date completed: September 19, 2020
 * 
 * Summary:
 * This program utilizes jsoup to extract a poem from a URL and relay the 20 most used words
 * along with their frequency
 */


public class Main {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		int count = 0;
		String myURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String poem = getData(myURL);
		String webData[] = poem.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
		HashMap<String , Integer> poemHash = new HashMap<String , Integer>();
		
		poemHash = wordCount(webData);
		
		Map<String, Integer> sortedMap = sortByValue(poemHash);
		 
		 
		 //This iterates through the sorted map and prints out the key and values
		 for (Map.Entry<String, Integer> en : sortedMap.entrySet()) { 
	            System.out.println("[" + en.getKey() +  
	                          " , " + en.getValue() + "]"); 
	            count++;
	            if(count == 20) {
	            	break;  //Probably poor form but I didn't figure another way to iterate through the map
	            			//without going through the entire map.
	            }
	        } 			
	}
	
	public static String getData(String myURL) throws IOException, InterruptedException {
		System.out.println("Connecting...");
		Document doc = Jsoup.connect(myURL).get();
		System.out.println("Connected!");
		Thread.sleep(750);
		System.out.println("This program will output the top 20 words and how many times they appear in \n" + doc.title());
		Thread.sleep(5000);
		
		//I get elements in this order because the website separates them.
		//Spaces added where necessary
		String poem = doc.body().getElementsByTag("h1").text() + " "
		+ doc.body().getElementsByTag("h4").text() + " "
		+ doc.body().getElementsByTag("h3").text()  + " " 
		+ doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	
	
	public static HashMap<String, Integer> wordCount(String[] strings) {
		  HashMap<String, Integer> map = new HashMap<String, Integer> ();
		  //Iterates through every string in the sanitized poem string
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
	
	
	
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        // Create a linkedlist from elements of HashMap (hm)
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sorts the list in descending order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o2,  
                               Map.Entry<String, Integer> o1) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	
}
