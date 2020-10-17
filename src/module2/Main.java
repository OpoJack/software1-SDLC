package module2;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


/* Name: Jack Oporto
 * Class: CEN 3024C
 * Professor Dhrgam Al Kafaf
 * Assignment: Module 2, SDLC
 * Date completed: September 19, 2020
 * 
 * Summary:
 * This program utilizes jsoup to extract a poem from a URL and relay the 20 most used words
 * along with their frequency
 * 
 * Functionality extended Oct 16, 2020
 * JavaFX UI implemented
 * User can submit their own URL for analysis OR choose the default "The Raven"
 * Results are output into a dynamic table to view
 */


public class Main extends Application{
	
	TableView<Data> table;
	
		@SuppressWarnings("unchecked")
		public void start(Stage primaryStage) throws Exception{
			
			//Scene and VBox to put everything inside of
			VBox root = new VBox();
			VBox root2 = new VBox();
			VBox root3 = new VBox();
			
			//Scene declaration
			Scene scene1 = new Scene(root, 360, 200);
			Scene scene2 = new Scene(root2, 360, 400);
			
			//index column
			TableColumn<Data, Integer> indexColumn = new TableColumn<>("Index");
			indexColumn.setMinWidth(50);
			indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
			
			//word column
			TableColumn<Data, String> wordColumn = new TableColumn<>("Word");
			wordColumn.setMinWidth(175);
			wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
			
			//frequency column
			TableColumn<Data, Integer> freqColumn = new TableColumn<>("Frequency");
			freqColumn.setMinWidth(100);
			freqColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
			
			Data myWords = new Data();
			table = new TableView<>();
			
			
			//Button and input here
			//Button theRaven = new Button("The Raven");
	        //theRaven.setOnAction(e -> primaryStage.setScene(scene2));
		
	

			Label labelFirst = new Label("Enter a url to get word frequencies from");
			Label label = new Label();
			        
			//User submission and default functionality button
			Button submitURL= new Button("Submit your url");
			Button theRaven = new Button("Default: The Raven");
			TextField text= new TextField();
			
			//User submission selection
			submitURL.setOnAction(e -> 
			{   
				//Most of the data is retrieved here
				try {
					table.setItems(getTableData(getAllData(myWords, text.getText())));
					primaryStage.setScene(scene2);
					
				} catch (InterruptedException | IOException e1) {
					label.setText("Invalid URL");
					e1.printStackTrace();
				}
				table.getColumns().addAll(indexColumn, wordColumn, freqColumn);
			        }
			);
			
			//Default "The Raven" selection
			theRaven.setOnAction(e -> 
			{   
				//Most of the data is retrieved here
				try {
					table.setItems(getTableData(getAllData(myWords, null)));
					primaryStage.setScene(scene2);
					
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				table.getColumns().addAll(indexColumn, wordColumn, freqColumn);
			        }
			);
			
			
			//theRaven seperated for spacing. root and root3 appear on the same page
			root3.getChildren().addAll(theRaven);
			
			root.getChildren().addAll(labelFirst, text, submitURL, label, root3);
			root2.getChildren().addAll(table);
			
			primaryStage.setTitle("Word counter - Jack Oporto 2020");
			primaryStage.setScene(scene1);
			primaryStage.show();
		}
	
	
	public static Map<String, Integer> getAllData(Data myWords, String myURL) throws InterruptedException, IOException {
		
		//If "Default: The Raven" is selected, myURL is null and set to The Raven here
		if(myURL == null) {
			myURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		}
		
		String poem = scrapeData(myURL);
		String webData[] = poem.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
		HashMap<String , Integer> poemHash = new HashMap<String , Integer>();
		
		//poemHash gets words and frequencies 
		poemHash = wordCount(webData);
		
		//sortedMap gets all the sorted values of poemHash
		Map<String, Integer> sortedMap = sortByValue(poemHash);
		
		 
		 return sortedMap;
	}
		
	//GETS DATA FROM WEBSITE
	public static String scrapeData(String myURL) throws IOException, InterruptedException {
		Document doc = Jsoup.connect(myURL).get();
		//if(doc.)
		
		//I get elements in this order because the website separates them.
		//Spaces added where necessary
		String poem = doc.body().getElementsByTag("h1").text() + " "
		+ doc.body().getElementsByTag("h4").text() + " "
		+ doc.body().getElementsByTag("h3").text()  + " " 
		+ doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	//WORD FREQUENCY COUNTER
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
	
	
	//WORD SORTER
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
	
	//GETS ALL DATA FOR THE TABLE
	public ObservableList<Data> getTableData(Map<String, Integer> sortedMap){
		int count = 0;
		ObservableList<Data> tableData = FXCollections.observableArrayList();
		//This iterates through the sorted map and prints out the key and values
		 for (Map.Entry<String, Integer> en : sortedMap.entrySet()) { 
			 tableData.add(new Data(count, en.getKey(), en.getValue()));
			 
	            count++;
	            if(count == 20) {
	            	break;  //Probably poor form but I didn't figure another way to iterate through the map
	            			//without going through the entire map.
	            }
	        }

		return tableData;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
