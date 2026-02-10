package cryptage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import fileExplorer.DetailedFile;
import fileExplorer.Directory;
import fileExplorer.FileExplorer;

public class AppCryptage {

	private JFrame frmCryptage;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField textFieldMotClef2;
	private JTextField textFieldSource;
	private JTextField textFieldCible;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	
	
	private final File EXEC_DIRECTORY = new File(Directory.pwd());
	
	private final String PREFIXE = DetailedFile.relativeToHome(EXEC_DIRECTORY.toPath()) + "$> ";
	private static final String MSG_REUSSITE = "Opération réussie\n\t";
	private static final String MSG_ECHEC = "Echec\nUn des chemins renseignés ne pointe pas vers un fichier accessible en lecture.";

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppCryptage window = new AppCryptage();
					window.frmCryptage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppCryptage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCryptage = new JFrame();
		frmCryptage.setTitle("Cryptage");
		frmCryptage.setBounds(100, 100, 550, 350);
		frmCryptage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCryptage.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmCryptage.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setToolTipText("");
		
		JToggleButton btnDecalage = new JToggleButton("Décalage");
		btnDecalage.setSelected(true);
		buttonGroup_1.add(btnDecalage);
		toolBar.add(btnDecalage);
		
		JToggleButton btnPlayfair = new JToggleButton("Playfair");
		buttonGroup_1.add(btnPlayfair);
		toolBar.add(btnPlayfair);
		
		JRadioButton rdbtnCryptage = new JRadioButton("cryptage");
		buttonGroup.add(rdbtnCryptage);
		rdbtnCryptage.setSelected(true);
		toolBar.add(rdbtnCryptage);
		
