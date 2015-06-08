package listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import main.Main;


	
public class DocListener implements DocumentListener {
    
    public void insertUpdate(DocumentEvent e) {
    	updateLog(e);
    }
    public void removeUpdate(DocumentEvent e) {
        updateLog(e);
    }
    public void changedUpdate(DocumentEvent e) {
      
    }

    public void updateLog(DocumentEvent e) {
        
    	try {
	    	if (e.getDocument().equals(Main.mf.jtf_date_1.getDocument()) ||
	    			e.getDocument().equals(Main.mf.jtf_date_2.getDocument())){
	    		Main.mf.rb2_3.setSelected(true);
	    	}
    	}catch (NullPointerException ex){}
    	
 //   	Document doc = (Document)e.getDocument();
 //       int changeLength = e.getLength();
 //       displayArea.append(
 //           changeLength + " character" +
 //           ((changeLength == 1) ? " " : "s ") +
 //           action + doc.getProperty("name") + "." + newline +
 //           "  Text length = " + doc.getLength() + newline);
    }
}