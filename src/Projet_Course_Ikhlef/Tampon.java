
package Projet_Course_Ikhlef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tampon {
 
	public HashMap<String,ArrayList<Integer>> listeportadresse;
	public HashMap<Integer,String> listeportnom;
	public HashMap<Integer,Integer> listeportposition;
public Tampon (){
	listeportnom = new HashMap<Integer,String>();
	listeportposition = new HashMap<Integer,Integer>();
	
}
// inserer un cheval dans la table 
// la table : port: nom, et port : position

public synchronized void ajoutercheval(String s, int port) throws InterruptedException{
	 listeportnom.put(port,s);
	 listeportposition.put(port, 0);
}
//supprimer un cheval
public synchronized void supprimer(int s){
	listeportnom.remove(s);
	listeportposition.remove(s);
}
//modifier la position dun cheval by port 
public synchronized void modifierposition(int port, int pos){
	 if(pos>listeportposition.get(port)){
		 listeportposition.put(port, pos);
	 }
}
public synchronized int gettaille(){
	return listeportnom.size();
}

//reccueperer la liste des ports , pour informer les chevaux qu'un cheval les a depassé

public synchronized int [] verifierposition(int port, double pos){
	int[] tab={};
	int i=0;
	for (Map.Entry<Integer, Integer> entry : listeportposition.entrySet()) {
		int b = entry.getKey();
		if((port!=b) && pos >listeportposition.get(b)){
			tab[i]=b;
			i++;
		 }
	}
	return tab;
}
}
