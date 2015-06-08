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

public class ClientThread extends Thread{
	
	private Socket socket;
	private Socket socket2;
	
	private BufferedReader textIn;
	private PrintWriter textOut;
	private BufferedReader textIn2;
	private PrintWriter textOut2;
	
	private ObjectOutputStream OOS; 
	private ObjectInputStream OIS;
	private ObjectOutputStream OOS2; 
	private ObjectInputStream OIS2;
	
	public ClientThread(Socket socket, BufferedReader textIn, PrintWriter textOut, 
			ObjectOutputStream OOS, ObjectInputStream OIS, Socket socket2, 
			BufferedReader textIn2, PrintWriter textOut2, ObjectOutputStream OOS2, 
			ObjectInputStream OIS2) throws IOException{
		
		this.socket = socket;
		this.socket2 = socket2;
		
		this.textIn = textIn;
		this.textOut = textOut;
		
		this.textIn2 = textIn2;
		this.textOut2 = textOut2;
		
		this.OOS = OOS;
		this.OIS = OIS;
		
		this.OOS2 = OOS2;
		this.OIS2 = OIS2;
		
		start();	//викликаємо run();
	}
		
	public void run(){
		
		try {
			System.out.println("(2)КЛІЄНТ");
			System.out.println("(2)Відкрили з'єднання на сервері: " + socket2);
			
			String str;
			
			while (true){
				
				str = textIn2.readLine();
				
				if (socket.isClosed())
					break;
				
				if (str.equals("refresh")){
					Main.mf.jb_load_data.doClick();
				}
				
				if (str.equals("exit")){
					Main.mf.isConnected = false;
					Main.mf.connection.setIcon(Main.mf.connectionFalseIcon);
					Main.mf.connection.setToolTipText("З'єднання відсутнє");
					Main.mf.labelTitle.setText("KMIT Склад [Client]");
				}
				
				if (str.equals("add_property_group")){
					textOut.println("get_new_group");
					Group g = (Group) OIS2.readObject();
					
					boolean needNew = true;
					
					for (Group gr : Main.mf.groups)
						if (g.getGroupID() == gr.getGroupID())
							needNew = false;
					
					if (needNew){
						Main.mf.groups.add(g);
						Main.mf.refreshComboBoxes();
					}
				}
				
				if (str.equals("add_property_subgroup")){
					textOut.println("get_new_subgroup");
					Subgroup g = (Subgroup) OIS2.readObject();
					
					boolean needNew = true;
					
					for (Subgroup gr : Main.mf.subgroups)
						if (g.getSubgroupID() == gr.getSubgroupID())
							needNew = false;
					
					if (needNew){
						Main.mf.subgroups.add(g);
						Main.mf.refreshComboBoxes();
					}
				}
				
				if (str.equals("add_property_goods")){
					textOut.println("get_new_goods");
					Goods g = (Goods) OIS2.readObject();
					
					boolean needNew = true;
					
					for (Goods gr : Main.mf.goods)
						if (g.getID() == gr.getID())
							needNew = false;
					
					if (needNew){
						Main.mf.goods.add(g);
						Main.mf.goodsTable.updateUI();
					}
				}
				
				if (str.equals("add_property_soldgoods")){
					textOut.println("get_new_soldgoods");
					SoldGoods g = (SoldGoods) OIS2.readObject();
					
					boolean needNew = true;
					
					for (SoldGoods gr : Main.mf.soldGoods)
						if (g.getID() == gr.getID())
							needNew = false;
					
					if (needNew){
						Main.mf.soldGoods.add(g);
						Main.mf.soldGoodsTable.updateUI();
					}
				}
				
				if (str.contains("remove_property_group")){
					
					String id = String.valueOf(str.charAt(str.length() - 2)) + 
								String.valueOf(str.charAt(str.length() - 1));
					
					int temp = -1;
					
					for (int i = 0; i < Main.mf.groups.size(); i++)
						if (Main.mf.groups.get(i).getGroupID() == Integer.parseInt(id))
							temp = i;
					
					if (temp != -1)
						Main.mf.groups.remove(temp);
					Main.mf.refreshComboBoxes();
				}
				
				System.out.println("(2)Клієнт отримав повідомлення: " + str);
			}
			
		} catch (IOException | ClassNotFoundException e) {		
		} finally{
			System.out.println("(2)Закриваємо сокет клієнта");
			try {
				textIn.close();
				textOut.close();
				
				textIn2.close();
				textOut2.close();
				
				OOS.close();
				OIS.close();
				
				OOS2.close();
				OIS2.close();
				
				socket.close();
				socket2.close();
				
			} catch (IOException e) {
				System.out.println("СОКЕТ НЕ ЗАКРИТО");
			}
		}
	}
}
