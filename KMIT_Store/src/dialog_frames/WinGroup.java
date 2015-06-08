package dialog_frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.Main;
import objects.Group;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

public class WinGroup extends javax.swing.JDialog{

	public boolean result; // результат роботи вікна 
	//(true - натиснення кнопки OK, false - кнопки Скасувати)
	
	JButton ok, cancel;
	JLabel title, groupName;
	JTextArea jt_groupName;
	eHandler available_handler = new eHandler();
	
	final int START_HEIGHT_POINT = 63;
	final int START_WIDTH_POINT = 25;
	final int ELEMENT_HEIGHT = 14;
	
	public WinGroup(java.awt.Frame parent) {
		super(parent, true);
		setLocation(500, 200);
		setSize(300, 210);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Додати нову групу товарів");
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 12));
		title.setBounds(42, 11, 226, 28);
		getContentPane().add (title);
		
		groupName = new JLabel("Назва групи:");
		groupName.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(groupName);
		
		jt_groupName = new JTextArea();
		jt_groupName.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //встановлюємо сірий ободок
		jt_groupName.setWrapStyleWord(true);
		jt_groupName.setRows(3);
		jt_groupName.setLineWrap(true); //переносить текст на наступний рядок
		jt_groupName.setBounds(126, START_HEIGHT_POINT, 142, 49);
		getContentPane().add(jt_groupName);
		
		ok = new JButton ("OK");
		ok.setBounds(30, 140, 100, 23);
		getContentPane().add(ok);
		ok.addActionListener(available_handler);
		
		cancel = new JButton ("Скасувати");
		cancel.setBounds(160, 140, 100, 23);
		getContentPane().add(cancel);
		cancel.addActionListener(available_handler);
	}
    
	class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == ok){
				result = true; // користувач натиснув ОК
				dispose(); // прибрати вікно
			}
			
			if (e.getSource() == cancel){
				result = false; // користувач натиснув Скасувати
				dispose(); // прибрати вікно
			}
		}
	}
	
	public void setResult() throws ClassNotFoundException, IOException{
		Group gr = new Group(Group.getGeneralID(), jt_groupName.getText());
		
		//перевіряємо наявність у базі аналогічного найменування
		for (Group g : Main.mf.groups){
			if (gr.getGroupName().equals(g.getGroupName())){
				JOptionPane.showMessageDialog(null, "Група із таким найменуванням вже існує!");
				return;
			}
		}
		
		Main.mf.groups.add(gr);
		if (Main.isServer)
			Main.mf.sql.addGroup(gr);
		else
			try {
				Main.mf.nl.addObjects(0, gr.getGroupID());
			} catch (ClassNotFoundException e1) {
			} catch (IOException e1) { 
				System.out.println("Неможливо відправити групу на сервер");
				JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
			}
	}
	
	private final long serialVersionUID = 1L;
}
