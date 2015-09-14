package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Goods;
import objects.Group;
import objects.SoldGoods;
import objects.Subgroup;
	
	/* ���� �������� ������� ���� ����� SQLite, �� ������ ��� �������:
	 * 
	 * Pass:
	 * - id (int);
	 * - pass (String);
	 * 
	 * Groups:
	 * - groupID (int);
	 * - groupName (String);
	 * 
	 * Subgroups:
	 * - subgroupID (int);
	 * - groupID (int);
	 * - subgroupName (String);
	 * 
	 * Goods:
	 * - ID (int);
	 * - subgroupID (int);
	 * - name (String);
	 * - description (String);
	 * - producer (String);
	 * - price (double);
	 * - quantity (double);
	 * - measureType (String);
	 * 
	 * Sold_Goods:
	 * - ID (int);
	 * - date (String);
	 * - goodsID (int);
	 * - price (double);
	 * - quantity (double);
	 */
	
public class SQLlib {
	
	private Connection con;
	
	public void initializeDB(String name){
		try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + name);
            
            PreparedStatement st = con.prepareStatement("create table if not exists 'pass' ('ID' INT PRIMARY KEY, 'pass' text);");
            int result = st.executeUpdate();
            
            st = con.prepareStatement("create table if not exists 'groups' ('groupID' INT PRIMARY KEY, 'groupName' text);");
            result = st.executeUpdate();
            
            st = con.prepareStatement("create table if not exists 'subgroups' ('subgroupID' INT PRIMARY KEY, 'groupID' INT, 'subgroupName' text);");
            result = st.executeUpdate();
            
            st = con.prepareStatement("create table if not exists 'goods' ('ID' INT PRIMARY KEY, 'subgroupID' INT, 'name' text, 'description' text, 'producer' text, 'price' real, 'quantity' real, 'measureType' text);");
            result = st.executeUpdate();
            
            st = con.prepareStatement("create table if not exists 'sold_goods' ('ID' INT PRIMARY KEY, 'date' text, 'goodsID' INT, 'price' real, 'quantity' real);");
            result = st.executeUpdate();
            
