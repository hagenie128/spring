package annotiation;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	public void send(String to, String message) {
		System.out.println("[EmailSender] " + to +" 에게 전송 : "+ message);
	}
}
