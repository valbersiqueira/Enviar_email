package dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.mail.EmailException;

import com.br.valber.Log;

import util.UtilString;

public class CopyFile {

	private File dirOrigem;
	private File dirdestino;

	public CopyFile() {
		this.dirdestino = new File(new File("").getAbsolutePath() + "/destinoCopy/");
		if (!dirdestino.exists()) {
			this.dirdestino.mkdir();
		}
		this.dirdestino = new File(new File("").getAbsolutePath() + "/destinoCopy/anexos/");
		if (!dirdestino.exists()) {
			this.dirdestino.mkdir();
		}

	}

	public void removerDir() {
		System.gc();
		File dirdestinoAnexos = new File(new File("").getAbsolutePath() + "/destinoCopy/anexos/");
		removerArquivos(dirdestinoAnexos);
		this.dirdestino = new File(new File("").getAbsolutePath() + "/destinoCopy/");
		removerArquivos(dirdestino);
	}

	public CopyFile(String dirOrigem) {
		this.dirdestino = new File(new File("").getAbsolutePath() + "/destinoCopy");
		this.dirdestino.mkdir();
		this.dirOrigem = new File(dirOrigem);
		efetuarCopia();
	}

	public void efetuarCopia() {
		try {

			copyAll(dirOrigem, dirdestino, true);

		} catch (Exception e) {
				new Log().montarFileErro(CopyFile.class,"Erro ", e.getMessage());;
		}
	}

	public void copyAll(File origem, File destino, boolean overwrite)
			throws IOException, UnsupportedOperationException {
		if (!destino.exists()) {
			destino.mkdir();
		}

		File[] files = origem.listFiles();
		for (int i = 0; i < files.length; ++i) {
			if (files[i].isDirectory()) {
				copyAll(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
			} else {

				copy(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
			}
		}
	}

	public void copy(File origem, File destino, boolean overwrite) throws IOException {
		if (destino.exists() && !overwrite) {
			return;
		}
		FileInputStream source = new FileInputStream(origem);
		FileOutputStream destination = new FileOutputStream(destino);
		FileChannel sourceFileChannel = source.getChannel();
		FileChannel destinationFileChannel = destination.getChannel();
		long size = sourceFileChannel.size();
		sourceFileChannel.transferTo(0, size, destinationFileChannel);
		sourceFileChannel.close();
		destinationFileChannel.close();
		source.close();
		destination.close();
	}

	public void removerArquivos(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				removerArquivos(file);
			}
		}
		f.delete();
	}

}