		JRadioButton rdbtnDecryptage = new JRadioButton("décryptage");
		buttonGroup.add(rdbtnDecryptage);
		toolBar.add(rdbtnDecryptage);
		panel_5.add(toolBar, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_5.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMotClef = new JLabel("Mot clef : ");
		panel_1.add(lblMotClef, BorderLayout.WEST);
		lblMotClef.setVerticalAlignment(SwingConstants.TOP);
		lblMotClef.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField textFieldMotClef = new JTextField();

		panel_1.add(textFieldMotClef, BorderLayout.CENTER);
		textFieldMotClef.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTextClair = new JLabel("Text clair");
		panel_2.add(lblTextClair, BorderLayout.NORTH);
		lblTextClair.setVerticalAlignment(SwingConstants.TOP);
		lblTextClair.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField textFieldClair = new JTextField();
		textFieldClair.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(textFieldClair, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTextCrypte = new JLabel("Text crypté");
		lblTextCrypte.setVerticalAlignment(SwingConstants.TOP);
		lblTextCrypte.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblTextCrypte, BorderLayout.NORTH);
		
		JTextField textFieldCrypte = new JTextField();
		textFieldCrypte.setEditable(false);
		panel_3.add(textFieldCrypte);
		
		
		tabbedPane.insertTab("Texte", null, panel_5, null, 0);
		
		
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		
		tabbedPane.insertTab("Fichier", null, panel_6, null, 1);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setToolTipText("");
		toolBar_1.setFloatable(false);
		panel_6.add(toolBar_1, BorderLayout.NORTH);
		
		JToggleButton btnDecalage_1 = new JToggleButton("Décalage");
		buttonGroup_2.add(btnDecalage_1);
		btnDecalage_1.setSelected(true);
		toolBar_1.add(btnDecalage_1);
		
		JToggleButton btnPlayfair_1 = new JToggleButton("Playfair");
		buttonGroup_2.add(btnPlayfair_1);
		toolBar_1.add(btnPlayfair_1);
		
		JRadioButton rdbtnCryptage_1 = new JRadioButton("cryptage");
		buttonGroup_3.add(rdbtnCryptage_1);
		rdbtnCryptage_1.setSelected(true);
		toolBar_1.add(rdbtnCryptage_1);
		
		JRadioButton rdbtnDecryptage_1 = new JRadioButton("décryptage");
		buttonGroup_3.add(rdbtnDecryptage_1);
		toolBar_1.add(rdbtnDecryptage_1);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_6.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMotClef = new JPanel();
		panelMotClef.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_7.add(panelMotClef, BorderLayout.NORTH);
		panelMotClef.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMotClef_1 = new JLabel("Mot clef : ");
		lblMotClef_1.setVerticalAlignment(SwingConstants.TOP);
		lblMotClef_1.setHorizontalAlignment(SwingConstants.CENTER);
		panelMotClef.add(lblMotClef_1, BorderLayout.WEST);
		
		textFieldMotClef2 = new JTextField();
		textFieldMotClef2.setHorizontalAlignment(SwingConstants.LEFT);
		panelMotClef.add(textFieldMotClef2, BorderLayout.CENTER);
		
		JPanel panelFichierEtLog = new JPanel();
		panelFichierEtLog.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_7.add(panelFichierEtLog, BorderLayout.CENTER);
		panelFichierEtLog.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFichier = new JPanel();
		panelFichierEtLog.add(panelFichier, BorderLayout.NORTH);
		panelFichier.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1_1_1 = new JPanel();
		panelFichier.add(panel_1_1_1);
		panel_1_1_1.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_1_1_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSource = new JLabel("Fichier source : ");
		lblSource.setVerticalAlignment(SwingConstants.TOP);
		lblSource.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1_1_1.add(lblSource, BorderLayout.WEST);
		
		JPanel panel_8 = new JPanel();
		panel_1_1_1.add(panel_8, BorderLayout.SOUTH);
		panel_8.setLayout(new FormLayout(new ColumnSpec[] {
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.DEFAULT, Sizes.constant("20px", true), Sizes.constant("300px", true)), 1),
				ColumnSpec.decode("50px"),},
			new RowSpec[] {
				RowSpec.decode("23px"),}));
		
		textFieldSource = new JTextField();
		textFieldSource.setHorizontalAlignment(SwingConstants.LEFT);
		panel_8.add(textFieldSource, "1, 1, fill, fill");
		
		JButton btnSelectSource = new JButton("🗁");
		panel_8.add(btnSelectSource, "2, 1, left, center");
		
		JPanel panel_1_1_2 = new JPanel();
		panelFichier.add(panel_1_1_2);
		panel_1_1_2.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_1_1_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCible = new JLabel("Fichier destination (optionnel) : ");
		lblCible.setVerticalAlignment(SwingConstants.TOP);
		lblCible.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1_1_2.add(lblCible, BorderLayout.WEST);
		
		JPanel panel_9 = new JPanel();
		panel_1_1_2.add(panel_9, BorderLayout.SOUTH);
		panel_9.setLayout(new FormLayout(new ColumnSpec[] {
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.DEFAULT, Sizes.constant("20px", true), Sizes.constant("300px", true)), 1),
				ColumnSpec.decode("50px"),},
			new RowSpec[] {
				RowSpec.decode("23px"),}));
		
		textFieldCible = new JTextField();
		textFieldCible.setHorizontalAlignment(SwingConstants.LEFT);
		panel_9.add(textFieldCible, "1, 1, fill, fill");
		
		JButton btnSelectCible = new JButton("🗁");
		btnSelectCible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_9.add(btnSelectCible, "2, 1, left, center");
		
		JPanel panelLog = new JPanel();
		panelLog.setEnabled(false);
		panelLog.setBorder(new EmptyBorder(1, 1, 1, 1));
		panelFichierEtLog.add(panelLog, BorderLayout.CENTER);
		panelLog.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLog = new JLabel("Log : ");
		lblLog.setVerticalAlignment(SwingConstants.TOP);
		lblLog.setHorizontalAlignment(SwingConstants.LEFT);
		panelLog.add(lblLog, BorderLayout.NORTH);
		
		JTextArea textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
		textAreaLog.setLineWrap(false);
		textAreaLog.setText(PREFIXE);
		
		JScrollPane scrollPaneLog = new JScrollPane(textAreaLog);
		panelLog.add(scrollPaneLog, BorderLayout.CENTER);
		
		JButton btnLaunch = new JButton("Lancer");
		panel_7.add(btnLaunch, BorderLayout.SOUTH);
		
		
		
