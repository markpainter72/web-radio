package radio;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class RadioGUI extends JFrame 
{
	private JPanel contentPane;
	
	Radio radio;
	
	JComboBox stationBox;
	JButton playButton;
	JLabel message;
	JSlider volumeSlider;

	public RadioGUI(Radio radio, StationList stations) 
	{
		super("Radio");
		
		this.radio = radio;
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		this.setMinimumSize(new Dimension(400, 150));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		String[] stationNames = new String[stations.stations.size()];
		for(int i = 0; i < stations.stations.size(); i++)
		{
			Station station = stations.stations.get(i);
			stationNames[i] = station.name;
		}
		stationBox = new JComboBox(stationNames);
		stationBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				comboBoxChange();
			}
		});
		panel.add(stationBox);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblVolume = new JLabel("Volume:  ");
		panel_2.add(lblVolume, BorderLayout.WEST);
		
		volumeSlider = new JSlider();
		volumeSlider.addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) 
			{
				sliderChange();
			}
			
		});
		panel_2.add(volumeSlider, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		playButton = new JButton("  Play   ");
		panel_3.add(playButton, BorderLayout.CENTER);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut, BorderLayout.EAST);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				buttonClick();
			}
		});
		
		this.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
			
			public void windowClosing(WindowEvent e) 
			{
				windowClose();
			}			
		});
		
		message = new JLabel("");
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		contentPane.add(message, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void setStreamTitle(String title)
	{
		message.setText(title);
	}
	
	private void comboBoxChange()
	{
		radio.stationChanged((String) (stationBox.getSelectedItem()));
	}
	
	private void sliderChange()
	{
		radio.volumeChanged(volumeSlider.getValue());
	}
	
	private void buttonClick()
	{
		radio.playButton();
	}
	
	public void setButtonText(String text)
	{
		playButton.setText(text);
	}
	
	public void windowClose()
	{
		radio.onExit();
	}
}