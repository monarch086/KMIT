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
		
		//�������� �� ������ 
		if (Main.mf.rb1_1.isSelected()) {
			Main.mf.goodsTable.setModel(Main.mf.goodsModel);
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//�������� ���� ������ ������ �����
		if (Main.mf.rb1_2.isSelected() | e.getSource() == Main.mf.comboGroup) {
			Main.mf.rb1_2.setSelected(true);
			int index = Main.mf.comboGroup.getSelectedIndex();
			int groupID = Main.mf.groups.get(index).getGroupID();
						
			ArrayList<Goods> tempGoods = new ArrayList<Goods>();
			for (int i = 0; i < Main.mf.goods.size(); i++){
				
				if (Main.mf.goods.get(i).getGroupID() == groupID)
					tempGoods.add(Main.mf.goods.get(i));
			}
			GoodsTableModel tempGoodsModel = new GoodsTableModel(tempGoods); //��������� ���� ������ ������� �� ����� ���� ��������
			Main.mf.goodsTable.setModel(tempGoodsModel); //������� � ���� ������� ������
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//�������� ���� ������ ������ �������
		if (Main.mf.rb1_3.isSelected() | e.getSource() == Main.mf.comboSubgroup) {
			Main.mf.rb1_3.setSelected(true);
			int index = Main.mf.comboSubgroup.getSelectedIndex();
			int subgroupID = Main.mf.subgroups.get(index).getSubgroupID();
						
			ArrayList<Goods> tempGoods = new ArrayList<Goods>();
			for (int i = 0; i < Main.mf.goods.size(); i++){
				
				if (Main.mf.goods.get(i).getSubgroupID() == subgroupID)
					tempGoods.add(Main.mf.goods.get(i));
			}
			GoodsTableModel tempGoodsModel = new GoodsTableModel(tempGoods); //��������� ���� ������ ������� �� ����� ���� ��������
			Main.mf.goodsTable.setModel(tempGoodsModel); //������� � ���� ������� ������
			Main.mf.setColumnWidth(Main.mf.goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});
		}
		
		//�������� �� ������ ������
		if (Main.mf.rb2_1.isSelected()) {
			Main.mf.soldGoodsTable.setModel(Main.mf.soldGoodsModel);
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
		
		//�������� ���� ������ ������ �� ������� �����
		if (Main.mf.rb2_2.isSelected()) {
			
			int current_month = (int) Main.mf.spinner.getValue(); //�������� �������� �����, ������ �� ���� ������� ��������
				        			
			ArrayList<SoldGoods> sold_month = new ArrayList<SoldGoods>(); //��������� ���� �������� ��� ��������� ������
				        			
			for (int i = 0; i < Main.mf.soldGoods.size(); i++){ //���������� ���� ��������
				SoldGoods tempSoldGoods = Main.mf.soldGoods.get(i); //������ ����� ������� �����
				String date = tempSoldGoods.getDate(); //�������� � ����� ����
				String month = date.substring(4, 5); //�������� � ���� �����
				
				if (Integer.parseInt(month) == current_month){ //���� � ����� ����� ����� ������� ��������
					sold_month.add(tempSoldGoods);					//��� ������ ��� ����� �� ���� ��������
				}
			}
			
			TableModel soldMonthModel = new SoldGoodsTableModel(sold_month); //��������� ���� ������ ������� �� ����� ���� ��������
			Main.mf.soldGoodsTable.setModel(soldMonthModel); //������� � ���� ������� ������
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
		
		//�������� ���� ������ ������ �� ������� ������� ����
		if (Main.mf.rb2_3.isSelected()) {
			String current_date_from = Main.mf.jtf_date_1.getText(); //�������� �������� ����, � ��� ����������� ������
			String current_date_to = Main.mf.jtf_date_2.getText();	//�������� �������� ����, �� ��� ����������� ������
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); //��������� ������ ����
			Date dateFrom = new Date();
			Date dateTo = new Date(); //��������� ���� ���� Date
			Date tempDate = new Date();
			
			try {
				dateFrom = format.parse(current_date_from); //������������ ���� � ������� String � ������ Date
				dateTo = format.parse(current_date_to);
			} catch (ParseException e2) {e2.printStackTrace();
			}
			        			
			ArrayList<SoldGoods> sold_period = new ArrayList<SoldGoods>(); //��������� ���� �������� ��� ������� �����
				        			
			for (int i = 0; i < Main.mf.soldGoods.size(); i++){ //���������� ���� ��������
				SoldGoods tempSoldGoods = Main.mf.soldGoods.get(i); //������ ����� ������� �����
				String date = tempSoldGoods.getDate(); //�������� � ����� ����
				
				try {
					tempDate = format.parse(date);	//������������ ���� � ������� String � ������ Date
				} catch (ParseException e1) {e1.printStackTrace();
				}
				
				if ((tempDate.after(dateFrom) | tempDate.equals(dateFrom)) & (tempDate.before(dateTo) | tempDate.equals(dateTo))){	//��������� ����	
					sold_period.add(tempSoldGoods);
				} 
			}
			
			TableModel tempSoldGoodsModel = new SoldGoodsTableModel(sold_period); //��������� ���� ������ ������� �� ����� ���� ��������
			Main.mf.soldGoodsTable.setModel(tempSoldGoodsModel); //������� � ���� ������� ������
			Main.mf.setColumnWidth(Main.mf.soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
		}
	}
	
}
