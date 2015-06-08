package main_frames;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import listeners.ButtonHandler;
import listeners.RadioButtonActionListener;
import main.Main;
import main.NetLib;
import main.SQLlib;
import objects.AllObjects;
import objects.Goods;
import objects.Group;
import objects.SoldGoods;
import objects.StatRecord;
import objects.StatRecordGoods;
import objects.Subgroup;
import table_models.GoodsTableModel;
import table_models.SoldGoodsTableModel;

public class MainFrame extends JFrame implements ChangeListener{
	
	private JPanel panelContent = new JPanel();
	protected JPanel panelTitle = new JPanel();
	private JPanel panelTabbedPane = new JPanel();
	private JTabbedPane jtp = new JTabbedPane();
	protected JPanel jp1 = new JPanel();
	protected JPanel jp2 = new JPanel();
	
	//проміжок між кнопками
	final int SPACE = 10;
	
	public JButton jb_group_add;
	public JButton jb_group_edit;
	public JButton jb_group_remove;
	public JButton jb_subgroup_add, jb_subgroup_edit, jb_subgroup_remove;
	public JButton jb_available_add, jb_available_edit, jb_available_rem;
	public JButton jb_search, jb_statistics;
	public JButton jb_set_connection, jb_load_data;
	
	public JButton jb_sold_add, jb_sold_edit, jb_sold_rem;
	public JButton jb_sold_statistics;
	
	public JLabel connection;
	public JLabel connectedClients;
	public Icon connectionTrueIcon;
	public Icon connectionFalseIcon;
	
	public JLabel addressIPandPorts;
	
	public JRadioButton rb1_1, rb1_2, rb1_3, rb2_1, rb2_2, rb2_3;
	public JComboBox comboGroup, comboSubgroup;
	public JSpinner spinner;
	public JTextField jtf_date_1;
	public JTextField jtf_date_2;
	
	public ArrayList<Group> groups;
	public ArrayList<Subgroup> subgroups;
	public ArrayList<Goods> goods;
	public ArrayList<SoldGoods> soldGoods;
	
	public SQLlib sql;
	public NetLib nl;
	
	//змінна клієнтської програми
	//показує стан підключення до сервера
	public boolean isConnected = false;
	
	JScrollPane jsp1;
	public JScrollPane jsp2;
	JScrollPane jsp3;
	
	ButtonHandler handler = new ButtonHandler(); //обработчик нажатий кнопок
	RadioButtonActionListener rbaListener = new RadioButtonActionListener();
	
	public AbstractTableModel goodsModel;
	public TableModel soldGoodsModel;
	public JTable goodsTable;
	public JTable soldGoodsTable;
	
	public JLabel labelTitle = new JLabel();
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++property
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    // Property propertyValue
    private String propertyValue;
    // Property propertySource
    private int propertySource;
    public String getPropertyValue() {
        return propertyValue;
    }
    public void setPropertyValue(String propertyValue) {
        pcs.firePropertyChange("propertyValue", this.propertyValue, propertyValue);
        this.propertyValue = propertyValue;
    }

    public int getPropertySource() {
        return propertySource;
    }
    public void setPropertySource(int propertySource) {
        pcs.firePropertyChange("propertySource", this.propertySource, propertySource);
        this.propertySource = propertySource;
    }
     
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
	//+++++++++++++++++++++++++
	
