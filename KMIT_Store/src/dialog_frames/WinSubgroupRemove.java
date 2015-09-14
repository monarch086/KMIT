package dialog_frames;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import main.Main;

public class WinSubgroupRemove extends WinGroupRemove{

	public WinSubgroupRemove(Frame parent) {
		super(parent);
		
		title.setText("��������� �������");
		groupName.setText("������ ������� ��� ���������:");
		groupName.setSize(190, ELEMENT_HEIGHT);
		
		String[] string = Main.mf.getSubgroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		setInformText();
		
		warn.setText("�����! �� ������ ������� ������ �������");
	}
	
	void setInformText(){
		int subgroupID = Main.mf.getSubgroupIDByName((String) comboGroup.getSelectedItem());
		
		inform.setText("<html>� ������ ������ �:<br>"+
				Main.mf.getGoodsOfSubgroup(subgroupID).size()+" ������.</html>");
	}
	
	//����� ������� ID ������ �������
	public int getResult(int s){//�������� s ����� �� �������; �� ������� ��� ����, 
						//��� �������� ��� ����� �� ������ getResult() 
						//����� WinGroup, �� ����� �� ����������,
						//������� getResult() ����� WinGroup ������� ��� 
						//������ ���� (Group)
		return Main.mf.getSubgroupIDByName((String) comboGroup.getSelectedItem());
	}
}
