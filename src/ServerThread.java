import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.io.*;
import java.lang.*;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class ServerThread extends Thread {
    
    protected Socket socket;
    String services[]={"s1","s2","s3"};
    String users[]={"u1","u2","u3"};
    
    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    
    public boolean hasAccess(String userSecurityLevel,String servSecurityLevel) {
        
        int userLevel=0,servLevel=0;
        switch(userSecurityLevel) {
            case "regular":
                userLevel=0;break;
            case "secret":
                userLevel=1;break;
            case "top_secret":
                userLevel=2;break;
        }
        
        switch(servSecurityLevel) {
            case "regular":
                servLevel=0;break;
            case "secret":
                servLevel=1;break;
            case "top_secret":
                servLevel=2;break;
        }
        
        if(userLevel<servLevel)
            return false;
        else
            return true;
    }
    
    public boolean rightToAccess(String user, String service) {
        
        try {
        FileReader f=new FileReader(user+".txt");
        BufferedReader br=new BufferedReader(f);
        
        String userSecurityLevel=br.readLine();
        f.close();
        
        FileReader g=new FileReader(user+".txt");
        BufferedReader br2=new BufferedReader(g);
        
        String servSecurityLevel=br2.readLine();
        g.close();
        
        return hasAccess(userSecurityLevel,servSecurityLevel);
        
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
        return true;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        PrintStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new PrintStream(socket.getOutputStream());
            out.println("[Server] Hello");
        } catch (IOException e) {
            return;
        }
        String line, response = "";
      
        while (true) {
            try {
                line = brinp.readLine();
                System.out.println(line);
                if(line.startsWith("u")) {
                    String user=line.substring(0,2);
                    String service=line.substring(2,4);
                    String randomNumber = line.substring(5);
                    System.out.println(randomNumber + " is n");
                    
                    if(!rightToAccess(user,service))
                        response="[Server] Not allowed";
                    else {
                    	//response = "[Server] Ok";
                    	System.out.println("User " + user + " has the right to access service " + service);
                    	
                    	/*generate key*/
                    	KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
                    	SecretKey newKey = keyGenerator.generateKey(); /*by default, 112 bits*/
                    	
                    	
                    	Crypt3DES c = new Crypt3DES(newKey);
                    	//String m = c.encryptMessage("Hello World of Encryption using 3DES ");
                    	//System.out.println("m: " + m);
                    	System.out.println("m " + c.decryptMessage(c.encryptMessage("aaaaaaaa")));
                    	//System.out.println("aici! " + c.encryptMessage());
                    	
                    	/*send message to user with key*/
                    	response = "[Server] K" + newKey.toString() + "n" + randomNumber + "L2" + service;
                    	
                    	
                    	
                		/*decrypting the text with 3DES*/
                    	//Crypt3DES dc = new Crypt3DES(m, newKey);
                    	
                    }           
                }
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } 
                out.println(response);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            catch (NoSuchAlgorithmException e) {
            	e.printStackTrace();
            	return;
            }
        }
    }
}
