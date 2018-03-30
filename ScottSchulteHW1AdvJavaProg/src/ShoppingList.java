
import java.util.*;
import java.io.*;

public class ShoppingList {
//file that loads and stores info for shopping list
private final String loadSavePath ="src/sList.txt";
//The set list of items to choose from
private final String possibleItemsPath = "src/groceryItems.txt";
private final int INITIALSHOPPINGLISTSIZE = 30;
private final int POSSIBLEITEMSLISTSIZE = 101;
private ArrayList <Item> shoppingList =new ArrayList <Item> (INITIALSHOPPINGLISTSIZE);
private ArrayList <Item> tempShoppingList =new ArrayList <Item> (INITIALSHOPPINGLISTSIZE);
private String possibleItemsArray [] = new String [POSSIBLEITEMSLISTSIZE];
private double totalBudget;
private double currentBudget;
	

	 public ShoppingList () throws FileNotFoundException
	 {
		 PossibleItems pI = new PossibleItems(possibleItemsPath);
		 possibleItemsArray=pI.getOfferedProducts();			//this is first time groceryItems.txt is checked to see if it is there
	}
	 
	 
	 public boolean writeToFile() throws FileNotFoundException
	 {
		 PrintWriter out = null; 
	 	 File listStart = new File (loadSavePath);
		 if (listStart.exists()==true)
		 {	
			 out = new PrintWriter (listStart);
			 out.print(totalBudget + " ");//budget info saved at beginning of file
			 out.print(currentBudget + " ");//budget info saved at beginning of file
			 this.sort();
			 for(int i=0;i<shoppingList.size();i++)
			 {
				out.print(shoppingList.get(i)); 
			 }
		 }
		 
		 else 
		 	{return false;}
		 
		 out.close();
		 return true;
	 }
	 
	 public boolean readFromFile () throws FileNotFoundException 
		{
		 Scanner in = null; 
	 	 File listStart = new File (loadSavePath);
		 if (listStart.exists()==true)
		 {	 shoppingList.clear();//resetting everything as we load from file
		 	 totalBudget=0;//resetting everything as we load from file
		 	 currentBudget=0;//resetting everything as we load from file
			 try {
				in = new Scanner (listStart);
				 totalBudget=Double.valueOf(in.next());//budget info is first data saved into file
			} catch (Exception e) {  //if file is empty, create a file with blanks to start from scratch
				 PrintWriter out = new PrintWriter (listStart);
				 out.print("0 0 ");
				 out.close();
				in = new Scanner (listStart);
				 totalBudget=Double.valueOf(in.next());//budget info is first data saved into file
			}
			 currentBudget=Double.valueOf(in.next());//budget info is first data saved into file
			 while (in.hasNext())//reading and creating the objects for 
			 {	String tempName="";
				 while(in.hasNextInt()==false)
				 {tempName = tempName + in.next() + " ";}
				 int tempQuantity = Integer.valueOf(in.next());
				 int tempPriority = Integer.valueOf(in.next());
				 double tempPrice = Double.valueOf(in.next());
				 Item tempItem = new Item (tempName,tempQuantity,tempPriority,tempPrice);
				 shoppingList.add(tempItem);
				 
			 }
		 }
		 else 
			 {return false;}
		 
		 in.close();
		 this.calculateBudget();//update what can be purchased

		 return true;//successful adding of items from file to shopping list
	}
	 
	 public void deleteFromList (String name)
	 
