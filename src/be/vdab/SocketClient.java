package be.vdab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String userName;
	
	public SocketClient(){}
	
	public void createSocket(String hostName, int portNumber){
		try { 
			socket = new Socket(hostName, portNumber);
			out = new PrintWriter(socket.getOutputStream(),	true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			createWriteThread();
			createReadThread();
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}
	
	public void createWriteThread(){
		Thread writeThread =  new Thread(){
			public void run(){
				String fromUser;
			
				try(Scanner scanner = new Scanner(System.in);){
					
					if(userName == null) {
						System.out.print("username: ");
						userName = scanner.next();
					}

					while (true) {
						fromUser = scanner.nextLine();
						out.println(userName + ": " + fromUser);
					}
				}
			}
		};
		writeThread.start();
	}
	
	public void createReadThread(){
		Thread readThread = new Thread(){
			public void run(){
				String fromServer;
				
				try{
					while(true) {
						if((fromServer = in.readLine()) != null){
							System.out.println(fromServer);
						}
					}
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		};
		readThread.start();
	}

	public static void main(String[] args) {
		String hostNameArg = args[0];
		int portNumberArg = Integer.parseInt(args[1]);
		SocketClient socketClient = new SocketClient();
		socketClient.createSocket(hostNameArg, portNumberArg);
	}

}
