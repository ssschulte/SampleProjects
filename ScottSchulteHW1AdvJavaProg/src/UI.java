import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.event.MouseEvent;

public class UI extends JFrame implements ActionListener
{
final int POSSIBLEITEMSLISTSIZE = 101;
private ShoppingList sList;
private String possibleItemsArray [] = new String [POSSIBLEITEMSLISTSIZE];
private	JPanel panel;
private JFrame frame;
private JButton addBtn;
private JButton deleteBtn;
private JButton viewListBtn;
private JButton saveBtn;
private JButton printBtn;
private JButton quitBtn;
private JButton mainMenuBtn;
private JLabel labelItem;
private JLabel lockedItemLabel;
private JLabel labelPriority;
private JLabel labelQuantity;
private JLabel labelPrice;
private JComboBox <Integer> prioritiesCB;
private JComboBox <Integer> quantityCB;
private JTextField priceField;
private JButton oKBtn;
private JComboBox <String> itemCB;
private String lockedItemName;
private JLabel deleteLabelItem;
private JButton budgetBtn;
private JLabel budgetLabel;
private JTextField budgetField;
private JButton budgetOK;
private JList<String> myList;
private DefaultListModel<String> myListModel;
private JButton orderOK;
private JScrollPane scrollPane;
private JScrollPane scrollPane2;
private JList<String> myPurchaseList;
private JList<String> myNonPurchaseList;

	UI(){}
	
