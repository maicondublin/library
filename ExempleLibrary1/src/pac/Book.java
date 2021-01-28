package pac;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Book {
	
	private int id;
	private String title;
	private String author;
	
	/*
	 * constructor 
	 */
	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public Book() {
		super();
	}
	
	/*
	 * getter & setters 
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	/*
	 *  load books
	 */
	public Book[] loadBooks(File file) {
		
		Operations op;
		op = new Operations();
		Book[] books;
		books = new Book[op.readLines(file)];
		Book book;
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader bf = new BufferedReader(isr);
			String line;
			line = bf.readLine();
			
			int i = 0;//i short for index
			while(true) {
				line = bf.readLine();
				if(line == null)
					break;
				System.out.println("line" + line);
				String[] data;
				data = line.split(";");
				book = new Book();
				book.setId(Integer.parseInt(data[0]));
				book.setTitle(data[1]);
				book.setAuthor(data[2]);
				books[i] = book;
				i++;
			}
			bf.close();
					
		}catch(Exception e) {
			System.out.println("Failed books");
			e.printStackTrace();
		}
		return books;
	}
	/*
	 * display books on the screen [console]
	 */
	public void list(Book[] books) {
		for(Book book: books) {
			System.out.println("id....:" +book.getId());
			System.out.println("Title....." +book.getTitle());
			System.out.println("Author....." +book.getAuthor());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
	}
	
	//show books by author order asc
	public void listAuthor(Book[] books) {
		Book bookLend;
		String author1,author2;
		for(int i = 0; i < books.length; i++) {
			for(int j = 0; j <books.length-1; j++) {
				author1=books[j].getAuthor().trim(); //remove spaces
				author2=books[j+1].getAuthor().trim();
				if(author1.charAt(0) > author2.charAt(0)){
					bookLend = books[j];
					books[j] = books[j+1];
					books[j+1] = bookLend;
				}
			}
		}
		
		this.list(books);
	}
	//shows books by the title order asc
	public void listTitle(Book[] books) {
		Book bookLend;
		String title1,title2;
		for(int i = 0; i < books.length; i++) {
			for(int j = 0; j <books.length-1; j++) {
				title1=books[j].getTitle().trim(); //remove spaces
				title2=books[j+1].getTitle().trim();
				if(title1.charAt(0) > title2.charAt(0)){
					bookLend = books[j];
					books[j] = books[j+1];
					books[j+1] = bookLend;
				}
			}
		}
		
		this.list(books);
}
	/*
	 * capture the user input search and trigger the search
	 */
	public void searchInput(Book[] books) {
		Scanner input = new Scanner(System.in);
		System.out.println("Please, enter with Author or Title name?");
		String param; //
		param = input.nextLine();
		this.search(books, param);
	}
	
	/*
	 * search for what the user searched for,
	 *  is done by the title or author of the book
	 */
	public void search(Book[] books, String param) {
		for(Book b: books) {
			//each title must be compared with param  when its typed 
			//both shall be converted to low characters 
			if(b.getTitle().toLowerCase().contains(param.toLowerCase())) {
				System.out.println("Id...:" +b.getId());
				System.out.println("Title....:" +b.getTitle());
				System.out.println("Author....:" +b.getAuthor());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			if(b.getAuthor().toLowerCase().contains(param.toLowerCase())){
				System.out.println("Id...:" +b.getId());
				System.out.println("Title....:" +b.getTitle());
				System.out.println("Author....:" +b.getAuthor());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				
			}
		}
	}
	/*
	 * search for the Id using the binary resource
	 */
	public Book searchIdBinary(Book[] books, int idBook) {
		//track the index & shall define limits
		//between them shall look foe the value
		Book book = new Book();
		int low = 0;
		int high = books.length -1;
		
		//search shall continue till a low index remains
		//low than a high index search
		while(low <= high){ // searching for a middle index
			int midd = (high + low) / 2;
			System.out.println("Test: " + books[midd]);
			
			//access a value in a middle index
			if(books[midd].getId() == idBook) {
				id = books[midd].getId();
				title = books[midd].getTitle();
				author = books[midd].getAuthor();
				System.out.println("Id....: " +books[midd].getId());
				System.out.println("Id....: " +books[midd].getTitle());
				System.out.println("Id....: " +books[midd].getAuthor());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				book.setId(id);
				book.setTitle(title);
				book.setAuthor(author);
				return book;
				
			}else if (books[midd].getId() > idBook) {
				high = midd -1;
			}
			else {
				low = midd -1;
			}
		}
		return book;
	}
	/*
	 *   search for "normal" id
	 */
	public Book searchId(Book[] books, int idBook) {
		
		Book book = new Book();
		
		for(Book b: books) {
			if(b.getId() == idBook) {
				id = b.getId();
				title = b.getTitle();
				author = b.getAuthor();
				System.out.println("Id....: " +b.getId());
				System.out.println("Id....: " +b.getTitle());
				System.out.println("Id....: " +b.getAuthor());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				book.setId(id);
				book.setTitle(title);
				book.setAuthor(author);
				break;
			}
		}
		return book;
		
	}
}
