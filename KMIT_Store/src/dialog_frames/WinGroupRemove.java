package dialog_frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.Main;

public class WinGroupRemove extends WinGroup{

	JComboBox comboGroup = new JComboBox();
	JLabel inform = new JLabel();
	JLabel warn = new JLabel();
	
	public WinGroupRemove(Frame parent) {
		super(parent);
		setSize(300, 310);
		
		title.setText("Видалення групи");
		
		groupName.setText("Оберіть групу для видалення:");
		groupName.setSize(170, ELEMENT_HEIGHT);
		groupName.setLocation(getWidth()/2 - groupName.getWidth()/2, START_HEIGHT_POINT);
		
		remove(jt_groupName);
		
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setSize(170, ELEMENT_HEIGHT + 10);
		comboGroup.setLocation(getWidth()/2 - comboGroup.getWidth()/2, START_HEIGHT_POINT + 25);
		comboGroup.addActionListener(new eHandler());
		getContentPane().add(comboGroup);
		
		setInformText();
		inform.setBounds(30, 130, 140, 45);
		getContentPane().add(inform);
		
		warn.setText("<html>Увага! Всі підгрупи та товари групи<br>будуть видалені</html>");
		warn.setBounds(30, 185, 250, 30);
		getContentPane().add(warn);
		
		ok.setLocation(30, 240);
		cancel.setLocation(160, 240);
	}
	
	void setInformText(){
		int groupID = Main.mf.getGroupIDByName((String) comboGroup.getSelectedItem());
		
		inform.setText("<html>В обраній групі є:<br>"+
				Main.mf.getSubgroupsByGroupID(groupID).size()+" підгруп, <br>"+
				Main.mf.getGoodsOfGroup(groupID).size()+" товарів.</html>");
	}
	
	class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboGroup){
				setInformText();
			}
		}
	}
	
	//метод повертає ID обраної групи
	public int getResult(int s){//параметр s нічого не значить; він потрібен для того, 
						//щоб відрізнити цей метод від методу getResult() 
						//класу WinGroup, від якого ми наслідуємось,
						//оскільки getResult() класу WinGroup повертає дані 
						//іншого типу (Group)
		return Main.mf.getGroupIDByName((String) comboGroup.getSelectedItem());
	}
	
}


