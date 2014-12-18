import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;

import model.Fahrschein;
import model.Kunde;
import model.Reservierung;
import model.Strecke;
import session.*;

public class GP_Fahrkarte {
	
	protected static Strecke streckex;
	protected static Kunde kundex;
	// Frame
	private static JFrame theFrame = new JFrame(" Fahrkartenreservierung");

	// JPanels
	private static JPanel inputPanel = new JPanel();
	private static JPanel inputPanel2 = new JPanel();
	private static JPanel outputPanel = new JPanel();
	private static JPanel statusPanel = new JPanel();
	private static JPanel actionPanel = new JPanel();
	private static JPanel statementPanel = new JPanel();

	// Text Felder
	private static JTextField kundeIdTextField = new JTextField(20);
	private static JTextField kundeNachnameTextField = new JTextField(20);
	private static JTextField kundeVornameTextField = new JTextField(20);

	private static JTextField streckeIdTextField = new JTextField(4);
	private static JTextField vonTextField = new JTextField(20);
	private static JTextField nachTextField = new JTextField(20);

	private static JTextField statusTextField = new JTextField(50);

	// Labels
	private static JLabel kundeIdLabel = new JLabel("KundeId");
	private static JLabel kundeNachnameLabel = new JLabel("Name");
	private static JLabel kundeVornameLabel = new JLabel("Vorname");

	private static JLabel streckeIdLabel = new JLabel("StreckeId");
	private static JLabel streckeVonLabel = new JLabel("von");
	private static JLabel streckeNachLabel = new JLabel("nach");

	private static JLabel statusLabel = new JLabel("Status:");
	private static JLabel statementLabel = new JLabel("Bitte geben Sie ihre Kundenummer ein um sich einzuloggen");

	// Buttons
	private static JButton loginButton = new JButton("Login");
	private static JButton selectButton = new JButton("Strecke auswahl");
	private static JButton submitButton = new JButton("Fahrkarte ausgeben");

	private static InitialContext ctx;
	private static KundeManagerInterface kmanager;
	private static StreckeManagerInterface smanager;
	private static ReservierungManagerInterface rmanager;
	private static FahrscheinManagerInterface fmanager;

	public static void main(String args[]) {
		buildTheFrame();
		// connect2JBoss();
	}

	private static void buildTheFrame() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		theFrame.setLayout(new GridLayout(0, 1));
		statementPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		inputPanel2.setLayout(new FlowLayout());
		outputPanel.setLayout(new FlowLayout());
		actionPanel.setLayout(new FlowLayout());
		statusPanel.setLayout(new FlowLayout());
		
		// Addiere Komponenten auf statementPanel
		statementPanel.add(statementLabel);

		// Addiere Komponenten auf inputPanel
		inputPanel.add(kundeIdLabel);
		inputPanel.add(kundeIdTextField);
		inputPanel.add(kundeNachnameLabel);
		inputPanel.add(kundeNachnameTextField);
		kundeNachnameTextField.setEditable(false);
		inputPanel.add(kundeVornameLabel);
		inputPanel.add(kundeVornameTextField);
		kundeVornameTextField.setEditable(false);
		
		inputPanel2.add(streckeIdLabel);
		inputPanel2.add(streckeIdTextField);
		inputPanel2.add(streckeVonLabel);
		inputPanel2.add(vonTextField);
		vonTextField.setEditable(false);
		inputPanel2.add(streckeNachLabel);
		inputPanel2.add(nachTextField);
		nachTextField.setEditable(false);

		// Addiere Komponenten auf actionPanel
		actionPanel.add(loginButton);
		actionPanel.add(selectButton);
		actionPanel.add(submitButton);
		selectButton.setVisible(false);
		submitButton.setVisible(false);
		
		
		
		
		// Addiere Komponenten auf statusPanel
		statusPanel.add(statusLabel);
		statusPanel.add(statusTextField);
		statusTextField.setEditable(false);
		
		// Addiere Komponenten auf Frame
		theFrame.add(statementPanel);
		theFrame.add(inputPanel);
		theFrame.add(inputPanel2);
		theFrame.add(outputPanel);
		theFrame.add(actionPanel);
		theFrame.add(statusPanel);

		theFrame.setSize(d.width / 2, d.height / 2);
		theFrame.setLocationRelativeTo(null);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setVisible(true);
		connect2JBoss();
		
		loginButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				kundeEinloggen();
			}
		});
		
		selectButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				streckeAusgeben();
			}
		});

		submitButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				fahrkarteAusgeben();
			}
		});
		
	}
	

	private static void kundeEinloggen() {
		
		Long kundeID = Long.valueOf(kundeIdTextField.getText());
		System.out.println(kmanager);
		
		kundex = kmanager.findByPrimaryKey(kundeID);
		kundeNachnameTextField.setText(kundex.getNachname());
		kundeVornameTextField.setText(kundex.getVorname());		
		statementLabel.setText("Bitte geben sie an welche Strecke sie fahren möchten!");
		selectButton.setVisible(true);
		
	}
	public static void streckeAusgeben() {
		Long streckeID = Long.valueOf(streckeIdTextField.getText());
		
		try {
			streckex = smanager.findByPrimaryKey(streckeID);
			vonTextField.setText(streckex.getVon());
			nachTextField.setText(streckex.getNach());
			submitButton.setVisible(true);
			statementLabel.setText("Drücken sie auf Fahrkarte ausgeben!");
		} catch (NoSuchStrecke e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private static void fahrkarteAusgeben(){
		if (streckex.getPlatz() > 0) {
			statementLabel.setText("Es sind noch " + streckex.getPlatz()
					+ " Plaetze verfuegbar");
			Reservierung neueReservierung = new Reservierung();
			neueReservierung.setStrecke(streckex);
			rmanager.reservierungEinpflegen(neueReservierung);
			
//			Fahrschein fahrschein = new Fahrschein();
//			fahrschein.setKunde(kundex);
//			fahrschein.setStrecke(streckex);
//			fmanager.saveFahrschein(fahrschein);
//			fmanager.checkout();
			rmanager.checkout();
			smanager.checkout();
			statusTextField.setText("Hier ihr Fahrschein!");
		} else {
			statementLabel.setText("Es tut uns leid es sind keine Plaetze verfuegbar");
		}
	}
	
	private static void connect2JBoss() {
		try {
			ctx = new InitialContext();
			kmanager = (KundeManagerInterface) ctx
					.lookup("FKAutomatBeans/KundeManager!session.KundeManagerInterface");
			smanager = (StreckeManagerInterface) ctx
					.lookup("FKAutomatBeans/StreckeManager!session.StreckeManagerInterface");
			rmanager = (ReservierungManagerInterface) ctx
					.lookup("FKAutomatBeans/ReservierungManager!session.ReservierungManagerInterface");
			fmanager = (FahrscheinManagerInterface) ctx
					.lookup("FKAutomatBeans/FahrscheinManager!session.FahrscheinManagerInterface");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

//	void doSomething() {
//		try {
//		
			

	
			

//			if (streckex.getPlatz() > 0) {
//				System.out.println("Es sind noch " + streckex.getPlatz()
//						+ " Plaetze verfuegbar");
//				Reservierung neueReservierung = new Reservierung();
//				neueReservierung.setStrecke(streckex);
//				rmanager.reservierungEinpflegen(neueReservierung);
//				rmanager.checkout();
//				smanager.checkout();
//			} else {
//				System.out
//						.println("Es tut uns leid es sind keine Plaetze verfuegbar");
//			}
//		} catch (NoSuchStrecke e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//	}

//}
}
