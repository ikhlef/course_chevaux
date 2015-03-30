package Projet_Course_Ikhlef;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre {
	
	JFrame fenetre ;
	JTextField jtf1;
	JTextField jtf2;
	JTextField jtf3;
	public Fenetre(String titre) {
	//création de la fenetre
	fenetre = new JFrame(titre);
	fenetre.setBounds(50,50,1300,200);//en pixels
	//fenetre.setSize(1300, 180);
	//récupération du conteneur de la fenêtre
	Container contenu =fenetre.getContentPane();
	//Création du panneau pour mettre des composants
	JPanel panneauBtn = new JPanel();
	jtf1 = new JTextField();
	jtf2 = new JTextField();
	jtf3 = new JTextField();
	
	contenu.setLayout(new GridLayout(2,1));
	//ajout de tous les elements dans le conteneur rattaché à la fenêtre
	contenu.add(jtf1);
	contenu.add(jtf2);
	//contenu.add(jtf3);
	
	//fenetre.pack();fenetre.p
	fenetre.setVisible(true);
	}

}
