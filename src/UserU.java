
import java.io.*;
import java.net.*;

public class UserU  {
     
    public static void main(String argv[])  throws Exception {
        String sentence;
        String modifiedSentence;
        
        Socket clientSocket = new Socket("127.0.0.1", 5009);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = new PrintStream(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        while(true){
                   
        String serverAnswer= in.readLine();
        System.out.println(serverAnswer);
        if(serverAnswer.startsWith("[Server] Hello")){
        	System.out.println("[User] Citeste user-ul: ");
            String userNo = inFromUser.readLine();
            System.out.println("[User] Citeste serviciul: ");
            String servNo = inFromUser.readLine();
            int randomNum = (int)(Math.random()*255); 
            out.println("u" + userNo + "s" + servNo + "n" + randomNum);
            //String keyForS = inFromUser.readLine();
            //System.out.println("from server: " + keyForS);
         
         }
        
        /*we got the key K to communicate with S*/
        if(serverAnswer.startsWith("[Server] K")){
        	System.out.println("Succes");
        }
        
        if(serverAnswer.equals("quit"))
            break;
       // else
       //   System.out.println(sentence);
      //  out.writeBytes(sentence + '\n');
      //  modifiedSentence = in.readLine();
     //   System.out.println(modifiedSentence);
       }
       clientSocket.close();
                
    }
}
    

