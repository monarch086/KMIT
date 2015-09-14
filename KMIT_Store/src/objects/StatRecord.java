/*
 * ����� ���� ��������������� ��� ��������� ������������ �����
 * ��� ������ �� ����������� ��� ����� � ��� ����������
 */

package objects;

import java.io.Serializable;

public class StatRecord implements Serializable{
	
	public double quantity;
	public String measureType;
	public double sum;
	
	public StatRecord(String measureType){
		this.measureType = measureType;
	}
	
	public String toString(){
		
		if (measureType.equals(""))
			return quantity + " ������ �� �������� ���� " + sum + " ���.";
		
		else
			return quantity + " " + measureType + " ������ �� �������� ���� " + sum + " ���.";
	}
}
