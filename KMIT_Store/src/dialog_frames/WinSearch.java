package dialog_frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionListener;

import main.Main;
import objects.Goods;

public class WinSearch extends javax.swing.JDialog{
	
	JLabel title, results, info;
	JRadioButton id, name;
	JTextField jt_id, jt_name;
	JButton search, edit, add;
	
	JScrollPane resultsJSP;
	JList<Goods> resultsList;
	
	eHandler handler = new eHandler();
	
	final int START_HEIGHT_POINT = 63;
	final int START_WIDTH_POINT = 25;
	final int ELEMENT_HEIGHT = 20;
	final int TEXTFIELD_HEIGHT = 25;
	final int SPACE = 15;
	
	public WinSearch(java.awt.Frame parent) {
		super(parent, true);
		
		setLocation(500, 200);
		setSize(300, 450);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Пошук товару");
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 12));
		title.setSize(100, 30);
		title.setLocation(getWidth()/2 - title.getWidth()/2, 11);
		getContentPane().add (title);
		
		id = new JRadioButton("За кодом:");
		id.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(id);
		
		jt_id = new JTextField();
		jt_id.setBounds(126, START_HEIGHT_POINT, 144, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_id);
		
		name = new JRadioButton("За назвою:");
		name.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE, 85, ELEMENT_HEIGHT);
		getContentPane().add(name);
		
		jt_name = new JTextField();
		jt_name.setBounds(126, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE, 144, TEXTFIELD_HEIGHT);
		getContentPane().add(jt_name);
		
		ButtonGroup bg1 = new ButtonGroup(); // створюємо группу взаємного виключення
		bg1.add(id);
		bg1.add(name);
		
		search = new JButton ("Пошук");
		search.setSize(100, 23);
		search.setLocation(getWidth()/2 - search.getWidth()/2, 150);
		getContentPane().add(search);
		search.addActionListener(handler);
		
		results = new JLabel("Результати:");
		results.setBounds(START_WIDTH_POINT, 180, 85, ELEMENT_HEIGHT);
		getContentPane().add(results);
		
		info = new JLabel();
		info.setBounds(160, 180, 120, ELEMENT_HEIGHT);
		getContentPane().add(info);
		info.setVisible(false);
				
		resultsList = new JList<Goods>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		resultsList.setVisibleRowCount(6);
		resultsJSP = new JScrollPane(resultsList);
		resultsJSP.setBounds(START_WIDTH_POINT, 210, 250, 125);
		getContentPane().add(resultsJSP);
		
		edit = new JButton ("Редагувати");
		edit.setBounds(30, 367, 100, 23);
		getContentPane().add(edit);
		edit.addActionListener(handler);
		
		add = new JButton ("Продано");
		add.setBounds(160, 367, 100, 23);
		getContentPane().add(add);
		add.addActionListener(handler);
	}
	
	public class eHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == search){
				
				ArrayList <Goods> tempGoods = new ArrayList<Goods>();
				
				if (id.isSelected()){
					
					if (jt_id.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Введіть код товару!");
						return;
					}
					
					int ID = Integer.parseInt(jt_id.getText());
					for (Goods g : Main.mf.goods)
						if (g.getID() == ID) tempGoods.add(g);
				}
				
				else if (name.isSelected()){
					
					if (jt_name.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Введіть найменування товару!");
						return;
					}
					
					String tempName = jt_name.getText().toLowerCase();
										
					for (Goods g : Main.mf.goods)
						if (g.getName().toLowerCase().contains(tempName)) tempGoods.add(g);
				}
				
				else return;
				
				Goods tempResults[] = new Goods[tempGoods.size()];
				for (int i = 0; i < tempResults.length; i++){
					tempResults[i] = tempGoods.get(i);
				}
				
				info.setText("знайдено " + tempResults.length + " товарів");
				info.setVisible(true);
				
				resultsList.setListData(tempResults);
			}
			
			if (e.getSource() == edit){
				if (resultsList.isSelectionEmpty())
					JOptionPane.showMessageDialog(null, "Оберіть товар");
				else{
					dispose();
					WinAvailableEdit dialog = new WinAvailableEdit (Main.mf, resultsList.getSelectedValue());
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			}

			if (e.getSource() == add){
				if (resultsList.isSelectionEmpty())
					JOptionPane.showMessageDialog(null, "Оберіть товар");
				else{
					dispose();
					
					Goods product = resultsList.getSelectedValue();
					WinSold dialog = new WinSold(Main.mf, product);
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			}
		}
		
	}
	
	
}
