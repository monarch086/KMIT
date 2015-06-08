package dialog_frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;

import dialog_frames.WinStatSold.eHandler;
import main.Main;

public class WinStatAvailable extends WinStatSold{
	
	eHandler handler = new eHandler();
	JButton newShow; 
	
	public WinStatAvailable(java.awt.Frame parent) {
		super(parent);
		
		setSize(450, 545);
		
		title.setText("Статистика наявних товарів");
		
		getContentPane().remove(check);
		getContentPane().remove(period1JT);
		getContentPane().remove(period);
		getContentPane().remove(period2JT);
		getContentPane().remove(show);
		
		checkGoods.setLocation(START_WIDTH_POINT, 210);

		newShow = new JButton ("Показати");
		newShow.setSize(100, 23);
		newShow.setLocation(getWidth()/2 - newShow.getWidth()/2, 255);
		newShow.addActionListener(handler);
		getContentPane().add(newShow);
		
		results.setText("Наявні товари:");
		results.setLocation(START_WIDTH_POINT, 295);
		resultsJSP.setLocation(getWidth()/2 - resultsJSP.getWidth()/2, 325);
		ok.setLocation(getWidth() - START_WIDTH_POINT - ok.getWidth(), 472);

	}
	
	public class eHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == newShow){
				
				ArrayList statCollection;
				
				if (allRB.isSelected()){
										
					if (checkGoods.isSelected()) 
						statCollection = Main.mf.getStatisticsAvailable(1, null, true);
					else
						statCollection = Main.mf.getStatisticsAvailable(1, null, false);
					Object[] statArray = statCollection.toArray();
					resultsList.setListData(statArray);
				
				}
				
				else if (groupRB.isSelected()){
				
					if (checkGoods.isSelected())
						statCollection = Main.mf.getStatisticsAvailable(2, (String)groupCB.getSelectedItem(), true);
					else
						statCollection = Main.mf.getStatisticsAvailable(2, (String)groupCB.getSelectedItem(), false);
					Object[] statArray = statCollection.toArray();
					resultsList.setListData(statArray);

				}
				
				else if (subgroupRB.isSelected()){
					
					if (checkGoods.isSelected())
						statCollection = Main.mf.getStatisticsAvailable(3, (String)subgroupCB.getSelectedItem(), true);
					else
						statCollection = Main.mf.getStatisticsAvailable(3, (String)subgroupCB.getSelectedItem(), false);
					Object[] statArray = statCollection.toArray();
					resultsList.setListData(statArray);
					
				}
				
				else if (goodsRB.isSelected()){
						
					if (checkGoods.isSelected())
						statCollection = Main.mf.getStatisticsAvailable(4, (String)goodsCB.getSelectedItem(), true);
					else
						statCollection = Main.mf.getStatisticsAvailable(4, (String)goodsCB.getSelectedItem(), false);
					Object[] statArray = statCollection.toArray();
					resultsList.setListData(statArray);
					
				}
				
				else return;
			}
		}
	}
}
