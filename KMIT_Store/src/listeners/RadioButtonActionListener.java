package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.TableModel;

import main.Main;
import objects.Goods;
import objects.SoldGoods;
import table_models.GoodsTableModel;
import table_models.SoldGoodsTableModel;

public class RadioButtonActionListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e) {
		
		//показати усі товари 
		if (Main.mf.rb1_1.isSelected()) {
			Main.mf.goodsTable.setModel(Main.mf.goodsModel);
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//показати лише товари обраної групи
		if (Main.mf.rb1_2.isSelected() | e.getSource() == Main.mf.comboGroup) {
			Main.mf.rb1_2.setSelected(true);
			int index = Main.mf.comboGroup.getSelectedIndex();
			int groupID = Main.mf.groups.get(index).getGroupID();
						
			ArrayList<Goods> tempGoods = new ArrayList<Goods>();
			for (int i = 0; i < Main.mf.goods.size(); i++){
				
				if (Main.mf.goods.get(i).getGroupID() == groupID)
					tempGoods.add(Main.mf.goods.get(i));
			}
			GoodsTableModel tempGoodsModel = new GoodsTableModel(tempGoods); //створюємо нову модель таблиці на основі нової колекції
			Main.mf.goodsTable.setModel(tempGoodsModel); //змінюємо у нашої таблиці модель
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//показати лише товари обраної підгрупи
		if (Main.mf.rb1_3.isSelected() | e.getSource() == Main.mf.comboSubgroup) {
			Main.mf.rb1_3.setSelected(true);
			int index = Main.mf.comboSubgroup.getSelectedIndex();
			int subgroupID = Main.mf.subgroups.get(index).getSubgroupID();
						
			ArrayList<Goods> tempGoods = new ArrayList<Goods>();
			for (int i = 0; i < Main.mf.goods.size(); i++){
				
				if (Main.mf.goods.get(i).getSubgroupID() == subgroupID)
					tempGoods.add(Main.mf.goods.get(i));
			}
			GoodsTableModel tempGoodsModel = new GoodsTableModel(tempGoods); //створюємо нову модель таблиці на основі нової колекції
			Main.mf.goodsTable.setModel(tempGoodsModel); //змінюємо у нашої таблиці модель
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//показати усі продані товари
		if (Main.mf.rb2_1.isSelected()) {
			Main.mf.soldGoodsTable.setModel(Main.mf.soldGoodsModel);
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
		
		//показати лише продані товари за обраний місяць
		if (Main.mf.rb2_2.isSelected()) {
			
			int current_month = (int) Main.mf.spinner.getValue(); //отримуємо значення місяця, записи за який потрібно показати
				        			
			ArrayList<SoldGoods> sold_month = new ArrayList<SoldGoods>(); //створюємо нову колекцію для проданого товару
				        			
			for (int i = 0; i < Main.mf.soldGoods.size(); i++){ //наповнюємо нову колекцію
				SoldGoods tempSoldGoods = Main.mf.soldGoods.get(i); //беремо кожен окремий запис
				String date = tempSoldGoods.getDate(); //отримуємо з нього дату
				String month = date.substring(4, 5); //отримуємо з дати місяць
				
				if (Integer.parseInt(month) == current_month){ //якщо у цьому записі місяць відповідає обраному
					sold_month.add(tempSoldGoods);					//тоді додаємо цей запис до нової колекції
				}
			}
			
			TableModel soldMonthModel = new SoldGoodsTableModel(sold_month); //створюємо нову модель таблиці на основі нової колекції
			Main.mf.soldGoodsTable.setModel(soldMonthModel); //змінюємо у нашої таблиці модель
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
		
		//показати лише продані товари за обраний проміжок часу
		if (Main.mf.rb2_3.isSelected()) {
			String current_date_from = Main.mf.jtf_date_1.getText(); //отримуємо значення дати, з якої показуються записи
			String current_date_to = Main.mf.jtf_date_2.getText();	//отримуємо значення дати, по яку показуються записи
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //створюємо формат дати
			Date dateFrom = new Date();
			Date dateTo = new Date(); //оголошуємо змінні типу Date
			Date tempDate = new Date();
			
			try {
				dateFrom = format.parse(current_date_from); //перетворюємо дату з формату String у формат Date
				dateTo = format.parse(current_date_to);
			} catch (ParseException e2) {e2.printStackTrace();
			}
			        			
			ArrayList<SoldGoods> sold_period = new ArrayList<SoldGoods>(); //створюємо нову колекцію для проданої дошки
				        			
			for (int i = 0; i < Main.mf.soldGoods.size(); i++){ //наповнюємо нову колекцію
				SoldGoods tempSoldGoods = Main.mf.soldGoods.get(i); //беремо кожен окремий запис
				String date = tempSoldGoods.getDate(); //отримуємо з нього дату
				
				try {
					tempDate = format.parse(date);	//перетворюємо дату з формату String у формат Date
				} catch (ParseException e1) {e1.printStackTrace();
				}
				
				if ((tempDate.after(dateFrom) | tempDate.equals(dateFrom)) & (tempDate.before(dateTo) | tempDate.equals(dateTo))){	//порівнюємо дати	
					sold_period.add(tempSoldGoods);
				} 
			}
			
			TableModel tempSoldGoodsModel = new SoldGoodsTableModel(sold_period); //створюємо нову модель таблиці на основі нової колекції
			Main.mf.soldGoodsTable.setModel(tempSoldGoodsModel); //змінюємо у нашої таблиці модель
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
	}
	
}
