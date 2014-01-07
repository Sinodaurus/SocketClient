package be.vdab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	public static void main(String[] args) {
//		String hostName = args[0];
//		int portNumber = Integer.parseInt(args[1]);

		String hostName = "192.168.56.1";
		int portNumber = 8082;

		try (Socket socket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String fromServer;
			String fromUser;

			while (true) {
				System.out.print("Client: ");
				if((fromUser = stdIn.readLine()) != null) {
					out.println(fromUser);
				
				if ((fromServer = in.readLine()) != null) {
					System.out.println("Server: " + fromServer);
					}
				//stdIn.readLine();
				}
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}

}
