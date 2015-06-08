package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import objects.Goods;
import objects.Group;
import objects.SoldGoods;
import objects.Subgroup;

public class NetLib extends Thread{
	
	private static final int PORT = 8091;
	private static final int PORT2 = 8092;
	
	public static final int MAX_THREADS = 5;
	public static int threadCount = 0;
	
	private ServerSocket sSocket;
	private ServerSocket sSocket2;
	
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
	
	public ClientThread CT;
	
	/*
	 * методи сервера //////////////////////////////////////////////////////////////////////////////////
	 */
	public void run(){
		try {
			startServer();
		} catch (IOException e) {
		}
	}
	
	private void startServer() throws IOException{
		try{
			sSocket = new ServerSocket(PORT);
			sSocket2 = new ServerSocket(PORT2);
		} catch (java.net.BindException e) {
			JOptionPane.showMessageDialog(null, "Порти " + PORT + " та " + PORT2 + " зайняті. "
					+ "Спробуйте створити підключення на інших портах");
			return;
		}
		
		InetAddress addr = InetAddress.getLocalHost();         
		String myLANIP = addr.getHostAddress();
		
		Main.mf.addressIPandPorts.setText("<html>IP-адреса: " + myLANIP + 
				"<br>" + "Порт 1: " + PORT + "<br>Порт 2: " + PORT2 + "</html>");
		
		System.out.println("СЕРВЕР");
		System.out.println("Запущено сервер-сокет: " + sSocket);
		System.out.println("(2)Запущено сервер-сокет: " + sSocket2);
		
		while (true){
			if (threadCount <= MAX_THREADS){
				try{
					if (threadCount > MAX_THREADS) break;
					socket = sSocket.accept();
					socket2 = sSocket2.accept();
					
					threadCount++;
					
					textIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					textOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					
					textIn2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
					textOut2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),true);
					
					OOS = new ObjectOutputStream(socket.getOutputStream());
					OIS = new ObjectInputStream(socket.getInputStream());
					
					OOS2 = new ObjectOutputStream(socket2.getOutputStream());
					OIS2 = new ObjectInputStream(socket2.getInputStream());
					
					Main.mf.connectedClients.setText("Кількість підключених клієнтів: " + threadCount);
					try{
						System.out.println("(1)Відкрили з'єднання на сервері: " + socket);
						System.out.println("(2)Відкрили з'єднання на сервері: " + socket2);
						ServerThread st = new ServerThread(socket, textIn, textOut, textIn2, textOut2, OOS, OIS, OOS2, OIS2);
						//System.out.println("ThreadName: " + st.getName());
																						
					}catch (java.net.SocketException e) {
						
						System.out.println("Client has closed connection");
						
					}finally{}
				}finally{}
			}
			
