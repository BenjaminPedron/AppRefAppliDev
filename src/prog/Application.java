package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Le client se connecte à un serveur dont le protocole est 
 * menu-choix-question-réponse client-réponse service
 * la réponse est saisie au clavier en String
 **/
class Application {
	
		private final static int PORT_SERVICE = 3001;
		private final static String HOST = "localhost";
	
	public static void main(String[] args) {
		Socket s = null;		
		try {
			/* On créé la socket allant questionner le serveur */
			s = new Socket(HOST, PORT_SERVICE);

			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			
			System.out.println("Connecté au serveur " + s.getInetAddress() + ":"+ s.getPort());
			
			String line;
			/* Menu et choix du service */
			line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
			sout.println(clavier.readLine());

			/* Tant que l'application ne recoit pas succes ou fail, on attend le serveur */
			while(true) {
				line = sin.readLine();
				if(line.equals("succes") || line.equals("fail")) {
					System.out.println(line.replaceAll("##", "\n"));
					break;
				}
				System.out.println(line.replaceAll("##", "\n"));
				sout.println(clavier.readLine());
			}
			
		}
		catch (IOException e) { 
			System.err.println("Fin de la connexion"); 
		}
		/* Referme dans tous les cas la socket */
		try { 
			if (s != null) s.close(); 
		} catch (IOException e2) {}		
	}
}
