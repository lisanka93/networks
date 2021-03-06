/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int		recvPort;
		int 		countTo;
		String 		message;

		// Get the parameters
		if (args.length < 3) 
		{
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
		} 
		catch (UnknownHostException e)
		{
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages

		UDPClient udpClient = new UDPClient();
		System.out.println("UDPClient created");
		udpClient.testLoop(serverAddr, recvPort, countTo);
	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try{
	    		sendSoc = new DatagramSocket();
		}
		catch(IOException e)
		{                     //or socketexception=
	   		System.out.println("Socket exception: " + e);
		}
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {

		// TO-DO: Send the messages to the server
	
		System.out.println("Sending messages..........");
		for (int i = 0 ; i <= countTo; i++)                                     //count to = value of messages i passed in command line
		{
	    		MessageInfo msg = new MessageInfo(countTo, i);                  //increments i until countTo
	    		send(msg.toString(), serverAddr, recvPort);
		}

	}

	private void send(String payload, InetAddress destAddr, int destPort) {
		int payloadSize = payload.length();       //initialise variables 
		byte[] pktData = payload.getBytes();         
		DatagramPacket pkt;

		// TO-DO: build the datagram packet and send it to the server

		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
		try {
	    		sendSoc.send(pkt);                                              //send message through the socket
		} 
		catch(IOException e)
		{
	    		System.out.println("IO exeption " + e);
		}
	}

}
