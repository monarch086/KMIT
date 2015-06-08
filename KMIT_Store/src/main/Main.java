package main;

import javax.swing.*;

import main_frames.MainFrame;
import main_frames.WinStart;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Main {

	public static MainFrame mf;
	public static WinStart start;
	public static boolean rights = false;
	//в режимі сервера та офлайн встановлюється значення "true"
	public static boolean isServer = false;
	/*
	 * права доступу:
	 * true - адміністратор
	 * false - гість
	 */
	
	public static void main(String[] args){
		
		 /* Set the Nimbus look and feel */
		  try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		
		start = new WinStart("KMIT Склад");
		start.setVisible(true);
		start.setDefaultCloseOperation(mf.DO_NOTHING_ON_CLOSE);
		  
		

	}

}
