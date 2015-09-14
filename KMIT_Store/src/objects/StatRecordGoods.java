/*
 * ����� ���� ��������������� ��� ��������� ������������ �����
 * ��� ������ �� ����������� ��� ����� � ��� ����������
 */

package objects;

import java.io.Serializable;

import main.Main;

public class StatRecordGoods implements Serializable{
	
	public double quantity;
	public String name;
	public double sum;
	
	public StatRecordGoods(String name){
		this.name = name;
	}
	
	public String toString(){
		return quantity + " " + Main.mf.getGoodsMeasureTypeByName(name) + " ������ \"" + name + "\" �� �������� ���� " + sum + " ���.";
	}
}
