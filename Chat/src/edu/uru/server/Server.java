package edu.uru.server;

import java.awt.EventQueue;






import javax.swing.JFrame;
import java.awt.TextArea;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Server {

	private JFrame frame;
	private JTextField Textfield;
	ServerSocket server = null;
	Socket socket = null;
	DataInputStream input = null;
	DataOutputStream output = null;
	ObjectInputStream in = null;
	FileOutputStream file = null;
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
					Server window = new Server();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Server() {
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
		TextArea.setBounds(10, 38, 414, 181);
		frame.getContentPane().add(TextArea);
		
		JOptionPane.showMessageDialog(null, "Por Favor, Digite el Usuario del Servidor");
		
		JButton SendButton = new JButton("Send");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String chat = TextArea.getText();
				String teclado = Textfield.getText();
				
				try {
					
					if(server == null && socket == null) {
						server = new ServerSocket(9806);
						JOptionPane.showMessageDialog(null, "Servidor Iniciado");
					
						socket = server.accept();
						
						JOptionPane.showMessageDialog(null, "La Direccion del Cliente es: " +  socket.getRemoteSocketAddress().toString());
						JOptionPane.showMessageDialog(null, "Cliente Conectado");
						
						if(teclado.length() == 0) {
							usuario = "InvitadoServer";
						}else {
							usuario = teclado;
							Textfield.setText("");
						}
						
					}else {
						
						HiloServer1 envio = new HiloServer1(input,  output,  TextArea,  usuario,  chat,  teclado,  path,  socket, server);
						envio.start();
						
						HiloServer DataBase = new HiloServer(url,  user,  password,  usuario,  fecha,  path,  valorRandom,  conn,  st,  teclado,  Textfield,  valorDado);
						DataBase.start();

						
						
					}
					
					
				}catch(Exception e1) {
					System.out.println(e1);
				}
				
			}
		});
		SendButton.setFont(new Font("Arial", Font.BOLD, 12));
		SendButton.setBounds(335, 225, 89, 23);
		frame.getContentPane().add(SendButton);
		
		JButton ConnectionButton = new JButton("Archivo");
		ConnectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String teclado = Textfield.getText();
				
				HiloServer2 archivo = new HiloServer2(in, file, teclado, socket);
				archivo.start();
				
			    HiloServer DataBase = new HiloServer(url, user, password, usuario, fecha, path, valorRandom, conn, st, teclado, Textfield, valorDado);
			    DataBase.start();
				
			}
		});
		ConnectionButton.setFont(new Font("Arial", Font.BOLD, 12));
		ConnectionButton.setBounds(306, 11, 118, 21);
		frame.getContentPane().add(ConnectionButton);
		
		Textfield = new JTextField();
		Textfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Textfield.setBounds(10, 225, 315, 21);
		frame.getContentPane().add(Textfield);
		Textfield.setColumns(10);
		
		JLabel ServerLabel = new JLabel("Server");
		ServerLabel.setFont(new Font("Arial", Font.BOLD, 12));
		ServerLabel.setBounds(183, 14, 46, 14);
		frame.getContentPane().add(ServerLabel);
		
		JButton CleanButton = new JButton("Clean");
		CleanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextArea.setText("");
			}
		});
		CleanButton.setFont(new Font("Arial", Font.BOLD, 12));
		CleanButton.setBounds(10, 10, 89, 23);
		frame.getContentPane().add(CleanButton);
	}

}
