package dialog_frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import main.Main;
import dialog_frames.WinGroupRemove.eHandler;
import objects.Group;

public class WinGroupEdit extends WinGroup{

	JComboBox comboGroup = new JComboBox();
	JLabel newName = new JLabel();
	
	public WinGroupEdit(Frame parent) {
		super(parent);
		
		setSize(300, 310);
		title.setText("Редагувати групу товарів");
		
		groupName.setText("Оберіть групу для редагування:");
		groupName.setSize(190, ELEMENT_HEIGHT);
		groupName.setLocation(getWidth()/2 - groupName.getWidth()/2, START_HEIGHT_POINT);
		
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setSize(170, ELEMENT_HEIGHT + 10);
		comboGroup.setLocation(getWidth()/2 - comboGroup.getWidth()/2, START_HEIGHT_POINT + 25);
		comboGroup.addActionListener(new eHandler());
		getContentPane().add(comboGroup);
		
		newName.setText("Введіть нову назву:");
		newName.setSize(110, ELEMENT_HEIGHT);
		newName.setLocation(getWidth()/2 - newName.getWidth()/2, START_HEIGHT_POINT + 75);
		getContentPane().add(newName);
		
		jt_groupName.setText((String) comboGroup.getSelectedItem());
		jt_groupName.setLocation(getWidth()/2 - jt_groupName.getWidth()/2, START_HEIGHT_POINT + 100);
		
		ok.setLocation(30, 240);
		cancel.setLocation(160, 240);
	}
	
	class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboGroup){
				jt_groupName.setText((String) comboGroup.getSelectedItem());
			}
		}
	}
	
	//метод вносить зміни до назви обраної групи
	public void setResult() throws ClassNotFoundException, IOException{
		int groupID = Main.mf.getGroupIDByName((String) comboGroup.getSelectedItem());
		for (Group gr : Main.mf.groups)
			if (gr.getGroupID() == groupID) gr.setGroupName(jt_groupName.getText());
		if (Main.isServer)
			Main.mf.sql.updateGroup(groupID, jt_groupName.getText());
		else
			Main.mf.nl.changeObjects(0, groupID);
	}

}
