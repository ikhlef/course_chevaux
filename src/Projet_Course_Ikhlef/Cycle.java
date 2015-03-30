package Projet_Course_Ikhlef;
import java.util.Date;

public class Cycle {
	private final long cycle;
	Date date;
	
	public Cycle(){
		date = new Date();
		cycle = date.getTime();
	}
	
	public long getCycle(){
		return cycle;
	}
	// la methode qui permet de tester si on peut envoyer un cycle ou pas, a chaque 30ms en envoie un cycle 
	public boolean cyclee(){
		if(((new Date()).getTime() - getCycle()) % 50==0){return true;
		}else{
		return false;
		}
	}
	
}
