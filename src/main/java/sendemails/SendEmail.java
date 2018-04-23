package sendemails;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.xml.sax.SAXException;

import com.br.valber.Log;

import dados.Dados;
import frame.ConfiguraView;
import toXml.LerXml;
import util.Criptografia;
import util.UtilString;

public class SendEmail {


	private static final String HOST_NAME_HOTMAIL = "smtp-mail.outlook.com";
	private static final String HOST_NAME_GMAIL = "smtp.gmail.com";
//	private static final String HOST_NAME_LIVE = "smtp.live.com";
	private static final String HOST_NAME_LIVE = "smtp-mail.outlook.com";
	private static final int PORT_HOTMAIL = 587;
	private static final int PORT_GMAIL = 465;

	private List<InternetAddress> emailsCopiasOcultas = new ArrayList<InternetAddress>();
	private List<InternetAddress> emailsDestinatarios = new ArrayList<InternetAddress>();

	private String pathXml;
	private static final String pathAnexos = new File("").getAbsolutePath() + "/destinoCopy/anexos/";

	public SendEmail(String pathXml) {
		this.pathXml = pathXml;
	}

	public boolean send()  {
		HtmlEmail email = new HtmlEmail();

		Map<String, String> dados = null;
		try {
			dados = Dados.getDados();
		} catch (IOException e) {
		}

		List<File> files = new ArrayList<File>();
		for (File path : new File(pathAnexos).listFiles()) {
			files.add(path);
		}

		if (files.size() > 0) {
			// sending one or more attachments
			EmailAttachment attachment = new EmailAttachment();
			attachment.setDisposition(EmailAttachment.ATTACHMENT);

			// for each in files
			for (File fileIn : files) {
				if (fileIn != null && fileIn.isFile()) {
					int indexOfExtension = fileIn.getName().indexOf(".");
					indexOfExtension = indexOfExtension == -1 ? 0 : indexOfExtension;
					attachment.setDescription(fileIn.getName().substring(indexOfExtension));
					attachment.setName(fileIn.getName());
					try {
						attachment.setURL(fileIn.toURI().toURL());
					} catch (MalformedURLException e) {
						new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
					}
					try {
						email.attach(attachment);
					} catch (EmailException e) {
						new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
					}
				}
			}
		}

		

		switch (dados.get(UtilString.TIPO_PROVEDOR)) {
		case "@hotmail":
			email.setSmtpPort(PORT_HOTMAIL);
			email.setHostName(HOST_NAME_HOTMAIL);
			break;
		case "@outlook":
			email.setSmtpPort(PORT_HOTMAIL);
			email.setHostName(HOST_NAME_HOTMAIL);
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
		email.setSSLCheckServerIdentity(true);
		email.setStartTLSEnabled(true);
		email.setSSLCheckServerIdentity(true);
		

		email.setAuthentication(dados.get(UtilString.EMAIL),
				Criptografia.descriptografiaBase64Decoder(dados.get(UtilString.SENHA)));
		try {
			email.setFrom(dados.get(UtilString.EMAIL), dados.get(UtilString.NOME));
		} catch (EmailException e2) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e2.getMessage());;
		}

		
		for (String emails : destinatarios()) {
			try {
				email.addCc(emails);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String emails : copiasOcultas()) {
			try {
				email.addBcc(emails);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Map<String, String> obs = observacoes();
		email.setSubject(obs.get(UtilString.ASSUNTO));

		String cid="";
		try {
			
		 cid = email.embed(new File(dados.get(UtilString.PATH_LOGO)));
		} catch (Exception e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}

		StringBuilder txt = new StringBuilder();
		txt.append("<html> ");
		txt.append("<h3>");
		txt.append(obs.get(UtilString.CABECALHO).replace("|", "<br>"));
		txt.append("</h3>");
		txt.append("<br>");
		txt.append("<p>");
		txt.append(obs.get(UtilString.CORPO).replace("|", "<br>"));
		txt.append("</p> ");
		txt.append("<br>");
		txt.append("<p>");
		txt.append(obs.get(UtilString.RODAPE).replace("|", "<br>"));
		txt.append("</p>");
		txt.append("<br>");
		txt.append("<a href=\"" + dados.get(UtilString.LINK) + "\">");
		txt.append("<img  src=\"cid:" + cid + "\">");
		txt.append("</a>");
		txt.append(" </html>");

		try {
			email.setHtmlMsg(txt.toString());
		} catch (EmailException e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}
		// send the email
		try {
			email.send();
		} catch (EmailException e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}

		
		return true;
	}

	private List<String> destinatarios() {
		List<String> listEmailDestinatarios = new ArrayList<>();
		try {
			LerXml xml = new LerXml(pathXml);
			listEmailDestinatarios = xml.getListStrings(UtilString.DESTINATARIOS);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}
		return listEmailDestinatarios;
	}

	private List<String> copiasOcultas() {
		List<String> listCopiasOcultas = new ArrayList<>();

		try {
			LerXml xml = new LerXml(pathXml);
			listCopiasOcultas = xml.getListStrings(UtilString.COPIASOCULTAS);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}
		return listCopiasOcultas;
	}



	private Map<String, String> observacoes() {
		Map<String, String> obs = new HashMap<>();
		try {
			LerXml xml = new LerXml(pathXml);
			obs = xml.getObservacoes(UtilString.OBSERVACOES);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			new Log().montarFileErro(SendEmail.class,"Erro ", e.getMessage());;
		}
		return obs;
	}
}
