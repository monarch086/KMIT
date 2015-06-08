package dialog_frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.Main;
import objects.Goods;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

public class WinAvailable extends javax.swing.JDialog{
	
	JButton ok, cancel;
	JLabel title, group, subgroup, name, description, producer, price, quantity, measureType;
	JComboBox comboGroup, comboSubgroup;
	JTextField jt_producer, jt_price, jt_quantity, jt_measureType;
	JTextArea jt_name, jt_description;
	eHandler available_handler = new eHandler();
	Goods product;
	
	final int START_HEIGHT_POINT = 63;
	final int START_WIDTH_POINT = 25;
	final int ELEMENT_HEIGHT = 14;
	final int TEXTFIELD_HEIGHT = 25;
	final int SPACE = 15;
	
	public WinAvailable(java.awt.Frame parent) {
		super(parent, true);
		setLocation(500, 200);
		setSize(300, 450);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Додати новий товар №"+Goods.getGeneralID());
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 12));
		title.setBounds(42, 11, 226, 30);
		getContentPane().add (title);
		
		group = new JLabel("Група*:");
		group.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(group);
		comboGroup = new JComboBox();
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setBounds(124, START_HEIGHT_POINT-3, 146, TEXTFIELD_HEIGHT);
		comboGroup.addActionListener(available_handler);
		getContentPane().add(comboGroup);
		
		subgroup = new JLabel("Підгрупа*:");
		subgroup.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT+SPACE, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(subgroup);
		comboSubgroup = new JComboBox();
		string = Main.mf.getSubgroupNamesByGroupID(comboGroup.getSelectedIndex()+1);
		comboSubgroup.setModel(new DefaultComboBoxModel(string));
		comboSubgroup.setBounds(124, START_HEIGHT_POINT+ELEMENT_HEIGHT+SPACE-3, 146, TEXTFIELD_HEIGHT);
		getContentPane().add(comboSubgroup);
		
		name = new JLabel("Найменування*:");
		name.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*2+SPACE*2, 
				100, ELEMENT_HEIGHT);
		getContentPane().add(name);
		jt_name = new JTextArea();
		jt_name.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //встановлюємо сірий ободок
		jt_name.setWrapStyleWord(true);
		jt_name.setRows(3);
		jt_name.setLineWrap(true); //переносить текст на наступний рядок
		jt_name.setBounds(126, START_HEIGHT_POINT+ELEMENT_HEIGHT*2+SPACE*2, 142, 49);
		getContentPane().add(jt_name);
		
		description = new JLabel("Опис");
		description.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*5+SPACE*3, 
				85, 14);
		getContentPane().add(description);
		jt_description = new JTextArea();
		jt_description.setBorder(BorderFactory.createLineBorder(Color.GRAY)); //встановлюємо сірий ободок
		jt_description.setWrapStyleWord(true);
		jt_description.setRows(3);
		jt_description.setLineWrap(true); //переносить текст на наступний рядок
		jt_description.setBounds(126, START_HEIGHT_POINT+ELEMENT_HEIGHT*5+SPACE*3, 142, 49);
		getContentPane().add(jt_description);
		
		producer = new JLabel("Виробник:");
		producer.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*8+SPACE*4, 
				85, 14);
		getContentPane().add(producer);
		jt_producer = new JTextField(20);
		jt_producer.setBounds(124, START_HEIGHT_POINT+ELEMENT_HEIGHT*8+SPACE*4-3, 
				146, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_producer);
		
		price = new JLabel("Ціна, грн");
		price.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*9+SPACE*5,
				60, 14);
		getContentPane().add(price);
		jt_price = new JTextField(20);
		jt_price.setBounds(124, START_HEIGHT_POINT+ELEMENT_HEIGHT*9+SPACE*5-3, 
				146, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_price);
		
		quantity = new JLabel("Кількість*:");
		quantity.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*10+SPACE*6, 
				90, 14);
		getContentPane().add(quantity);
		jt_quantity = new JTextField();
		jt_quantity.setBounds(124, START_HEIGHT_POINT+ELEMENT_HEIGHT*10+SPACE*6-3, 
				146, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_quantity);
		
		measureType = new JLabel("Одиниця виміру*:");
		measureType.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT+ELEMENT_HEIGHT*11+SPACE*7, 
				100, 14);
		getContentPane().add(measureType);
		jt_measureType = new JTextField();
		jt_measureType.setBounds(124, START_HEIGHT_POINT+ELEMENT_HEIGHT*11+SPACE*7-3, 
				146, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_measureType);
		
		ok = new JButton ("OK");
		ok.setBounds(30, 367, 100, 23);
		getContentPane().add(ok);
		ok.addActionListener(available_handler);
		
		cancel = new JButton ("Скасувати");
		cancel.setBounds(160, 367, 100, 23);
		getContentPane().add(cancel);
		cancel.addActionListener(available_handler);
	}
    
	public class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == ok){
				if (jt_name.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Введіть дані в поле \"Найменування\"");
					return;
				}
				if (jt_quantity.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Введіть дані в поле \"Кількість\"");
					return;
				}
				if (jt_measureType.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Введіть дані в поле \"Одиниця виміру\"");
					return;
				}
				else
					setResult();
					dispose(); // прибрати вікно
			}
			
			if (e.getSource() == cancel){
				dispose(); // прибрати вікно
			}
			
			if (e.getSource() == comboGroup){ //у разі вибору групи товарів змінити перелік підгруп товарів
				String[] string = Main.mf.getSubgroupNamesByGroupID(comboGroup.getSelectedIndex()+1);
				comboSubgroup.setModel(new DefaultComboBoxModel(string));
			}
		}
	}
	
	//метод замінює введені коми на точки (потрібно для чисел double)
	String setCommas(String str){
		if (str.contains(","))
			str = str.replace (",", ".");
		return str;
	}
	
	void setResult(){
		//перевіряємо наявність у базі аналогічного найменування
		String temp_str = jt_name.getText();
		for (int i = 0; i < Main.mf.goods.size(); i++){
			Goods goods = Main.mf.goods.get(i);
			String str2 = goods.getName();
			if (temp_str.equals(str2)){
				JOptionPane.showMessageDialog(null, "Запис із таким найменуванням вже існує!");
				Main.mf.goodsTable.setRowSelectionInterval(i, i); //виділяємо рядок з аналогічним найменуванням
				return;
			}
		}
		
		//якщо аналогічного найменування не знайдено - готуємо об'єкт - новий товар
		int subgroupID = Main.mf.getSubgroupIDByName((String) comboSubgroup.getSelectedItem());
		
		//перевіряємо, чи не введено у числі випадково кому замість крапки
		String quantity = setCommas(jt_quantity.getText());
		String price = setCommas(jt_price.getText());
		if (price.equals("")) price = "0";
		
		product = new Goods(Goods.getGeneralID(), subgroupID, jt_name.getText(), jt_description.getText(), jt_producer.getText(), 
				Double.parseDouble(price), Double.parseDouble(quantity), jt_measureType.getText());
		
		//додаємо запис
		Main.mf.goods.add(product);
		if (Main.isServer)
			Main.mf.sql.addGoods(product);
		else
			try {
				Main.mf.nl.addObjects(2, product.getID());
			} catch (ClassNotFoundException e1) {
			} catch (IOException e1) { 
				System.out.println("Неможливо відправити товар на сервер");
				JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
			}
		Main.mf.goodsTable.updateUI(); //оновлюємо вигляд таблиці
	}
}
