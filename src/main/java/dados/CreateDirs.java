package dados;

import java.io.File;


import util.UtilString;

public class CreateDirs {

	public CreateDirs() {

		File pathDados = new File(new File("").getAbsolutePath()+ "/dados");
		File pathLogo = new File(new File("").getAbsolutePath()+ "/logomarca");
		File pathEnviar = new File(new File("").getAbsolutePath() + "/enviar/");
		File pathLogs = new File(new File("").getAbsolutePath() + "/logs/");
		File pathTurtorial = new File(new File("").getAbsolutePath() + "/turtorial");
		try {
			pathDados.mkdir();
		} catch (Exception e) {
		}
		try {
			pathLogo.mkdir();
		} catch (Exception e) {
		}
		try {
			pathEnviar.mkdir();
		} catch (Exception e) {
		}
		try {
			pathLogs.mkdir();
		} catch (Exception e) {
		}
		try {
			pathTurtorial.mkdir();
		} catch (Exception e) {
		}
	}

}
