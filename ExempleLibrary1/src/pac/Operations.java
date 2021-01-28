/**
 * 
 */
package pac;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;

/**
 * @author maiconalmeida
 *
 */
public class Operations {
	
	/*
	 * read param file & return read lines in the file
	 *  jump first line
	 */
	public int readLines(File file) {
		int total = 0;
		try {
			FileReader isr = new FileReader(file);
			BufferedReader bf = new BufferedReader(isr);
			String line;
			line = bf.readLine();
			while(true) {
				line = bf.readLine();
				if(line == null)
					break;
				total++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return total;
	}

}
