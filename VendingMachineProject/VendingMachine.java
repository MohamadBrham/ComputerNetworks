

import java.util.*;
import java.net.*;
import java.io.*;


public class VendingMachine{
	public static void main(String[] args){
		if(args.length == 2){ // client
			Client client = new Client(args[0] , Integer.parseInt(args[1]));
     		        client.run();
		}else if(args.length == 1){//server			
       		  	Server server = new Server(Integer.parseInt(args[0]));
        		while(true){
            		server.run();
        		}
		}else{
			System.out.println("usage java VendingMachine [<IP_address>] <port_number> ");	
		}
		

	}

}
