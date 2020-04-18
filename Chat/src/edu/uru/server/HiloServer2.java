package edu.uru.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class HiloServer2 extends Thread{
	
	ObjectInputStream in = null;
	FileOutputStream file = null;
	String teclado = null;
	Socket socket = null;
	
	public HiloServer2(ObjectInputStream in, FileOutputStream file, String teclado, Socket socket) {
		this.in = in;
		this.file = file;
		this.teclado = teclado;
		this.socket = socket;
	}

	
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			file =  new FileOutputStream(teclado);
			byte [] buf = new byte[9999];
			
			int len = 0;
			
			while((len = in.read(buf))>0){
				file.write(buf,0,len);
			}
			
			JOptionPane.showMessageDialog(null, "El Envio se ha Realizado");
			
			in.close();
			file.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
