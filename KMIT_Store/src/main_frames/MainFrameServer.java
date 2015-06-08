package main_frames;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.Main;
import main.NetLib;

public class MainFrameServer extends MainFrame{
	
	MainFrameServer(String s) throws IOException{
		super(s);
		
		labelTitle.setText("KMIT Склад [Server]");
		
		connectedClients = new JLabel("Кількість підключених клієнтів: 0");
		connectedClients.setBounds(600, 10, 240, 15);
		connectedClients.setFont(new Font("Verdana", Font.BOLD, 12));
		jp1.add(connectedClients);
		
		addressIPandPorts = new JLabel();
		addressIPandPorts.setBounds(730, 510, 180, 50);
		addressIPandPorts.setFont(new Font("Verdana", Font.BOLD, 10));
		jp1.add(addressIPandPorts);
		
		NetLib nl = new NetLib();
		nl.start();//запускаємо сервер окремим потоком від головного вікна
		
		//window-listener
		removeWindowListener(getWindowListeners()[0]);
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
                    sql.closeConnection();
                    Main.mf.setPropertyValue("exit");
            		Main.mf.setPropertyValue("");
                    try {
						nl.interrupt();
                    	nl.closeServer();
					} catch (IOException e) {
					}
                    
                    System.exit(0);
                }
            }
 
            public void windowDeactivated(WindowEvent event) {}
 
            public void windowDeiconified(WindowEvent event) {}
 
            public void windowIconified(WindowEvent event) {}
 
            public void windowOpened(WindowEvent event) {}
 
        });
	}
	
}