			else JOptionPane.showMessageDialog(null, "До сервера можуть підключитись не більш як " + 
					MAX_THREADS + " клієнтів");
		}
	}
	
	void closeSocket() throws IOException {
		try{
			socket.close();
			socket2.close();
		} catch (NullPointerException e){
		}
	}
	
	public void closeServer() throws IOException{
		try{
			closeSocket();
			sSocket.close();
			sSocket2.close();
		} catch (NullPointerException e){
		}
	}
	
	/*
	 * методи клієнта //////////////////////////////////////////////////////////////////////////////////
	 */
	public void setConnection() throws IOException{
		InetAddress addr = InetAddress.getByName(null);
		socket = new Socket (addr, PORT);
		socket2 = new Socket (addr, PORT2);
		
		addr = InetAddress.getLocalHost();         
		String myLANIP = addr.getHostAddress();
		
		Main.mf.addressIPandPorts.setText("<html>IP-адреса: " + myLANIP + 
				"<br>" + "Порт 1: " + socket.getLocalPort() + "<br>Порт 2: " + 
				socket2.getLocalPort() + "</html>");
		
		System.out.println("КЛІЄНТ");
		System.out.println("(1)Клієнтом встановлене з'єднання = " + socket);
		System.out.println("(2)Клієнтом встановлене з'єднання = " + socket2);
		
		textIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		textOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		
		OOS = new ObjectOutputStream(socket.getOutputStream());
		OIS = new ObjectInputStream(socket.getInputStream());
		
		textIn2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
		textOut2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),true);
		
		OOS2 = new ObjectOutputStream(socket2.getOutputStream());
		OIS2 = new ObjectInputStream(socket2.getInputStream());
		
		try{
			System.out.println("Відкрили з'єднання на сервері: " + socket);
			CT = new ClientThread(socket, textIn, textOut, OOS, OIS, socket2, textIn2, textOut2, OOS2, OIS2);								
		}catch (java.net.SocketException e) {
			System.out.println("Client has closed connection");
		}finally{}
	}
	
	public int getObjectsCount(int type) throws IOException{
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 */
		
		try{
			
			if (checkConnection() == false) return -1;
			
			int count;
			
			switch (type) {
				
			case 0:	textOut.println("get_groups_count");
					break;
			case 1:	textOut.println("get_subgroups_count");
					break;
			case 2:	textOut.println("get_goods_count");
					break;
			case 3:	textOut.println("get_sold_goods_count");
					break;
			}
			
			count = Integer.parseInt(textIn.readLine());
			
			System.out.println("Client got ObjectsCount: " + count);
			return count;
			
		}finally{}
	}
	
	public int getClientCount() throws IOException{
				
		try{
			if (checkConnection() == false) return -1;
			
			int count;
			textOut.println("get_client_count");
			count = Integer.parseInt(textIn.readLine());
			
			System.out.println("Client got ClientCount: " + count);
			return count;
		}finally{}
	}
	
	public int getClientMaxCount() throws IOException{
		
		try{
			if (checkConnection() == false) return -1;
			
			int count;
			textOut.println("get_client_maxcount");
			count = Integer.parseInt(textIn.readLine());
			
			System.out.println("Client got ClientMaxCount: " + count);
			return count;
		}finally{}
	}
	
	public Object getObjects(int type) throws IOException, ClassNotFoundException, InterruptedException{
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 * 3 - sold goods
		 */
		
		try{
			
			if (checkConnection() == false) return null;
			
			int count = getObjectsCount(type);
			
			switch (type) {
			
			case 0:	textOut.println("get_groups");
			
					ArrayList<Group> groups = new ArrayList<Group>();
			
					for (int i = 0; i < count; i++){
						Group g = (Group) OIS.readObject();
						groups.add(g);
					}
					Group.setGeneralID(groups.get(count - 1).getGroupID() + 1);
					return groups;
					
			case 1:	textOut.println("get_subgroups");
					
					ArrayList<Subgroup> subgroups = new ArrayList<Subgroup>();
			
					for (int i = 0; i < count; i++){
						Subgroup sg = (Subgroup) OIS.readObject();
						subgroups.add(sg);
					}
					Subgroup.setGeneralID(subgroups.get(count - 1).getSubgroupID() + 1);
					return subgroups;
					
			case 2:	textOut.println("get_goods");

					ArrayList<Goods> goods = new ArrayList<Goods>();
			
					for (int i = 0; i < count; i++){
						Goods g = (Goods) OIS.readObject();
						goods.add(g);
					}
					Goods.setGeneralID(goods.get(count - 1).getID() + 1);
					return goods;
						
			case 3:	textOut.println("get_sold_goods");

				ArrayList<SoldGoods> soldGoods = new ArrayList<SoldGoods>();

				for (int i = 0; i < count; i++){
					SoldGoods sg = (SoldGoods) OIS.readObject();
					soldGoods.add(sg);
				}
				SoldGoods.setGeneralID(soldGoods.get(count - 1).getID() + 1);
				return soldGoods;
			}
			
			return null;
			
		}finally{}
	}
	
	//метод додавання клієнтом об'єктів до бази
	public void addObjects(int type, int ID) throws IOException, ClassNotFoundException{
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 * 3 - sold goods
		 */
		
		try{
			
			if (checkConnection() == false) {
				JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
				return;
			}
			
			switch (type) {
			
			case 0:	textOut.println("add_group");
					
					Group g = (Group) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(g);
					OOS.flush();
					return;
					
			case 1:	textOut.println("add_subgroup");
					
					Subgroup sg = (Subgroup) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(sg);
					OOS.flush();
					return;
					
			case 2:	textOut.println("add_goods");

					Goods goods = (Goods) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(goods);
					OOS.flush();
					return;
						
			case 3:	textOut.println("add_sold_goods");

					SoldGoods sGoods = (SoldGoods) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(sGoods);
					OOS.flush();
					return;
			}
			
			return;
			
		}finally{}
	}
	
	//метод редагування клієнтом об'єктів у базі
	public void changeObjects(int type, int ID) throws IOException, ClassNotFoundException{
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 * 
		 */
		
		try{
			
			if (checkConnection() == false) {
				JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
				return;
			}
			
			switch (type) {
			
			case 0:	textOut.println("change_group");
					
					Group g = (Group) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(g);
					OOS.flush();
					return;
					
			case 1:	textOut.println("change_subgroup");
					
					Subgroup sg = (Subgroup) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(sg);
					OOS.flush();
					return;
					
			case 2:	textOut.println("change_goods");

					Goods goods = (Goods) Main.mf.getObjectByID(type, ID);
					OOS.writeObject(goods);
					OOS.flush();
					return;
			}
			
			return;
			
		}finally{}
	}
	
	//метод видалення клієнтом об'єктів у базі
	public void removeObjects(int type, int ID) throws IOException, ClassNotFoundException{
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 * 3 - sold goods
		 */
		
		try{
			
			if (checkConnection() == false) {
				JOptionPane.showMessageDialog(null, "Відсутнє з'єднання з сервером!");
				return;
			}
			
			switch (type) {
			
			case 0:	textOut.println("remove_group");
					
					textOut.println(ID);
					return;
					
			case 1:	textOut.println("remove_subgroup");
					
					textOut.println(ID);
					return;
					
			case 2:	textOut.println("remove_goods");
					
					textOut.println(ID);
					return;
					
			case 3:	textOut.println("remove_sold_goods");
			
					textOut.println(ID);
					return;
			}
			
			return;
			
		}finally{}
	}
	
	boolean checkConnection(){
		if (Main.mf.isConnected == false){
			JOptionPane.showMessageDialog(null, "Під'єднайтесь до сервера спочатку");
			return false;
		}
		
		if (textOut.checkError() || socket.isClosed()){
			Main.mf.isConnected = false;
			Main.mf.connection.setIcon(Main.mf.connectionFalseIcon);
			Main.mf.connection.setToolTipText("З'єднання відсутнє");
			return false;
		}
		
		else return true;
	}
	
	public void closeConnection(){
		if (socket != null || socket.isConnected())
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
				
				CT.interrupt();
				
			} catch (IOException e) {
				System.out.println("IOException during closing Client");
			}
	}
}
