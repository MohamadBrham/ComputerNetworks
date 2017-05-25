
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Server {
    ServerSocket socket;
    int port;
    Socket connection = null;   
    ArrayList<Items> items;
    String filename = "item_list.txt";
    String[] parts;
    int tnum ;
    
    Server (int port){
	this.port = port;
        tnum = 1 ;
	// reading the file before creating any thing
        try{    
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            
            items = new ArrayList<Items>();
            while ((line = bufferReader.readLine()) != null)   {
                parts = line.split(" ");
                items.add(new Items(Integer.parseInt(parts[0]),parts[1],Integer.parseInt(parts[2])));
            }
            System.out.println("Reading the items from the file is done");
            printItems();
        }
        catch(FileNotFoundException ioException){
            ioException.printStackTrace();
        }catch(IOException e){
            
        }
    }
    void run()
    {   
        
        try{
            
            //the first thing is to create the server socket  to any port larger than 1024 , say 8080
            socket = new ServerSocket(port);
            // Now the server waiting for connection 
            
            while(true){
                System.out.println("Listening for Client on THREAD"+tnum +" :");
                System.out.println("**********************************************");
                connection = socket.accept();
                System.out.println("A client connected on thread "+tnum +"from " + connection.getInetAddress().getHostName());
                // get Input and Output streams 
                ChatServerThread thread = new ChatServerThread(this, connection ,tnum++);
                thread.start();
                
            } 
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
       
    }
    
    public synchronized void printItems(){
        System.out.println("The current list of Items : ");
        for(int i = 0  ; i< items.size() ; i++){
           System.out.println(" "+items.get(i).getId()+" "+items.get(i).getName()+" "+items.get(i).getCount());
        }
    }
    public synchronized boolean getItem(int id , int amount){
        int i; 
        for( i = 0  ; i< items.size() ; i++){
            if(items.get(i).getId()== id){
                break;
            }
        }
        if(i < items.size() && amount <= items.get(i).getCount() ){ 
            items.get(i).setCount(items.get(i).getCount()- amount );
            return true;
        }else {                // if the item is not there or there is no enough count
            return false; 
        }
    }
    
    
    public synchronized ArrayList<Items> getItems(){
        return items;
    }
}
