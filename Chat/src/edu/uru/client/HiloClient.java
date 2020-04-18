package edu.uru.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class HiloClient extends Thread {
	
	ObjectOutputStream out = null;
	FileInputStream file = null;
	String teclado = null;
	Socket socket = null;
	
	
	public HiloClient(ObjectOutputStream out, FileInputStream file, String teclado, Socket socket) {
		this.out = out;
		this.file = file;
		this.teclado = teclado;
		this.socket = socket;
	}
	
	
	public void run() {
		
		try {
			
			out = new ObjectOutputStream(socket.getOutputStream());
			file =  new FileInputStream(teclado);
			byte [] buf = new byte[9999];
			
			int len = 0;
			
			while((len = file.read(buf))>0){
				out.write(buf,0,len);
				}
			
			JOptionPane.showMessageDialog(null, "El Envio se ha Realizado");
			
		    out.close();
		    file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	

	}
	
}
