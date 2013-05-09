import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerTCP implements Runnable {
	private Thread           thread = null;
	private ChatServerThread client = null;
	private ServerSocket serverSocket = null;
	public ArrayList<UserClass> users = new ArrayList<UserClass>();
	
        public ServerTCP(){
             ArrayList<UserClass> users = new ArrayList<UserClass>();
        }
        
        
	public ServerTCP(int port) {
		try {
			System.out.println("Binding to port, please wait  ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started");
			start();
		} catch(IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	   public void run()
	   {  while (thread != null)
	      {  try
	         {  System.out.println("Waiting for a client ..."); 
	            addThread(serverSocket.accept());
	         }
	         catch(IOException ie)
	         {  System.out.println("Acceptance Error: " + ie); }
	      }
	   }
	
	   
	   
	   public void addThread(Socket socket)
	   {  System.out.println("Client accepted: " + socket);
	      client = new ChatServerThread(this, socket);
	      try
	      {  client.open();
	         client.start();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error opening thread: " + ioe); }
	   }


	   public void start()
	   {  if (thread == null)
	      {  thread = new Thread(this); 
	         thread.start();
	      }
	   }
	   public void stop()
	   {  if (thread != null)
	      {  thread.stop(); 
	         thread = null;
	      }
	   }
	   

	   
	   
	   

		   public static void main(String args[])
		      {
			   ServerTCP server = null;

			         server = new ServerTCP(19193);
		         
		         
		      }
		  
		   
		}
