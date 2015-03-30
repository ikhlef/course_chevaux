package Projet_Course_Ikhlef;

import java.io.IOException;
import java.net.DatagramSocket;

public class TestServeur {

	private static DatagramSocket socket;

	public static void main(String[] args) throws IOException {
		int port=3038;	
		System.out.println("---------Course chevaux------------");
		socket = null;
		try{
			 socket=new DatagramSocket(port);
			 while(true){	
				 Moniteur t=new Moniteur(socket);			 
				 t.start();
			 }						
	}catch(IOException e ){
		System.out.println(e.toString());		
	}finally{
		//socket.close();	
	}
}
}