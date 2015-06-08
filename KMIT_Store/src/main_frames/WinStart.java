package main_frames;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import main.Main;
import main.SQLlib;

public class WinStart extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JLabel title, user, password;
	JComboBox<String> userCombo;
	JPasswordField passField;
	JRadioButton offline, server, client;
	ButtonGroup bg;
	JButton ok, cancel;
	
	eHandler handler = new eHandler();
	
	SQLlib sql;
	
	final int START_HEIGHT_POINT = 100;
	final int START_WIDTH_POINT = 50;
	final int ELEMENT_HEIGHT = 20;
	final int TEXTFIELD_HEIGHT = 25;
	final int SPACE = 15;
	
	public WinStart(String s){
		super(s);
		
		setLocation(500, 200);
		setSize(450, 400);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Вхід до програми");
		title.setHorizontalAlignment(SwingConstants.CENTER); //розташовуємо напис по центру
		title.setFont(new Font("Verdana", Font.BOLD, 15));
		title.setSize(150, 30);
		title.setLocation(getWidth()/2 - title.getWidth()/2, 11);
		getContentPane().add (title);
		
		user = new JLabel("Користувач:");
		user.setBounds(START_WIDTH_POINT*2, START_HEIGHT_POINT, 85, ELEMENT_HEIGHT);
		getContentPane().add(user);
		
		userCombo = new JComboBox<String>();
		String string[] = {"Адміністратор", "Гість"};
		userCombo.setModel(new DefaultComboBoxModel<String>(string));
		userCombo.setBounds(195, START_HEIGHT_POINT, 146, TEXTFIELD_HEIGHT);
		userCombo.addActionListener(handler);
		getContentPane().add(userCombo);
		
		password = new JLabel("Пароль:");
		password.setBounds(START_WIDTH_POINT*2, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE, 
				85, ELEMENT_HEIGHT);
		getContentPane().add(password);
		
		passField = new JPasswordField();
		passField.setBounds(195, START_HEIGHT_POINT + ELEMENT_HEIGHT + SPACE, 
				146, TEXTFIELD_HEIGHT);
		getContentPane().add(passField);
		
		offline = new JRadioButton("Запуск offline-версії програми");
		offline.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*2 + SPACE*3, 
				220, ELEMENT_HEIGHT);
		offline.setSelected(true);
		getContentPane().add(offline);
		
		server = new JRadioButton("Запуск серверної версії програми");
		server.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*3 + SPACE*4, 
				220, ELEMENT_HEIGHT);
		getContentPane().add(server);
		
		client = new JRadioButton("Запуск клієнтської версії програми");
		client.setBounds(START_WIDTH_POINT, START_HEIGHT_POINT + ELEMENT_HEIGHT*4 + SPACE*5, 
				220, ELEMENT_HEIGHT);
		getContentPane().add(client);
		
		bg = new ButtonGroup(); // створюємо группу взаємного виключення
		bg.add(offline);
		bg.add(server);
		bg.add(client);
		
		ok = new JButton ("OK");
		ok.setSize(getWidth()/4, 23);
		ok.setLocation(getWidth()/6*2 - ok.getWidth()/2, 320);
		getContentPane().add(ok);
		ok.addActionListener(handler);
		
		cancel = new JButton ("Вийти");
		cancel.setSize(getWidth()/4, 23);
		cancel.setLocation(getWidth()/6*4 - cancel.getWidth()/2, 320);
		getContentPane().add(cancel);
		cancel.addActionListener(handler);
		
		sql = new SQLlib();
     	sql.initializeDB("data.db");
	}
	
	boolean checkPassword(){
    	char[] correctPass = {'1'};
    	char[] pass = passField.getPassword();
    	
    	if (pass.length != correctPass.length) return false;
    	
    	else 
    		for (int i=0; i<correctPass.length; i++){
    			if (pass[i] != correctPass[i]) return false;
    	}
    	return true;
    	
   }
	
	public class eHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == ok){
				
				if (userCombo.getSelectedItem().equals("Адміністратор")){
					if (checkPassword())					
						Main.rights = true;
					else{
						JOptionPane.showMessageDialog(null, "Введено невірний пароль");
						return;
					}
				}
				
				if (offline.isSelected()){
					Main.isServer = true;
					Main.mf = new MainFrame ("KMIT Склад - " + userCombo.getSelectedItem());
				} else if (server.isSelected())
					try {
						Main.isServer = true;
						Main.mf = new MainFrameServer ("KMIT Склад - " + userCombo.getSelectedItem());
					} catch (IOException e1) {e1.printStackTrace();
				} else if (client.isSelected())
					try {
						sql.closeConnection();
						Main.mf = new MainFrameClient ("KMIT Склад - " + userCombo.getSelectedItem());
					} catch (IOException e1) {e1.printStackTrace();}
					
				Main.mf.setVisible(true);
				Main.mf.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				
				dispose();
			}
			
			if (e.getSource() == cancel){
				sql.closeConnection();
				System.exit(0);
			}
			
			if (e.getSource() == userCombo){
				if (userCombo.getSelectedItem().equals("Гість")){
					password.setText(null);
					getContentPane().remove(passField);
				}
				
				if (userCombo.getSelectedItem().equals("Адміністратор")){
					password.setText("Пароль:");
					getContentPane().add(passField);
				}
			}
		}
	}
}
