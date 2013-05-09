import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ChatServerThread extends Thread {
	   private Socket          socket   = null;
	   private ServerTCP      server   = null;
	   private int             ID       = -1;
	   private DataInputStream streamIn =  null;
           ArrayList<UserClass> user = new ArrayList<UserClass>();
	   String msg;
           private DataOutputStream outToServer;
	   public ChatServerThread(ServerTCP _server, Socket _socket)
	   {  server = _server;  socket = _socket;  ID = socket.getPort();
	   }
	   public void run()
	   {  System.out.println("Server Thread " + ID + " running.");
	      while (true)
	      {  try
	         { 
                     
	    	  msg = streamIn.readLine();
                  System.out.println(ID + " >>  " + msg);
                  
                  //dodaj pracownika
                  if(msg.equals("add")) {
                      String list = streamIn.readLine();
                      //System.out.println(list);
                  // Tworzymy obiekt StringTokenizer
                    StringTokenizer stringTokenizer = new StringTokenizer(list, ";");
                    while(stringTokenizer.hasMoreTokens())
                    {
                        String imie = stringTokenizer.nextToken();
                        String nazwisko = stringTokenizer.nextToken();
                        user.add(new UserClass(imie, nazwisko, true));
                        
                        System.out.println("Dodano "+imie+" "+nazwisko);
                        System.out.println(user.size());
                    }
                  }
	    	  
                  //skasuj pracownika
                  if(msg.equals("remove")) {
                      String list = streamIn.readLine();
                      //System.out.println(list);
                  // Tworzymy obiekt StringTokenizer
                    StringTokenizer stringTokenizer = new StringTokenizer(list, ";");
                    while(stringTokenizer.hasMoreTokens())
                    {
                        
                        System.out.println("Skasowano "+stringTokenizer.nextToken()+" "+stringTokenizer.nextToken());
                    
                    }
                  }
                  
                  //funkcje
                  if(msg.equals("givelist")) {
                      System.out.println("wyslij liste!");
                      String contacts="";
                      for(int o=0;o<user.size();o++)
                      {
                          UserClass obiekt = user.get(o);
                          contacts += obiekt.imie+"(s);"+obiekt.nazwisko+"(s);";
                          //contacts += pracownicy.get(a)+"(s);";
                      }
                      outToServer = new DataOutputStream(socket.getOutputStream());
                      outToServer.writeBytes(contacts+'\n');
                      System.out.println(contacts);
                  }
                  
                  
	         }
	         catch(IOException ioe) {  }
	      }
	   }
	   public void open() throws IOException
	   {  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	   }
	   public void close() throws IOException
	   {  if (socket != null)    socket.close();
	      if (streamIn != null)  streamIn.close();
	   }
	}
