package edu.uru.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HiloClient1 extends Thread {
	
	String url = null;
    String user = null;
    String password = null;
    String usuario = null;
    String fecha = null;
    String path = null;
    String valorRandom = null;
    Connection conn = null;
    Statement st = null;
    String teclado = null;
    JTextField Textfield = null;
    int valorDado = 0;
	
	public HiloClient1(String url, String user, String password, String usuario, String fecha, String path, String valorRandom, Connection conn, Statement st, String teclado, JTextField Textfield, int valorDado) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.usuario = usuario;
		this.fecha = fecha;
		this.path = path;
		this.valorRandom = valorRandom;
		this.conn = conn;
		this.st = st;
		this.teclado = teclado;
		this.Textfield = Textfield;
		this.valorDado = valorDado;
		
	}
	
	public void run(){
		
		path = teclado;
		valorRandom = Integer.toString(valorDado);
		
		try {
			conn = DriverManager.getConnection(url, user, password);
	    	JOptionPane.showMessageDialog(null, "La Conexion es Exitosa");
	    	st = conn.createStatement();
	    	st.executeUpdate("INSERT INTO chat(usuario, fecha, mensaje_o_path, random)VALUES('"+usuario+"','"+fecha+"','"+path+"','"+valorRandom+"')");
	    	JOptionPane.showMessageDialog(null, "Se Ingresaron Los Datos en La Base de Datos con Exito");
	    	conn.close();
			
		}
		 catch(SQLException e1) {
    	    	System.out.println(e1.getMessage());
    	    	JOptionPane.showMessageDialog(null, "No se Pudo Realizar la Conexion");
		 }
		
		Textfield.setText("");
		
		
		
		
		
	}
}
