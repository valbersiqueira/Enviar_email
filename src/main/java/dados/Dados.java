package dados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import frame.ConfiguraView;
import util.UtilString;

public class Dados {

	private static Properties getProperties() throws IOException {
		Properties properties = new Properties();
		FileInputStream stream = new FileInputStream(new File("").getAbsolutePath() + "/dados/config.properties");
		properties.load(stream);
		return properties;
	}

	public static void SalvarDados(String... args) {
		try {

			BufferedWriter write = new BufferedWriter(new FileWriter(new File("").getAbsolutePath()+ "/dados/config.properties"));
			
			try {
				write.write(UtilString.NOME + " = " + args[0]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.TIPO_PROVEDOR + " = " + args[1]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.EMAIL + " = " + args[2]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.SENHA + " = " + args[3]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.PATH_LOGO + " = " + args[4]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.PORTA + " = " + args[5]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.LINK + " = " + args[6]);
			} catch (Exception e) {
			}
			try {
				write.newLine();
				write.write(UtilString.PROVEDOR + " = " + args[7]);
			} catch (Exception e) {
			}
			write.flush();
			write.close();
		} catch (IOException e) {
		}

	}

	public static Map<String, String> getDados() throws IOException {
		Map<String, String> dados = new HashMap<String,String>();
		Properties propt = getProperties();

		try {
			dados.put(UtilString.NOME, propt.getProperty(UtilString.NOME));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.TIPO_PROVEDOR, propt.getProperty(UtilString.TIPO_PROVEDOR));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.EMAIL, propt.getProperty(UtilString.EMAIL));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.SENHA, propt.getProperty(UtilString.SENHA));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.PATH_LOGO, propt.getProperty(UtilString.PATH_LOGO));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.PORTA, propt.getProperty(UtilString.PORTA));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.LINK, propt.getProperty(UtilString.LINK));
		} catch (Exception e) {
		}
		try {
			dados.put(UtilString.PROVEDOR, propt.getProperty(UtilString.PROVEDOR));
		} catch (Exception e) {
		}

		return dados;
	}

	

}
