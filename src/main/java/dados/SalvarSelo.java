package dados;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SalvarSelo {

	private JLabel imagemLb;
	
	public static void salvarImg() throws IOException {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg","png");
	    JFileChooser choose = new JFileChooser();
	    File img;

	    choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    choose.setAcceptAllFileFilterUsed(false);
	    choose.addChoosableFileFilter(filter);

	    choose.showOpenDialog(null);
	    
	    img = choose.getSelectedFile();
	    BufferedImage imagem1 = ImageIO.read(img);
	    ImageIO.write(imagem1,"jpg",new File(new File(new File("").getAbsolutePath())+"/selo.png"));
	    
	    
	}

	public JLabel getImagemLb() {
		return imagemLb;
	}

	public void setImagemLb(JLabel imagemLb) {
		this.imagemLb = imagemLb;
	}
	
	
}
