import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class MyMouseAdaptor extends MouseInputAdapter  {
	//class that allows user to reorder list
    private boolean Drag = false;
    private int draggedFromIndex;
    private JList<String> myList;
    DefaultListModel<String> myListModel;
    
    MyMouseAdaptor(JList<String> myList, DefaultListModel<String> myListModel)
    {
    	this.myList=myList;
    	this.myListModel=myListModel;
    }

    public void mousePressed(MouseEvent e) {
    	//part of method that allows user to reorder list
        if (SwingUtilities.isLeftMouseButton(e)) {
        	draggedFromIndex = myList.getSelectedIndex();
            Drag = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
    	//part of method that allows user to reorder list
    	Drag = false;
    }

    public void mouseDragged(MouseEvent e) {
    	//part of method that allows user to reorder list
        if (Drag) {
            int currentIndex = myList.locationToIndex(e.getPoint());
            if (currentIndex != draggedFromIndex) {
                int draggedToIndex = myList.getSelectedIndex();
                String dragElement = myListModel.get(draggedFromIndex);
                myListModel.remove(draggedFromIndex);
                myListModel.add(draggedToIndex, dragElement);
                draggedFromIndex = currentIndex;
            }
        }
    }
}