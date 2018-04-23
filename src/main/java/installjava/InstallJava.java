package installjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InstallJava {

	public static void main(String[] args) {
	String path =	 "setx JAVA_HOME C:\\Program Files\\Java\\jdk1.8.0_162";
	String path2 =	 "setx CLASSPATH %JAVA_HOME%\\lib";
	String path3 =	 "setx PATH %PATH%;%JAVA_HOME%\\bin";
	String pathFull =" setx PATH %PATH%;C:\\Program Files\\Java\\jdk1.8.0_162\\bin";
	
	boolean success = false;
	String result;
	Process pros;
	StringBuffer cmdOut = new StringBuffer();
	BufferedReader input;
	String lineOut = null;
	int numberOfOutline = 0;
	try {
		pros = Runtime.getRuntime().exec(pathFull);
		input = new BufferedReader(new InputStreamReader(pros.getInputStream()));
		while ((lineOut = input.readLine()) != null) {
			if (numberOfOutline > 0) {
				cmdOut.append("\n");
			}
			cmdOut.append(lineOut);
			numberOfOutline++;
		}
		result = cmdOut.toString();
		success = true;
		input.close();
		
		System.out.println(result);
		System.out.println("finish");
		
	} catch (Exception e) {
		// TODO: handle exception
//		e.printStackTrace();
	}
	}
}
