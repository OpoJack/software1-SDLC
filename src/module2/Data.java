package module2;

/** Represents data received from a website
 * @author Jack Oporto
 * @author oportojack@gmail.com
 * @version 1.0
*/

public class Data {
	private int index;
	private String word;
	private int frequency;
	
	public Data() {
		this.index = 0;
		this.word = "";
		this.frequency = 0;
	}
	
	/** Creates an employee with the specified name.
	 * @param index Value to keep track of which index it is at
	 * @param word The word in the index
	 * @param frequency The amount of time a word appears
	*/
	public Data(int index, String word, int frequency) {
		super();
		this.index = index;
		this.word = word;
		this.frequency = frequency;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	
}
