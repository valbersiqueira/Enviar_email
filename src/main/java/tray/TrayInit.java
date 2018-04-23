package tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import org.apache.commons.mail.EmailException;

import com.br.valber.Log;

import frame.ConfiguraView;
import thred.ColetaPasta;

public class TrayInit {
	private ConfiguraView frame;
	
	

	public TrayInit(ConfiguraView frame) {
		this.frame = frame;
	}

	public  void inicializarTray() {

		// inicialiar systray
		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) {
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			Image image = Toolkit.getDefaultToolkit()
					.getImage(ConfiguraView.class.getClassLoader().getResource("img/send.png"));
			// create a action listener to listen for default action executed on
			// the tray icon
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(true);
				}
			};
			// create a popup menu
			PopupMenu popup = new PopupMenu();
			final MenuItem menuExit = new MenuItem("Sair");
			ActionListener exitList = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Runtime r = Runtime.getRuntime();
					r.exit(0);
					
				}
			};

			// create menu item for the default action
			menuExit.addActionListener(listener);
			menuExit.addActionListener(exitList);
			popup.add(menuExit);
			/// ... add other items
			
			// construct a TrayIcon
			trayIcon = new TrayIcon(image, "SendMessage", popup);
			// set the TrayIcon properties
			trayIcon.addActionListener(listener);
			// ...
			// add the tray image
			trayIcon.addMouseListener(new MouseAdapter() {
			});
			try { 
				tray.add(trayIcon);
			} catch (AWTException e) {
				new Log().montarFileErro(TrayInit.class,"Erro ", e.getMessage());;
			}
			// ...
		} else {

		}

		if (trayIcon != null) {
		}
	}

}
