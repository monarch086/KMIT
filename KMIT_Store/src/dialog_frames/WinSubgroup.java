package dialog_frames;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import main.Main;
import objects.Subgroup;

public class WinSubgroup extends WinGroup{

	JLabel subgroupName;
	JTextArea jt_subgroupName;
	JComboBox comboGroup;
	
	public WinSubgroup(Frame parent) {
		super(parent);
		setSize(300, 250);
		
		title.setText("Додати нову підгрупу товарів");
		
		comboGroup = new JComboBox();
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setBounds(124, START_HEIGHT_POINT-3, 146, 25);
		//comboGroup.addActionListener(available_handler);
		getContentPane().add(comboGroup);
		
		subgroupName = new JLabel("Назва підгрупи:");
		subgroupName.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+40, 100, ELEMENT_HEIGHT);
		getContentPane().add(subgroupName);
		
		remove(jt_groupName);
		
		jt_subgroupName = new JTextArea();
		jt_subgroupName.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //встановлюємо сірий ободок
		jt_subgroupName.setWrapStyleWord(true);
		jt_subgroupName.setRows(3);
		jt_subgroupName.setLineWrap(true); //переносить текст на наступний рядок
		jt_subgroupName.setBounds(126, START_HEIGHT_POINT+40, 142, 49);
		getContentPane().add(jt_subgroupName);
		
		ok.setLocation(30, 180);
		cancel.setLocation(160, 180);
	}
	
	//метод повертає ID групи та назву підгрупи
	public Subgroup getResult(int s){//параметр s нічого не значить; він потрібен для того, 
		//щоб відрізнити цей метод від методу getResult() 
		//класу WinGroup, від якого ми наслідуємось,
		//оскільки getResult() класу WinGroup повертає дані 
		//іншого типу (Group)
		return new Subgroup(Subgroup.getGeneralID(), Main.mf.getGroupIDByName((String)comboGroup.getSelectedItem()), jt_subgroupName.getText());
	}
}