	UI(ShoppingList sList)
	{
		this.sList = sList;
		this.possibleItemsArray = sList.getPossibleItemsArray();
		
	}
	
private void viewOptions ()
	 {	//opening window that allows users to choose what function they want to perform
		 System.out.println();
		frame = new JFrame ("What Action Would You Like to Take?");	
													
	    
	    panel = new JPanel();
	    panel.setLayout(new GridLayout(1,0));
	    frame.add(panel);
	     addBtn = new JButton("Add Item(s)");
	    panel.add(addBtn);
	    addBtn.addActionListener(this);
	     deleteBtn = new JButton("Delete Item(s)");
	    panel.add(deleteBtn);
	    deleteBtn.addActionListener(this);
	     viewListBtn = new JButton("Order List");
	    panel.add(viewListBtn);
	    viewListBtn.addActionListener(this);
	    budgetBtn = new JButton("Change Budget");
	    panel.add(budgetBtn);
	    budgetBtn.addActionListener(this);
	     saveBtn = new JButton("Save");
	    panel.add(saveBtn);
	    saveBtn.addActionListener(this);
	    printBtn = new JButton("Print");
	    panel.add(printBtn);
	    printBtn.addActionListener(this);
	     quitBtn = new JButton("Quit");
	    panel.add(quitBtn);
	    quitBtn.addActionListener(this);

	    frame.pack();
		frame.setSize(900, 300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);				


	    panel.setVisible(true);
	    addBtn.setVisible(true);  
	    deleteBtn.setVisible(true);    
	    viewListBtn.setVisible(true); 
	    budgetBtn.setVisible(true);
	    saveBtn.setVisible(true);   
	    quitBtn.setVisible(true);
	    panel.setVisible(true);
	    frame.setVisible(true);

	    
	 }


private DefaultListModel<String> createStringListModel() 
	{//part of method that allows user to reorder list
	 
	        final String[] listElements = sList.getCurrentListItemsPlusPriority();
	        DefaultListModel<String> listModel = new DefaultListModel<String>();
	        for (int i=0;i<listElements.length;i++)  {
	            listModel.addElement(listElements[i]);
	        }
	        return listModel;
	    }


		

private void viewAndModifyList()
{	 //method that allows user to reorder their list
	  myListModel = createStringListModel();
      myList = new JList<String>(myListModel);
      MyMouseAdaptor myMouseAdaptor = new MyMouseAdaptor(myList, myListModel);
      myList.addMouseListener(myMouseAdaptor);
      myList.addMouseMotionListener(myMouseAdaptor);

      frame = new JFrame("List Order");
      
      panel = new JPanel(new BorderLayout());
      panel.setLayout(new GridLayout(1,3));
      panel.add(myList);
      
      scrollPane = new JScrollPane(myList);
      scrollPane.setPreferredSize(new Dimension(900,600));
      panel.add(scrollPane, BorderLayout.CENTER);
      
      orderOK = new JButton("Save New Order");
      orderOK.setActionCommand("orderOK");
     panel.add(orderOK);
     orderOK.addActionListener(this);
     
     mainMenuBtn = new JButton("Main Menu");
     panel.add(mainMenuBtn);
     mainMenuBtn.addActionListener(this);
     
     
     
      frame.add(panel);  
      frame.setSize(900, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
  	  frame.setLocationRelativeTo(null);	

      frame.setVisible(true);
     
}

private void changeBudget()
{//change budget window pane
	
	frame = new JFrame ("Change Budget");
    
    panel = new JPanel();
    panel.setLayout(new GridLayout(4,1));
    frame.add(panel);
    
    
    budgetLabel = new JLabel ("Current Total Budget");
    panel.add(budgetLabel);
    
    budgetField = new JTextField();
    budgetField.setText(String.valueOf(sList.getTotalBudget()));
    budgetField.setActionCommand("budgetValue");
    budgetField.addMouseListener(new MouseAdapter()    //clears text when box is selected
        	{
            @Override
            public void mouseClicked(MouseEvent e){
            	if(e.getClickCount()!=0)
            		budgetField.setText("");
            }    });
    
    budgetField.addActionListener(this);
    panel.add(budgetField);
    

    budgetOK = new JButton("Change Budget");
    budgetOK.setActionCommand("budgetOK");
   panel.add(budgetOK);
   budgetOK.addActionListener(this);
   
   mainMenuBtn = new JButton("Main Menu");
   panel.add(mainMenuBtn);
   mainMenuBtn.addActionListener(this);
    
    frame.pack();
	frame.setSize(900, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    
    mainMenuBtn.setVisible(true);
    budgetLabel.setVisible(true);
	budgetField.setVisible(true);
    panel.setVisible(true);
    frame.setVisible(true);
	
}

public void explainWhyMoreInfoNeeded (String itemName)
{	//popup dialog that tells user they need to clarify what they want in list
	//because they entered the same item into the list twice
	
	this.lockedItemName= itemName;
	JFrame informationalFrame = new JFrame ("Item Already in List");
	JOptionPane informationalPane = new JOptionPane ("clarifyItems");
	informationalFrame.add(informationalPane);
	JOptionPane.showMessageDialog(informationalFrame, "Item Already in List\nPlease enter the Priority, Quantity, and Price you want in your list (Previous values for this item will be deleted)");
	this.clarifyItems();
}

private void clarifyItems()//almost the same as addItems method but needs to be separate because need to have different tags for the listeners 
{						 //this is when someone tries to add something already in list
	
	frame = new JFrame ("clarifyItems");
    
    panel = new JPanel();
    panel.setLayout(new GridLayout(3,4));
    frame.add(panel);
    
    
    labelItem = new JLabel ("Item");
    panel.add(labelItem);
    
    labelPriority = new JLabel ("Priority");
    panel.add(labelPriority);
    labelQuantity = new JLabel ("Quantity");
    panel.add(labelQuantity);
    labelPrice = new JLabel ("Price (in $)");
    panel.add(labelPrice);
    //this is the code that is different from addItems
    lockedItemLabel = new JLabel (lockedItemName);
    panel.add(lockedItemLabel);
    
    prioritiesCB = new JComboBox<Integer> ();

    for(int i=1;i<10;i++) 
    {
    	prioritiesCB.addItem(new Integer(i));
    }
    prioritiesCB.setMaximumRowCount(9);
    panel.add(prioritiesCB);
    prioritiesCB.setActionCommand("clarifyPriority");

    prioritiesCB.addActionListener(this);
    
    quantityCB = new JComboBox<Integer> ();

    for(int i=1;i<100;i++) 
    {
    	quantityCB.addItem(new Integer(i));
    }
    quantityCB.setMaximumRowCount(9);
    panel.add(quantityCB);
    quantityCB.setActionCommand("clarifyQuantity");

    quantityCB.addActionListener(this);
    
    
    priceField = new JTextField();
    priceField.setText("Price          ");
    priceField.setActionCommand("clarifyPrice");
    priceField.addMouseListener(new MouseAdapter()    //clears text when box is selected
        	{
            @Override
            public void mouseClicked(MouseEvent e){
            	if(e.getClickCount()!=0)
            		priceField.setText("");
            }    });
    
    priceField.addActionListener(this);
    panel.add(priceField);

    
     oKBtn = new JButton("OK");
     oKBtn.setActionCommand("clarifyOK");
    panel.add(oKBtn);
    oKBtn.addActionListener(this);
    
    mainMenuBtn = new JButton("Main Menu");
    panel.add(mainMenuBtn);
    mainMenuBtn.addActionListener(this);
    
    frame.pack();
	frame.setSize(900, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);



    frame.setVisible(true);
    oKBtn.setVisible(true);
    panel.setVisible(true);
    labelItem.setVisible(true); 
    labelPriority.setVisible(true);    
    labelQuantity.setVisible(true);    
    labelPrice.setVisible(true);  
    lockedItemLabel.setVisible(true);
    prioritiesCB.setVisible(true);
    quantityCB.setVisible(true);
    priceField.setVisible(true);
    mainMenuBtn.setVisible(true);
	
	
    

    
    
	
}

public void addItems() {
	//window that allows user to enter data for items they want to add to their list
	
	frame = new JFrame ("Add Items");
    
    panel = new JPanel();
    panel.setLayout(new GridLayout(3,4));
    frame.add(panel);
    
    
    labelItem = new JLabel ("Item");
    panel.add(labelItem);
    
    labelPriority = new JLabel ("Priority");
    panel.add(labelPriority);
    labelQuantity = new JLabel ("Quantity");
    panel.add(labelQuantity);
    labelPrice = new JLabel ("Price (in $)");
    panel.add(labelPrice);
 
    itemCB = new JComboBox<String>(possibleItemsArray);
    itemCB.setMaximumRowCount(12);
    
    panel.add(itemCB);
    itemCB.setActionCommand("item");
    itemCB.addActionListener(this);
    
    
    prioritiesCB = new JComboBox<Integer> ();

    for(int i=1;i<10;i++) 
    {
    	prioritiesCB.addItem(new Integer(i));
    }
    prioritiesCB.setMaximumRowCount(9);
    panel.add(prioritiesCB);
    prioritiesCB.setActionCommand("priority");

    prioritiesCB.addActionListener(this);
    
    quantityCB = new JComboBox<Integer> ();

    for(int i=1;i<100;i++) 
    {
    	quantityCB.addItem(new Integer(i));
    }
    quantityCB.setMaximumRowCount(9);
    panel.add(quantityCB);
    quantityCB.setActionCommand("quantity");

    quantityCB.addActionListener(this);
    
    
    priceField = new JTextField();
    priceField.setText("Price          ");
    priceField.setActionCommand("Price");
    priceField.addMouseListener(new MouseAdapter()    //clears text when box is selected
        	{
            @Override
            public void mouseClicked(MouseEvent e){
            	if(e.getClickCount()!=0)
            		priceField.setText("");
            }    });
    
    priceField.addActionListener(this);
    panel.add(priceField);

    
     oKBtn = new JButton("OK");
    panel.add(oKBtn);
    oKBtn.addActionListener(this);
    
    mainMenuBtn = new JButton("Main Menu");
    panel.add(mainMenuBtn);
    mainMenuBtn.addActionListener(this);
    
    frame.pack();
	frame.setSize(900, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);



    frame.setVisible(true);
    oKBtn.setVisible(true);
    panel.setVisible(true);
    labelItem.setVisible(true); 
    labelPriority.setVisible(true);    
    labelQuantity.setVisible(true);    
    labelPrice.setVisible(true);  
   
    itemCB.setVisible(true);
    
    prioritiesCB.setVisible(true);
    quantityCB.setVisible(true);
    priceField.setVisible(true);
    mainMenuBtn.setVisible(true);
    

	}

private void print()
{//window that displays the items and price they can afford to purchase
	//and the items they can't afford to purchase/or that do not yet have a price applied to them
     sList.updateBudget();
     final String[] listElements = sList.getCurrentListItemsPlusPriceToPurchase();
     DefaultListModel<String> listModelPurchase = new DefaultListModel<String>();
     for (String element : listElements) {
         listModelPurchase.addElement(element);
     }
     
     final String[] listElementsNonPurchase = sList.getCurrentListItemsPlusPriceToNotPurchase();
     DefaultListModel<String> listModelNonPurchase = new DefaultListModel<String>();
     for (String element : listElementsNonPurchase) {
    	 listModelNonPurchase.addElement(element);
     }
     
     myPurchaseList = new JList<String>(listModelPurchase);
     myNonPurchaseList = new JList<String>(listModelNonPurchase);


     frame = new JFrame("Display Lists");
     
     panel = new JPanel(new BorderLayout());
     panel.setLayout(new GridLayout(1,3));
     panel.add(myPurchaseList);
     panel.add(myNonPurchaseList);

     
     scrollPane = new JScrollPane(myPurchaseList);
     scrollPane.setPreferredSize(new Dimension(900,600));
     scrollPane.setBorder(BorderFactory.createTitledBorder("Items to Be Purchased"));

     scrollPane2 = new JScrollPane(myNonPurchaseList);
     scrollPane2.setPreferredSize(new Dimension(900,600));
     scrollPane2.setBorder(BorderFactory.createTitledBorder("Items to Not Be Purchased"));

     panel.add(scrollPane, BorderLayout.CENTER);
     panel.add(scrollPane2, BorderLayout.CENTER);

    
    
    mainMenuBtn = new JButton("Main Menu");
    panel.add(mainMenuBtn);
    mainMenuBtn.addActionListener(this);
    
    
    
     frame.add(panel);  
     frame.setSize(900, 600);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.pack();
 	  frame.setLocationRelativeTo(null);	

 	 scrollPane.setVisible(true);
 	 scrollPane2.setVisible(true);
 	  panel.setVisible(true);
     frame.setVisible(true);
     
	
	
}

private void deleteItems()
{
	//Window that allows user to select items they want to delete from their list
frame = new JFrame ("Delete");
    
    panel = new JPanel();
    panel.setLayout(new GridLayout(4,1));
    frame.add(panel);
    
    
    deleteLabelItem = new JLabel ("Which Item do you want to Delete?");
    panel.add(deleteLabelItem);
    deleteLabelItem.setHorizontalAlignment(JLabel.CENTER);
 
    itemCB = new JComboBox<String>(sList.getCurrentListItems());
    itemCB.setMaximumRowCount(12);
    panel.add(itemCB);
    itemCB.setAlignmentX(CENTER_ALIGNMENT);
    itemCB.setActionCommand("item");
    itemCB.addActionListener(this);
    
    deleteBtn = new JButton("Delete");
    panel.add(deleteBtn);
    deleteBtn.addActionListener(this);
    
    mainMenuBtn = new JButton("Main Menu");
    panel.add(mainMenuBtn);
    mainMenuBtn.addActionListener(this);
	
    frame.pack();
	frame.setSize(900, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    
    frame.setVisible(true);
    itemCB.setVisible(true);
    deleteLabelItem.setVisible(true);
    
}


public void actionPerformed(ActionEvent e)
{//method that catches all the actionEvents and directs program to the corresponding method

	String actionCommand = e.getActionCommand();
	if (actionCommand=="Add Item(s)")
	{
		frame.dispose();
		this.addItems();
	}
	else if (actionCommand=="Delete Item(s)")
	{
		frame.dispose();
		this.deleteItems();
	}
	else if (actionCommand=="Order List")
	{
		frame.dispose();
		this.viewAndModifyList();
	
	}
	else if (actionCommand=="Main Menu")
	{
		frame.dispose();
		this.viewOptions();
		
	}
	else if (actionCommand=="Save")
	{
		try {
			sList.writeToFile();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	else if (actionCommand=="Print")
	{
		frame.dispose();
		this.print();
	}
	else if (actionCommand=="Quit")
	{
		try {
			sList.writeToFile();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}
	else if (actionCommand=="clarifyPrice")
	{
	}
	else if (actionCommand=="clarifyPriority")
	{
	}
	else if (actionCommand=="clarifyQuantity")
	{
	}
	else if (actionCommand=="clarifyOK")
	{//Need this to overwrite stuff
		sList.deleteFromList(lockedItemName);
		try{
			Double.valueOf(priceField.getText());
			sList.addToList(lockedItemName, Integer.valueOf(String.valueOf(quantityCB.getSelectedItem())), Integer.valueOf(String.valueOf(prioritiesCB.getSelectedItem())), Double.valueOf(priceField.getText()));
			frame.dispose();

		}
		catch(NumberFormatException a)
		{	//send a 0 for the price if the Price wasn't entered or was invalid
			sList.addToList(lockedItemName, Integer.valueOf(String.valueOf(quantityCB.getSelectedItem())), Integer.valueOf(String.valueOf(prioritiesCB.getSelectedItem())),0);
			frame.dispose();

		}
		
	}
	else if (actionCommand=="priority")
	{
		
		
	}
	else if (actionCommand=="quantity")
	{
		
		
	}
	else if (actionCommand=="OK")
	{	
		try{
			Double.valueOf(priceField.getText());
			frame.dispose();
			sList.addToList(String.valueOf(itemCB.getSelectedItem()), Integer.valueOf(String.valueOf(quantityCB.getSelectedItem())), Integer.valueOf(String.valueOf(prioritiesCB.getSelectedItem())), Double.valueOf(priceField.getText()));
			
		}
		catch(NumberFormatException a)
		{	//send a 0 for the price if the Price wasn't entered or was invalid
			frame.dispose();
			sList.addToList(String.valueOf(itemCB.getSelectedItem()), Integer.valueOf(String.valueOf(quantityCB.getSelectedItem())), Integer.valueOf(String.valueOf(prioritiesCB.getSelectedItem())),0);

		}
	}
	else if (actionCommand=="Price")
	{
		
	}
	else if (actionCommand=="item")
	{
		
	}
	else if (actionCommand=="Delete")
	{	
		frame.dispose();
		if(itemCB.getSelectedItem()==null)
			{
			this.viewOptions();
			}
		else {
		sList.deleteFromList(String.valueOf(itemCB.getSelectedItem()));
		this.deleteItems();
			 }
	}
	else if (actionCommand=="orderOK")
	{
		frame.dispose();
		String [] toChangeList = new String[myList.getModel().getSize()];
		for(int i=0;myList.getModel().getSize()>i;i++)
		{
			
			toChangeList[i] = myList.getModel().getElementAt(i);

		}
		sList.reorderItemList(toChangeList);
		this.viewOptions();

		
	
	}
	else if (actionCommand=="Change Budget")
	{
		frame.dispose();
		this.changeBudget();
	}
	else if (actionCommand=="budgetOK")
	{
		frame.dispose();
		try{
			sList.setTotalBudget(Double.valueOf(budgetField.getText()));
			frame.dispose();
			this.viewOptions();

		}
		catch(NumberFormatException a)
		{	
			frame.dispose();
			JFrame budgetErrorFrame = new JFrame ("Item Already in List");
			JOptionPane budgetErrorPane = new JOptionPane ("clarifyItems");
			budgetErrorFrame.add(budgetErrorPane);
			JOptionPane.showMessageDialog(budgetErrorFrame, "Budget Value needs to be a number");
			this.changeBudget();

		}
	}
	else//do not want to hit this code
		{}
}




public static void main (String args[]) throws FileNotFoundException 
{//main method that just gets everything started
UI ui = new UI();
ui.initialize();
	
}
private void initialize() throws FileNotFoundException
{//method that creates the ShoppingList, loads possible items to select from file, loads previous results from file
 //opens the initial GUI window

	sList = new ShoppingList();
	possibleItemsArray=sList.getPossibleItemsArray();
	
	sList.readFromFile();
	this.viewOptions();
}


}