	@SuppressWarnings("unchecked")
	MainFrame (String s){
			super(s);
			
			setSize(900, 670);
			setResizable(false);
			setLocationRelativeTo(null);
			
			//створюємо та додаємо елементи JPanel
			panelContent.setLayout(new BorderLayout());
			panelContent.add(panelTitle, BorderLayout.NORTH);
			panelTitle.setLayout(new FlowLayout());
												
			labelTitle.setText("KMIT Склад");
			labelTitle.setFont(new Font("Verdana", Font.BOLD, 20));
			panelTitle.add(labelTitle);
			
	     	panelTabbedPane.setLayout(new BorderLayout());
			panelTabbedPane.add(jtp);
	     	
	     	panelContent.add(panelTabbedPane, BorderLayout.CENTER);
	     	getContentPane().add(panelContent);

	     	//створюємо колекції груп товарів, підгруп товарів та товарів
	     	groups = new ArrayList<Group>();
	     	subgroups = new ArrayList<Subgroup>();
	     	goods = new ArrayList<Goods>();
	     	soldGoods = new ArrayList<SoldGoods>();
	     	
	     	if (Main.isServer){
	     		sql = Main.start.sql;
	     		sql.initializeArrays(groups, subgroups, goods, soldGoods);
	     	}
			
			//створюємо таблицю для першої вкладки
			goodsModel = new GoodsTableModel(goods);
			goodsTable = new JTable(goodsModel);
			setColumnWidth(goodsTable, new int[] {10, 110, 110, 180, 20, 20, 20});		
			jsp1 = new JScrollPane (goodsTable);
			jsp1.setBounds(20, 80, 850, 351);
									
			//створюємо таблицю для другої вкладки
			soldGoodsModel = new SoldGoodsTableModel(soldGoods);
			soldGoodsTable = new JTable(soldGoodsModel);
			setColumnWidth(soldGoodsTable, new int[] {25, 5, 150, 10, 180, 10, 10, 10});
			jsp2 = new JScrollPane (soldGoodsTable);
			jsp2.setBounds(20, 80, 850, 351);
			
			goodsTable.addMouseListener(new MouseAdapter() {
			    public void mousePressed(MouseEvent me) {
			        JTable table =(JTable) me.getSource();
			        Point p = me.getPoint();
			        int row = table.rowAtPoint(p);
			        if (me.getClickCount() == 2) {
			        	jb_available_edit.doClick();
			        }
			    }
			});
			
			
			//завантажуємо малюнки для кнопок
			Icon groupAddIcon = new ImageIcon("images\\group_add.png");
			Icon groupEditIcon = new ImageIcon("images\\group_edit.png");
			Icon groupRemoveIcon = new ImageIcon("images\\group_remove.png");
			
			Icon subgroupAddIcon = new ImageIcon("images\\subgroup_add.png");
			Icon subgroupEditIcon = new ImageIcon("images\\subgroup_edit.png");
			Icon subgroupRemoveIcon = new ImageIcon("images\\subgroup_remove.png");
			
			Icon pageAddIcon = new ImageIcon("images\\page_add.png");
			Icon pageEditIcon = new ImageIcon("images\\page_edit.png");
			Icon pageRemoveIcon = new ImageIcon("images\\page_remove.png");
			
			Icon searchIcon = new ImageIcon("images\\search.png");
			Icon statisticsIcon = new ImageIcon("images\\statistics.png");
			
			//створюємо кнопки до першої вкладки
			jb_group_add = new JButton(groupAddIcon);
			jb_group_add.setBounds(SPACE*2, 435, 64, 64);
			jb_group_add.setToolTipText("Додати групу товарів");
			
			jb_group_edit = new JButton(groupEditIcon);
			jb_group_edit.setBounds(32 + 5 + SPACE*2, 505, 64, 64);
			jb_group_edit.setToolTipText("Редагувати групу товарів");
			
			jb_group_remove = new JButton(groupRemoveIcon);
			jb_group_remove.setBounds(SPACE*3+64, 435, 64, 64);
			jb_group_remove.setToolTipText("Видалити групу товарів");
			
			jb_subgroup_add = new JButton(subgroupAddIcon);
			jb_subgroup_add.setBounds(64*2+SPACE*6, 435, 64, 64);
			jb_subgroup_add.setToolTipText("Додати підгрупу товарів");
			
			jb_subgroup_edit = new JButton(subgroupEditIcon);
			jb_subgroup_edit.setBounds(37 + 64*2+SPACE*6, 505, 64, 64);
			jb_subgroup_edit.setToolTipText("Редагувати підгрупу товарів");
			
			jb_subgroup_remove = new JButton(subgroupRemoveIcon);
			jb_subgroup_remove.setBounds(64*3+SPACE*7, 435, 64, 64);
			jb_subgroup_remove.setToolTipText("Видалити підгрупу товарів");
			
			jb_available_add = new JButton(pageAddIcon);
			jb_available_add.setBounds(64*4+SPACE*10, 435, 64, 64);
			jb_available_add.setToolTipText("Додати товар");
			
			jb_available_edit = new JButton(pageEditIcon);
			jb_available_edit.setBounds(37 + 64*4+SPACE*10, 505, 64, 64);
			jb_available_edit.setToolTipText("Редагувати запис про товар");
			
			jb_available_rem = new JButton(pageRemoveIcon);
			jb_available_rem.setBounds(64*5+SPACE*11, 435, 64, 64);
			jb_available_rem.setToolTipText("Видалити товар");
			
			jb_search = new JButton(searchIcon);
			jb_search.setBounds(64*6+SPACE*14, 435, 64, 64);
			jb_search.setToolTipText("Знайти товар");
			
			jb_statistics = new JButton(statisticsIcon);
			jb_statistics.setBounds(64*7+SPACE*15, 435, 64, 64);
			jb_statistics.setToolTipText("Показати статистику");
			
			//створюємо кнопки до другої вкладки
			jb_sold_add = new JButton(pageAddIcon);
			jb_sold_add.setBounds(SPACE*2, 435, 64, 64);
			jb_sold_add.setToolTipText("Додати запис");
			
			jb_sold_rem = new JButton(pageRemoveIcon);
			jb_sold_rem.setBounds(64*1+SPACE*3, 435, 64, 64);
			jb_sold_rem.setToolTipText("Видалити запис");
			
			jb_sold_statistics = new JButton(statisticsIcon);
			jb_sold_statistics.setBounds(64*2+SPACE*6, 435, 64, 64);
			jb_sold_statistics.setToolTipText("Показати статистику");
						
			//вішаємо на кнопки ActionListener
			jb_group_add.addActionListener(handler);
			jb_group_edit.addActionListener(handler);
			jb_group_remove.addActionListener(handler);
			jb_subgroup_add.addActionListener(handler);
			jb_subgroup_edit.addActionListener(handler);
			jb_subgroup_remove.addActionListener(handler);
			jb_available_add.addActionListener(handler);
			jb_available_edit.addActionListener(handler);
			jb_available_rem.addActionListener(handler);
			jb_search.addActionListener(handler);
			jb_statistics.addActionListener(handler);
			jb_sold_add.addActionListener(handler);
			jb_sold_rem.addActionListener(handler);
			jb_sold_statistics.addActionListener(handler);
	     	
	        //додаємо вкладки
	        jtp.addTab("Наявні товари", jp1);
	        jtp.addTab("Продані товари", jp2);
	        	        
	        //додаємо елементи першої вкладки
	        jp1.setLayout(null);
	        JLabel jl1 = new JLabel("Наявні товари");
	        jl1.setBounds(375, 5, 150, 21);
	        jl1.setFont(new Font("Verdana", Font.BOLD, 16));
	        jp1.add(jl1);
	        jp1.add(jsp1);
	        jp1.add(jb_group_add);
	        jp1.add(jb_group_edit);
	        jp1.add(jb_group_remove);
	        jp1.add(jb_subgroup_add);
	        jp1.add(jb_subgroup_edit);
	        jp1.add(jb_subgroup_remove);
	        jp1.add(jb_available_add);
	        jp1.add(jb_available_edit);
	        jp1.add(jb_available_rem);
	        jp1.add(jb_search);
	        jp1.add(jb_statistics);
	        
	        //RADIO BUTTONS першої вкладки
	        JLabel jl1_1 = new JLabel("Показати товари:");
	        jl1_1.setBounds(35, 45, 110, 21);
	        jp1.add(jl1_1);
	        
	        rb1_1 = new JRadioButton("усі");
			rb1_1.setBounds(150, 45, 50, 23);
			rb1_1.addActionListener(rbaListener);
			jp1.add(rb1_1);
	        
	        rb1_2 = new JRadioButton("лише товари групи: ");
			rb1_2.setBounds(200, 45, 150, 23);
			rb1_2.addActionListener(rbaListener);
			jp1.add(rb1_2);
			
			comboGroup = new JComboBox();
			String[] string = getGroupNames();
			comboGroup.setModel(new DefaultComboBoxModel(string));
			comboGroup.setBounds(340, 45, 180, 25);
			comboGroup.addActionListener(rbaListener);
			jp1.add(comboGroup);
			
			rb1_3 = new JRadioButton("лише товари підгрупи: ");
			rb1_3.setBounds(540, 45, 150, 23);
			rb1_3.addActionListener(rbaListener);
			jp1.add(rb1_3);
	        
			comboSubgroup = new JComboBox();
			string = getSubgroupNames();
			comboSubgroup.setModel(new DefaultComboBoxModel(string));
			comboSubgroup.setBounds(700, 45, 180, 25);
			comboSubgroup.addActionListener(rbaListener);
			jp1.add(comboSubgroup);
			
			ButtonGroup bg1 = new ButtonGroup(); // створюємо группу взаємного виключення
			bg1.add(rb1_1);
			bg1.add(rb1_2);
			bg1.add(rb1_3);
			
	        //додаємо елементи другої вкладки
	        jp2.setLayout(null);
	        JLabel jl2 = new JLabel("Продані товари");
	        jl2.setBounds(375, 5, 150, 21);
	        jl2.setFont(new Font("Verdana", Font.BOLD, 16));
	        jp2.add(jl2);
	        jp2.add(jsp2);
	        jp2.add(jb_sold_add);
	        jp2.add(jb_sold_rem);
	        jp2.add(jb_sold_statistics);
	        
	        //RADIO BUTTONS другої вкладки
	        JLabel jl2_1 = new JLabel("Показати записи:");
	        jl2_1.setBounds(35, 45, 110, 21);
	        jp2.add(jl2_1);
	        
	        rb2_1 = new JRadioButton("усі");
			rb2_1.setBounds(170, 45, 50, 23);
			rb2_1.addActionListener(rbaListener);
			jp2.add(rb2_1);
	        
	        rb2_2 = new JRadioButton("за місяць");
			rb2_2.setBounds(320, 45, 80, 23);
			rb2_2.addActionListener(rbaListener);
			jp2.add(rb2_2);
			
			SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 12, 1); //визначаємо модель спіннера для того, щоб встановити його мінімальне та максимальне значення 
			spinner = new JSpinner(spinnerModel);
			spinner.setBounds(400, 45, 47, 25);
			jp2.add(spinner);
			
