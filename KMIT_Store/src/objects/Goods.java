package objects;

import java.io.Serializable;

import main.Main;

@SuppressWarnings("serial")
public class Goods implements AllObjects, Serializable{
	
	private static int generalID = 1;
	
	private int ID;
	private int subgroupID;
	private String name;
	private String description;
	private String producer;
	private double price;
	private double quantity;
	private String measureType; //одиниці виміру
	
	//створюємо конструктори
	public Goods(int ID, int subgroupID, String name, double quantity, String measureType){
		this.ID = ID;
		if (generalID < ID) generalID = ID;
		generalID++;
		this.subgroupID = subgroupID;
		this.name = name;
		this.quantity = quantity;
		this.measureType = measureType;
	}
	
	public Goods(int ID, int subgroupID, String name, double price, double quantity, String measureType){
		this(ID, subgroupID, name, quantity, measureType);
		this.price = price;
	}
	
	public Goods(int ID, int subgroupID, String name, String description, String producer, double price, double quantity, String measureType){
		this(ID, subgroupID, name, price, quantity, measureType);
		this.producer = producer;
		this.description = description;
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
	
	public String getGroupName(){
		for (int i = 0; i < Main.mf.groups.size(); i++){
			if (Main.mf.groups.get(i).getGroupID() == getGroupID())
				return Main.mf.groups.get(i).getGroupName();
		}
		return null;
	}
	
	public int getGroupID(){
		for (int i = 0; i < Main.mf.subgroups.size(); i++){
			if (Main.mf.subgroups.get(i).getSubgroupID() == subgroupID) 
				return Main.mf.subgroups.get(i).getGroupID();
		}
		return -1;
	}
	
	public int getSubgroupID() {
		return subgroupID;
	}
	
	public void setSubgroupID(int subgroupID) {
		this.subgroupID = subgroupID;
	}
	
	public String getSubgroupName(){
		for (int i = 0; i < Main.mf.subgroups.size(); i++){
			if (Main.mf.subgroups.get(i).getSubgroupID() == subgroupID) 
				return Main.mf.subgroups.get(i).getSubgroupName();
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public String getMeasureType() {
		return measureType;
	}
	
	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	
	public Goods get(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return ID + ". " + name + ", " + getGroupName() + ", " + getSubgroupName() + ", " + 
				description + ", " + producer + ", " + price + ", " + quantity + ", " + measureType + ";" + "/n";
	}
}
