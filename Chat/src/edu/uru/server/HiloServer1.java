package edu.uru.server;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HiloServer1 extends Thread{
	
	DataInputStream input = null;
	DataOutputStream output = null;
	TextArea TextArea = null;
	String usuario = null;
	String chat = null;
	String teclado = null;
	String path = null;
	Socket socket = null;
	ServerSocket server = null;
	
	
	
	
	public HiloServer1(DataInputStream input, DataOutputStream output, TextArea TextArea, String usuario, String chat, String teclado, String path, Socket socket, ServerSocket server) {
		this.input = input;
		this.output = output;
		this.TextArea = TextArea;
		this.usuario = usuario;
		this.chat = chat;
		this.teclado = teclado;
		this.path = path;
		this.socket = socket;
		this.server = server;
	}
	
	public void run() {
		
		try {
			
			input = new DataInputStream(socket.getInputStream()); 
			output = new DataOutputStream(socket.getOutputStream());
			
			String mensaje = input.readUTF();
			
			if(chat.length() == 0) {
				TextArea.setText(usuario + ": " + teclado + "\n" + mensaje);	
			}else {
				TextArea.setText(chat + "\n" + usuario + ": " + teclado + "\n" + mensaje);
			}
			
			 
			output.writeUTF(usuario + ": " + teclado + "\n" + mensaje);
			
		}catch(Exception e2) {
			System.out.println(e2);
		}
		
	}

}