			rb2_3 = new JRadioButton("за період з");
			rb2_3.setBounds(530, 45, 100, 25);
			rb2_3.addActionListener(rbaListener);
			jp2.add(rb2_3);
			
			jtf_date_1 = new JTextField();
			jtf_date_1.setBounds(630, 45, 90, 25);
			jtf_date_1.getDocument().addDocumentListener(new listeners.DocListener());
			jp2.add(jtf_date_1);
			
			JLabel jl2_2 = new JLabel("по");
	        jl2_2.setBounds(730, 45, 30, 21);
	        jp2.add(jl2_2);
	        
	        jtf_date_2 = new JTextField();
			jtf_date_2.setBounds(760, 45, 90, 23);
			jtf_date_2.getDocument().addDocumentListener(new listeners.DocListener());
			jp2.add(jtf_date_2);
			
			//визначаємо поточну дату
			Date now = new Date();
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			String date = formatter.format(now);
			jtf_date_1.setText(date);
			jtf_date_2.setText(date);
			
			int month = Integer.parseInt(date.substring(4, 5)); //отримуємо з дати місяць
			spinner.setValue(month);			//встановлюємо у спіннері поточний місяць
			
			ButtonGroup bg2 = new ButtonGroup(); // створюємо группу взаємного виключення
			bg2.add(rb2_1);
			bg2.add(rb2_2);
			bg2.add(rb2_3);
	        	        
