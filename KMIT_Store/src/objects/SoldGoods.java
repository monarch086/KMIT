package objects;

import java.io.Serializable;

import main.Main;

public class SoldGoods implements AllObjects, Serializable{
	
	private static int generalID = 1;
	
	private int ID;
	private String date;
	private int goodsID;
	private double price;
	private double quantity;
	
	//створюємо конструктор
	public SoldGoods(int ID, String date, int goodsID, double price, double quantity){
		this.ID = ID;
		if (generalID < ID) generalID = ID;
		generalID++;
		this.date = date;
		this.goodsID = goodsID;
		this.price = price;
		this.quantity = quantity;
		}
	
	//методи класу
	public static int getGeneralID() {
		return generalID;
	}
	
	public static void setGeneralID(int ID) {
		generalID = ID;
	}
	
	//методи об'єктів
	public int getID() {
		return ID;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public int getGoodsID() {
		return goodsID;
	}
	
	public void setGoodsID(int goodsID){
		this.goodsID = goodsID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getName();
			}
		}
		return null;
	}
	
	public int getGroupID() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getGroupID();
			}
		}
		return -1;
	}
	
	public String getGroupName() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getGroupName();
			}
		}
		return null;
	}
	
	public int getSubgroupID() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getSubgroupID();
			}
		}
		return -1;
	}
	
	public String getSubgroupName(){
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getSubgroupName();
			}
		}
		return null;
	}
	
	public String getDescription() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getDescription();
			}
		}
		return null;
	}
	
	public String getProducer() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getProducer();
			}
		}
		return null;
	}
	
	public double getSum(){
		return quantity * getPrice();
	}
	
	public String getMeasureType() {
		for (int i = 0; i < Main.mf.goods.size(); i++){
			if (Main.mf.goods.get(i).getID() == goodsID){
				return Main.mf.goods.get(i).getMeasureType();
			}
		}
		return null;
	}
	
	public SoldGoods get(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
