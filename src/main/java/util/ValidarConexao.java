package util;

import com.br.valber.Log;

public class ValidarConexao {
	public static boolean isConection() {
		boolean isConection = false;
		try {
			java.net.URL mandarMail = new java.net.URL("http://www.guj.com.br");
			java.net.URLConnection conn = mandarMail.openConnection();
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) conn;
			httpConn.connect();
			int x = httpConn.getResponseCode();
			if (x == 200) {
				isConection = true;
			}
		} catch (java.net.MalformedURLException e) {
			new Log().montarFileErro(ValidarConexao.class, "Erro ", e.getMessage());
			;
			return isConection;
		} catch (java.io.IOException e2) {
			new Log().montarFileErro(ValidarConexao.class, "Erro ", e2.getMessage());
			;
			return isConection;
		}
		return isConection;
	}

}
