package dialog_frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import main.Main;
import objects.Goods;
import objects.StatRecord;

public class WinStatSold extends javax.swing.JDialog{
	
	JLabel title, period, results;
	JRadioButton allRB, groupRB, subgroupRB, goodsRB;
	JComboBox groupCB, subgroupCB, goodsCB;
	JTextField period1JT, period2JT;
	JCheckBox check, checkGoods;
	JButton show, ok;
	
	JScrollPane resultsJSP;
	JList<Object> resultsList;
	
	eHandler handler = new eHandler();
	
	final int START_HEIGHT_POINT = 63;
	final int START_WIDTH_POINT = 85;
	final int ELEMENT_HEIGHT = 20;
	final int TEXTFIELD_HEIGHT = 25;
	final int SPACE = 15;
	
	public WinStatSold(java.awt.Frame parent) {
		super(parent, true);
		
		setLocation(500, 200);
		setSize(450, 590);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Статистика проданих товарів");
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 12));
		title.setSize(200, 30);
		title.setLocation(getWidth()/2 - title.getWidth()/2, 11);
		getContentPane().add (title);
		
		allRB = new JRadioButton("Усі товари");
		allRB.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(allRB);
		
		groupRB = new JRadioButton("Група:");
		groupRB.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE, 85, ELEMENT_HEIGHT);
		getContentPane().add(groupRB);
		
		groupCB = new JComboBox();
		String[] string = Main.mf.getGroupNames();
		groupCB.setModel(new DefaultComboBoxModel(string));
		groupCB.setBounds(184, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE - 3, 176, TEXTFIELD_HEIGHT);
		groupCB.addActionListener(handler);
		getContentPane().add(groupCB);
		
		subgroupRB = new JRadioButton("Підгрупа:");
		subgroupRB.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*2 + SPACE*2, 85, ELEMENT_HEIGHT);
		getContentPane().add(subgroupRB);
		
		subgroupCB = new JComboBox();
		string = Main.mf.getSubgroupNames();
		subgroupCB.setModel(new DefaultComboBoxModel(string));
		subgroupCB.setBounds(184, START_HEIGHT_POINT + ELEMENT_HEIGHT*2 + SPACE*2 - 3, 176, TEXTFIELD_HEIGHT);
		subgroupCB.addActionListener(handler);
		getContentPane().add(subgroupCB);
		
		goodsRB = new JRadioButton("Товари:");
		goodsRB.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*3 + SPACE*3, 85, ELEMENT_HEIGHT);
		getContentPane().add(goodsRB);
		
		goodsCB = new JComboBox();
		string = Main.mf.getGoodsNames();
		goodsCB.setModel(new DefaultComboBoxModel(string));
		goodsCB.setBounds(184, START_HEIGHT_POINT + ELEMENT_HEIGHT*3 + SPACE*3 - 3, 176, TEXTFIELD_HEIGHT);
		goodsCB.addActionListener(handler);
		getContentPane().add(goodsCB);
		
		ButtonGroup bg1 = new ButtonGroup(); // створюємо группу взаємного виключення
		bg1.add(allRB);
		bg1.add(groupRB);
		bg1.add(subgroupRB);
		bg1.add(goodsRB);
		
		check = new JCheckBox();
		check.setBounds(START_WIDTH_POINT, 210, 80, 20);
		check.setText("Період: з");
		getContentPane().add(check);
		check.addActionListener(handler);
		
		period1JT = new JTextField();
		period1JT.setBounds(START_WIDTH_POINT + 90, 210, 80, TEXTFIELD_HEIGHT);
		getContentPane().add(period1JT);
		period1JT.setEnabled(false);
		
		period = new JLabel("по:");
		period.setBounds(START_WIDTH_POINT + 180, 210, 20, ELEMENT_HEIGHT);
		getContentPane().add(period);
		
		period2JT = new JTextField();
		period2JT.setBounds(START_WIDTH_POINT + 205, 210, 80, TEXTFIELD_HEIGHT);
		getContentPane().add(period2JT);
		period2JT.setEnabled(false);
		
		//визначаємо поточну дату
		Date now = new Date();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String date = formatter.format(now);
		period2JT.setText(date);
		period1JT.setText(date.replaceFirst("\\d\\d", "01"));
		
		checkGoods = new JCheckBox();
		checkGoods.setBounds(START_WIDTH_POINT, 255, 250, 20);
		checkGoods.setText("Показати окремо по кожному товару");
		getContentPane().add(checkGoods);
		checkGoods.addActionListener(handler);
		
		show = new JButton ("Показати");
		show.setSize(100, 23);
		show.setLocation(getWidth()/2 - show.getWidth()/2, 300);
		getContentPane().add(show);
		show.addActionListener(handler);
		
		results = new JLabel("Продано товарів:");
		results.setBounds(START_WIDTH_POINT, 340, 110, ELEMENT_HEIGHT);
		getContentPane().add(results);
				
		resultsList = new JList<Object>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		resultsList.setVisibleRowCount(6);
		resultsJSP = new JScrollPane(resultsList);
		resultsJSP.setSize(300, 125);
		resultsJSP.setLocation(getWidth()/2 - resultsJSP.getWidth()/2, 370);
		getContentPane().add(resultsJSP);
		
		ok = new JButton ("ОК");
		ok.setSize(100, 23);
		ok.setLocation(getWidth() - START_WIDTH_POINT - ok.getWidth(), 517);
		getContentPane().add(ok);
		ok.addActionListener(handler);
	}
	
	public class eHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == show){
				
				String from = "";
				String to = "";
				
				if (check.isSelected()){
					from = period1JT.getText();
					to = period2JT.getText();
				}
				
				ArrayList statCollection;
				
				if (allRB.isSelected()){
										
					try {
						if (checkGoods.isSelected()) 
							statCollection = Main.mf.getStatisticsSoldByGoods(1, null, from, to);
						else
							statCollection = Main.mf.getStatisticsSold(1, null, from, to);
						Object[] statArray = statCollection.toArray();
						resultsList.setListData(statArray);
					} catch (ParseException e1) {
						System.out.println("Помилка обробки дати проданого товару");
					}
					
				}
				
				else if (groupRB.isSelected()){
					
					try {
						if (checkGoods.isSelected())
							statCollection = Main.mf.getStatisticsSoldByGoods(2, (String)groupCB.getSelectedItem(), from, to);
						else
							statCollection = Main.mf.getStatisticsSold(2, (String)groupCB.getSelectedItem(), from, to);
						Object[] statArray = statCollection.toArray();
						resultsList.setListData(statArray);
					} catch (ParseException e1) {
						System.out.println("Помилка обробки дати проданого товару");
					}
				}
				
				else if (subgroupRB.isSelected()){
					
					try {
						if (checkGoods.isSelected())
							statCollection = Main.mf.getStatisticsSoldByGoods(3, (String)subgroupCB.getSelectedItem(), from, to);
						else
							statCollection = Main.mf.getStatisticsSold(3, (String)subgroupCB.getSelectedItem(), from, to);
						Object[] statArray = statCollection.toArray();
						resultsList.setListData(statArray);
					} catch (ParseException e1) {
						System.out.println("Помилка обробки дати проданого товару");
					}
				}
				
				else if (goodsRB.isSelected()){
					
					try {
						if (checkGoods.isSelected())
							statCollection = Main.mf.getStatisticsSoldByGoods(4, (String)goodsCB.getSelectedItem(), from, to);
						else
							statCollection = Main.mf.getStatisticsSold(4, (String)goodsCB.getSelectedItem(), from, to);
						Object[] statArray = statCollection.toArray();
						resultsList.setListData(statArray);
					} catch (ParseException e1) {
						System.out.println("Помилка обробки дати проданого товару");
					}
				}
				
				else return;
			}

			if (e.getSource() == ok){
					dispose();
			}
			
			if (e.getSource() == groupCB){
				groupRB.setSelected(true);
			}
			
			if (e.getSource() == subgroupCB){
				subgroupRB.setSelected(true);
			}
			
			if (e.getSource() == goodsCB){
				goodsRB.setSelected(true);
			}
			
			if (e.getSource() == check){
				
				if (check.isSelected()){
					period1JT.setEnabled(true);
					period2JT.setEnabled(true);
				}
				
				else{
					period1JT.setEnabled(false);
					period2JT.setEnabled(false);
				}
			}
			
		}
		
	}
	
	
}
