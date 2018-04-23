package util;

import org.apache.commons.codec.binary.Base64;

public class Criptografia {
	
	public static String criptografiaBase64Encoder(String valor) {
        return new Base64().encodeToString(valor.getBytes());
    }
    public static String descriptografiaBase64Decoder(String valorCriptografado) {
        return new String(new Base64().decode(valorCriptografado));
    }
	
}
