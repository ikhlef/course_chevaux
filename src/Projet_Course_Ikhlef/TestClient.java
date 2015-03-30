package Projet_Course_Ikhlef;

import java.io.IOException;
import java.net.SocketException;

public class TestClient {
	public static void main(String[] args) throws IOException {
		String[] nom = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"};

		for(int i=0; i<5;i++){
			try {
				Cheval c= new Cheval(nom[i]);
				c.start();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}
}
