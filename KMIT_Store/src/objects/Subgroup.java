package objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Subgroup implements Serializable{
	
	private static int generalID = 1;
	
	private int subgroupID;
	private int groupID;
	private String subgroupName;
	
	//створюємо конструктор
	public Subgroup(int subgroupID, int groupID, String subgroupName){
		this.subgroupID = subgroupID;
		this.groupID = groupID;
		this.subgroupName = subgroupName;
		if (generalID < subgroupID) generalID = subgroupID;
		generalID++;
		}
	
	//методи класу
	public static int getGeneralID() {
		return generalID;
	}
	
	public static void setGeneralID(int ID) {
		generalID = ID;
	}
	
	//методи об'єктів
	public int getSubgroupID() {
		return subgroupID;
	}
	
	public int getGroupID() {
		return groupID;
	}
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public String getSubgroupName() {
		return subgroupName;
	}
	
	public void setSubgroupName(String subgroupName) {
		this.subgroupName = subgroupName;
	}

	public Subgroup get(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
