package thred;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.mail.EmailException;
import org.xml.sax.SAXException;

import com.br.valber.Log;

import dados.CopyFile;
import sendemails.SendEmail;
import toXml.LerXml;
import util.UtilMessage;
import util.UtilString;
import util.ValidarConexao;

public class ColetaPasta implements Runnable {


	public ColetaPasta() {
		new Thread(this).start();

	}

	@Override
	public void run() {

		getXmlVarrer();

	}

	private static void getXmlVarrer() {
		while (true) {

			try {
				
				File fileXml = null;
				String origem =new File("").getAbsolutePath()+ "/enviar/";
				File enviar = new File(origem);
				File[] listfile = enviar.listFiles();
				
				
				for (File file : listfile) {
					String xml = file.getName();

					int initTag = xml.indexOf("e");
					int fimTag = xml.indexOf("_");

					String cofirmarXml = "";
					try {
						cofirmarXml = xml.substring(initTag, fimTag);
					} catch (Exception e) {
					}

					int ini = xml.indexOf(".");
					String nomeExtensao="";
					try {
						 nomeExtensao = xml.substring(ini);
					} catch (Exception e) {
					}

					if (cofirmarXml.equalsIgnoreCase("envio") && nomeExtensao.equalsIgnoreCase(".xml")) {
						origem += "/" + xml;

						LerXml mLerXml = new LerXml(origem);
						for (String pathXml : mLerXml.getListStrings(UtilString.ANEXOS)) {
							
							File anexos = new File(new File("").getAbsolutePath() + "/destinoCopy/anexos/" + pathXml);
							CopyFile copy = new CopyFile();
							copy.copy(new File(new File("").getAbsolutePath() + "/enviar/" + pathXml), anexos, true);
							new File(new File("").getAbsolutePath() + "/enviar/" + pathXml).delete();
						}

						fileXml = new File(new File("").getAbsolutePath() + "/destinoCopy/" + xml);
						CopyFile copy = new CopyFile();
						copy.copy(new File(origem), fileXml, true);
						new File(origem).delete();
					}
				}
				if (fileXml != null) {
					caregarEmails(fileXml);
					fileXml = null;
					new CopyFile().removerDir();
					
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				new Log().montarFileErro(ColetaPasta.class,"Erro ", e.getMessage());;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				new Log().montarFileErro(ColetaPasta.class,"Erro ", e.getMessage());;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				new Log().montarFileErro(ColetaPasta.class,"Erro ", e.getMessage());;
			}
		}
	}

	private static void caregarEmails(File xml) {

		
		if (ValidarConexao.isConection()) {
			new SendEmail(xml.getAbsolutePath()).send();
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {

			}
		} else {
			String message = "falha na conexão \n Sem sinal de internet. \n Verifique a conexão com a internet para tentar enviar \n \n Tentar novamente?";
			if (JOptionPane.showConfirmDialog(null, message, "Erro ao tentar enviar email.", JOptionPane.YES_OPTION, 0,
					new ImageIcon(UtilMessage.class.getClassLoader()
							.getResource("img/warning.png"))) == JOptionPane.OK_OPTION) {
				caregarEmails(xml);
			}
		}
		 
	}

}