	 {	int index=this.alreadyInListCheck(name);
		shoppingList.remove(index);
		 this.calculateBudget();//update what can be purchased


	 }
	
	 
	 public void addToList ( String itemName,int quantity, int priority, double price) 
	 {	//adds item to list if it is not already in list
		//calls UI method to resolve issue if item is already in list

		 String itemToAdd=itemName;
		 int newQuantity=quantity;
		 int newPriority=priority;
		 Double newPrice=price;
		 
		 int tempCheckValue=this.alreadyInListCheck(itemName);//checks if item is in list
		 if(tempCheckValue!=-1)								//returns index of matching item if is in list
		 {		
			 UI verifyResults = new UI (this);
			 verifyResults.explainWhyMoreInfoNeeded(itemName);
			 
		 
		 }
		 else if (newPrice==0)//if price was not entered or invalid
		 {
			  Item newestItem = new Item (itemToAdd,newQuantity,newPriority);
			  shoppingList.add(newestItem);
			  this.calculateBudget();//update what can be purchased
			  UI ui= new UI (this);
			  ui.addItems();

		 }
		 else
		 {Item newestItem = new Item (itemToAdd, newQuantity,newPriority,newPrice);
		  shoppingList.add(newestItem);
		  this.calculateBudget();//update what can be purchased
		  UI ui= new UI (this);
		  ui.addItems();
		 }
		 
	
		 
		 
	 }
	 
	 

	
	private int alreadyInListCheck(String name)//returns -1 if not in list
												//returns index of matching name if item is in list
	{//checks if Item is already in list by checking name
		name=name.trim();
		int inListReference = -1;
		for (int i =0;i<shoppingList.size();i++)
		{	
			if(name.compareToIgnoreCase(shoppingList.get(i).getName().trim())==0)
				{inListReference=i;
				}
		}
		return inListReference;
	}
	 
	
	
	
	private void calculateBudget()
	{	//calculates budget by going through list by priority and tagging everything user can purchase as "being purchased"
		//and then moving on to the next item
		currentBudget=totalBudget;
		for(int j=0;j<shoppingList.size();j++)//reset beingPurchased flags
		{
		shoppingList.get(j).setBeingPurchased(false);
		}
	
	
		for (int i =1;i<10;i++)//for each priority
		{
			
		
		for(int j=0;j<shoppingList.size();j++)
		{
			if((shoppingList.get(j).isBeingPurchased()==false) && (shoppingList.get(j).getPriority()==i) 
				&& (shoppingList.get(j).getPrice() * shoppingList.get(j).getQuantity()<currentBudget) && (shoppingList.get(j).getPrice()!=0))
			{
				currentBudget = currentBudget - (shoppingList.get(j).getPrice()*shoppingList.get(j).getQuantity());
				shoppingList.get(j).setBeingPurchased(true);

			}
			
		}
		
		
	}
	}
	
	

	public void sort()
	{//sorts list by priority
		Collections.sort(
				 shoppingList,
				 (item1, item2) -> item1.getPriority() - item2.getPriority());
		 
		 
	}
	
	
	public String [] getCurrentListItems()
	{	//to display all items in list
		int sListSize = shoppingList.size();
		String [] tempList = new String [sListSize];
		for (int i=0;i<sListSize;i++)
			{
		tempList[i]=shoppingList.get(i).getName();
			}
		return tempList;
	}
	
	public String [] getCurrentListItemsPlusPriceToPurchase()
	{//to display items in list that are being purchased
		this.calculateBudget();
		int sListSize = shoppingList.size();
		String [] tempList = new String [sListSize];
		int count=0;
		for (int i=0;i<sListSize;i++)
			{
			if(shoppingList.get(i).isBeingPurchased()==true)
		tempList[count++]=shoppingList.get(i).getName() + "  $" + shoppingList.get(i).getPrice() + " x " +  shoppingList.get(i).getQuantity() + " = $" +(shoppingList.get(i).getPrice()*shoppingList.get(i).getQuantity());
			}
		return tempList;
	}
	public String [] getCurrentListItemsPlusPriceToNotPurchase()
	{//to display items in list that are not being purchased
		this.calculateBudget();
		int sListSize = shoppingList.size();
		String [] tempList = new String [sListSize];
		int count=0;
		for (int i=0;i<sListSize;i++)
			{
			if(shoppingList.get(i).isBeingPurchased()==false)
				tempList[count++]=shoppingList.get(i).getName() + "  $" + shoppingList.get(i).getPrice() + " x " +  shoppingList.get(i).getQuantity() + " = $" + (shoppingList.get(i).getPrice()*shoppingList.get(i).getQuantity());
			}
		return tempList;
	}
	
	
	public String [] getCurrentListItemsPlusPriority()
	{//for displaying items and priorities in your list
		int sListSize = shoppingList.size();
		String [] tempList = new String [sListSize];
		for (int i=0;i<sListSize;i++)
			{
		tempList[i]=shoppingList.get(i).getName() + "- P" + shoppingList.get(i).getPriority() + " - Old #"+ i;
			}
		return tempList;
	}
	
	public void reorderItemList (String [] newOrderList)
	{//after the user reorders list in UI, this allows those changes to be represented in the ArrayList of Items
		tempShoppingList=(ArrayList<Item>) shoppingList.clone();
		for (int i =0; i<newOrderList.length;i++)
		{
			int indexOfNumSign=newOrderList[i].lastIndexOf("#");
			String originalNum = newOrderList[i].substring(indexOfNumSign+1);
			shoppingList.set(i, tempShoppingList.get(Integer.parseInt(originalNum)));
		}
		
	}
	
	public void updateBudget()
	{
		this.calculateBudget();
	}

	public double getTotalBudget() {
		return totalBudget;
		
	}
	
	private void setCurrentBudget(double currentBudget) {
		this.currentBudget = totalBudget;
	}

	public  void setTotalBudget(double totalBudget) {
		this.totalBudget = totalBudget;
		this.currentBudget = totalBudget;
	}


	public double getCurrentBudget() {
		return currentBudget;
	}



	public String[] getPossibleItemsArray() {
		return possibleItemsArray;
	}





}
