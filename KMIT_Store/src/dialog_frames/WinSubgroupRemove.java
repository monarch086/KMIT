package dialog_frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import main.Main;

public class WinSubgroupRemove extends WinGroupRemove{

	public WinSubgroupRemove(Frame parent) {
		super(parent);
		
		title.setText("Видалення підгрупи");
		groupName.setText("Оберіть підгрупу для видалення:");
		groupName.setSize(190, ELEMENT_HEIGHT);
		
		String[] string = Main.mf.getSubgroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		setInformText();
		
		warn.setText("Увага! Всі товари підгрупи будуть видалені");
	}
	
	void setInformText(){
		int subgroupID = Main.mf.getSubgroupIDByName((String) comboGroup.getSelectedItem());
		
		inform.setText("<html>В обраній підгрупі є:<br>"+
				Main.mf.getGoodsOfSubgroup(subgroupID).size()+" товарів.</html>");
	}
	
	//метод повертає ID обраної підгрупи
	public int getResult(int s){//параметр s нічого не значить; він потрібен для того, 
						//щоб відрізнити цей метод від методу getResult() 
						//класу WinGroup, від якого ми наслідуємось,
						//оскільки getResult() класу WinGroup повертає дані 
						//іншого типу (Group)
		return Main.mf.getSubgroupIDByName((String) comboGroup.getSelectedItem());
	}
}