	        // добавляем обработчик выбора закладки
	        jtp.addChangeListener(this);
	        
	        //window-listener
	        addWindowListener(new WindowListener() {
				public void windowActivated(WindowEvent event) {}
	            
				public void windowClosed(WindowEvent event) {}
	            
				public void windowClosing(WindowEvent event) {
	                Object[] options = { "Так", "Ні" };
	                int n = JOptionPane
	                        .showOptionDialog(event.getWindow(), "Вийти з програми?",
	                                "Підтвердження", JOptionPane.YES_NO_OPTION,
	                                JOptionPane.QUESTION_MESSAGE, null, options,
	                                options[0]);
	                if (n == 0) {
	                    event.getWindow().setVisible(false);
	                    if (Main.isServer)
	                    	sql.closeConnection();
	                    System.exit(0);
	                }
	            }
	 
	            public void windowDeactivated(WindowEvent event) {}
	 
	            public void windowDeiconified(WindowEvent event) {}
	 
	            public void windowIconified(WindowEvent event) {}
	 
	            public void windowOpened(WindowEvent event) {}
	 
	        });
	 }

	public void stateChanged(ChangeEvent arg0) {}
	
	//метод, що повертає перелік найменувань наявних груп товарів
	public String[] getGroupNames(){
		String[] string = new String[groups.size()];
		for (int i = 0; i < groups.size(); i++){ //заповнюємо масив
			Group gr = groups.get(i);
			string[i] = gr.getGroupName();
		}
		return string;
	}
	
	//метод, що повертає позицію групи в колекції за номером ID
	public int getGroupPositionByID(int groupID){
		for (int i = 0; i < groups.size(); i++){
			if (groups.get(i).getGroupID() == groupID) return i;
		}
		return -1;
	}
	
	//метод, що повертає ID групи за найменуванням групи
	public int getGroupIDByName(String name){
		for (Group gr : groups){
			if (gr.getGroupName() == name) return gr.getGroupID();
		}
		return -1;
	}
	
	//метод, що повертає групу за її ID
	public Object getObjectByID(int type, int ID){
		/*
		 * type:
		 * 0 - groups
		 * 1 - subgroups
		 * 2 - goods
		 * 3 - sold goods
		 */
		switch (type){
			case 0:
				for (Group g : groups)
					if (g.getGroupID() == ID) return g;
			case 1:
				for (Subgroup g : subgroups)
					if (g.getSubgroupID() == ID) return g;
			case 2:
				for (Goods g : goods)
					if (g.getID() == ID) return g;
			case 3:
				for (SoldGoods g : soldGoods)
					if (g.getID() == ID) return g;
		}
		return null;
	}
	
	//метод, що повертає ID групи за ID товару
	public int getGroupIDByGoodsID(int ID){
		for (Goods g : goods)
			if (g.getID() == ID) return g.getGroupID();
		return -1;
	}
	
	//метод, що повертає ID підгрупи за найменуванням підгрупи
	public int getSubgroupIDByName(String name){
		for (Subgroup sgr : subgroups){
			if (sgr.getSubgroupName() == name) return sgr.getSubgroupID();
		}
		return -1;
	}
	
	//метод, що повертає перелік найменувань наявних підгруп товарів
	public String[] getSubgroupNames(){
		String[] string = new String[subgroups.size()];
		for (int i = 0; i < subgroups.size(); i++){ //заповнюємо масив
			Subgroup sgr = subgroups.get(i);
			string[i] = sgr.getSubgroupName();
		}
		return string;
	}
	
	//метод, що повертає колекцію підгруп визначеної групи товарів
	public ArrayList<Subgroup> getSubgroupsByGroupID(int groupID){
		ArrayList<Subgroup> arrayList = new ArrayList<Subgroup>();
		
		for (Subgroup sgr : subgroups)
			if (sgr.getGroupID() == groupID) arrayList.add(sgr);
		return arrayList;
	}
	
	//метод, що повертає масив найменувань підгруп визначеної групи товарів
	public String[] getSubgroupNamesByGroupID(int groupID){
		ArrayList<Subgroup> arrayList = getSubgroupsByGroupID(groupID);
		
		String[] array = new String[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++){
			array[i] = arrayList.get(i).getSubgroupName();
		}
		return array;
	}
	
	//метод, що повертає перелік найменувань наявних товарів
	public String[] getGoodsNames(){
		String[] string = new String[goods.size()];
		for (int i = 0; i < goods.size(); i++){ //заповнюємо масив
			Goods g = goods.get(i);
			string[i] = g.getName();
		}
		return string;
	}
	
	//метод, що повертає колекцію товарів групи
	public ArrayList<Goods> getGoodsOfGroup(int groupID){
		ArrayList<Goods> tempGoods = new ArrayList<Goods>();
		for (Goods g : goods){
			if (g.getGroupID() == groupID)
				tempGoods.add(g);
		}
		return tempGoods;
	}
	
	//метод, що повертає колекцію товарів підгрупи
	public ArrayList<Goods> getGoodsOfSubgroup(int subgroupID){
		ArrayList<Goods> tempGoods = new ArrayList<Goods>();
		for (Goods g : goods){
			if (g.getSubgroupID() == subgroupID)
				tempGoods.add(g);
		}
		return tempGoods;
	}
	
	//метод, що повертає перелік найменувань товарів певної підгрупи
	public String[] getGoodsNamesOfSubgroup(int subgroupID){
		ArrayList<Goods> tempGoods = getGoodsOfSubgroup(subgroupID);
		String[] array = new String[tempGoods.size()];
		
		for (int i = 0; i < tempGoods.size(); i++)
			array[i] = tempGoods.get(i).getName();
		
		return array;
	}
	
	//метод, що повертає ID товару за його назвою
	public int getGoodsIDByName(String name){
		for (Goods g : goods)
			if (g.getName().equals(name))
				return g.getID();
		return -1;
	}
	
	//метод, що повертає одиницю виміру товару за його назвою
	public String getGoodsMeasureTypeByName(String name){
		for (Goods g : goods)
			if (g.getName().equals(name))
				return g.getMeasureType();
		return null;
	}
	
	//метод, що повертає позицію товару в колекції за номером ID
	public int getObjectPositionByID(int ID, ArrayList list){
		for (int i = 0; i < list.size(); i++){
			if (((AllObjects)list.get(i)).getID() == ID) return i;
		}
		return -1;
	}
	
	//отримання даних типу Date зі String
	public Date toDate(String s) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("dd.MM.yyyy");
		return format.parse(s);
	}
	
	//метод, що повертає колекцію статистичних даних про наявні товари
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getStatisticsAvailable(int Type, String name, boolean isGoods){
		/*
		 * Type:
		 * 1 - усі товари
		 * 2 - товари певної групи
		 * 3 - товари певної підгрупи
		 * 4 - конкретно визначений товар
		 * 
		 * name - назва групи/підгрупи/товару
		 * 
		 * isGoods - надати інформацію окремо по кожному товару
		 */
				
		ArrayList list = new ArrayList();
				
		for (Goods g : goods){
			
			if (Type == 2 && g.getGroupName().equals(name) == false)
				continue;
			
			if (Type == 3 && g.getSubgroupName().equals(name) == false)
				continue;
				
			if (Type == 4 && g.getName().equals(name) == false)
				continue;
				
			if (list.size() == 0){
				if (isGoods){
					list.add(new StatRecordGoods(g.getName()));
					((StatRecordGoods)list.get(0)).quantity = g.getQuantity();
					((StatRecordGoods)list.get(0)).sum = g.getPrice() * g.getQuantity();
				}
				else{
					list.add(new StatRecord(g.getMeasureType()));
					((StatRecord)list.get(0)).quantity = g.getQuantity();
					((StatRecord)list.get(0)).sum = g.getPrice() * g.getQuantity();
				}
			}
			else {
				boolean needNew = true;
				for (Object sr : list)
					if (isGoods){
						if (((StatRecordGoods)sr).name.equals(g.getName())){
							((StatRecordGoods)sr).quantity += g.getQuantity();
							((StatRecordGoods)sr).sum += g.getPrice() * g.getQuantity();
							needNew = false;
							break;
						}
					}
					else{
						if (((StatRecord)(sr)).measureType.equals(g.getMeasureType())){
							((StatRecord)sr).quantity += g.getQuantity();
							((StatRecord)sr).sum += g.getPrice() * g.getQuantity();
							needNew = false;
							break;
						}
					}
				if (needNew){
					if (isGoods){
						list.add(new StatRecordGoods(g.getName()));
						((StatRecordGoods)list.get(list.size()-1)).quantity = g.getQuantity();
						((StatRecordGoods)list.get(list.size()-1)).sum = g.getPrice() * g.getQuantity();
					}
					else {
						list.add(new StatRecord(g.getMeasureType()));
						((StatRecord)list.get(list.size()-1)).quantity = g.getQuantity();
						((StatRecord)list.get(list.size()-1)).sum = g.getPrice() * g.getQuantity();
					}
				}
			}
		}
				
		return list;
	}
	
	//метод, що повертає колекцію статистичних даних про продані товари
	public ArrayList<StatRecord> getStatisticsSold(int Type, String name, String from, String to) throws ParseException{
		/*
		 * Type:
		 * 1 - усі товари
		 * 2 - товари певної групи
		 * 3 - товари певної підгрупи
		 * 4 - конкретно визначений товар
		 * 
		 * name - назва групи/підгрупи/товару
		 * from - початкова дата
		 * to - кінцева дата
		 */
				
		ArrayList<StatRecord> list = new ArrayList<StatRecord>();
				
		for (SoldGoods sg : soldGoods){
			
			if (from.equals("") == false && toDate(sg.getDate()).before(toDate(from)))
				continue;
			
			if (to.equals("") == false && toDate(sg.getDate()).after(toDate(to)))
				continue;
			
			if (Type == 2 && sg.getGroupName().equals(name) == false)
				continue;
			
			if (Type == 3 && sg.getSubgroupName().equals(name) == false)
				continue;
				
			if (Type == 4 && sg.getName().equals(name) == false)
				continue;
				
			if (list.size() == 0){
				list.add(new StatRecord(sg.getMeasureType()));
				list.get(0).quantity = sg.getQuantity();
				list.get(0).sum = sg.getSum();
			}
			else {
				boolean needNew = true;
				for (StatRecord sr : list)
					if (sr.measureType.equals(sg.getMeasureType())){
						sr.quantity += sg.getQuantity();
						sr.sum += sg.getSum();
						needNew = false;
						break;
					}
				if (needNew){
					list.add(new StatRecord(sg.getMeasureType()));
					list.get(list.size()-1).quantity = sg.getQuantity();
					list.get(list.size()-1).sum = sg.getSum();
				}
			}
				
		}
				
		return list;
	}
	
	//метод, що повертає колекцію статистичних даних про продані товари за окремими товарами
	public ArrayList<StatRecordGoods> getStatisticsSoldByGoods(int Type, String name, String from, String to) throws ParseException{
		/*
		 * Type:
		 * 1 - усі товари
		 * 2 - товари певної групи
		 * 3 - товари певної підгрупи
		 * 4 - конкретно визначений товар
		 * 
		 * name - назва групи/підгрупи/товару
		 * from - початкова дата
		 * to - кінцева дата
		 */
				
		ArrayList<StatRecordGoods> list = new ArrayList<StatRecordGoods>();
				
		for (SoldGoods sg : soldGoods){
			
			if (from.equals("") == false && toDate(sg.getDate()).before(toDate(from)))
				continue;
			
			if (to.equals("") == false && toDate(sg.getDate()).after(toDate(to)))
				continue;
			
			if (Type == 2 && sg.getGroupName().equals(name) == false)
				continue;
			
			if (Type == 3 && sg.getSubgroupName().equals(name) == false)
				continue;
				
			if (Type == 4 && sg.getName().equals(name) == false)
				continue;
				
			if (list.size() == 0){
				list.add(new StatRecordGoods(sg.getName()));
				list.get(0).quantity = sg.getQuantity();
				list.get(0).sum = sg.getSum();
			}
			else {
				boolean needNew = true;
				for (StatRecordGoods sr : list)
					if (sr.name.equals(sg.getName())){
						sr.quantity += sg.getQuantity();
						sr.sum += sg.getSum();
						needNew = false;
						break;
					}
				if (needNew){
					list.add(new StatRecordGoods(sg.getName()));
					list.get(list.size()-1).quantity = sg.getQuantity();
					list.get(list.size()-1).sum = sg.getSum();
				}
			}
				
		}
				
		return list;
	}
	
	//метод для налаштування розмірів стовпців таблиць
	public void setColumnWidth(JTable table, int[] array){
		for (int i = 0; i < array.length; i++)
			table.getColumnModel().getColumn(i).setPreferredWidth(array[i]);
	}
	
	//метод, що оновлює перелік груп та підгруп у комбобоксах головного вікна
	public void refreshComboBoxes(){
		String[] string = getGroupNames();
		comboGroup.setModel(new DefaultComboBoxModel(string));
		
		string = getSubgroupNames();
		comboSubgroup.setModel(new DefaultComboBoxModel(string));
	}
}
