package util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UtilMessage {

	Map<Integer, String> msg;

	public UtilMessage() {
		this.msg = new HashMap<Integer, String>();
	}
	
	public  int getMessage(int idMsg){
		this.msg.put(0, "Selecione o tipo de provedor.");
		this.msg.put(1, "Informe o provedor.");
		this.msg.put(2, "Informe o E-mail.");
		this.msg.put(3, "Informe a senha.");
		this.msg.put(4, "Imagem não encontrada.");
		this.msg.put(5, "Informe um e-mail válido exemplo (exemplo@hotmail.com).");
		this.msg.put(6, "Informe um e-mail válido exemplo (exemplo@gmail.com).");
		this.msg.put(7, "Erro ao tentar abrir site do outlook.");
		this.msg.put(8, "Erro ao tentar abrir site do gmail.");
		this.msg.put(9, "Informe a porta.");
		this.msg.put(10, "Informe o provedor.");
		this.msg.put(11, "Falha na autenticação E-mail ou senha invalida.");
		this.msg.put(12, "Falha na conexão com a internet.");
		this.msg.put(13, "Informe um e-mail válido exemplo (exemplo@outlook.com)");
		
		
		
		return JOptionPane.showConfirmDialog(null, this.msg.get(idMsg), "Erro ao salvar.", JOptionPane.YES_OPTION,
				0, new ImageIcon(UtilMessage.class.getClassLoader().getResource("img/warning.png")));
	}
	
	
	
}
