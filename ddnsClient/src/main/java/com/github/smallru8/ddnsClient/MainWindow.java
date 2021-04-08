package com.github.smallru8.ddnsClient;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {

	public JTextArea textArea;
	public JFrame frame;
	public JLabel lbl_IP;
	BufferedImage image;
	
	private TrayIcon trayIcon;
	private boolean supTray = SystemTray.isSupported();
	
	private JCheckBox chckbxNewCheckBox_SSL;
	private JTextField textField_Domain;
	private JTextField textField_Server;
	private JTextField textField_Username;
	private JPasswordField passwordField;
	private JTextField textField_Time;
	private JTextField textField_Protocol;

	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		createTrayIcon();
		App.guiReady = true;
		reloadData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				if(supTray) {
					try {
						SystemTray.getSystemTray().add(trayIcon);
						frame.setVisible(false);
						//frame.dispose();
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		try {
			image = ImageIO.read(new File("resources/ico.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JPanel panel = new JPanel() {
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
		    }

		};
		panel.setBounds(376, 194, 48, 57);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Domain");
		lblNewLabel.setBounds(10, 10, 100, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setBounds(10, 35, 100, 15);
		frame.getContentPane().add(lblServer);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 60, 100, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 85, 100, 15);
		frame.getContentPane().add(lblPassword);
		
		textField_Domain = new JTextField();
		textField_Domain.setBounds(120, 7, 96, 21);
		frame.getContentPane().add(textField_Domain);
		textField_Domain.setColumns(10);
		
		textField_Server = new JTextField();
		textField_Server.setBounds(120, 32, 96, 21);
		frame.getContentPane().add(textField_Server);
		textField_Server.setColumns(10);
		
		textField_Username = new JTextField();
		textField_Username.setBounds(120, 57, 96, 21);
		frame.getContentPane().add(textField_Username);
		textField_Username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(120, 82, 96, 21);
		frame.getContentPane().add(passwordField);
		
		chckbxNewCheckBox_SSL = new JCheckBox("SSL");
		chckbxNewCheckBox_SSL.setBounds(222, 31, 97, 23);
		frame.getContentPane().add(chckbxNewCheckBox_SSL);
		
		JLabel lblNewLabel_1 = new JLabel("Time");
		lblNewLabel_1.setBounds(10, 110, 100, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_Time = new JTextField();
		textField_Time.setBounds(120, 107, 96, 21);
		frame.getContentPane().add(textField_Time);
		textField_Time.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Protocol");
		lblNewLabel_2.setBounds(226, 10, 93, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_Protocol = new JTextField();
		textField_Protocol.setBounds(329, 7, 96, 21);
		frame.getContentPane().add(textField_Protocol);
		textField_Protocol.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Current IP");
		lblNewLabel_3.setBounds(226, 85, 79, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		lbl_IP = new JLabel("0.0.0.0");
		lbl_IP.setBounds(315, 85, 109, 15);
		frame.getContentPane().add(lbl_IP);
		
		JButton btnNewButton_Save = new JButton("Save");
		btnNewButton_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				App.cfg.pro.setProperty("poto", textField_Protocol.getText());
				App.cfg.pro.setProperty("server", textField_Server.getText());
				if(chckbxNewCheckBox_SSL.isSelected())	
					App.cfg.pro.setProperty("ssl", "yes");
				else
					App.cfg.pro.setProperty("ssl", "no");
				App.cfg.pro.setProperty("username", textField_Username.getText());
				App.cfg.pro.setProperty("passwd", String.valueOf(passwordField.getPassword()));
				App.cfg.pro.setProperty("domain", textField_Domain.getText());
				App.cfg.pro.setProperty("time", textField_Time.getText());
				
				App.cfg.saveConf();
				App.timer.cancel();
				App.cfg.getConf();
				DoJob.runClient();
			}
		});
		btnNewButton_Save.setBounds(222, 106, 87, 23);
		frame.getContentPane().add(btnNewButton_Save);
		textArea = new JTextArea();
		
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setBounds(10, 135, 357, 116);
		frame.getContentPane().add(jsp);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//刷新
				reloadData();
			}
		});
		btnNewButton.setBounds(315, 106, 87, 23);
		frame.getContentPane().add(btnNewButton);
	}
	
	private void createTrayIcon() {
		if(supTray) {
			Dimension trayIconSize = SystemTray.getSystemTray().getTrayIconSize();
			Image image = Toolkit.getDefaultToolkit().getImage("resources/ico.png").getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);;
			PopupMenu popup = new PopupMenu();
			MenuItem showItem = new MenuItem("Show");
			MenuItem exitItem = new MenuItem("Exit");
			popup.add(showItem);
			popup.add(exitItem);
			trayIcon = new TrayIcon(image, "KWDD", popup);
			showItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(true);
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					SystemTray.getSystemTray().remove(trayIcon);
				}
			});
			exitItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					SystemTray.getSystemTray().remove(trayIcon);
					App.timer.cancel();
					frame.dispose();
				}
				
			});
		}
	}
	
	private void reloadData() {
		textField_Protocol.setText(App.cfg.pro.getProperty("poto"));
		textField_Server.setText(App.cfg.pro.getProperty("server"));
		chckbxNewCheckBox_SSL.setSelected(App.cfg.pro.getProperty("ssl").equals("yes"));
		textField_Username.setText(App.cfg.pro.getProperty("username"));
		passwordField.setText(App.cfg.pro.getProperty("passwd"));
		textField_Domain.setText(App.cfg.pro.getProperty("domain"));
		textField_Time.setText(App.cfg.pro.getProperty("time"));
		textArea.setText("");
	}
}
