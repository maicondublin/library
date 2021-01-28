/**
 * 
 */
package pac;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author maiconalmeida
 *
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File fileBooks = new File("books.txt");
		File fileReaders = new File("reader.txt");
		File fileRent = new File("rent.txt");
		File fileQueue = new File("queue.txt");
		
		/*
		 * Load data from the files
		 */
		Book book = new Book();
		Book[] books;             //array to store all books.
		books = book.loadBooks(fileBooks); 
		
		Reader reader;
		reader = new Reader();
		Reader[] readers;      //array to store readers
		readers = reader.loadReaders(fileReaders); 
		
		Rent rent;
		rent = new Rent();
		Rent[] rents;       //array to store rents
		rents = rent.loadRents(fileRent); 
	
		Queue  queue;
		queue = new Queue(fileQueue);
		queue.loadQueue(fileQueue); //Queue class has an array into itself.
			
		/*
		 * shows user inputs 
		 */
		Scanner input;
		input = new Scanner(System.in);
		int option = 0;
		for (;;) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>Welcome to The Library.>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>1 - List of all Books.");
		System.out.println(">>>2 - List all Books by Author name.");
		System.out.println(">>>3 - List all Books by the Title name.");
		System.out.println(">>>4 - Search Book by Title name.");
		System.out.println(">>>5 - Search Reader by Id number.");
		System.out.println(">>>6 - List Readers in alphabetical order.");
		System.out.println(">>>7 - List Readers by Id number.");
		System.out.println(">>>8 - Rent a Book.");
		System.out.println(">>>9 - Register a returned Book.");
		System.out.println(">>>10 - List all Rents.");
		System.out.println(">>>11 - List Queue.");
		System.out.println(">>>12 - List Books borrowed by Reader.");
		System.out.println(">>>0 - Exit");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		
		option = input.nextInt();
		switch(option) {
		case 1:
			book.list(books);
			break;
		case 2:
			book.listAuthor(books);
			break;
		case 3:
			book.listTitle(books);
			break;
		case 4:
			book.searchInput(books);
			break;
		case 5:
			reader.searchInput(readers);
			break;
		case 6:
			reader.ListAlpha(readers);
			break;
		case 7:
			reader.listId(readers);
			break;
		case 8:
			//the rent could be rent or insert to the queue
			rent.rentBook(rents, books,readers, queue);
			//re load a queue
			queue.loadQueue(fileQueue);
			//re load a file with the rents
			rents = rent.loadRents(fileRent);
		case 9:
			//shows status & queue if has one
			rent.returnedBook(rents, books, readers, queue);
			// re load rent for other reader
			rents = rent.loadRents(fileRent);
			//removed from the queue if needs
			queue.loadQueue(fileQueue);
			break;
		case 10:
			rent.listAllRents(rents);
			break;
		case 11:
			System.out.println(queue.toString());
			break;
		case 12:
			rent.listRentsReader(rents);
			break;
		case 0:
			System.out.println("Finished!!!");
			break;
			default:
				System.out.println("Please,choose a different option.");
		}
		}
	}

}
