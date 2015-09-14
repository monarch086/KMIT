package table_models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import objects.Goods;

//������ ��� ����� �������
public class GoodsTableModel extends AbstractTableModel{
		 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

	private ArrayList<Goods> goods;

    public GoodsTableModel(ArrayList<Goods> goods) {
    	this.goods = goods;
    }

    public void addTableModelListener(TableModelListener listener) {
    	listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
    	switch (columnIndex) {
        	case 0:
        		return Integer.class;
        	case 1:
        		return String.class;
        	case 2:
        		return String.class;
        	case 3:
        		return String.class;
        	case 4:
        		return Double.class;
        	case 5:
        		return Double.class;
        	case 6:
        		return String.class;
            }
			return null;
      }

      public int getColumnCount() {
          return 7;
      }

      public String getColumnName(int columnIndex) {
          switch (columnIndex) {
          case 0:
              return "<HTML>���<BR> ������</HTML>";
          case 1:
              return "�����";
          case 2:
              return "ϳ������";
          case 3:
              return "�����";
          case 4:
              return "ֳ��";
          case 5:
              return "ʳ������";
          case 6:
              return "<HTML>�������<BR> �����</HTML>";
          }
          return "";
      }

      public int getRowCount() {
          return goods.size();
      }

      public Object getValueAt(int rowIndex, int columnIndex) {
          Goods goodRow = goods.get(rowIndex);
          switch (columnIndex) {
          case 0:
              return goodRow.getID();
          case 1:
              return goodRow.getGroupName();
          case 2:
              return goodRow.getSubgroupName();
          case 3:
              return goodRow.getName();
          case 4:
              return goodRow.getPrice();
          case 5:
              return goodRow.getQuantity();
          case 6:
              return goodRow.getMeasureType();
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
