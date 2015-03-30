package Projet_Course_Ikhlef;

public class Exemple {

	public static void main(String[] args) {
		String chaine = "A:2.5:12";
		String [] tab =chaine.split(":");
		System.out.println(tab.length);
		for(int i=0;i<tab.length;i++)
		   System.out.println(tab[i] + tab.length);
	}

}
