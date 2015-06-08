package main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import objects.Goods;
import objects.Group;
import objects.SoldGoods;
import objects.Subgroup;

public class ServerThread extends Thread{
	
	private Socket socket;
	
	private BufferedReader textIn;
	private PrintWriter textOut;
	
	private BufferedReader textIn2;
	private PrintWriter textOut2;
	
	private ObjectOutputStream OOS;
	private ObjectInputStream OIS;
	
	private ObjectOutputStream OOS2;
	private ObjectInputStream OIS2;
	
	public ServerThread(Socket s, BufferedReader textIn, PrintWriter textOut, 
			BufferedReader textIn2, PrintWriter textOut2, ObjectOutputStream OOS, 
			ObjectInputStream OIS, ObjectOutputStream OOS2, ObjectInputStream OIS2) 
					throws IOException{
		
		socket = s;
		
		this.textIn = textIn;
		this.textOut = textOut;
		
		this.textIn2 = textIn2;
		this.textOut2 = textOut2;
		
		this.OOS = OOS;
		this.OIS = OIS;
		
		this.OOS2 = OOS2;
		this.OIS2 = OIS2;
		
		System.out.println("(1) Запущено потік ServerThread");
		
		Main.mf.addPropertyChangeListener(new MyPropertyChangeListener());//+++++++++++++++++++++++property
		
		start();	//викликаємо run();
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++property
	public class MyPropertyChangeListener implements PropertyChangeListener {
		// This method is called every time the property value is changed
		public void propertyChange(PropertyChangeEvent evt) {
			System.out.println("Name = " + evt.getPropertyName());
			System.out.println("Old Value = " + evt.getOldValue());
			System.out.println("New Value = " + evt.getNewValue());
			System.out.println("**********************************");
			
			textOut2.println(evt.getNewValue());
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
	
	public void run(){
		
		try {
			
			while (true){
				
				String str = textIn.readLine();
				
				if (str.equals("get_groups_count")){
					textOut.println(Main.mf.groups.size());
				}
				
				if (str.equals("get_subgroups_count")){
					textOut.println(Main.mf.subgroups.size());
				}

				if (str.equals("get_goods_count")){
					textOut.println(Main.mf.goods.size());
				}
				
				if (str.equals("get_sold_goods_count")){
					textOut.println(Main.mf.soldGoods.size());
				}
									
				if (str.equals("get_groups")){
					for (Group g : Main.mf.groups){
						OOS.writeObject(g);
						OOS.flush();
					}
				}
				
				if (str.equals("get_subgroups")){
					for (Subgroup sg : Main.mf.subgroups){
						OOS.writeObject(sg);
						OOS.flush();
					}
				}
				
				if (str.equals("get_goods")){
					for (Goods g : Main.mf.goods){
						OOS.writeObject(g);
						OOS.flush();
					}
				}
				
				if (str.equals("get_sold_goods")){
					for (SoldGoods sg : Main.mf.soldGoods){
						OOS.writeObject(sg);
						OOS.flush();
					}
				}
				
				if (str.equals("add_group")){
					Group g = (Group) OIS.readObject();
					Main.mf.groups.add(g);
					Main.mf.sql.addGroup(g);
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("add_subgroup")){
					Subgroup g = (Subgroup) OIS.readObject();
					Main.mf.subgroups.add(g);
					Main.mf.sql.addSubgroup(g);
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("add_goods")){
					Goods g = (Goods) OIS.readObject();
					Main.mf.goods.add(g);
					Main.mf.sql.addGoods(g);
					Main.mf.goodsTable.updateUI();
				}
				
				if (str.equals("add_sold_goods")){
					SoldGoods g = (SoldGoods) OIS.readObject();
					Main.mf.soldGoods.add(g);
					Main.mf.sql.addSoldGoods(g);
					Main.mf.goodsTable.updateUI();
				}
				
				if (str.equals("change_group")){
					Group g = (Group) OIS.readObject();
					Main.mf.groups.set(Main.mf.getObjectPositionByID(g.getGroupID(), Main.mf.groups), g);
					Main.mf.sql.updateGroup(g.getGroupID(), g.getGroupName());
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("change_subgroup")){
					Subgroup g = (Subgroup) OIS.readObject();
					Main.mf.subgroups.set(Main.mf.getObjectPositionByID(g.getSubgroupID(), Main.mf.subgroups), g);
					Main.mf.sql.updateSubgroup(g.getSubgroupID(), g.getSubgroupName());
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("change_goods")){
					Goods g = (Goods) OIS.readObject();
					Main.mf.goods.set(Main.mf.getObjectPositionByID(g.getID(), Main.mf.goods), g);
					Main.mf.sql.updateGoods(g);
					Main.mf.goodsTable.updateUI();
				}
				
				if (str.equals("remove_group")){
					int ID = Integer.parseInt(textIn.readLine());
					Main.mf.groups.remove(Main.mf.getObjectPositionByID(ID, Main.mf.groups));
					Main.mf.sql.removeItem(1, ID);
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("remove_subgroup")){
					int ID = Integer.parseInt(textIn.readLine());
					Main.mf.subgroups.remove(Main.mf.getObjectPositionByID(ID, Main.mf.subgroups));
					Main.mf.sql.removeItem(2, ID);
					Main.mf.refreshComboBoxes();
				}
				
				if (str.equals("remove_goods")){
					int ID = Integer.parseInt(textIn.readLine());
					Main.mf.goods.remove(Main.mf.getObjectPositionByID(ID, Main.mf.goods));
					Main.mf.sql.removeItem(3, ID);
					Main.mf.goodsTable.updateUI();
				}
				
				if (str.equals("remove_sold_goods")){
					int ID = Integer.parseInt(textIn.readLine());
					Main.mf.soldGoods.remove(Main.mf.getObjectPositionByID(ID, Main.mf.soldGoods));
					Main.mf.sql.removeItem(4, ID);
					Main.mf.soldGoodsTable.updateUI();
				}
				
				if (str.equals("get_new_group")){
					Group g = Main.mf.groups.get(Main.mf.groups.size() - 1);
					OOS2.writeObject(g);
					OOS2.flush();
				}
				
				if (str.equals("get_new_subgroup")){
					Subgroup g = Main.mf.subgroups.get(Main.mf.subgroups.size() - 1);
					OOS2.writeObject(g);
					OOS2.flush();
				}
				
				if (str.equals("get_new_goods")){
					Goods g = Main.mf.goods.get(Main.mf.goods.size() - 1);
					OOS2.writeObject(g);
					OOS2.flush();
				}
				
				if (str.equals("get_new_soldgoods")){
					SoldGoods g = Main.mf.soldGoods.get(Main.mf.soldGoods.size() - 1);
					OOS2.writeObject(g);
					OOS2.flush();
				}
				
				if (str.equals("get_client_count")){
					textOut.println(NetLib.threadCount);
				}
				
				if (str.equals("get_client_maxcount")){
					textOut.println(NetLib.MAX_THREADS);
				}
				
				System.out.println("Сервер отримав запит: " + str);
			}
			
			
			
		} catch (IOException | ClassNotFoundException | java.lang.NullPointerException e) {
			//e.printStackTrace();
		
		} finally{
			try{
				System.out.println("Закриваємо сокет на сервері");
				
				textIn.close();
				textOut.close();
				
				OOS.close();
				OIS.close();
				
				try{
					socket.close();
				} catch (java.net.SocketException e1) {}
				
				NetLib.threadCount--;
				Main.mf.connectedClients.setText("Кількість підключених клієнтів: " + NetLib.threadCount);
											
			}catch(IOException e){
				System.err.println("Сокет не закрито ...");
			}
		}
	}
	
}
