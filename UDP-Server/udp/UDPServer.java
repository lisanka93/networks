/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.InetAddress;
import java.util.Arrays;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages;
	private int[] receivedMessages;


	private void run() {
		int pacSize;
		byte[]	pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

		pacSize = 1024;
		pacData = new byte[pacSize];                                                   //init array size
	
		try{
			while(true)
			{
			
				pac = new DatagramPacket(pacData, pacSize);                    //creating new DP
			
			try {
				recvSoc.setSoTimeout(60000);                                   //timeout
				recvSoc.receive(pac);          		                       //Socket receives DP
		    		
		    		byte[] bytes = pac.getData(); 
		    		String data = new String(bytes);	                       //convert byte array into string
				processMessage(data);					       //processing...
			}  
			catch (SocketTimeoutException e)
			{	
		    		System.out.println("Socket timeout!");                         //catching timeoutExcp
		    	
				printSummary();                                                //printing summary - wrote extra function
				System.out.println("bye!");
				System.exit(0);                                                //exiting system otherwise I keep receiving errormessages after each timeout
			} 
	   	 }
	   	 			 
		} 
		catch (SocketException e) 
		{ 
		  	System.out.println("Socket exception: " + e);                          //catching Socket exception
		} 
		catch (IOException e)
		{ 
	    		System.out.println("Exception: " + e);                                 //incase input data is corrupted
		}  
    } 

	public void processMessage(String data) {

		// TO-DO: Use the data to construct a new MessageInfo object
		
		MessageInfo msg = null;                                                        //creating new MI
		try {
			// TO-DO: On receipt of first message, initialise the receive buffer
			//if received messages array not initialised, initialise it
		
			
			msg = new MessageInfo(data.trim());                                    //that took me a while to figure out!! why is there so much whitespace??
			
			if(receivedMessages == null) 
			{
				totalMessages = 0;                                             //counter for received messages
				receivedMessages = new int[msg.totalMessages];                 //initialise receivedMessages array to zero
			}
			
			// TO-DO: Log receipt of the messag
		
			receivedMessages[msg.messageNum-1] = 1;                                //initialise to 1 if received   
			totalMessages++;   

			System.out.println("message received: " + (msg.messageNum));							

			// TO-DO: If this is the last expected message, then identify
			//        any missing messages

			if(totalMessages == msg.totalMessages)
			{
		        	printSummary();
			}
		}
		catch (Exception e)
		{ 
	    		System.out.println("lets go!"); 
		} 
	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try{ 
	   		 recvSoc = new DatagramSocket(rp);                                     //socket created
		} 
		catch (SocketException e)
		{ 
	  		System.out.println("Error initialising UDP socket" + e); 
		} 

		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;;
			
		try {
			InetAddress address = InetAddress.getLocalHost();                              //printing IP to screen
			String ipAddress = address.getHostAddress();
			System.out.println("Host " + ipAddress);
		}
		catch(Exception e)
		{
			System.out.println("Error finding host address" + e);
		}	


		// Get the parameters from command line
		if (args.length < 1) 
		{
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		
		UDPServer server = new UDPServer(recvPort); 
		server.run(); 
	}
	
	
	public void printSummary(){

			
		if(receivedMessages == null || totalMessages <= 0)
		{
			System.out.println("all good!");
				return;
		}
			
			
		int missingMessages = 0;
		for(int i = 0; i < receivedMessages.length; i++)
		{
			if(receivedMessages[i] != 1)                            	       //check if initialised to 1, if not then not received
			{
				missingMessages++;
			}
		}
				
		System.out.println(".......................... S U M MA R Y ......................");
		System.out.println("Number of messages received: " + totalMessages);
			
		if(totalMessages == receivedMessages.length)
		{
			System.out.println("No missing messages!");                	      //receivedcount as long as arraysize = no went missing!
		}
		else
		{
			System.out.println("Lost Messages: " + missingMessages);
		}
		
		receivedMessages = null;                                                      //reset values
		totalMessages = -1;



	}

}
