import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PossibleItems {
	private final int numOfProducts = 101;
	private String offeredProducts [] = new String[numOfProducts];
	
	PossibleItems(String path) throws FileNotFoundException
	{
		
		 Scanner in = null; 
	 	 File listOfAllItems = new File (path);
		 if (listOfAllItems.exists()==true)
		 {	 int i=0;
			 in = new Scanner (listOfAllItems);
			 while (in.hasNextLine())
			 {
				offeredProducts[i]=in.nextLine();
				i++;
				 
			 }
		 }
		 else 
			 {}
		 
		 in.close();
	
		
	}

	public String[] getOfferedProducts() {
		String [] arrayToBePassed = offeredProducts.clone();
		return arrayToBePassed;
	}

	
	
	
	
	

}
