package main_frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrameClient extends MainFrame{
	private static final long serialVersionUID = 1L;

	MainFrameClient(String s) throws IOException{
		super(s);
		
		labelTitle.setText("KMIT Склад [Client]");
		setSize(900, 690);
		
		//завантажуємо малюнки для нових кнопок та іконки
		Icon setConnectionIcon = new ImageIcon("images\\set_connection.png");
		Icon loadDataIcon = new ImageIcon("images\\load_data.png");
		connectionTrueIcon = new ImageIcon("images\\connection_true.png");
		connectionFalseIcon = new ImageIcon("images\\connection_false.png");
		
		//створюємо нові кнопки та іконку
		jb_set_connection = new JButton(setConnectionIcon);
		jb_set_connection.setBounds(64*8+SPACE*18, 435, 64, 64);
		jb_set_connection.setToolTipText("Встановити з'єднання");
		
		jb_load_data = new JButton(loadDataIcon);
		jb_load_data.setBounds(64*9+SPACE*19, 435, 64, 64);
		jb_load_data.setToolTipText("Завантажити дані з сервера");
		
		connection = new JLabel(connectionFalseIcon);
		connection.setToolTipText("З'єднання відсутнє");
		connection.setBorder(BorderFactory.createEmptyBorder(5,0,5,20));
		
		addressIPandPorts = new JLabel();
		addressIPandPorts.setBounds(730, 510, 180, 50);
		addressIPandPorts.setFont(new Font("Verdana", Font.BOLD, 10));
		jp1.add(addressIPandPorts);
		
		//додаємо ActionListener
		jb_set_connection.addActionListener(handler);
		jb_load_data.addActionListener(handler);
		
		//додаємо кнопки до панелі
		jp1.add(jb_set_connection);
		jp1.add(jb_load_data);
		
		panelTitle.setLayout(new BorderLayout());
		labelTitle.setBorder(BorderFactory.createEmptyBorder(10,330,10,0));
		panelTitle.add(labelTitle, BorderLayout.CENTER);
		panelTitle.add(connection, BorderLayout.EAST);
		
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
                                       
                    try{
                    	nl.closeConnection();
                    } catch (java.lang.NullPointerException e){
                    	System.out.println("КЛІЄНТ НЕ ЗАКРИТО");
                    }
                    
                    try{
                    	nl.CT.interrupt();
                    } catch (java.lang.NullPointerException e){
                    	System.out.println("КЛІЄНТСЬКИЙ ПОТІК НЕ ЗАКРИТО");
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
