package sendemails;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import dados.Dados;
import util.Criptografia;
import util.UtilString;

public class ValidarEmail  {

//	private static final String HOST_NAME_HOTMAIL = "smtp-mail.outlook.com";
	private static final String HOST_NAME_GMAIL = "smtp.gmail.com";
	private static final String HOST_NAME_LIVE = "smtp.live.com";
	private static final String HOST_NAME_OUTLOOK = "smtp-mail.outlook.com";
	private static final int PORT_HOTMAIL = 587;
	private static final int PORT_GMAIL = 465;

	@SuppressWarnings("deprecation")
	public  boolean sendValid() throws MalformedURLException, EmailException, AddressException {
		List<InternetAddress> emailsDestinatarios = new ArrayList<InternetAddress>();
		emailsDestinatarios.add(new InternetAddress("suporte4_intersysnet_teste_dc@hotmail.com"));
		
		String[] emails ={"suporte4_intersysnet_teste_dc@hotmail.com"};

		HtmlEmail email = new HtmlEmail();

		Map<String, String> dados = null;
		try {
			dados = Dados.getDados();
		} catch (IOException e) {
		}

		// show log
		// set port

		switch (dados.get(UtilString.TIPO_PROVEDOR)) {
		case "@hotmail":
			email.setSmtpPort(PORT_HOTMAIL);
			email.setHostName(HOST_NAME_OUTLOOK);
			break;
		case "@outlook":
			email.setSmtpPort(PORT_HOTMAIL);
			email.setHostName(HOST_NAME_OUTLOOK);
			break;
		case "@gmail":
			email.setSmtpPort(PORT_GMAIL);
			email.setHostName(HOST_NAME_GMAIL);
			email.setSSL(true);
			email.setTLS(true);
			break;
		case "outros":
			email.setSmtpPort(Integer.parseInt(dados.get(UtilString.PORTA)));
			email.setHostName(dados.get(UtilString.PROVEDOR));
			break;
		default:
			break;
		}
		email.setDebug(true);
		// check this rules for security
		
		email.setSSLCheckServerIdentity(true);
		
		email.setStartTLSEnabled(true);
		email.setSSLCheckServerIdentity(true);
		
		System.out.println(Criptografia.descriptografiaBase64Decoder(dados.get(UtilString.SENHA)));
		
		email.setAuthentication(dados.get(UtilString.EMAIL),
				Criptografia.descriptografiaBase64Decoder(dados.get(UtilString.SENHA)));
		
		email.setFrom(dados.get(UtilString.EMAIL), dados.get(UtilString.NOME));
		
		email.addCc(emails[0]);
		//email.setCc(emailsDestinatarios);

		String txtHtml = "<html>" + "Teste validação email e senha ,<br>" + "</html>";

		email.setHtmlMsg(txtHtml);

		email.send();
		return true;
	}
	
	

}
