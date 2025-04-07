/*COSC 241
 * May 10
 * William Jackson
 * My book management system
 */
package Lab;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
public class BookManagement {

	private static ArrayList<String> booklist;



	//READ BOOKS FROM FILE
	  public static String[] readBooks(String filename){
	    try {
	      File booksFile = new File(filename);
	      Scanner scan = new Scanner(booksFile);
	      ArrayList<String> booklist = new ArrayList<String>();
	      
	      while(scan.hasNextLine()){
	        String book = scan.nextLine();
	        booklist.add(book);
	      }
	      scan.close();
	      
	      String[] booksArray = new String[booklist.size()];
	      booksArray = booklist.toArray(new String[booklist.size()]);
	      // turning the array list into an array
	      
	      return booksArray;
	    } 
	    catch (IOException e) {
	      e.printStackTrace();
	      String[] booksArray={""};
	      return booksArray;
	    }
	  }// This is my method to read the books in the file array

	  
	  //WRITE BOOKS
	  public static void writeBooks(String[] books,String filename){
	    try {
	      FileWriter writer = new FileWriter(filename); 
	      for (String book:books){
	        writer.write(book+"\n");
	      }
	       
	      writer.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	 
	  //gets the whole unsorted list
	  public static void viewbooks(String filename){ 
		    String[] books = readBooks(filename); 
		    
		    System.out.printf("Books are : \n%s\n\n",Arrays.toString(books));
	  }
	  
	  
	  //SORT METHOD
	  public static void sortbooks(String filename){ 
	    String[] books = readBooks(filename); 
	    Arrays.sort(books);
	    System.out.printf("Books are : \n%s\n\n",Arrays.toString(books));
	  }//this is the view book method and when using .sort it puts them in ABC order.

	  
	  
	  //THIS IS MY ADD BOOK
	  public static void addbook(String filename){
	    String[] books = readBooks(filename);
	    Scanner scan = new Scanner(System.in);
	    System.out.print("Please enter new book to add: ");
	    String newbook = scan.nextLine();
	    newbook = newbook.toUpperCase();
	    ArrayList<String> booklist = new ArrayList<String>(Arrays.asList(books));//change array to list
	    booklist.add(newbook);
	    String[] booksArray = new String[booklist.size()];
	    booksArray = booklist.toArray(new String[booklist.size()]);

	    writeBooks(booksArray,filename);//using the method to actually write the book
	    
	    System.out.printf("Books are : \n%s\n\n",Arrays.toString(booksArray));
	  }//prints your new book in list

	  
	  
	  //MY DELETE METHOD this was horrible to figure out 
	  public static void deletebook(String filename) {
		  String[] books = readBooks(filename);
		    Scanner scan = new Scanner(System.in);
		    System.out.print("Please enter book to delete: ");
		    String book = scan.nextLine();
		   book= book.toUpperCase();
		    ArrayList<String> booklist = new ArrayList<String>(Arrays.asList(books));
		    String[] booksArray = new String[booklist.size()];
		    booksArray = booklist.toArray(new String[booklist.size()]);
		   
		  try {

		      File inFile = new File(filename);

		      if (!inFile.isFile()) {
		        System.out.println("Is not a book on list");
		        return;
		      }

		      //gets your new file
		      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

		      BufferedReader br = new BufferedReader(new FileReader(filename));
		      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

		      String line = null;

		      //gets stuff from original file and rewrites it until
		      while ((line = br.readLine()) != null) {

		        if (!line.trim().equals(book)) {

		          pw.println(line);
		          pw.flush();
		        }
		      }
		      pw.close();
		      br.close();
		      	//close writers and readers
		    
		      if (!inFile.delete()) {
		        System.out.println("Could not delete file");
		        return;
		      }

		      
		      if (!tempFile.renameTo(inFile))
		        System.out.println("Could not rename file");
		      	//if statements incase something goes wrong
		    }
		    catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }}

	// MY SEARCH BOOK
	  public static void searchbook(String filename) {
	      String[] books = readBooks(filename);

	      Scanner scan = new Scanner(System.in);
	      System.out.print("Please enter book name to search: ");
	      String book = scan.nextLine();
	      
	      boolean bookFound = false;
	      for(String book_ : books) {
	          if(book_.equalsIgnoreCase(book)) {
	              bookFound = true;
	              System.out.printf("\nBook Found: %s\n", book_);
	              break;
	          }//if its the same itl find it because of ignore case
	      }

	      if(!bookFound) {
	          System.out.println("\nBook not found!\n");
	      }
	  }
	  
		  

	//MY EDIT BOOK not the original for loop idea but this actually works
	        
		  public static void editbook (String filename) {
		       
			  Scanner scan = new Scanner(System.in);
		      System.out.print("Please enter book name to Edit: ");
		      String oldBookName = scan.nextLine();
		      
		      Scanner sc = new Scanner(System.in);
		    		  System.out.print("please enter the New Name for the book");
		      String newBookName = scan.nextLine();
		      
			  
			  try {
		          //gets a temp file, reads and writes
		            File tempFile = new File("temp.txt");
		            BufferedReader reader = new BufferedReader(new FileReader(filename));
		            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
		           
		            //ditched the for loop because it wouldn't work for nothing
		            // the loop was right but it wouldn't work
		            String line;
		            while ((line = reader.readLine()) != null) {
		                oldBookName=oldBookName.toUpperCase();
		                if (line.contains(oldBookName)) {
		                    line = line.replace(oldBookName, newBookName);
		                }
		                line=line.toUpperCase();//make sure the input stays caps
		                writer.println(line);
		            }

		            // close this junk
		            reader.close();
		            writer.close();

		            //gets ride of old makes a new one
		            File originalFile = new File(filename);
		            originalFile.delete();

		            //make it easier and renamed it
		            tempFile.renameTo(originalFile);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			  
		    }
	


		  

		  
	  public static void main(String[] args) throws IOException  {
	    // defining scanner object
	    Scanner scan = new Scanner(System.in);
	    // displaying menu, using infinite loop 
	    while(true){
	      // displaying menu 
	      System.out.println("Please choose option");
	      System.out.println("1. View the book list");
	      System.out.println("2. View books in order");
	      System.out.println("3. Add a Book");
	      System.out.println("4. Edit a Book");
	      System.out.println("5. Delete a Book");
	      System.out.println("6. Search for a Book");
	      System.out.println("7. Exit");
	     

	      // fetching input
	      int option = scan.nextInt();

	      // using switch case to work on option
	      switch(option){
	        case 1: // calling function viewbooks
	          viewbooks("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	          break;
	        case 2: // calling function sortbooks
	          sortbooks("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	          break;
	        case 3: // calling function addbook
	        addbook("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	          break;
	        case 4: //edit your books
	        	editbook("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	        	
	          break;
	        case 5://delete your books
	        	deletebook("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	        	break;
	        case 6://search for books
	        	searchbook("C:\\Users\\Bill\\eclipse-workspace\\COSC240\\src\\Lab\\books.txt");
	        	break;
	        case 7:
	        	return;
	        	
	        
	        default:
	          System.out.println("Invalid option!");        
	      }

	    }
	  }
	}
