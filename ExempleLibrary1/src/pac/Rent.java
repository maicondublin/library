/**
 * 
 */
package pac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * @author maiconalmeida
 *
 */
public class Rent {
	
	
	private int id;
	private int idReader;
	private int idBook;
	private int status; //this define if the book were borrowed or not 
	Operations operations;
	
	/*
	 * const param
	 */
	public Rent() {
		
	}
	/*
	 * const
	 */
	public Rent(int id, int idReader, int idBook, int status) {
	
		this.id = id;
		this.idReader = idReader;
		this.idBook = idBook;
		this.status = status;
	}
	/*
	 * getters & setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdReader() {
		return idReader;
	}

	public void setIdReader(int idReader) {
		this.idReader = idReader;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	/*
	 * load the rents
	 */
	public Rent[] loadRents(File file) {
		int total=0;
		Rent[] rents;
		operations = new Operations();
		rents = new Rent[operations.readLines(file)];
		Rent rent;
		try {
			FileReader isr = new FileReader(file);
			BufferedReader bf = new BufferedReader(isr);
			String line;
			line=bf.readLine();

			while(true) {
				line=bf.readLine();
				if(line==null)
					break;
				String[] data=line.split(";");
				rent=new Rent();
				rent.setId(Integer.parseInt(data[0]));
				rent.setIdReader(Integer.parseInt(data[1].trim()));
				rent.setIdBook(Integer.parseInt(data[2].trim()));
				rent.setStatus(Integer.parseInt(data[3].trim()));
				rents[total]=rent;
				total++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rents;
	}
	//method to rent a book by reader.
	//method receives array books, readers & queue.
	public void rentBook(Rent[] rents, Book[] books, Reader[] readers, Queue queue) {
		
	
		int idBook, idReader;
		Scanner input = new Scanner(System.in);
		System.out.println("Please, enter with the id choosen --book--");
        idBook = input.nextInt();
        System.out.println("Please, enter with the id of the Reader.");
        idReader = input.nextInt();
        
        Book book = new Book();
        //check if book exists
        book = book.searchIdBinary(books, idBook);
        
        if(book.getId()==0) {
        	System.out.println("Book cannot be found.");
        	return;
        }
        
        Reader reader = new Reader();
        reader = reader.searchId(readers,idReader);
        
        //check if reader exists
        if(reader.getId()==0){
        	System.out.println("Reader cannot be found.");
        	return;
        }
        
        Rent rentCheck;
        //check if book is available
        rentCheck = searchRentIdBook(rents, idBook);
        
        //test if book is available
        if(rentCheck.getIdReader()!=0){
        	System.out.println("Book borrowed for the Reader of id "+rentCheck.getIdReader());
        	System.out.println("Would you like to stay in the waiting Queue?(y/n)");
            //in case if is not available, read the waiting queue. 
            //add to the waiting list.
            input = new Scanner(System.in);
            String okay;
            okay = input.nextLine();
            if(okay.equals("y")){
                Node node = new Node();
                node.setId(queue.getSize());//id is the size, because created a queue with size plus one
                node.setIdReader(idReader);//typed by user
                node.setIdBook(idBook);//typed by user
                queue.insert(node);
                System.out.println(queue.toString());
                queue.save("queue.txt",node);
            }
         	
        }else {
        	System.out.println("Okay for borrow. (y/n)");
        	input = new Scanner(System.in);
        	String okayborrow;
        	okayborrow = input.nextLine();
        	if(okayborrow.equals("y")) {
        		System.out.println("borrow okay.");
                Rent rent = new Rent();
                rent.setId(rents.length+1);
                rent.setIdReader(idReader);
                rent.setIdBook(idBook);
                rent.setStatus(1);
                insertRent("rent.txt", rent);//all content is in the file.
        	}
        }
		
	}
	     //method receives array rent & idBook
		//search in array the rent with status = 1 
	    //check if some rent has idBook
		public Rent searchRentIdBook(Rent[] rents,int idBookSearch){
			
			Rent rent = new Rent();
	        for (Rent tempRent : rents) {  //tempRent = temporary rent  
	            if(tempRent.getStatus()==1){//only the borrows would be considered 
	                if(tempRent.getIdBook()==idBookSearch){ //idLivro in order to verify if the book has been borrowed
	                    id = tempRent.getId();
	                    idReader = tempRent.getIdReader();
	                    idBook = tempRent.getIdBook();
	                    status = tempRent.getStatus();
	                    rent.setId(id);
	                    rent.setIdReader(idReader);
	                    rent.setIdBook(idBook);
	                    rent.setStatus(status);
	                    break;
	                }
	            }    
	        }
	        return rent;
	    } 
		//write one more line on file rents.txt
		public void insertRent(String filename,Rent rent){
	        try {
	            File file = new File(filename);
	            FileWriter fw = new FileWriter(file,true);
	            id=rent.getId();
	            idReader=rent.getIdReader();
	            idBook=rent.getIdBook();
	            status=rent.getStatus();    
	            fw.write("\n"+id+";"+idReader+";"+idBook+";"+status);
	            fw.close();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		//list all rents and return size of rents
		public int listAllRents(Rent[] rents){
	        int total=0;
	        System.out.println("List of borrows.");
	        for (Rent rent : rents) {
	            total++;
	            //if(borrow.getStatus()==1){
	                System.out.println("Id......:"+rent.getId());
	                System.out.println("Reader..:"+rent.getIdReader());
	                System.out.println("Book...:"+rent.getIdBook());
	                System.out.println("Status..:"+rent.getStatus());
	                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	            //} 
	        }
	        return total;
	    }
		//show all rents by specific user
		public void listRentsReader(Rent[] rents){
			
	        int total=0;
	        System.out.println("Please, enter with the id of the Reader.");
	        Scanner input = new Scanner(System.in);
	        int id;
	        id=input.nextInt();
	        
	        for (Rent rent : rents) {
	            total++;
	            if(rent.getIdReader()==id){
	                System.out.println("Id......:"+rent.getId());
	                System.out.println("Reader..:"+rent.getIdReader());
	                System.out.println("Book...:"+rent.getIdBook());
	                System.out.println("Status..:"+rent.getStatus());
	                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	            }
	        }
	    }
		//method receive array & id.
		//Search & return a rent with idBook
		public Rent searchBookBorrow(Rent[] rents, int idBook){
	        Rent rent=new Rent();
	        for (Rent e : rents) {
	            if(e.getStatus()==1){
	                if(e.getIdBook()==idBook){
	                	rent.setId(e.getId());
	                	rent.setIdReader(e.getIdReader());
	                	rent.setIdBook(e.getIdBook());

	                }
	            }
	 
	        }
	        
	        return rent;
	        
	    }
		//change status from 1 to 0.
		//0 means book has returned.
		//1 means book has borrowed.
	    public void updateBorrow(String filename,Rent[] rents, Rent rent){
	        try {
	            File file = new File(filename);
	            FileWriter fw = new FileWriter(file);
	            fw.write("id;idLeitor;idLivro;status");
	            for (int i = 0; i < rents.length; i++) {
	                id = rents[i].getId();
	                idReader = rents[i].getIdReader();
	                idBook = rents[i].getIdBook();
	                //if id of array != id rent, the status do not change
	                if(rents[i].getId()!= rent.getId()){
	                    status = rents[i].getStatus();
	                }else{
	                	//is the rent I need change status
	                    status=0;
	                }
	                fw.write("\n"+id+";"+idReader+";"+idBook+";"+status);
	            }
	            fw.close();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    //Method register returned book
	    public void returnedBook(Rent[] rents, Book[] books, Reader[] readers, Queue queue){
	    	
	    
	        int idReader,idBook;
	        String option;
	        Scanner keyboard = new Scanner(System.in);
	        System.out.println("Please, enter with the book id choosen.");
	        idBook = keyboard.nextInt();
	        
	        //check if the book its really borrowed.
	        Rent rent = new Rent();
	        rent = searchBookBorrow(rents,idBook);
	        
	        if(rent.getIdBook()==0){
	            System.out.println("Book is not borrow."); 
	            return;//method to exit from returnBook
	        }
	        keyboard = new Scanner(System.in);
	        System.out.println("Is it okay for book to be returned? (y/n)");

	        String okay;
	        okay = keyboard.nextLine();
	        if(okay.equals("y")){
	            //set status 0 on borrows
	            updateBorrow("rent.txt", rents, rent);
	            System.out.println("Book has been returned.");
	        }
	        //check if it in the queue & notify
	        Node node = new Node();
	        Reader r=new Reader();
	        //it is possible to have more than one reader in the waiting queue.
	        int[] have = queue.checkReaderWait(queue,idBook);
	        if(have.length > 0){
	            System.out.println("Has "+have.length+" in queue");
	            for (int i = 0; i < have.length; i++) {
	                r = r.searchId(readers, have[i]);
	                System.out.println("The reader "+ r.getName()+" is in the queue");
	            }
	            
	            /*remove from the waiting queue if needs. 
	            r = r.searchId(readers, have[0]);
	            System.out.println("the reader "+ r.getName());
	            
	            // remover from the queue but that is one option.
	            queue.removeQueue("queue.txt",queue,idBook, have[0]);
	            */
	            
	        }
	        
	    }
	    
	}
