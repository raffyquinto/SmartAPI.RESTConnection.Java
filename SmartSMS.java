import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER10
 */
public class SmartSMS {
    
    private static String receiver = "";
    private static String msg = "Static Message";
    private static String spID = "";
    private static String servID = "";
    private static String passwd = "";
    private static String creationTime = "";
    private static String nonce = "";
    private static String accessCode = "";
	
    
    
    public SmartSMS(String spID, String servID, String passwd, String creationTime, String nonce, String accessCode){
        this.spID = spID;
		this.servID = servID;
		this.passwd = passwd;
		this.creationTime = creationTime;
		this.nonce = nonce;
		this.accessCode = accessCode;
    }
    
	public void setSPID(String spID){
		this.spID = spID;
	}
	
	public void setServiceID(String servID){
		this.servID = servID;
	}
	
	public void setPassword(String passwd){
		this.passwd = passwd;
	}
	
	public void setCreationTime(String creationTime){
		this.creationTime = creationTime;
	}
	
	public void setNonce(String nonce){
		this.nonce = nonce;
	}
	
	public void setAccessCode(String accessCode){
		this.accessCode = accessCode;
	}
	
	public String getSPID(){
		return this.spID;
	}
	
	public String getServiceID(){
		return this.servID;
	}
	
	public String getPassword(){
		return this.passwd;
	}
	
	public String getCreationTime(){
		return this.creationTime;
	}
	
	public String getNonce(){
		return this.nonce;
	}
	
	public String  getAccessCode(){
		return this.accessCode;
	}
	
	public void setRecipient(String receiver){
		this.receiver = receiver;
	}
	
	public void setMessage(String message){
		this.msg = message;
	}
	
	public String getRecipient(){
		return this.receiver;
	}
	
	public String getMessage(){
		return this.msg;
	}
	
    public void sendSMS() {

        String server = "http://npwifi.smart.com.ph:8080/1/smsmessaging/outbound/"+this.accessCode+"/requests";

        try {
            URL u = new URL(server);
            URLConnection uc = u.openConnection();
            HttpURLConnection connection = (HttpURLConnection) uc;

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            connection.setRequestProperty("User-Agent", "Jakarta Commons-HttpClient/3.1");
            connection.setRequestProperty("Host", "npwifi.smart.com.ph");
            connection.setRequestProperty("Content-Length", "700");
            connection.setRequestProperty("Authorization", "WSSE realm=\"SDP\",profile=\"UsernameToken\"");
            connection.setRequestProperty("X-WSSE", "UsernameToken Username=\""+this.spID+"\",PasswordDigest=\""+this.passwd+"\",Nonce=\""+this.nonce+"\", Created=\""+this.creationTime+"\"");
            connection.setRequestProperty("X-RequestHeader", "request TransId=\"123456789012345678901234567890\",ServiceId=\""+this.servID+"\"");


            OutputStream out = connection.getOutputStream();
            Writer wout = new OutputStreamWriter(out);
            
            wout.write("<sms:outboundSMSMessageRequest xmlns:sms=\"urn:oma:xml:rest:sms:1\">");
            wout.write("<address>tel:"+this.receiver+"</address>");
            wout.write("<senderAddress>"+this.accessCode+"</senderAddress>");
            wout.write("<senderName>MyName</senderName>");
            wout.write("<outboundSMSTextMessage>");
            wout.write("<message>"+msg+"</message>");
            wout.write("</outboundSMSTextMessage>");
            wout.write("</sms:outboundSMSMessageRequest>");
            
            wout.flush();
            wout.close();

            InputStream in = connection.getInputStream();
            int c;
            while ((c = in.read()) != -1) System.out.write(c);
            in.close();



        }
        catch (IOException e) {
            System.out.println("ETO TAYO!");
            System.err.println(e); 
        }

    }
}
