/*
 * Даний клас використовується для зберігання статистичних даних
 * про товари та відображення цих даних у вікні статистики
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
			return quantity + " товарів на загальну суму " + sum + " грн.";
		
		else
			return quantity + " " + measureType + " товарів на загальну суму " + sum + " грн.";
	}
}
