package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import main.Main;
import main.NetLib;
import dialog_frames.WinAvailable;
import dialog_frames.WinAvailableEdit;
import dialog_frames.WinGroup;
import dialog_frames.WinGroupEdit;
import dialog_frames.WinGroupRemove;
import dialog_frames.WinSearch;
import dialog_frames.WinSold;
import dialog_frames.WinStatAvailable;
import dialog_frames.WinStatSold;
import dialog_frames.WinSubgroup;
import dialog_frames.WinSubgroupEdit;
import dialog_frames.WinSubgroupRemove;
import objects.Goods;
import objects.Group;
import objects.SoldGoods;
import objects.Subgroup;
import table_models.GoodsTableModel;
import table_models.SoldGoodsTableModel;

public class ButtonHandler implements ActionListener{

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
			
		//обробка кнопок вкладки "Наявні товари" -----------------------------------------------------------------------------------
		if (e.getSource() == Main.mf.jb_group_add){
			WinGroup dialog = new WinGroup(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				try {
					dialog.setResult();
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				Main.mf.refreshComboBoxes();
			}
		}
		
		if (e.getSource() == Main.mf.jb_group_edit){
			WinGroupEdit dialog = new WinGroupEdit(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				try {
					dialog.setResult();
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				Main.mf.refreshComboBoxes();
			}
		}
		
		if (e.getSource() == Main.mf.jb_group_remove){
			
			if (Main.rights == false){
				JOptionPane.showMessageDialog(null, "Для видалення необхідно володіти правами адміністратора");
				return;
			}
			
			WinGroupRemove dialog = new WinGroupRemove(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				
				int ID = dialog.getResult(0);
				
				if (Main.isServer)
					Main.mf.sql.removeItem(1, ID); //видаляємо необхідні записи із бази
				else
					try {
						Main.mf.nl.removeObjects(0, ID);
					} catch (ClassNotFoundException e1) {
					} catch (IOException e1) {
					}
				
				for (int i = 0; i < Main.mf.goods.size(); i++){
					if (Main.mf.goods.get(i).getGroupID() == ID){
						Main.mf.goods.remove(i);
						i--;
					}
				}
				
				for (int i = 0; i < Main.mf.subgroups.size(); i++){
					if (Main.mf.subgroups.get(i).getGroupID() == ID){
						Main.mf.subgroups.remove(i);
						i--;
					}
				}
				
				Main.mf.groups.remove(Main.mf.getGroupPositionByID(ID));
				Main.mf.refreshComboBoxes(); //оновлюємо комбобокси головного вікна
				Main.mf.goodsTable.updateUI(); //оновлюємо вигляд таблиці
			}
		}
		
		if (e.getSource() == Main.mf.jb_subgroup_add){
			WinSubgroup dialog = new WinSubgroup(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				Subgroup sgr = dialog.getResult(1); //параметр 1 нічого не значить
				Main.mf.subgroups.add(sgr);
				if (Main.isServer)
					Main.mf.sql.addSubgroup(sgr);
				else
					try {
						Main.mf.nl.addObjects(1, sgr.getSubgroupID());
					} catch (ClassNotFoundException e1) {
					} catch (IOException e1) { 
						System.out.println("Неможливо відправити підгрупу на сервер");
						JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
					}
				Main.mf.refreshComboBoxes(); //оновлюємо комбобокси головного вікна
			}
		}
		
		if (e.getSource() == Main.mf.jb_subgroup_edit){
			WinSubgroupEdit dialog = new WinSubgroupEdit(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				try {
					dialog.setResult();
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
				Main.mf.goodsTable.updateUI(); //оновлюємо вигляд таблиці
				Main.mf.refreshComboBoxes(); //оновлюємо комбобокси головного вікна
			}
		}
		
		if (e.getSource() == Main.mf.jb_subgroup_remove){
			
			if (Main.rights == false){
				JOptionPane.showMessageDialog(null, "Для видалення необхідно володіти правами адміністратора");
				return;
			}
			
			WinSubgroupRemove dialog = new WinSubgroupRemove(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			if (dialog.result == true){
				
				int ID = dialog.getResult(0);
				
				if (Main.isServer)
					Main.mf.sql.removeItem(2, ID); //видаляємо запис із бази
				else
					try {
						Main.mf.nl.removeObjects(1, ID);
					} catch (ClassNotFoundException e1) {
					} catch (IOException e1) {
					}
								
				for (int i = 0; i < Main.mf.goods.size(); i++){
					if (Main.mf.goods.get(i).getSubgroupID() == ID){
						Main.mf.goods.remove(i);
						i--;
					}
				}
				
				for (int i = 0; i < Main.mf.subgroups.size(); i++){
					if (Main.mf.subgroups.get(i).getSubgroupID() == ID){
						Main.mf.subgroups.remove(i);
						i--;
					}
				}
				
				Main.mf.refreshComboBoxes(); //оновлюємо комбобокси головного вікна
				Main.mf.goodsTable.updateUI(); //оновлюємо вигляд таблиці
			}
		}
		
		if (e.getSource() == Main.mf.jb_available_add){			//якщо натиснули кнопку "Додати"
			WinAvailable dialog = new WinAvailable(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}

		if (e.getSource() == Main.mf.jb_available_edit){		//якщо натиснули кнопку "Редагувати"
			Goods product;
			int rowIndex = Main.mf.goodsTable.getSelectedRow();
			int goodsID;
			int goodsPosition;
			
			try{
				goodsID = (int) Main.mf.goodsTable.getValueAt(rowIndex, 0);//отримали ID обраного товару
			}catch (Exception ArrayIndexOutOfBoundsException) {
				JOptionPane.showMessageDialog(null, "Виберіть запис для редагування");
				return;
			}
			
			goodsPosition = Main.mf.getObjectPositionByID(goodsID, Main.mf.goods);
			product = Main.mf.goods.get(goodsPosition);
			WinAvailableEdit dialog = new WinAvailableEdit(Main.mf, product);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}

		if (e.getSource()==Main.mf.jb_available_rem){			//якщо натиснули кнопку "Видалити"
			
			if (Main.rights == false){
				JOptionPane.showMessageDialog(null, "Для видалення необхідно володіти правами адміністратора");
				return;
			}
			
			int rowIndex = Main.mf.goodsTable.getSelectedRow(); //визначаємо індекс виділеного рядка в таблиці
			int numberOfRows = Main.mf.goodsTable.getSelectedRowCount(); //визначаємо кількість виділених рядків
			
			if (numberOfRows == 0){ //якщо нічого не обрано
				JOptionPane.showMessageDialog(null, "Виберіть запис для видалення");
				return;
			}
			
			//якщо вибрано якусь кількість рядків - запускаємо вікно-підтвердження
			int x = JOptionPane.showConfirmDialog(null, "Ви бажаєте видалити обрані записи?", "Видалення записів", JOptionPane.YES_NO_OPTION);
			switch (x){
				case 0: //при виборі кнопки YES
					
					int goodsID = (int) Main.mf.goodsTable.getValueAt(rowIndex, 0);
					int goodsPosition = Main.mf.getObjectPositionByID(goodsID, Main.mf.goods);
					
					if (numberOfRows == 1){ //якщо обрано один рядок
						if (Main.isServer)
							Main.mf.sql.removeItem(3, goodsID);
						else
							try {
								Main.mf.nl.removeObjects(2, goodsID);
							} catch (ClassNotFoundException e1) {
							} catch (IOException e1) {
							}
												
						Main.mf.goods.remove(goodsPosition); 
					}
					else if (numberOfRows > 1){ //якщо обрано декілька рядків
						for(int i = 0; i < numberOfRows; i++){
							
							goodsID = Main.mf.goods.get(goodsPosition).getID();
							
							if (Main.isServer)
								Main.mf.sql.removeItem(3, goodsID);
							else
								try {
									Main.mf.nl.removeObjects(2, goodsID);
								} catch (ClassNotFoundException e1) {
								} catch (IOException e1) {
								}
							
							Main.mf.goods.remove(goodsPosition);	
						}
					}
					Main.mf.goodsTable.clearSelection(); 	//прибираємо виділення рядків
					Main.mf.goodsTable.updateUI(); 			//оновлюємо вигляд таблиці

				case 1: //при виборі кнопки NO
					break;
			}
		}
		
		if (e.getSource() == Main.mf.jb_search){
			WinSearch dialog = new WinSearch(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		
		if (e.getSource() == Main.mf.jb_statistics){
			WinStatAvailable dialog = new WinStatAvailable(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		
		if (e.getSource() == Main.mf.jb_set_connection){
			
			if (Main.mf.isConnected) {
				JOptionPane.showMessageDialog(null, "З'єднання вже встановлене");
				return;
			}
			
			Main.mf.nl = new NetLib();
			try {
				Main.mf.nl.setConnection();
				Main.mf.isConnected = true;
				Main.mf.connection.setIcon(Main.mf.connectionTrueIcon);
				Main.mf.connection.setToolTipText("З'єднання з сервером встановлене");
				
				int count = Main.mf.nl.getClientCount();
				int maxCount = Main.mf.nl.getClientMaxCount();
				
				if (count > maxCount){
					JOptionPane.showMessageDialog(null, "Неможливо підключитись до сервера: максимально допустиму "
							+ "кількість підключень вже встановлено");
					Main.mf.nl.closeConnection();
					return;
				}
				
				Main.mf.labelTitle.setText("KMIT Склад [Client " + count + "]");
				
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Неможливо встановити з'єднання з сервером");
			}
		}
		
		if (e.getSource() == Main.mf.jb_load_data){
			if (Main.mf.isConnected){
				try {
					Main.mf.groups = (ArrayList<Group>) Main.mf.nl.getObjects(0);
					Main.mf.subgroups = (ArrayList<Subgroup>) Main.mf.nl.getObjects(1);
					Main.mf.goods = (ArrayList<Goods>) Main.mf.nl.getObjects(2);
					Main.mf.soldGoods = (ArrayList<SoldGoods>) Main.mf.nl.getObjects(3);
				} catch (ClassNotFoundException | IOException | InterruptedException e1) {
					JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером");
					Main.mf.isConnected = false;
					Main.mf.connection.setIcon(Main.mf.connectionFalseIcon);
					Main.mf.connection.setToolTipText("З'єднання відсутнє");
				}
				
				Main.mf.goodsModel = new GoodsTableModel(Main.mf.goods);
				Main.mf.soldGoodsModel = new SoldGoodsTableModel(Main.mf.soldGoods);
				
				Main.mf.goodsTable.updateUI(); 		//оновлюємо вигляд таблиці -- не працює (тому треба "doClick")
				Main.mf.refreshComboBoxes(); 		//оновлюємо комбобокси
				Main.mf.rb1_1.doClick();			//"натискаємо" радіокнопку "показати усі товари"
				Main.mf.rb2_1.doClick();
								
			}else JOptionPane.showMessageDialog(null, "Під'єднайтесь до сервера спочатку");
		}
		
		//обробка кнопок вкладки "Продані товари" -----------------------------------------------------------------------------------
		if (e.getSource() == Main.mf.jb_sold_add){			//якщо натиснули кнопку "Додати"
			WinSold dialog = new WinSold(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		
		if (e.getSource() == Main.mf.jb_sold_rem){			//якщо натиснули кнопку "Видалити"
			
			if (Main.rights == false){
				JOptionPane.showMessageDialog(null, "Для видалення необхідно володіти правами адміністратора");
				return;
			}
			
			int rowIndex = Main.mf.soldGoodsTable.getSelectedRow(); 			//визначаємо індекс виділеного рядка в таблиці
			int numberOfRows = Main.mf.soldGoodsTable.getSelectedRowCount(); 	//визначаємо кількість виділених рядків
			
			if (numberOfRows == 0){ //якщо нічого не обрано
				JOptionPane.showMessageDialog(null, "Виберіть запис для видалення");
				return;
			}
			
			//якщо вибрано якусь кількість рядків - запускаємо вікно-підтвердження
			int x = JOptionPane.showConfirmDialog(null, "Ви бажаєте видалити обрані записи?", "Видалення записів", JOptionPane.YES_NO_OPTION);
			switch (x){
				case 0: //при виборі кнопки YES
					
					int soldGoodsID = (int) Main.mf.soldGoodsTable.getValueAt(rowIndex, 1);
					int goodsID = (int) Main.mf.soldGoodsTable.getValueAt(rowIndex, 3);
					int soldGoodsPosition = Main.mf.getObjectPositionByID(soldGoodsID, Main.mf.soldGoods);
										
					if (numberOfRows == 1){ //якщо обрано один рядок
						//збільшуємо кількість наявного товару
						try{
							Goods g = Main.mf.goods.get(Main.mf.getObjectPositionByID(goodsID, Main.mf.goods));
							double newQuantity = g.getQuantity() + Main.mf.soldGoods.get(soldGoodsPosition).getQuantity();
							g.setQuantity(newQuantity);
							
							if (Main.isServer)
								Main.mf.sql.updateGoods(g);
							else
								try {
									Main.mf.nl.changeObjects(2, g.getID());
								} catch (ClassNotFoundException e1) {
								} catch (IOException e1) {
								}
							
						} catch (Exception ex) {}
						
						if (Main.isServer)
							Main.mf.sql.removeItem(4, soldGoodsID);
						else
							try {
								Main.mf.nl.removeObjects(3, soldGoodsID);
							} catch (ClassNotFoundException e1) {
							} catch (IOException e1) {
							}
						
						Main.mf.soldGoods.remove(soldGoodsPosition);
					}
					else if (numberOfRows>1){ //якщо обрано декілька рядків
						for(int i = 0; i<numberOfRows; i++){
							//збільшуємо кількість наявного товару
							try{
								goodsID = Main.mf.soldGoods.get(soldGoodsPosition).getGoodsID();
								Goods g = Main.mf.goods.get(Main.mf.getObjectPositionByID(goodsID, Main.mf.goods));
								double newQuantity = g.getQuantity() + Main.mf.soldGoods.get(soldGoodsPosition).getQuantity();
								g.setQuantity(newQuantity);
								
								if (Main.isServer)
									Main.mf.sql.updateGoods(g);
								else
									try {
										Main.mf.nl.changeObjects(2, g.getID());
									} catch (ClassNotFoundException e1) {
									} catch (IOException e1) {
									}
								
							} catch (Exception ex) {}
							
							soldGoodsID = Main.mf.soldGoods.get(soldGoodsPosition).getID();
							if (Main.isServer)
								Main.mf.sql.removeItem(4, soldGoodsID);
							else
								try {
									Main.mf.nl.removeObjects(3, soldGoodsID);
								} catch (ClassNotFoundException e1) {
								} catch (IOException e1) {
								}
							
							Main.mf.soldGoods.remove(soldGoodsPosition);
						}
					}
					Main.mf.soldGoodsTable.clearSelection(); //прибираємо виділення рядків
					Main.mf.soldGoodsTable.updateUI(); //оновлюємо вигляд таблиці
					
				case 1: //при виборі кнопки NO
					break;
			}
		}
		
		if (e.getSource() == Main.mf.jb_sold_statistics){
			WinStatSold dialog = new WinStatSold(Main.mf);
			dialog.setVisible(true);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}
