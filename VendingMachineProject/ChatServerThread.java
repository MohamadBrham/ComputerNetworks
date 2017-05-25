
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ChatServerThread extends Thread{
    private Socket socket = null;
    private Server server = null;
    private ObjectOutputStream  out;
    private ObjectInputStream in;
    private int tnum;
    private ArrayList<Items> items;
    
    public ChatServerThread(Server server, Socket socket, int tnum){
        this.server = server;
        this.socket = socket;     
        this.tnum = tnum;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public void run(){
    
        // The two parts communicate via the input and output streams
            // here the server will receive messages from the client and responce to them
            sendMessage("Connection successful");
            String message;
            String[] parts;
            int i;
            try{
                do{       
                
                    message = (String)in.readObject();
                    if(!message.equals("bye"))
                        System.out.print("Message received on THREAD"+tnum+": \n"+message);
                    // checking the message type
                    if(message.equals("GET ITEM LIST\r\n\r\n")){
                        // the case when the message is List
                        message ="ITEM LIST\r\n";
                        //form the message from the array list
                        items = server.getItems();
                        for( i = 0  ; i< items.size() ; i++){
                            message += items.get(i).getId() +" "+items.get(i).getName()+" "+items.get(i).getCount()+"\r\n";
                        }
                        message += "\r\n";
                        System.out.print("Send the Message: \n"+message);
                        sendMessage(message);
                        System.out.println("*********************************************************");
                        
                    }else if(!message.equals("bye")){
                        //the case when the message is get item
                         parts = message.split("\r\n");
                         parts = parts[1].split(" ");
                         // parts[0] contains id , parts[1] containt amount                        
                         boolean flag = server.getItem(Integer.parseInt(parts[0]) ,Integer.parseInt(parts[1]));
                         //return True if SUCCESS , else False
                         if(flag == true){ 
                            message = "SUCCESS\r\n\r\n";
                            System.out.print("Send the Message: \n"+message);
                            sendMessage(message);
                            System.out.println("*********************************************************");
                         }else {                // if the item is not there or there is no enough count
                            message = "OUT OF STOCK\r\n\r\n";
                            System.out.print("Send the Message: \n"+message);
                            sendMessage(message);
                            System.out.println("*********************************************************"); 
                         }
                         
                    }else if (message.equals("bye")){
                        System.out.println("THREAD"+tnum+" : has terminated the connection .");
                        server.printItems();
                        System.out.println("*********************************************************");
                        
                    } 
                }while(!message.equals("bye"));
            }catch(ClassNotFoundException classnot){
                    System.err.println("Data received in unknown format");
            }catch (IOException ex) {
                    Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }               
                
            finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
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
            ioException.printStackTrace();
        }
    }
    
}
