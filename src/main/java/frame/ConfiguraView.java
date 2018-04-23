package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import java.awt.Color;

import org.apache.commons.mail.EmailException;

import com.br.valber.Log;

import dados.CreateDirs;
import dados.Dados;
import sendemails.ValidarEmail;
import thred.ColetaPasta;
import thred.ValidarAplicacao;
import tray.TrayInit;
import util.Criptografia;
import util.UtilMessage;
import util.UtilString;
import util.ValidarConexao;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConfiguraView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTxt;
	private JPasswordField senhaTxt;
	private JLabel lblEmail;
	private JLabel imagemLbl;
	private JComboBox<String> tipoCbox;
	private JButton comoConfigurargmailBtn;
	private JButton comoConfigurahotmailBtn;
	private JButton salvarBtn;
	private JButton btnSelecionarSelo;

	private String tipo;

	private File imgChoose;
	private JFileChooser choose;

	private String path;
	private JTextField txtPorta;
	private JLabel lblPorta;

	private String porta;
	private JTextField linktxt;
	private JLabel lblLinkDoSite;
	private JLabel lblProvedor;
	private JTextField provedorTxt;

	private JTextField nomeTxt;
	private JLabel lblNomeDeTitulo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// inicializar jfrma
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CreateDirs();

					ConfiguraView frame = new ConfiguraView();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

					TrayInit iniTray = new TrayInit(frame);
					iniTray.inicializarTray();

					new ColetaPasta();
					new ValidarAplicacao();

				} catch (Exception e) {
						new Log().montarFileErro(ConfiguraView.class,"Erro ", e.getMessage());;
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfiguraView() {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int result = JOptionPane.showConfirmDialog(null, "Fechar Tela de configuração?", "Confirmação",
						JOptionPane.YES_OPTION, 0,
						new ImageIcon(UtilMessage.class.getClassLoader().getResource("img/info.png")));

				if (result == JOptionPane.YES_OPTION) {
					setVisible(false);
				} else {

				}
			}
		});

		setResizable(false);
		setType(Type.NORMAL);
		setTitle("Configuração para enviar email");
		// Toolkit.getDefaultToolkit()
		// .getImage(ConfiguraView.class.getClassLoader().getResource("img/intersys.jpg"));

		setBackground(Color.WHITE);
		setBounds(100, 100, 630, 532);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);

		this.tipoCbox = new JComboBox<String>();
		tipoCbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipo = String.valueOf(tipoCbox.getSelectedItem());

				if (tipo.equalsIgnoreCase("outros")) {
					provedorTxt.setVisible(true);
					lblProvedor.setVisible(true);
					txtPorta.setVisible(true);
					lblPorta.setVisible(true);
					txtPorta.requestFocus();
				} else if (tipo.equalsIgnoreCase("@hotmail") || tipo.equalsIgnoreCase("@gmail")) {
					lblEmail.setText("E-mail");
					provedorTxt.setVisible(false);
					lblProvedor.setVisible(false);
					txtPorta.setVisible(false);
					lblPorta.setVisible(false);
					emailTxt.requestFocus();
				} else if (tipo.equalsIgnoreCase("selecione")) {
					if (txtPorta != null && lblPorta != null) {
						provedorTxt.setVisible(false);
						lblProvedor.setVisible(false);
						txtPorta.setVisible(false);
						lblPorta.setVisible(false);
					}
				}

			}
		});

		tipoCbox.setForeground(Color.BLACK);
		tipoCbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
		tipoCbox.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Selecione", "@hotmail", "@outlook", "@gmail", "Outros" }));
		tipoCbox.setSelectedIndex(0);
		tipoCbox.setBackground(Color.WHITE);

		JLabel lblTipoDeEmail = new JLabel("Tipo de provedor:");
		lblTipoDeEmail.setFont(new Font("SansSerif", Font.BOLD, 11));

		lblEmail = new JLabel("E-mail:");
		lblEmail.setFont(new Font("SansSerif", Font.BOLD, 11));

		emailTxt = new JTextField();
		emailTxt.setFont(new Font("SansSerif", Font.PLAIN, 12));
		emailTxt.setColumns(10);

		senhaTxt = new JPasswordField();
		senhaTxt.setFont(new Font("SansSerif", Font.PLAIN, 12));

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("SansSerif", Font.BOLD, 11));

		comoConfigurargmailBtn = new JButton("Como configurar @gmail");
		comoConfigurargmailBtn.setBackground(Color.WHITE);
		comoConfigurargmailBtn
				.setIcon(new ImageIcon(ConfiguraView.class.getClassLoader().getResource("img/gmail.png")));

		comoConfigurargmailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String path = new File("").getAbsolutePath() + "\\turtorial\\gmail.png";

					Desktop.getDesktop().open(new File(path));
					Runtime.getRuntime().exec("cmd /c start https://myaccount.google.com/lesssecureapps?pli=1");

				} catch (IOException e1) {
					new UtilMessage().getMessage(8);
				}
			}
		});

		comoConfigurargmailBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));

		comoConfigurahotmailBtn = new JButton("Como configura @hotmail");
		comoConfigurahotmailBtn.setBackground(Color.WHITE);
		comoConfigurahotmailBtn
				.setIcon(new ImageIcon(ConfiguraView.class.getClassLoader().getResource("img/outlook.png")));

		comoConfigurahotmailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String path = new File("").getAbsolutePath() + "\\turtorial\\hotmail.png";

					Desktop.getDesktop().open(new File(path));
					Runtime.getRuntime().exec("cmd /c start https://outlook.live.com/owa/?path=/options/popandimap");
				} catch (IOException e1) {
					new UtilMessage().getMessage(7);
				}
			}
		});

		comoConfigurahotmailBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));

		salvarBtn = new JButton("Salvar");
		salvarBtn.setBackground(Color.WHITE);
		salvarBtn.setIcon(new ImageIcon(ConfiguraView.class.getClassLoader().getResource("img/save.png")));

		salvarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean isValid = true;

				String email = emailTxt.getText();
				String emailTipo = "";
				if (!email.equals("")) {
					if (tipo.equalsIgnoreCase("@gmail")) {
						int init = email.indexOf("@");
						emailTipo = email.substring(init, init+6);
					} else if(tipo.equalsIgnoreCase("@hotmail") || tipo.equalsIgnoreCase("@outlook")){
						int init = email.indexOf("@");
						emailTipo = email.substring(init, init+8);
					}
				}

				if (tipo.equalsIgnoreCase("selecione")) {
					new UtilMessage().getMessage(0);
					isValid = false;
				} else if (tipo.equalsIgnoreCase("outros") && txtPorta.getText().equalsIgnoreCase("")) {
					if (new UtilMessage().getMessage(9) == 0) {
						txtPorta.requestFocus();
					}
					isValid = false;
				} else if (emailTxt.getText().toString().equalsIgnoreCase("")
						&& !emailTxt.getText().toString().contains("@")) {
					if (new UtilMessage().getMessage(2) == 0) {
						emailTxt.requestFocus();
					}
					isValid = false;
				} else if (!email.contains("@")) {
					if (tipo.equalsIgnoreCase("@hotmail")) {
						if (new UtilMessage().getMessage(5) == 0) {
							emailTxt.requestFocus();
						}
					} else if (tipo.equalsIgnoreCase("@outlook")) {
						if (new UtilMessage().getMessage(13) == 0) {
							emailTxt.requestFocus();
						}
					} else if (tipo.equalsIgnoreCase("@gmail")) {
						if (new UtilMessage().getMessage(6) == 0) {
							emailTxt.requestFocus();
						}
					}
					isValid = false;
				} else if (tipo.equalsIgnoreCase("outros") && provedorTxt.getText().toString().equalsIgnoreCase("")) {
					if (new UtilMessage().getMessage(10) == 0) {
						provedorTxt.requestFocus();
					}
					isValid = false;
				} else if (tipo.equalsIgnoreCase("outros") && emailTxt.getText().toString().equalsIgnoreCase("")
						&& provedorTxt.getText().toString().equalsIgnoreCase("")) {
					if (new UtilMessage().getMessage(1) == 0) {
						emailTxt.requestFocus();
					}
					isValid = false;
				} else if (senhaTxt.getPassword().toString().equalsIgnoreCase("")) {
					if (new UtilMessage().getMessage(3) == 0) {
						senhaTxt.requestFocus();
					}
					isValid = false;
				} else if (tipo.equalsIgnoreCase("@hotmail") && !emailTipo.equalsIgnoreCase("@hotmail")) {
					if (new UtilMessage().getMessage(5) == 0) {
						emailTxt.requestFocus();
					}
					isValid = false;
				} else if (tipo.equalsIgnoreCase("@outlook") && !emailTipo.equalsIgnoreCase("@outlook")) {
					if (new UtilMessage().getMessage(13) == 0) {
						emailTxt.requestFocus();
					}
					isValid = false;
				} else if (tipo.equalsIgnoreCase("@gmail") && !emailTipo.equalsIgnoreCase("@gmail")) {
					if (new UtilMessage().getMessage(6) == 0) {
						emailTxt.requestFocus();
					}
					isValid = false;
				}

				if (isValid) {

					if (imgChoose != null) {
						try {
							BufferedImage imagem1 = null;
							imagem1 = ImageIO.read(imgChoose);
							ImageIO.write(imagem1, "jpg",
									new File(new File(new File("").getAbsolutePath()) + "\\logomarca\\logomarca.png"));
						} catch (IOException e1) {
							new UtilMessage().getMessage(4);
						}
					}

					path = new File("").getAbsolutePath() + "";
					path = path.replace("\\", "/");
					path += "/logomarca/logomarca.png";
					if (txtPorta.isVisible()) {
						porta = txtPorta.getText().toString();
						provedorTxt.getText().toString().toLowerCase();
					} else {
						porta = "";
					}

					String[] args = { nomeTxt.getText().toString(), tipo, emailTxt.getText().toLowerCase(),
							Criptografia.criptografiaBase64Encoder(new String(senhaTxt.getPassword())), path, porta,
							linktxt.getText().toString().toLowerCase(), };

					Dados.SalvarDados(args);

					try {
						getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						if (ValidarConexao.isConection() && new ValidarEmail().sendValid()) {
							//
							getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							int result = JOptionPane.showConfirmDialog(null,
									"Salvo com sucesso. \n Fechar Tela de configuração?", "Sucesso.",
									JOptionPane.YES_OPTION, 0,
									new ImageIcon(UtilMessage.class.getClassLoader().getResource("img/info.png")));

							if (result == JOptionPane.YES_OPTION) {
								setVisible(false);
							} else {

							}
						} else {
							ConfiguraView.this.getContentPane()
									.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							if (new UtilMessage().getMessage(12) == 0) {
								salvarBtn.requestFocus();
							}
						}
					} catch (MalformedURLException | EmailException | AddressException | HeadlessException e2) {
						ConfiguraView.this.getContentPane()
								.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						if (new UtilMessage().getMessage(11) == 0) {
							emailTxt.requestFocus();
							isValid = false;
							emailTxt.selectAll();
							senhaTxt.setText("");
						}
							new Log().montarFileErro(ConfiguraView.class,"Erro ", e2.getMessage());;
					}

				}
			}
		});

		salvarBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));

		btnSelecionarSelo = new JButton("Selecionar logomorca");
		btnSelecionarSelo.setBackground(Color.WHITE);
		btnSelecionarSelo.setIcon(new ImageIcon(ConfiguraView.class.getClassLoader().getResource("img/picture.png")));

		btnSelecionarSelo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					processarImg();
				} catch (IOException e) {
					new Log().montarFileErro(ConfiguraView.class,"Erro ", e.getMessage());;
				}
			}
		});

		btnSelecionarSelo.setFont(new Font("SansSerif", Font.PLAIN, 11));

		imagemLbl = new JLabel("");

		JLabel lblImagemSelo = new JLabel("Imagem logomarca:");
		lblImagemSelo.setFont(new Font("SansSerif", Font.BOLD, 11));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int result = JOptionPane.showConfirmDialog(null, "Fechar Tela de configuração?", "Confirmação",
						JOptionPane.YES_OPTION, 0,
						new ImageIcon(UtilMessage.class.getClassLoader().getResource("img/info.png")));

				if (result == JOptionPane.YES_OPTION) {
					setVisible(false);

				} else {

				}

			}
		});
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setIcon(new ImageIcon(ConfiguraView.class.getClassLoader().getResource("img/cancel.png")));

		btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 11));

		lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("SansSerif", Font.BOLD, 11));
		lblPorta.setVisible(false);

		txtPorta = new JTextField();
		txtPorta.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtPorta.setColumns(10);
		txtPorta.setVisible(false);

		linktxt = new JTextField();
		linktxt.setColumns(10);

		lblLinkDoSite = new JLabel("Link do site da empresa:");
		lblLinkDoSite.setFont(new Font("SansSerif", Font.BOLD, 11));

		lblProvedor = new JLabel("Provedor:");
		lblProvedor.setFont(new Font("SansSerif", Font.BOLD, 11));
		lblProvedor.setVisible(false);

		provedorTxt = new JTextField();
		provedorTxt.setFont(new Font("SansSerif", Font.PLAIN, 12));
		provedorTxt.setColumns(10);
		provedorTxt.setVisible(false);

		nomeTxt = new JTextField();
		nomeTxt.setFont(new Font("SansSerif", Font.PLAIN, 12));
		nomeTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((int) e.getKeyChar() == KeyEvent.VK_TAB) {
					System.out.println("Que legal!");
				}
			}
		});

		nomeTxt.setColumns(10);

		lblNomeDeTitulo = new JLabel("Nome de titulo E-mail:");
		lblNomeDeTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblImagemSelo)
								.addComponent(lblEmail).addComponent(lblPorta).addComponent(lblSenha)
								.addComponent(lblTipoDeEmail).addComponent(lblLinkDoSite))
						.addGap(10))
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblNomeDeTitulo)
								.addPreferredGap(ComponentPlacement.UNRELATED)))
				.addGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnSelecionarSelo)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(salvarBtn,
												GroupLayout.PREFERRED_SIZE, 124,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnCancelar))
								.addGroup(
										gl_contentPane
												.createParallelGroup(Alignment.TRAILING).addGroup(
														gl_contentPane
																.createSequentialGroup().addGroup(gl_contentPane
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(linktxt,
																				424, 424, Short.MAX_VALUE)
																		.addComponent(senhaTxt, 424, 424,
																				Short.MAX_VALUE)
																		.addComponent(emailTxt, 424, 424,
																				Short.MAX_VALUE)
																		.addGroup(gl_contentPane.createSequentialGroup()
																				.addComponent(txtPorta,
																						GroupLayout.PREFERRED_SIZE, 75,
																						GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(lblProvedor)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(provedorTxt))
																		.addGroup(gl_contentPane.createSequentialGroup()
																				.addGroup(gl_contentPane
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(tipoCbox,
																								Alignment.LEADING, 0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								comoConfigurahotmailBtn,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																				.addPreferredGap(
																						ComponentPlacement.UNRELATED)
																				.addComponent(comoConfigurargmailBtn))
																		.addComponent(nomeTxt))
																.addPreferredGap(ComponentPlacement.RELATED))
												.addComponent(imagemLbl, GroupLayout.PREFERRED_SIZE, 425,
														GroupLayout.PREFERRED_SIZE)))
				.addGap(63)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(17, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nomeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNomeDeTitulo))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipoCbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTipoDeEmail))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(comoConfigurahotmailBtn).addComponent(comoConfigurargmailBtn))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPorta)
								.addComponent(txtPorta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblProvedor).addComponent(provedorTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(emailTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEmail))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSenha)
								.addComponent(senhaTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblLinkDoSite)
								.addComponent(linktxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(33)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnSelecionarSelo)
								.addComponent(salvarBtn).addComponent(btnCancelar))
						.addPreferredGap(ComponentPlacement.RELATED).addGroup(
								gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane
												.createSequentialGroup().addComponent(lblImagemSelo).addGap(112))
										.addGroup(gl_contentPane
												.createSequentialGroup().addComponent(imagemLbl,
														GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
												.addContainerGap()))));
		contentPane.setLayout(gl_contentPane);
		// contentPane
		// .setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
		// lblEmail, emailTxt, lblTipoDeEmail,
		// lblSenha, comoConfigurahotmailBtn, comoConfigurargmailBtn, tipoCbox,
		// senhaTxt, salvarBtn }));
		// setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
		// contentPane, lblTipoDeEmail, lblSenha,
		// lblEmail, emailTxt, comoConfigurahotmailBtn, comoConfigurargmailBtn,
		// tipoCbox, senhaTxt, salvarBtn }));

		this.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				setEnabled(false);
				setEnabled(true);
			}
		});

		// preenche dados salvos na tela de configuração
		this.preencherDadosSalvos();

	}

	private void processarImg() throws IOException {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "png");
		choose = new JFileChooser();

		choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		choose.setAcceptAllFileFilterUsed(false);
		choose.addChoosableFileFilter(filter);

		choose.showOpenDialog(null);
		imgChoose = choose.getSelectedFile();

		try {
			ImageIcon icon = new ImageIcon(imgChoose.getAbsolutePath());
			icon.setImage(icon.getImage().getScaledInstance(332, 140, 100));
			imagemLbl.setIcon(icon);
		} catch (Exception e) {

		}
	}

	private void preencherDadosSalvos() {
		try {
			Map<String, String> dados = Dados.getDados();
			try {
				nomeTxt.setText(dados.get(UtilString.NOME));
			} catch (Exception e) {
			}
			try {
				tipoCbox.setSelectedItem(dados.get(UtilString.TIPO_PROVEDOR));
			} catch (Exception e) {
			}
			try {
				emailTxt.setText(dados.get(UtilString.EMAIL));
			} catch (Exception e) {
			}
			try {
				senhaTxt.setText(Criptografia.descriptografiaBase64Decoder(dados.get(UtilString.SENHA)));
			} catch (Exception e) {
			}
			try {
				txtPorta.setText(dados.get(UtilString.PORTA));

			} catch (Exception e) {
			}
			try {
				linktxt.setText(dados.get(UtilString.LINK));

			} catch (Exception e) {
			}
			try {
				provedorTxt.setText(dados.get(UtilString.PROVEDOR));

			} catch (Exception e) {
			}
			try {
				ImageIcon icon = new ImageIcon(dados.get(UtilString.PATH_LOGO));
				icon.setImage(icon.getImage().getScaledInstance(332, 140, 100));
				imagemLbl.setIcon(icon);
			} catch (Exception e) {
			}

		} catch (IOException e) {
		}
	}

}
