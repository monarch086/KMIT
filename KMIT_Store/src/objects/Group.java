package objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Group implements Serializable{
	
	private static int generalID = 1;
	
	private int groupID;
	private String groupName;
	
	//��������� �����������
	public Group(int groupID, String groupName){
		this.groupID = groupID;
		this.groupName = groupName;
		if (generalID < groupID) generalID = groupID;
		generalID++;
		}

	//������ �����
	public static int getGeneralID() {
		return generalID;
	}
	
	public static void setGeneralID(int ID) {
		generalID = ID;
	}
	
	//������ ��'����
	public int getGroupID() {
		return groupID;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Group get(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return groupID + ", " + groupName + ";" + "/n";
	}
}
