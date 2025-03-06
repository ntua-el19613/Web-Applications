import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class

class MainClass {
	
  public static void main(String[] args) {
	  Scanner input = new Scanner(System.in);  // Create a Scanner object

	  System.out.println("Enter username and password:");
	  String userInput = input.nextLine();  // Read user input

	  input.close();
	  
	  try {
		  BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		  writer.write(userInput);
		  writer.close();
	  }catch(IOException e) {
		  e.printStackTrace();
	  }
	  
	  try {
		BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
		System.out.println("User Input is: " + reader.readLine());
		reader.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}