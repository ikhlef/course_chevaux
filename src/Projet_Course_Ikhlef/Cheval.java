package Projet_Course_Ikhlef;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Cheval extends Thread {
	byte[] envoi = new byte[2024];
	byte[] reception = new byte[2024];
	byte[] cycle = new byte[2024];
	  InetAddress adressserveur; 	
	  InetAddress  groupeIP ;
	 int pG = 4010;
	 int port = 3038;
	 double  cpt=0;
	 double vitesse=0;
	 int position=0;
	 int cyc = 50;
	 DatagramSocket s;
	 MulticastSocket socketReception;
	 DatagramPacket packetenvoie, packetreception, packetcycle;
	 
	 String name, requete, reponse, cycleposition;
	 boolean arret = false, arrive = false;
	
	 public Cheval(String a) throws IOException{
		 s= new DatagramSocket();
			name=a;
			 adressserveur = InetAddress.getLocalHost(); 
			groupeIP = InetAddress.getByName("224.1.2.3");
			  socketReception = new MulticastSocket(pG);
			  socketReception.joinGroup(groupeIP);
			  vitesse =(double)(Math.random()*2.5)+0.5;
	}
	 
	public String getname(){return name;}
	public double getvitesse(){return vitesse;}
	
	public int position(){
		position=(int ) (vitesse * cpt * cyc);
		return position ; }
	
	public int getposition(){
		return position; }
	
	public  boolean receptionCycle() throws IOException{
		packetcycle = new DatagramPacket(cycle, cycle.length);
		socketReception.receive(packetcycle);
	     cycleposition = new String(packetcycle.getData(), 0, packetcycle.getLength());
		 if(cycleposition.equals("cycle")){
			 cpt++;
			 return true;
		 }
		return false;
	}
	
	public  void envoyerpacket(String chaine) throws IOException{
		envoi = chaine.getBytes();
		packetenvoie = new DatagramPacket(envoi, envoi.length, adressserveur, port);	
		s.send(packetenvoie);
		
	}
	
	public  String receptionpacket() throws IOException{
		packetreception	= new DatagramPacket(reception, reception.length);
		s.receive(packetreception);
		reponse =  new String(packetreception.getData(), 0, packetreception.getLength());
		return reponse;
	}
	public void enregistrerCheval(String s) throws IOException{
		envoyerpacket(s);
	//	augmenternbreChevaux();
	}
	
	public synchronized void augmenternbreChevaux(){
		Moniteur.nbChevaux++;
	}
	public synchronized void diminunbreChevaux(){
		Moniteur.nbChevaux--;
	} 
	public synchronized void augmenternbreChevauxarret(){
		Moniteur.nbChArretes++;
	}
	
	// accelerer
	public void accelerer(){
		vitesse =getvitesse() + 0.5;
	}
	// decelerer
	public void decelerer(){
		if(getposition()>80 && getvitesse()>2.9){
		vitesse =getvitesse() - 0.5;
		}
	}
	// changer allure
	public void changerallure(){
	     if(getvitesse()< 3){
	    	 accelerer();
	     }	
	}
	
	// arret apres epuisement
	public boolean arretparpuisement(){
		if(getposition()>130 && getvitesse()>2.8){
			augmenternbreChevauxarret();
			diminunbreChevaux();
			return true;
		}
		return false;
	}
	boolean connecte=true;
	
	public void run(){
		String pac,rep=null;
		try {  // phase de qualification , participation a la course
			envoyerpacket("qualification");
			// pac =receptionpacket();
			//if(pac.equals("yes")){
				String enr = getname();
				enregistrerCheval(enr);	
				System.out.println("enregitrer"+ getname() );	
				try {
	 		          Thread.sleep(2000);
	 		        } catch (InterruptedException ie) {}
			//}
				
				while(connecte){
					if(receptionCycle()){
						position();
						//String pos = String.valueOf(getposition())+":"+getname();
						String pos = String.valueOf(getposition());
						envoyerpacket(pos);
					}
					rep= receptionpacket();			
						
					if(rep!=null){
						System.out.println(getname() +" )  la course donnee:  "+rep);
						}
					try {
		 		          Thread.sleep(100);
		 		        } catch (InterruptedException ie) {}
					
						
					//if(pacc.equals("disqualification")){
					
				}
			
					
				/*	
				   //synchronized(pacc){
					if(pacc!=null){
					
					else if(pacc.equals("deceleration")){
						decelerer();
						System.out.println(getname() +" ) deceleration."+getposition()+"vitesse"+ getvitesse());
					
					}else if(pacc.equals("acceleration")){
						accelerer();
						if(arretparpuisement()){
							System.out.println(getname() +" ) epuisement."+getposition()+"vitesse"+ getvitesse());
							arret= true;
							break;
						}else{
						System.out.println(getname() +" ) acceleration."+getposition()+"vitesse"+ getvitesse());
						}
					}else if(pacc.equals("gagant")){
						System.out.println(getname() +" ) :qualifier pour la prochaine etape." +getposition()+"vitesse"+ getvitesse());
						arret= true;
						break;
					}else{
						System.out.println(getname() +" ) depasser par: " + pacc);
						changerallure();
						System.out.println(getname() +" ) changerallure."+getposition()+"vitesse"+ getvitesse());
					} 
					} }	
			   }*/
			
			
			}catch (IOException e1) {
				e1.printStackTrace();
			}finally{
				s.close();
				socketReception.close();
			}
		
	}
}
