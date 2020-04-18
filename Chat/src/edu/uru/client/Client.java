package edu.uru.client;


import java.awt.EventQueue;





import javax.swing.JFrame;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Client {

	private JFrame frame;
	private JTextField Textfield;
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	ObjectOutputStream out = null;
	FileInputStream file = null;
	Calendar fecha1 = Calendar.getInstance();
	Date date = fecha1.getTime();
	String fecha = date.toString();
	String usuario = null;	
	String path = null;
	final String url = "jdbc:postgresql://localhost:5432/chat";
    final String user = "postgres";
    final String password = "17395";
    Connection conn = null;
    Statement st = null;
    int valorDado = (int) Math.floor(Math.random()*1000000);
    String valorRandom = Integer.toString(valorDado);

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Client() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		TextArea TextArea = new TextArea();
		TextArea.setEditable(false);
		TextArea.setBounds(10, 35, 414, 175);
		frame.getContentPane().add(TextArea);
		
		JOptionPane.showMessageDialog(null, "Por Favor, Digite el Usuario del Cliente");
		
		JButton SendButton = new JButton("Send");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String chat = TextArea.getText();
				String teclado = Textfield.getText();
				
				try {
					
					if(socket == null) {
						socket = new Socket("localhost", 9806);	
						
						if(teclado.length() == 0) {
							usuario = "InvitadoClient";
						}else {
							usuario = teclado;
							Textfield.setText("");
						}
					}
					else {
						
						HiloClient2 envio = new HiloClient2(input,  output,  TextArea,  usuario,  chat,  teclado,  path,  socket);
						envio.start();
						
						HiloClient1 DataBase = new HiloClient1(url,  user,  password,  usuario,  fecha,  path,  valorRandom,  conn,  st,  teclado,  Textfield,  valorDado);
						DataBase.start();
						
						
							
						
					}
					
					
					
				}catch(Exception e1) {
					System.out.println(e1);
				}
				
				
			}
		});
		SendButton.setFont(new Font("Arial", Font.BOLD, 12));
		SendButton.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(SendButton);
		
		Textfield = new JTextField();
		Textfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Textfield.setBounds(10, 227, 301, 21);
		frame.getContentPane().add(Textfield);
		Textfield.setColumns(10);
		
		JLabel ClientLabel = new JLabel("Client");
		ClientLabel.setBackground(Color.WHITE);
		ClientLabel.setForeground(Color.BLACK);
		ClientLabel.setFont(new Font("Arial", Font.BOLD, 12));
		ClientLabel.setBounds(183, 11, 46, 14);
		frame.getContentPane().add(ClientLabel);
		
		JButton ConnectionButton = new JButton("Archivo");
		ConnectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String teclado = Textfield.getText();
			
					HiloClient archivo = new HiloClient(out, file, teclado, socket);
					archivo.start();
					
					HiloClient1 DataBase = new HiloClient1(url, user, password, usuario, fecha, path, valorRandom, conn, st, teclado, Textfield, valorDado);
				    DataBase.start();
				
			}
		});
		ConnectionButton.setFont(new Font("Arial", Font.BOLD, 12));
		ConnectionButton.setBounds(306, 11, 118, 19);
		frame.getContentPane().add(ConnectionButton);
		
		JButton CleanButton = new JButton("Clean");
		CleanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextArea.setText("");
			}
		});
		CleanButton.setFont(new Font("Arial", Font.BOLD, 12));
		CleanButton.setBounds(10, 7, 89, 23);
		frame.getContentPane().add(CleanButton);
	}
}
