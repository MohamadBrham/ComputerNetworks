import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Client {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Scanner input ;
    String ip ;
    int port;
    ArrayList<Items> summury ;
    Client(String ip , int port){
	this.port = port;
        this.ip = ip;
    }
    void run()
    {
        try{
            summury  = new ArrayList<Items>();
            //1. creating a socket to connect to the server
            requestSocket = new Socket(ip,port);
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            input = new Scanner(System.in);
            String choice;
            int id ;
            int count;
            //3: Communicating with the server
            do{
                try{
                    message = (String)in.readObject();
                    System.out.println(message);
                    while(true){
                        System.out.print("Chose a message type((GET ITEM (L)IST, (G)ET ITEM, (Q)UIT):)");
                        choice = input.next();
                        if(choice.equals("L")){
                            sendMessage("GET ITEM LIST\r\n\r\n");
                            message = (String)in.readObject();
                            System.out.print("The received message is : \n"+message);
                            //continue;
                        }else if(choice.equals("G")){
                            System.out.print("Give the item id :  ");
                            id = Integer.parseInt(input.next());
                            System.out.print("Give the number of item(s) :  ");
                            count= Integer.parseInt(input.next());
                            sendMessage("GET ITEM\r\n"+id+" "+count+"\r\n\r\n");
                            message = (String)in.readObject();
                            System.out.print("The received message is : \n"+message );
                            
                            if(message.length() == 11){
                                int i;
                                for( i = 0  ; i< summury.size() ; i++){
                                    if(summury.get(i).getId()== id)
                                     break;
                                }
                                if( i < summury.size()){
                                    summury.get(i).setCount(summury.get(i).getCount()+count);
                                }else{
                                    summury.add(new Items(id," ",count));
                                }
                            }                            
                        }else if(choice.equals("Q")){
                            message = "bye";
                            // print the summery
                            System.out.println("The summury of received items is :  "  );
                            for(int i = 0  ; i< summury.size() ; i++){
                                System.out.println(" "+summury.get(i).getId()+" "+summury.get(i).getCount());
                            }
                            sendMessage(message);
                            break;
                        }else{
                            System.out.println("Enter one of the defined chices : L , G , Q");
                        }
                    }                   
                    
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }catch (SocketException e){
                    System.out.println("the connection is terminated suddenly from the client");
                    break;
                }
            }while(!message.equals("bye"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
            }
        }
    }
    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
        }
        catch(IOException ioException){
        }
    }
}