            st.close();
            
        }catch(ClassNotFoundException e){
            System.out.println("�� ������� ������� JDBC");
            e.printStackTrace();
            System.exit(0);
        }catch (SQLException e){
            System.out.println("�� ����� SQL �����");
            e.printStackTrace();
        }     
	}
	
	//����� ������������ ����� �� ���� �����
	public void firstUpload(ArrayList<Group> groups, ArrayList<Subgroup> subgroups, ArrayList<Goods> goods, ArrayList<SoldGoods> soldGoods){
	       try{
	    	   //���� � ��� ��� � ���, ��� ��������� �� ������
	           Statement stm = con.createStatement();
	           ResultSet res = stm.executeQuery( "SELECT * FROM goods;" );
	           if (res.next()){
	        	   stm.close();
	        	   res.close();
	        	   System.out.println("� ��� ��� ����� ���. ��� ��� �� ������ �������� �����");
	        	   return;
	           }

	           stm.close();
	           res.close();
	           
	           PreparedStatement statement = con.prepareStatement("INSERT INTO groups(groupID, groupName) VALUES (?, ?)");
	           
	           for (Group gr : groups){
	        	   statement.setInt(1, gr.getGroupID());
	        	   statement.setString(2, gr.getGroupName());
	        	   int result = statement.executeUpdate();
	           }
	            
	           statement = con.prepareStatement("INSERT INTO subgroups(subgroupID, groupID, subgroupName) VALUES (?, ?, ?)");
	            
	           for (Subgroup sgr : subgroups){
	        	   statement.setInt(1, sgr.getSubgroupID());
	        	   statement.setInt(2, sgr.getGroupID());
	        	   statement.setString(3, sgr.getSubgroupName());
	        	   int result = statement.executeUpdate();
	           }
	            
	           statement = con.prepareStatement("INSERT INTO goods(ID, subgroupID, name, description, producer, price, quantity, measureType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	            
	           for (Goods g : goods){
	        	   statement.setInt(1, g.getID());
	        	   statement.setInt(2, g.getSubgroupID());
	        	   statement.setString(3, g.getName());
	        	   statement.setString(4, g.getDescription());
	        	   statement.setString(5, g.getProducer());
	        	   statement.setDouble(6, g.getPrice());
	        	   statement.setDouble(7, g.getQuantity());
	        	   statement.setString(8, g.getMeasureType());
	        	   int result = statement.executeUpdate();
	           }
	           
	           statement = con.prepareStatement("INSERT INTO sold_goods(ID, date, goodsID, price, quantity) VALUES (?, ?, ?, ?, ?)");
	            
	           for (SoldGoods sg : soldGoods){
	        	   statement.setInt(1, sg.getID());
	        	   statement.setString(2, sg.getDate());
	        	   statement.setInt(3, sg.getGoodsID());
	        	   statement.setDouble(4, sg.getPrice());
	        	   statement.setDouble(5, sg.getQuantity());
	        	   int result = statement.executeUpdate();
	           }
	           
	           statement.close();
	            
	        }catch (SQLException e){
	            System.out.println("�� ����� SQL ����� �� �������");
	            e.printStackTrace();
	        }
	}
	
	//������������ ����� � ���� �� �������� �������� (���������� ��� ������� ��������)
	public void initializeArrays(ArrayList<Group> groups, ArrayList<Subgroup> subgroups, ArrayList<Goods> goods, ArrayList<SoldGoods> soldGoods){
		try{
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM groups");
            
            while (res.next()) {
                groups.add(new Group(res.getInt("groupID"), res.getString("groupName")));
            }
            
            res = st.executeQuery("SELECT * FROM subgroups");
            while (res.next()) {
                subgroups.add(new Subgroup(res.getInt("subgroupID"), res.getInt("groupID"), res.getString("subgroupName")));
            }
            
            res = st.executeQuery("SELECT * FROM goods");
            
            while (res.next()) {
                goods.add(new Goods(res.getShort("ID"), res.getShort("subgroupID"), 
                		res.getString("name"), res.getString("description"), 
                		res.getString("producer"), res.getDouble("price"), 
                		res.getDouble("quantity"), res.getString("measureType")));
            }
            
            res = st.executeQuery("SELECT * FROM sold_goods");
            
            while (res.next()) {
                soldGoods.add(new SoldGoods(res.getShort("ID"), res.getString("date"), 
                		res.getShort("goodsID"), res.getDouble("price"), 
                		res.getDouble("quantity")));
            }
            
            st.close();
            res.close();
            
		}catch(SQLException e){
            System.out.println("�� ����� SQL ����� �� ������ �����");
            e.printStackTrace();
        }
	}
	
	//������������ ������
	public void setPassword(char password[]){
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO pass(id, pass) VALUES (?, ?)");
			statement.setInt(1, 1);
	 	   	statement.setString(2, password.toString());
	 	   	int result = statement.executeUpdate();
			statement.close();
        
        }catch (SQLException e){
            System.out.println("�� ����� SQL ����� �� ������� ������");
            e.printStackTrace();
        }
	}
	
	//��������� ������ � ����
	public char[] getPassword(){
		try{
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM pass");
            
            char password[] = res.getString("pass").toCharArray();
            
            st.close();
            res.close();
            
            return password;
            
		}catch(SQLException e){
            System.out.println("�� ����� SQL ����� �� ������ �����");
            e.printStackTrace();
        }
		return null;
	}
	
	//��������� ����� ������ �� ����
	public void addGroup(Group gr){
		
		//Main.mf.setPropertyValue("add_property_group");
		Main.mf.setPropertyValue("refresh");
		Main.mf.setPropertyValue("");
				
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO groups(groupID, groupName) VALUES (?, ?)");
			statement.setInt(1, gr.getGroupID());
	 	   	statement.setString(2, gr.getGroupName());
	 	   	int result = statement.executeUpdate();
			statement.close();
        
        }catch (SQLException e){
            System.out.println("�� ����� SQL ����� �� ������� ����� ������");
            e.printStackTrace();
        }
	}
	
	//��������� ������� ������ �� ����
	public void addSubgroup(Subgroup sgr){
		
		//Main.mf.setPropertyValue("add_property_subgroup");
		Main.mf.setPropertyValue("refresh");
		Main.mf.setPropertyValue("");
		
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO subgroups(subgroupID, groupID, subgroupName) VALUES (?, ?, ?)");
			statement.setInt(1, sgr.getSubgroupID());
			statement.setInt(2, sgr.getGroupID());
	 	   	statement.setString(3, sgr.getSubgroupName());
	 	   	int result = statement.executeUpdate();
			statement.close();
			
		}catch (SQLException e){
            System.out.println("�� ����� SQL ����� �� ������� ������� ������");
            e.printStackTrace();
        }
	}
	
	//��������� ������ �� ����
	public void addGoods(Goods g){
		
		//Main.mf.setPropertyValue("add_property_goods");
		Main.mf.setPropertyValue("refresh");
		Main.mf.setPropertyValue("");
		
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO goods(ID, subgroupID, name, description, producer, price, quantity, measureType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, g.getID());
			statement.setInt(2, g.getSubgroupID());
	 	   	statement.setString(3, g.getName());
	 	    statement.setString(4, g.getDescription());
	 	    statement.setString(5, g.getProducer());
	 	    statement.setDouble(6, g.getPrice());
	 	    statement.setDouble(7, g.getQuantity());
	 	    statement.setString(8, g.getMeasureType());
	 	   	int result = statement.executeUpdate();
			statement.close();
		}catch (SQLException e){
            System.out.println("�� ����� SQL ����� �� ������� ������");
            e.printStackTrace();
        }
	}
	
	//��������� ������ ��� �������� �����
	public void addSoldGoods(SoldGoods sGoods){
		
		//Main.mf.setPropertyValue("add_property_soldgoods");
		Main.mf.setPropertyValue("refresh");
		Main.mf.setPropertyValue("");
		
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO sold_goods(ID, date, goodsID, price, quantity) VALUES (?, ?, ?, ?, ?)");
			statement.setInt(1, sGoods.getID());
			statement.setString(2, sGoods.getDate());
			statement.setInt(3, sGoods.getGoodsID());
			statement.setDouble(4, sGoods.getPrice());
			statement.setDouble(5, sGoods.getQuantity());
			int result = statement.executeUpdate();
			statement.close();
		}catch (SQLException e){
            System.out.println("�� ����� SQL ����� �� ������� ������ ��� �����");
            e.printStackTrace();
        }
	}
	
	//��������� �������� � ����-��� �������
	public void removeItem(int type, int id){
		/*
		 * �������� type:
		 * 1 - ��������� ����� ������
		 * 2 - ��������� ������� ������
		 * 3 - ��������� ������
		 * 4 - ��������� ������ ��� �������� �����
		 */
		try{
			PreparedStatement statement = null;
			int result;
			
			switch (type){
				
				//��������� �����
				case 1:
					statement = con.prepareStatement("DELETE FROM groups WHERE groupID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
					
					statement = con.prepareStatement("DELETE FROM subgroups WHERE groupID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
		            
		            Statement st = con.createStatement();
		            ResultSet res = st.executeQuery("SELECT * FROM goods");
		            		            
		            while (res.next()) {
		            	int goodsID = res.getShort("ID");
		                if (Main.mf.getGroupIDByGoodsID(goodsID) == id){
		                	statement = con.prepareStatement("DELETE FROM goods WHERE ID=?");
							statement.setInt(1, goodsID);
							result = statement.executeUpdate();
		                }
		            }
		            
		            st.close();
		            res.close();
					
		            if (id < 10){
		            	//Main.mf.setPropertyValue("remove_property_group0" + id);
		            	Main.mf.setPropertyValue("refresh");
		            	Main.mf.setPropertyValue("");
		            }else{
		            	//Main.mf.setPropertyValue("remove_property_group" + id);
		            	Main.mf.setPropertyValue("refresh");
		            	Main.mf.setPropertyValue("");
		            }
		            
		            break;
					
				//��������� �������
				case 2:
					statement = con.prepareStatement("DELETE FROM subgroups WHERE subgroupID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
					
					statement = con.prepareStatement("DELETE FROM goods WHERE subgroupID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
					
					//Main.mf.setPropertyValue("remove_property_subgroup" + id);
					Main.mf.setPropertyValue("refresh");
					Main.mf.setPropertyValue("");
					
					break;
					
				//��������� ������
				case 3:
					statement = con.prepareStatement("DELETE FROM goods WHERE ID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
					
					//Main.mf.setPropertyValue("remove_property_goods" + id);
					Main.mf.setPropertyValue("refresh");
					Main.mf.setPropertyValue("");
					
					break;
				
				//��������� ������ ��� �������� �����
				case 4:
					statement = con.prepareStatement("DELETE FROM sold_goods WHERE ID=?");
					statement.setInt(1, id);
					result = statement.executeUpdate();
					
					//Main.mf.setPropertyValue("remove_property_soldgoods" + id);
					Main.mf.setPropertyValue("refresh");
					Main.mf.setPropertyValue("");
					
					break;
			}

			statement.close();
			
		}catch (SQLException e){
			System.out.println("�� ����� SQL ����� �� ���������");
			e.printStackTrace();
		}
	}
	
	//������� ����� ��� ����� ������
	public void updateGroup(int groupID, String groupName){
		try{
			Statement st = con.createStatement();
			st.execute("update 'groups' set groupName='" + groupName + "' where groupID = " + groupID + ";");
			
			Main.mf.setPropertyValue("refresh");
			Main.mf.setPropertyValue("");
			
		}catch (SQLException e){
			System.out.println("�� ����� SQL ����� �� ��������� ���������� ��� ����� ������");
			e.printStackTrace();
		}
	}
	
	//������� ����� ��� ������� ������
	public void updateSubgroup(int subgroupID, String subgroupName){
		try{
			Statement st = con.createStatement();
			st.execute("update 'subgroups' set subgroupName='" + subgroupName + "' where subgroupID = " + subgroupID + ";");
			
			Main.mf.setPropertyValue("refresh");
			Main.mf.setPropertyValue("");
			
		}catch (SQLException e){
			System.out.println("�� ����� SQL ����� �� ��������� ���������� ��� ������� ������");
			e.printStackTrace();
		}
	}
	
	//������� ����� ��� �����
	public void updateGoods(Goods g){
		try{
			Statement st = con.createStatement();
			st.execute("update 'goods' set subgroupID='" + g.getSubgroupID() + "', name='" + g.getName() + 
					"', description='" + g.getDescription() + "', producer='" + g.getProducer() + 
					"', price='" + g.getPrice() + "', quantity='" + g.getQuantity() + 
					"', measureType='" + g.getMeasureType() + "' where id = " + g.getID() + ";");
			
			Main.mf.setPropertyValue("refresh");
			Main.mf.setPropertyValue("");
			
		}catch (SQLException e){
			System.out.println("�� ����� SQL ����� �� ��������� ���������� ��� �����");
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {e.printStackTrace();
		}
	}
}
