import java.text.DecimalFormat;

public class Item {
private String name;
private int quantity;
private int priority;
private double price;
private boolean beingPurchased=false;


 Item(String name)
{
	this.name=name;
}

 Item (String name, int quantity, int priority)
{
	this.name=name;
	this.quantity = quantity;
	this.priority = priority;
	

}

 Item (String name, int quantity,  int priority, double price)
{
	this.name=name;
	this.quantity = quantity;
	this.priority = priority;
	this.price = price;
}

public double getPrice() {
	return price;
}

private void setPrice(double price) {
	this.price = price;
}

public int getPriority() {
	return priority;
}

private void setPriority(int priority) {
	this.priority = priority;
}

public String getName() {
	return name;
}

public int getQuantity() {
	return quantity;
}

private void setQuantity(int quantity) {
	this.quantity = quantity;
}

public boolean isBeingPurchased() {
	return beingPurchased;
}

public void setBeingPurchased(boolean beingPurchased) {
	this.beingPurchased = beingPurchased;
}

public String toString() {
	DecimalFormat numberFormat = new DecimalFormat("#.00");
	return name + " " + quantity + " " + priority +" " + numberFormat.format(price) + " ";
}



}
