package dialog_frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.Main;
import dialog_frames.WinAvailable.eHandler;
import objects.AllObjects;
import objects.Goods;
import objects.SoldGoods;

public class WinSold extends javax.swing.JDialog{
	
	JButton ok, cancel;
	JLabel title, date, group, subgroup, goods, price, quantity, sum, info;
	JComboBox comboGroup, comboSubgroup, comboGoods;
	JTextField jt_date, jt_price, jt_quantity, jt_sum;
	eHandler available_handler = new eHandler();
	
	final int START_HEIGHT_POINT = 63;
	final int START_WIDTH_POINT = 25;
	final int ELEMENT_HEIGHT = 14;
	final int TEXTFIELD_HEIGHT = 25;
	final int SPACE = 15;
	
	public WinSold(java.awt.Frame parent) {
		super(parent, true);
		
		setLocation(500, 200);
		setSize(300, 450);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("<html>Додати новий запис<br>про проданий товар</html>");
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 12));
		title.setSize(150, 30);
		title.setLocation(getWidth()/2 - title.getWidth()/2, 11);
		getContentPane().add (title);
		
		date = new JLabel("Дата*:");
		date.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(date);
		
		jt_date = new JTextField();
		jt_date.setBounds(126, START_HEIGHT_POINT, 144, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_date);
		
		//визначаємо поточну дату
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String date = formatter.format(now);
		jt_date.setText(date); //заносимо поточну дату у поле "Дата"
		
		group = new JLabel("Група*:");
		group.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE*2, 85, ELEMENT_HEIGHT);
		getContentPane().add(group);
		comboGroup = new JComboBox();
		String[] string = Main.mf.getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		comboGroup.setBounds(124, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE*2 - 3, 146, TEXTFIELD_HEIGHT);
		comboGroup.addActionListener(available_handler);
		getContentPane().add(comboGroup);
		
		subgroup = new JLabel("Підгрупа*:");
		subgroup.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*2 +SPACE*3, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(subgroup);
		comboSubgroup = new JComboBox();
		string = Main.mf.getSubgroupNamesByGroupID(comboGroup.getSelectedIndex()+1);
		comboSubgroup.setModel(new DefaultComboBoxModel(string));
		comboSubgroup.setBounds(124, START_HEIGHT_POINT + ELEMENT_HEIGHT*2 + SPACE*3 - 3, 146, TEXTFIELD_HEIGHT);
		comboSubgroup.addActionListener(available_handler);
		getContentPane().add(comboSubgroup);
		
		goods = new JLabel("Товар*:");
		goods.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*3 + SPACE*4, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(goods);
		
		comboGoods = new JComboBox();
		string = Main.mf.getGoodsNamesOfSubgroup(Main.mf.getSubgroupIDByName((String)comboSubgroup.getSelectedItem()));
		comboGoods.setModel(new DefaultComboBoxModel(string));
		comboGoods.setBounds(124, START_HEIGHT_POINT + ELEMENT_HEIGHT*3 + SPACE*4 - 3, 146, TEXTFIELD_HEIGHT);
		comboGoods.addActionListener(available_handler);
		getContentPane().add(comboGoods);
		
		price = new JLabel("Ціна:");
		price.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*4 + SPACE*6, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(price);
		
		jt_price = new JTextField();
		jt_price.setBounds(126, START_HEIGHT_POINT + ELEMENT_HEIGHT*4 + SPACE*6, 144, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_price);
		jt_price.setEditable(false);
		setPrice();
		
		quantity = new JLabel("Кількість*:");
		quantity.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*5 + SPACE*7, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(quantity);
		
		jt_quantity = new JTextField();
		jt_quantity.setBounds(126, START_HEIGHT_POINT + ELEMENT_HEIGHT*5 + SPACE*7, 144, TEXTFIELD_HEIGHT);
		jt_quantity.addActionListener(available_handler);
		getContentPane().add(jt_quantity);
		
		sum = new JLabel("Сума:");
		sum.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*6 + SPACE*8, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(sum);
				
		jt_sum = new JTextField();
		jt_sum.setBounds(126, START_HEIGHT_POINT + ELEMENT_HEIGHT*6 + SPACE*8, 144, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_sum);
		jt_sum.setEditable(false);
		
		info = new JLabel("* - поля, обов'язкові для заповнення");
		info.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*7 + SPACE*10, 
				210, ELEMENT_HEIGHT);
		getContentPane().add(info);
		
		ok = new JButton ("OK");
		ok.setBounds(30, 367, 100, 23);
		getContentPane().add(ok);
		ok.addActionListener(available_handler);
		
		cancel = new JButton ("Скасувати");
		cancel.setBounds(160, 367, 100, 23);
		getContentPane().add(cancel);
		cancel.addActionListener(available_handler);
	}
	
	public WinSold(java.awt.Frame parent, Goods goods) {
		this(parent);
		
		comboGroup.setSelectedItem(goods.getGroupName());
		comboSubgroup.setSelectedItem(goods.getSubgroupName());
		comboGoods.setSelectedItem(goods.getName());
	}
	
	void setPrice(){
		int goodsID = Main.mf.getGoodsIDByName((String)comboGoods.getSelectedItem());
		int goodsPosition = Main.mf.getObjectPositionByID(goodsID, Main.mf.goods);
		jt_price.setText(String.valueOf(Main.mf.goods.get(goodsPosition).getPrice()));
	}
	
	void setSum(){
		double price = Double.parseDouble(jt_price.getText());
		if (jt_quantity.getText().equals("")) return;
		String sQuantity = setCommas(jt_quantity.getText());
		double quantity = Double.parseDouble(sQuantity);
		
		jt_sum.setText(String.valueOf(price*quantity));
	}
	
	void updateComboGoods(){
		String[] string = Main.mf.getGoodsNamesOfSubgroup(Main.mf.getSubgroupIDByName((String)comboSubgroup.getSelectedItem()));
		comboGoods.setModel(new DefaultComboBoxModel(string));
	}
	
	public class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == ok){
				if (jt_date.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Введіть дані в поле \"Дата\"");
					return;
				}
				if (jt_quantity.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Введіть дані в поле \"Кількість\"");
					return;
				}
				
				int goodsID = Main.mf.getGoodsIDByName((String)comboGoods.getSelectedItem());
				double availableQuantity = Main.mf.goods.get(Main.mf.getObjectPositionByID(goodsID, Main.mf.goods)).getQuantity();
				
				if (Double.parseDouble(jt_quantity.getText()) > availableQuantity) {
					JOptionPane.showMessageDialog(null, "Введена кількість товарів перевищує наявну!");
					return;
				}
				else
					setResult();
					dispose(); // прибрати вікно
			}
			
			if (e.getSource() == cancel){
				dispose(); // прибрати вікно
			}
			
			if (e.getSource() == comboGroup){ //у разі вибору групи товарів
				String[] string = Main.mf.getSubgroupNamesByGroupID(comboGroup.getSelectedIndex()+1);
				comboSubgroup.setModel(new DefaultComboBoxModel(string));
				
				updateComboGoods();
				setPrice();
				setSum();
			}
			
			if (e.getSource() == comboSubgroup){ //у разі вибору підгрупи товарів
				updateComboGoods();
				setPrice();
				setSum();
			}
			
			if (e.getSource() == comboGoods){ //у разі вибору товару
				setPrice();
				setSum();
			}
			
			if (e.getSource() == jt_quantity){ //у разі вибору товару
				setSum();
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
		int goodsID = Main.mf.getGoodsIDByName((String)comboGoods.getSelectedItem());
		
		//перевіряємо, чи не введено у числі випадково кому замість крапки
		String quantity = setCommas(jt_quantity.getText());
		
		SoldGoods sGoods = new SoldGoods(SoldGoods.getGeneralID(), jt_date.getText(), goodsID,
				Double.parseDouble(jt_price.getText()), Double.parseDouble(quantity));
				
		Main.mf.soldGoods.add(sGoods); //додаємо його до колекції
		if (Main.isServer)
			Main.mf.sql.addSoldGoods(sGoods); //та до бази даних
		else
			try {
				Main.mf.nl.addObjects(3, sGoods.getID());
			} catch (ClassNotFoundException e1) {
			} catch (IOException e1) { System.out.println("Неможливо відправити групу на сервер");
			}
		
		//зменшуємо кількість наявного товару
		Goods g = Main.mf.goods.get(Main.mf.getObjectPositionByID(goodsID, Main.mf.goods));
		double newQuantity = g.getQuantity() - Double.parseDouble(quantity);
		g.setQuantity(newQuantity);
		Main.mf.sql.updateGoods(g);
		
		//виділяємо в таблиці доданий рядок
		int a = Main.mf.soldGoods.size(); //визначаємо індекс доданого рядка - для цього беремо розмір колекції sold,
		a--;				//зменшуємо його на 1
		Main.mf.soldGoodsTable.setRowSelectionInterval(a, a); //і виділяємо доданий рядок
		Main.mf.jsp2.getVerticalScrollBar().setValue(Main.mf.jsp2.getVerticalScrollBar().getMaximum()); //опускаємо скрол-бар до низу
		Main.mf.soldGoodsTable.updateUI(); //оновлюємо вигляд таблиці		
	}
}
