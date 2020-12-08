package module2;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import javafx.application.*;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/* Name: Jack Oporto
 * Class: CEN 3024C
 * Professor Dhrgam Al Kafaf
 * Assignment: Word Occurrences
 * 
 * Summary:
 * This was a project completed for Software Dev 1 during Fall 2020
 * 
 * This program utilizes jsoup to extract a poem from a URL and relays the 20 most used words
 * along with their frequency
 * 
 * User can submit their own URL for analysis OR choose the default "The Raven"
 * User input works best with text from https://www.gutenberg.org/
 * 
 * Results are output into a dynamic table to view
 * 
 * Database functionality is commented out.
 */
/** Main Class
 * @author Jack Oporto
 * @author oportojack@gmail.com
*/

public class Main extends Application{
	
	TableView<Data> table;
	
		@SuppressWarnings("unchecked")
		public void start(Stage primaryStage) throws Exception{
			
			//Connection myConnection = getConnection();
			
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
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
				} catch (SQLException e1) {
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
		
		
		/** This method connects to the database
		 * @param driver is the Driver that is used to work
		 * @param url is the location of the database
		 * @param username is the username you'd like to connect as
		 * @param password is the password for your selected username
		 * @return Returns the Connection object
		*/
//		public static Connection getConnection() throws Exception{
//			try {
//				String driver = "com.mysql.cj.jdbc.Driver";
//				String url = "jdbc:mysql://localhost:3306/wordOccurrences";
//				String username = "root";
//				String password = "";
//				Class.forName(driver);
//				
//				Connection conn = DriverManager.getConnection(url, username, password);
//				System.out.println("Connection successful");
//				return conn;
//			}catch(Exception e) {System.out.println(e);}
//			
//			return null;
//		}
		
		
	
		/** The main data gathering/sorting method
		 * @param myWords is the object of words from the url passed in
		 * @param myURL is the url passed in either from the user or the Raven default
		 * @return Returns the sorted map of words after it's been processed by the other methods
		*/
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
		
		//System.out.println(sortedMap);
		 return sortedMap;
	}
		
	
	/** Scrapes the given url for poem text
	 * @param myURL is the url passed in from the user or the Raven default
	 * @return Returns the raw poem
	*/
	//GETS DATA FROM WEBSITE
	public static String scrapeData(String myURL) throws IOException, InterruptedException {
		Document doc = Jsoup.connect(myURL).get();
		
		//I get elements in this order because the website separates them.
		//Spaces added where necessary
		String poem = doc.body().getElementsByTag("h1").text() + " "
		+ doc.body().getElementsByTag("h4").text() + " "
		+ doc.body().getElementsByTag("h3").text()  + " " 
		+ doc.body().getElementsByTag("p").text();
		return poem;
		
	}
	
	/** Counts the amount of times each word appears in the given array
	 * @param strings is the array of words passed in to count
	 * @return Returns the map of data containing the frequency of each word
	*/
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
	
	/** Sorts the list of words/frequencies from most to least frequent
	 * @param hm is the HashMap passed in with the key and values of each word.
	 * @return Returns the map of data containing the sorted hashmap
	*/
	//WORD SORTER
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm){ 
		//Count variable exists so database is only written into 20 times
		int count = 0;
		
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
//            if(count <20) {
//	            try {
//	            	PreparedStatement posted = thisConnection.prepareStatement(
//	            			//Kept getting syntax errors for this, it should check to see if the word exists in the table
//	            			//and not insert a new entry if it exists.
//	            			"INSERT INTO word (word, frequency)" + 
//	            			"SELECT * FROM (SELECT '"+aa.getKey()+"', '"+aa.getValue()+"') AS tmp" + 
//	            			"WHERE NOT EXISTS (" + 
//	            			"    SELECT name FROM word WHERE word = '"+aa.getKey()+"'" + 
//	            			") LIMIT 1");
//	            			"INSERT INTO word (word, frequency) VALUES('"+aa.getKey()+"', '"+aa.getValue()+"')");
//	            	posted.executeUpdate();
//	            }catch(Exception e) {System.out.println(e); 
//            }
//            }count++;//Count variable exists so database is only written into 20 times
        }
        return temp; 
    } 
	
	
	
	/** Creates an ObservableList object to pass our data into so the table can read it
	 * @param sortedMap is the final sorted map containing the key and values of the sorted words.
	 * @return Returns the data as tabledata for the table to use
	 * @throws SQLException 
	*/
	//GETS ALL DATA FOR THE TABLE
	public ObservableList<Data> getTableData(Map<String, Integer> sortedMap) throws SQLException{
		int count = 0;
		ObservableList<Data> tableData = FXCollections.observableArrayList();
		
		//PreparedStatement statement = thisConnection.prepareStatement("SELECT * FROM word");
		
//		ResultSet result = statement.executeQuery();
//			while(result.next()) {
//			tableData.add(new Data(result.getInt("word_id"), result.getString("word"), result.getInt("frequency")));
//			}
		
		
		
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
		
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
}
