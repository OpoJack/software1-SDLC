package module2;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import org.jsoup.parser.*;

public class Test { 
    public static void main(String[] args) throws IOException 
    {        
    	String myURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String poem = getData(myURL);
		String webData[] = poem.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
		
        //String text = "the quick brown fox jumps fox fox over the lazy dog brown"; 
        //String[] poemLIST = text.split(" "); 
        String[] uniqueKeys; 
        int count = 0; 
        System.out.println(poem); 
        uniqueKeys = getUniqueKeys(webData); 
        for(String key: uniqueKeys) 
        { 
            if(null == key) 
            { 
                break; 
            }            
            for(String s : webData) 
            { 
                if(key.equals(s)) 
                { 
                    count++; 
                }                
            } 
            System.out.println("Count of ["+key+"] is : "+count); 
            count=0; 
        } 
    } 
    private static String[] getUniqueKeys(String[] keys) 
    { 
        String[] uniqueKeys = new String[keys.length]; 
        uniqueKeys[0] = keys[0]; 
        int uniqueKeyIndex = 1; 
        boolean keyAlreadyExists = false; 
        for(int i=1; i<keys.length ; i++) 
        { 
            for(int j=0; j<=uniqueKeyIndex; j++) 
            { 
                if(keys[i].equals(uniqueKeys[j])) 
                { 
                    keyAlreadyExists = true; 
                } 
            }            
            if(!keyAlreadyExists) 
            { 
                uniqueKeys[uniqueKeyIndex] = keys[i]; 
                uniqueKeyIndex++;                
            } 
            keyAlreadyExists = false; 
        }        
        return uniqueKeys; 
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
} 