package table_models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import objects.SoldGoods;

public class SoldGoodsTableModel implements TableModel {
	 
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private ArrayList<SoldGoods> soldGoods;

    public SoldGoodsTableModel(ArrayList<SoldGoods> soldGoods) {
        this.soldGoods = soldGoods;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
    	switch (columnIndex) {
	    	case 0:
	    		return String.class;
	    	case 1:
	    		return Integer.class;
	    	case 2:
	    		return String.class;
	    	case 3:
	    		return Integer.class;
	    	case 4:
	    		return String.class;
	    	case 5:
	    		return Double.class;
	    	case 6:
	    		return Double.class;
	    	case 7:
	    		return Double.class;
        }
		return null;
    }

    public int getColumnCount() {
        return 8;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Дата";
        case 1:
        	return "№";
        case 2:
            return "Підгрупа";
        case 3:
            return "Код товару";
        case 4:
            return "Назва";
        case 5:
            return "Ціна";
        case 6:
            return "Кількість";
        case 7:
            return "Сума";
        }
        return "";
    }

    public int getRowCount() {
        return soldGoods.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        SoldGoods sg = soldGoods.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return sg.getDate();
        case 1:
        	return sg.getID();
        case 2:
            return sg.getSubgroupName();
        case 3:
            return sg.getGoodsID();
        case 4:
            return sg.getName();
        case 5:
            return sg.getPrice();
        case 6:
            return sg.getQuantity();
        case 7:
            return sg.getSum();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}
