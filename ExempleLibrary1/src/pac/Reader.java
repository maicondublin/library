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
public class Reader {
	
	
	private int id;
	private String name;
	private String address;
	private String phone;
	Operations operations;
	
	//constructor 
	public Reader(int id, String name, String address, String phone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public Reader() {
		
	}
	
    // getters & stters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/*
	 * load readers
	 */
	public Reader[] loadReaders(File fileReaders) {
		
		operations = new Operations();
		Reader readers[];
		readers = new Reader[operations.readLines(fileReaders)];
		Reader reader;
		try{
			FileInputStream fis = new FileInputStream(fileReaders);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			line = br.readLine();
			int i = 0; //i = index
			while(true) {
				line = br.readLine();
				if(line == null)
					break;
				System.out.println("line " + line);
				String[] data;
				data = line.split(";");
				reader = new Reader();
				reader.setId(Integer.parseInt(data[0]));
				reader.setName(data[1]);
				reader.setAddress(data[2]);
				reader.setPhone(data[3]);
				readers[i] = reader;// i = index
				i++; // i = index
			}
			br.close();
			
		}catch(Exception e) { //display errors if has any
			System.out.println("Failed in Readers.");
			e.printStackTrace();//display file error 
		}
		return readers;
	}
	
	/*
	* @param  Array with readers
	* @param  id of reader to be returned
	* @return return reader found 
	* search reader by id 
	*/
	public Reader searchId(Reader[] readers, int idReader) {
		
		Reader reader = new Reader();
		for(Reader r: readers) {
			if(r.getId() == idReader) {
				id = r.getId();
				name = r.getName();
				address = r.getAddress();
				phone = r.getPhone();
				System.out.println("Id........: "+r.getId());
	            System.out.println("Name.....: "+r.getName());
	            System.out.println("Address.....: "+r.getAddress());
	            System.out.println("Phone.....: "+r.getPhone());
	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	            reader.setId(id);
				reader.setName(name);
				reader.setAddress(address);
				reader.setPhone(phone);
				break;
			}
		}
		return reader;
	}
	//ask user for idReader
	//play search method
	public void searchInput(Reader[] readers) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please, enter with the id of the reader.");
		int param;
		param = input.nextInt();
		this.search(readers, param);//parameter
	}
	
	//display to the screen reader with id
	public void search(Reader[] readers, int param) {
		
		for(Reader b: readers) {
			if(b.getId() == param){
				System.out.println("Id.........: "+b.getId());
	            System.out.println("Name.......: "+b.getName());
	            System.out.println("Address....: "+b.getAddress());
	            System.out.println("Phone......: "+b.getPhone());
	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			}	  
		}
	
	}
	//bubble short method used for alphabetical name order
	///display list method
	public void ListAlpha(Reader[] readers) {
		
          Reader readerTemp; //temp = temporary 
		
		  String s1,s2; //string1 & string2
		
		    for (int i = 0; i < readers.length; i++) {
	           for (int j = 0; j < readers.length-1-i; j++) {
	            s1=readers[j].getName().trim().toLowerCase();
	            s2=readers[j+1].getName().trim().toLowerCase();
	            if(s1.compareTo(s2)>0){
	                readerTemp=readers[j];
	                readers[j]=readers[j+1];
	                readers[j+1]=readerTemp;
	            }
	        }
	    }
		
		this.list(readers);
	}
	//bubble sort method used for id numeric asc
	//display and list method
	public void listId(Reader[] readers) {
		
	
		Reader readerTemp; // temp = temporary
		
		for (int i = 0; i < readers.length; i++) {
	        for (int j = 0; j < readers.length-1-i; j++) {  
	            if(readers[j].getId()>readers[j+1].getId()){
	                readerTemp=readers[j];
	                readers[j]=readers[j+1];
	                readers[j+1]=readerTemp;
	            }
	        }
	    }
		
		this.list(readers);
	}
	/*
	 *  lists readers on the screen
	 */
	public void list(Reader[] readers) {
		for(Reader reader: readers) {
			System.out.println("Id..........: "+reader.getId());
	        System.out.println("Name........: "+reader.getName());
	        System.out.println("Address.....: "+reader.getAddress());
	        System.out.println("Phone.....: "+reader.getPhone());
	        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
	}
}































