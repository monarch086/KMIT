package dialog_frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import main.Main;
import objects.Subgroup;

public class WinSubgroupEdit extends WinGroup{
	
	JComboBox comboGroup = new JComboBox();
	JComboBox comboSubgroup = new JComboBox();
	
	JLabel subgroupName = new JLabel();
	JLabel newName = new JLabel();
	
	public WinSubgroupEdit(Frame parent) {
		super(parent);
		
		setSize(300, 385);
		title.setText("Редагувати підгрупу товарів");
		
		groupName.setText("Оберіть групу товарів:");
		groupName.setSize(130, ELEMENT_HEIGHT);
		groupName.setLocation(getWidth()/2 - groupName.getWidth()/2, START_HEIGHT_POINT);
		
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setSize(170, ELEMENT_HEIGHT + 10);
		comboGroup.setLocation(getWidth()/2 - comboGroup.getWidth()/2, START_HEIGHT_POINT + 25);
		comboGroup.addActionListener(new eHandler());
		getContentPane().add(comboGroup);
		
		subgroupName.setText("Оберіть підгрупу для редагування:");
		subgroupName.setSize(200, ELEMENT_HEIGHT);
		subgroupName.setLocation(getWidth()/2 - subgroupName.getWidth()/2, START_HEIGHT_POINT + 75);
		getContentPane().add(subgroupName);
		
		int groupID = Main.mf.getGroupIDByName((String) comboGroup.getSelectedItem());
		string = Main.mf.getSubgroupNamesByGroupID(groupID);
		comboSubgroup.setModel(new DefaultComboBoxModel(string));
		comboSubgroup.setSize(170, ELEMENT_HEIGHT + 10);
		comboSubgroup.setLocation(getWidth()/2 - comboSubgroup.getWidth()/2, START_HEIGHT_POINT + 100);
		comboSubgroup.addActionListener(new eHandler());
		getContentPane().add(comboSubgroup);
		
		newName.setText("Введіть нову назву:");
		newName.setSize(110, ELEMENT_HEIGHT);
		newName.setLocation(getWidth()/2 - newName.getWidth()/2, START_HEIGHT_POINT + 150);
		getContentPane().add(newName);
				
		jt_groupName.setText((String) comboSubgroup.getSelectedItem());
		jt_groupName.setLocation(getWidth()/2 - jt_groupName.getWidth()/2, START_HEIGHT_POINT + 175);
		
		ok.setLocation(30, 315);
		cancel.setLocation(160, 315);
	}
	
	class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboGroup){
				int groupID = Main.mf.getGroupIDByName((String) comboGroup.getSelectedItem());
				String[] string = Main.mf.getSubgroupNamesByGroupID(groupID);
				comboSubgroup.setModel(new DefaultComboBoxModel(string));
				jt_groupName.setText((String) comboSubgroup.getSelectedItem());
			}
			
			if (e.getSource() == comboSubgroup){
				jt_groupName.setText((String) comboSubgroup.getSelectedItem());
			}
		}
	}
	
	//метод вносить зміни до назви обраної підгрупи
	public void setResult() throws ClassNotFoundException, IOException{
		int subgroupID = Main.mf.getSubgroupIDByName((String) comboSubgroup.getSelectedItem());
		for (Subgroup sgr : Main.mf.subgroups)
			if (sgr.getSubgroupID() == subgroupID) sgr.setSubgroupName(jt_groupName.getText());
		if (Main.isServer)
			Main.mf.sql.updateSubgroup(subgroupID, jt_groupName.getText());
		else
			Main.mf.nl.changeObjects(1, subgroupID);
	}
}
