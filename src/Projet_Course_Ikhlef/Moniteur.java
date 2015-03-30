package Projet_Course_Ikhlef;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Moniteur extends Thread {
	InetAddress  groupeIP;
	DatagramSocket s;
	DatagramPacket packetenvoie, packetreception;
	MulticastSocket socketEmission;
	String requete, reponse;
	byte[] envoi = new byte[2024];
	byte[] reception = new byte[2024];
	int portserv= 3038;
	int pG = 4010;
	static int nbChArretes=0;
	static int nbChevaux=0;
	Tampon tampon ;   
	Cycle cc ;
	int nbrecycle=0;
boolean fini=true;

	public Moniteur(DatagramSocket socket) throws IOException {
		s=socket;
		tampon = new Tampon();
		cc = new Cycle();
		socketEmission = new MulticastSocket();
		socketEmission.setTimeToLive(15);
	}
// pour la diffusion du message cycle a tous les chevaux
	public  void diffuser(String s) throws IOException{
		groupeIP = InetAddress.getByName("224.1.2.3");
		byte[] b = s.getBytes();
		DatagramPacket  dp = new DatagramPacket(b , b.length , groupeIP , pG);
		socketEmission.send(dp);
		//socketEmission.close();
	}
	
	int portclient ;
	InetAddress adress;
	
	public int getportclient(){return portclient; }
	public InetAddress getAdress(){ return adress;}

	// pour la reception du pacqket envoyé par le cheval sur le port 3001
	
	public  String receptionpacket() throws IOException{
		packetreception	= new DatagramPacket(reception, reception.length);
		s.receive(packetreception);
		requete =  new String(packetreception.getData(), 0, packetreception.getLength());
		portclient = packetreception.getPort();
		adress = packetreception.getAddress();
		return requete;
	}
	public  DatagramPacket getpacket() throws IOException{
		packetreception	= new DatagramPacket(reception, reception.length);
		s.receive(packetreception);
		return packetreception;
	}
	
	public  void envoyerpacket(String chaine, InetAddress ad, int numport) throws IOException{
		envoi = chaine.getBytes();
		packetenvoie = new DatagramPacket(envoi, envoi.length, ad, numport);	
		s.send(packetenvoie);
		
	}
	
	public  String[] annalyserchaine(String chaine){
		return chaine.split(":");
		
	}
	public int donnerposition(String [] chaine){
		return Integer.parseInt(chaine[0].trim());
		
	}
	public synchronized void augmenternbreChevaux(){
		Moniteur.nbChevaux++;
	}
	public  synchronized int getnbChevaux(){
		return nbChevaux;
		
	}
	
	
	
	public class Traitement extends Thread{
		DatagramPacket valeur ;
		public Traitement(DatagramPacket ch){
			valeur= ch;
		}
		
		public void run(){
			try {
				traitement(valeur);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		public void traitement (DatagramPacket pace) throws IOException{
			String re =  new String(pace.getData(), 0, pace.getLength());
			int prte = pace.getPort();
			InetAddress adr = pace.getAddress();
			envoyerpacket("eeeeeeeeeee",adr , prte);
			if(!re.equals("qualification")){
				envoyerpacket(re,adr , prte);
				//double vitesse=0;
				//int dist = Integer.parseInt(re);
				//try{
					//vitesse = (double)(dist/(50*nbrecycle));
					//String s= String.valueOf(vitesse);	
					//if(vitesse!=0)
					//String s = String.valueOf(dist);
				//	envoyerpacket("alloooo", adr, prte);
				//}catch(Exception e){}	
				}
			envoyerpacket("mmmmmmmmmmmmm",adr , prte);
			}
	}
	
	
public void run(){
	
	String recep=null;
	
	boolean connecte=true;
	try {	 
	while(true){	
		recep = receptionpacket();
		if(recep.equals("qualification")){
			envoyerpacket("yes", getAdress(), getportclient());
			// annalyser la reponse du cheval , pour stocker les donnees de départ dans le tampan, les données sont: nom et position.
			String nom = receptionpacket();
			tampon.ajoutercheval(nom,getportclient());
			augmenternbreChevaux();
			try {
		         Thread.sleep(2000);
		      } catch (InterruptedException ie) {}
			
		}	
		//while(getnbChevaux()>0){
			
			while(connecte){
				if(cc.cyclee()){
				diffuser("cycle");		
				nbrecycle++;	
				//(new Traitement(getpacket())).start();
				recep = receptionpacket();
				
				}
				//envoyerpacket("1111111111", getAdress(), getportclient());
				
				if(!recep.equals("qualification")){
					
					//int posit = Integer.parseInt(recep);	
					//if(posit>50){
	 					//String s= String.valueOf(posit);	
	 				envoyerpacket(""+tampon.gettaille(), getAdress(), getportclient());	
	 				//envoyerpacket(s, getAdress(), getportclient());	
					
					//}
					
	 			}
				try {
			         Thread.sleep(100);
			      } catch (InterruptedException ie) {}
				
				
						
	}		
	} 			/*
 					double vitesse=0;
	 					try{
	 						vitesse = (double)(posit/(50*nbrecycle));
	 					}catch(Exception e){}
	 					System.out.println("voila la vitesse"+vitesse);
	 				String s= String.valueOf(vitesse);
					
 					String chai = nomm+":"+vitesse;*/
 				//	envoyerpacket(recep, getAdress(), getportclient());
 		 			
	 			//}
	 			
	 			//
	 			//envoyerpacket("disqualification", getAdress(), getportclient());
	 			
			
	 		
	 		/*	
	 				if(vitesse>3){	
	 					 envoyerpacket("disqualification", getAdress(), getportclient());
	 					 Moniteur.nbChevaux--;
	 					 Moniteur.nbChArretes++;
	 					 tampon.delete(nomm);
	 				     fini=false;
	 				     break;
	 				}else if(vitesse<0.5){
		 					 envoyerpacket("disqualification", getAdress(), getportclient());
		 					 Moniteur.nbChevaux--;
		 					 Moniteur.nbChArretes++;
		 					 tampon.delete(nomm);
		 				     fini=false;
		 				     break;
	 				
	 				} else if(vitesse<1){
		 						  envoyerpacket("acceleration", getAdress(), getportclient());
	 				}else if(vitesse>2.5){
	 					  	  envoyerpacket("deceleration", getAdress(), getportclient());
	 				  }else{
	 					  this.tampon.modifierposition(nomm, pos);
	 					  String [] tabnom=this.tampon.verifierposition(nomm);
	 					  int [] tabport = this.tampon.donnerport(tabnom);
	 					  if(tabport!=null){
	 					  for(int d=0; d<tabport.length;d++){
	 						 envoyerpacket(nomm, getAdress(), tabport[d]);
	 					  }
	 					  }
	 					  else{
	 					  	  envoyerpacket("depasser", getAdress(), getportclient());
	 					  	} else{
	 							  envoyerpacket("acceleration", getAdress(), getportclient());
	 				 				
	 			    }
	 				}*/
	//	fini=false;
	 //	}		
	 
	} catch (IOException e1) {
		e1.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		s.close();
		socketEmission.close();
		
	}
	
	/*try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}*/
}
	
}