//		LISTNERS -----------------------
		
		
		ActionListener traiterEtAfficher = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String motClef = textFieldMotClef.getText(); //use toLowerCase() ?
					
					if (!motClef.equals("")) {
						Cryptage chiffrement;
						
						if(btnDecalage.isSelected())
							chiffrement = new Decalage(motClef);
						
						else //Playfair a été selectionné
							chiffrement = new Playfair(motClef);
						
						if (rdbtnCryptage.isSelected()) {
							String aCrypter = textFieldClair.getText();
							textFieldCrypte.setText(chiffrement.cryptage(aCrypter));
							}
						
						else { //Décripter a été selectionné
							String aDecrypter = textFieldCrypte.getText();
							textFieldClair.setText(chiffrement.deCryptage(aDecrypter));
						}
					}
					
				} catch (IllegalArgumentException e1) {
					
					JOptionPane.showMessageDialog(panelFichier, e1.getMessage());
					
				}
			}
		};
		
		
		ActionListener traiterFichier = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {				 
				
				try {
					String motClef = textFieldMotClef2.getText(); //use toLowerCase() ?
					String source = textFieldSource.getText();
					String cible = textFieldCible.getText();
					
					if (!motClef.equals("") && !source.equals("")) {
						Cryptage chiffrement;
						String feedback;
						
						
						if(btnDecalage_1.isSelected())
							chiffrement = new Decalage(motClef);
						
						else //Playfair a été selectionné
							chiffrement = new Playfair(motClef);
						
						
						if (rdbtnCryptage_1.isSelected())
							
							if (!cible.equals(""))
								feedback = chiffrement.cryptageFichier(source, cible);
						
							else
								feedback = chiffrement.cryptageFichier(source);
						
						
						else //Décripter a été selectionné
							
							if (!cible.equals(""))
								feedback = chiffrement.deCryptageFichier(source, cible);
						
							else
								feedback = chiffrement.deCryptageFichier(source);
						
						textAreaLog.append(MSG_REUSSITE + feedback + "\n\n" + PREFIXE);						
					}
					
				} catch (IllegalArgumentException e1) {
					
					JOptionPane.showMessageDialog(panelFichier, e1.getMessage());
					
				} catch (IOException e1) {
					
					textAreaLog.append(MSG_ECHEC + "\n\n" + PREFIXE);
				}
				textAreaLog.setCaretPosition(textAreaLog.getDocument().getLength());
			}
		};
		
		
		
		//ajout du déclencheur sur les boutons concernés
		final AbstractButton[] buttonsDeclencheursTraitement = {btnDecalage, btnPlayfair, rdbtnCryptage, rdbtnDecryptage};
		for(AbstractButton b : buttonsDeclencheursTraitement)
			b.addActionListener(traiterEtAfficher);
		
		//ajout du déclencheur sur les textFiels concernés
		final JTextField[] textFieldsDeclencheursTraitement = {textFieldMotClef, textFieldClair, textFieldCrypte};
		for(JTextField f : textFieldsDeclencheursTraitement)
			f.addActionListener(traiterEtAfficher);
		
		
		//gestion de l'affichage crypter/décrypter
		rdbtnCryptage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldClair.setEditable(true);
				textFieldCrypte.setEditable(false);
			}
		});
		
		rdbtnDecryptage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldClair.setEditable(false);
				textFieldCrypte.setEditable(true);
			}
		});
		
		//ajout du déclencheur fichier sur les textFiels concernés
		final JTextField[] textFieldsDeclencheursTraitementFichier = {textFieldMotClef2, textFieldSource, textFieldCible};
		for(JTextField f : textFieldsDeclencheursTraitementFichier)
			f.addActionListener(traiterFichier);
		
		//ajout du déclencheur fichier sur le btn concerné
		btnLaunch.addActionListener(traiterFichier);
		
		
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileExplorer.launch(textFieldSource, new Directory(EXEC_DIRECTORY));
			}
		});
		
		btnSelectCible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileExplorer.launch(textFieldCible, new Directory(EXEC_DIRECTORY));
			}
		});
		
	}	
	
}

