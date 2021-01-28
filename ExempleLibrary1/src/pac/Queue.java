/**
 * 
 */
package pac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * @author maiconalmeida
 *
 */
public class Queue {
     
	/*class makes functions in queue.
	 * this array stores all queue element/nodes
	 *  like reader is waiting for a book,its make by class node [id, idReader and idBook]
	 */
	private Node[] nodes;
	private int head;
	private int tail;
	private int size;
	private int count;
	
	Operations operations;
	
	/*
	 * constructor display file & build an node array
	 * with size of readlines has into a file
	 */
	public Queue(File fileName) {
		operations = new Operations();
		int allLines = 0;
		allLines=operations.readLines(fileName);
		allLines++;
		
		/*
		 * a queue must have one more gape; like a file have 3 lines
		 * readLines method shall return 4 lines
		 */
		nodes =  new Node[allLines];

		for(int i = 0; i < allLines; i++) {
			nodes[i] = new Node();
		}
		size = allLines;
		head = 0;
		tail = 0;
		count = 0;
		System.out.println("Play queue size " +size);
	}
	/*
	 * const. for play node an array size.
	 */
	public Queue(int size_) {
		nodes = new Node[size_];
		for(int i = 0; i <size_; i++) {
			nodes[i] = new Node();
		}
		size = size_;
		head = 0;
		tail = 0;
		count = 0;
	}
	/*
	 * display all line in a file & create a queue.
	 */
	public void loadQueue(File fileQueue) {
		
		try {
			//stream data from a file.
			FileInputStream FIS = new FileInputStream(fileQueue);
			/*
			 * read word with special character 
			 */
			InputStreamReader ISR = new InputStreamReader(FIS, "UTF-8");
			//BR memory with each line in the file
			BufferedReader br = new BufferedReader(ISR);
			
			String line;
			line = br.readLine();//read head data & jump it
			
			int i = 0;
			Node node;
			while(true) {
				line = br.readLine();
				if(line == null)//tested the end of the file
					break;     //if ends file, shall stopped while
				
				String[] data; //variable to keep data line
				data = line.split(";"); // split lines by ;
				node = new Node();
				if(data.length == 3) {
					//validate line contents
					node.setId(Integer.parseInt(data[0])); //take 1 data from line & set id value from node
					node.setIdReader(Integer.parseInt(data[1].trim()));//take 2 data from line & set idReader value from node
					node.setIdBook(Integer.parseInt(data[2].trim())); //take 3 data from line & set idBook value from node.
					this.insert(node);
					i++;
				}
			}
			br.close(); //br close
		}catch(Exception e) { //display errors if has any
			System.out.println("Failed in queue.");
			e.printStackTrace(); //display file error 
			
		}
	}
	/*
	 * method enter into a queue.
	 */
	public void insert(Node item) {
		
		if(isFull()) {           //example from the internet this event can be
			//System.exit(1);   //a queue never is full because i insert an element and saved in the file
			return;            // re load it with a plus more unit.
		}
		tail = (tail+1) % size; // 1
		nodes[tail] = item;
		count++;              //recorded file
	}
	
	/*
	 * display how many elements has the queue.
	 */
	public int getCount() {
		return count;
	}
	
	public Boolean isEmpty() {
		return (getCount() == 0);
	}
	public Boolean isFull() {
		return (getCount() == size);
	}
	public int getSize() {
		return size;
	}
	/*
	 * method override toString to display data of a queue; like all nodes elements
	 */
	@Override
	public String toString() {
		String result = "";
		result += "Head" +head+" \n";
		result += "Tail" +tail+" \n";
		result += "Count" +count+" \n";
		for(int i = 0; i < size; i++) {
			result += "Position Queue" +i+"\n";
			result += "Id"+nodes[i].getId()+"\n";
			result += "Book"+nodes[i].getIdBook()+"\n";
			result += "Reader"+nodes[i].getIdReader()+"\n";
			result += ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
					
		}
		return result;
	}
	/*
	 * file is written again, however one line is jumped.
	 * the jumped line is the line with returned book & idReader it happen once only.
	 */
	
	public void removeQueue(String filename, Queue queue, int idBook, int idReader) {
		
		int idReader_ = 0;
		int idBook_ = 0;
		int id;
	//try to write a file
		try {
			File file = new File(filename);
			FileWriter fw = new FileWriter(file);
			fw.write("id; idReader; idBook");
			int first = 0;
			for(int i = 0; i < queue.nodes.length; i++) {
				id = queue.nodes[i].getId();
				idReader_ = queue.nodes[i].getIdReader();
				idBook_ = queue.nodes[i].getIdBook();
				
				/*
				 * 3 conditons to idenfify here
				 * book & reader only one time 
				 */
				if((idBook_ == idBook) &&(first == 0) &&(idReader_ == idReader)){
					first = 1;
					
				}else {
					fw.write("\n" +id+ ";" +idReader_+ ";" +idBook_);
				}
			}
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * method for identify who is waiting the book.
	 * could be 1 or more
	 */
	
	public int[] checkReaderWait(Queue queue, int idBook) {
		int[] wait;
		int sizeQueue = 0;
		//check how many readers are waiting same book
		for(Node node: queue.nodes) {
			if(node.getIdBook() == idBook) {
				sizeQueue++;// size of queue voters waiting for that book
			}
		}
		//generate an array with idReaders waiting same book
		wait = new int[sizeQueue];
		int i = 0;
		for(Node node: queue.nodes) {
			if(node.getIdBook() == idBook) {
				wait[i++] = node.getIdReader();
			}
		}
		return wait;
	}
	//recorded 1 more line in a file
	public void save(String filename, Node node) {
		int id, idReader, idBook;
		
		try {
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, true);
			id = node.getId();
			idReader = node.getIdReader();
			idBook = node.getIdBook();
			fw.write("\n" +id+";" +idReader+ ";" +idBook);
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
