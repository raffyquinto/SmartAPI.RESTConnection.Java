public class Test1{
	public static void main(String[] args){
		SmartSMS messaging = new SmartSMS ("001259","0012592000001346","Fu9wPaknmbsIx2kk+kAMyX0I/7s=a","2010-08-21T08:33:46Z","2010082108334600001","406821");
		
		messaging.setRecipient("639177192127");
		messaging.setMessage("Hello Moto!");
		messaging.sendSMS();
	}
}