package dialog_frames;

import java.awt.Frame;
import java.io.IOException;

import javax.swing.SwingConstants;

import main.Main;
import objects.Goods;

public class WinAvailableEdit extends WinAvailable{

	public WinAvailableEdit(Frame parent, Goods product) {
		super(parent);
		this.product = product;
		
		title.setText("<html>Редагувати інформацію<br> про товар №" + product.getID() + "</html>");
				
		comboGroup.setSelectedIndex(Main.mf.getGroupPositionByID(product.getGroupID()));
		
		String[] string = Main.mf.getSubgroupNamesByGroupID(product.getGroupID());
		for (int i = 0; i < string.length; i++){
			if (string[i].equals(product.getSubgroupName())) comboSubgroup.setSelectedIndex(i);
		}
		
		jt_name.setText(product.getName());
		jt_description.setText(product.getDescription());
		jt_producer.setText(product.getProducer());
		jt_price.setText(String.valueOf(product.getPrice()));
		jt_quantity.setText(String.valueOf(product.getQuantity()));
		jt_measureType.setText(product.getMeasureType());
	}
	
	void setResult(){
		
		int subgroupID = Main.mf.getSubgroupIDByName((String) comboSubgroup.getSelectedItem());
		
		
		
		//перевіряємо, чи не введено у числі випадково кому замість крапки
		String quantity = setCommas(jt_quantity.getText());
		String price = setCommas(jt_price.getText());
		if (price.equals("")) price = "0";
		
		product.setSubgroupID(subgroupID);
		product.setName(jt_name.getText());
		product.setDescription(jt_description.getText());
		product.setProducer(jt_producer.getText());
		product.setPrice(Double.parseDouble(price));
		product.setQuantity(Double.parseDouble(quantity));
		product.setMeasureType(jt_measureType.getText());
		
		if (Main.isServer)
			Main.mf.sql.updateGoods(product);
		else
			try {
				Main.mf.nl.changeObjects(2, product.getID());
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
			}
		Main.mf.goods.set(Main.mf.getObjectPositionByID(product.getID(), Main.mf.goods), product);
		Main.mf.goodsTable.updateUI(); //оновлюємо вигляд таблиці
	}
}
