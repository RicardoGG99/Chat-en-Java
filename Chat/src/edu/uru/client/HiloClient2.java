package edu.uru.client;

import java.awt.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class HiloClient2 extends Thread {

	DataInputStream input = null;
	DataOutputStream output = null;
	TextArea TextArea = null;
	String usuario = null;
	String chat = null;
	String teclado = null;
	String path = null;
	Socket socket = null;
	
	public HiloClient2(DataInputStream input, DataOutputStream output, TextArea TextArea, String usuario, String chat, String teclado, String path, Socket socket) {
		this.input = input;
		this.output = output;
		this.TextArea = TextArea;
		this.usuario = usuario;
		this.chat = chat;
		this.teclado = teclado;
		this.path = path;
		this.socket = socket;
		
	}
	
	public void run() {
		
	try {
			
			input = new DataInputStream(socket.getInputStream()); 
			output = new DataOutputStream(socket.getOutputStream());
			
			output.writeUTF(usuario + ": " + teclado);
			output.flush();
			
			String mensaje = input.readUTF();
			if(chat.length() == 0) {
				TextArea.setText(mensaje);
			}
			else {
				TextArea.setText(chat + "\n" + mensaje);
			}
			
		}catch(Exception e2) {
			System.out.println(e2);
		}
		
	}
}
