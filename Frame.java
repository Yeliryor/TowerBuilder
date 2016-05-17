package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 4222593001594430555L;

	TaperTower tower = new TaperTower();
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 520;

	private JPanel topPanel;
	private JPanel materialPanel;
	private JPanel textPanel;

	private JPanel middlePanel;
	private JPanel infoPanel;
	private JPanel arrayPanel;

	private JPanel bottomPanel;
	private JPanel pathPanel;
	private JPanel buttonPanel;
	
	private JComboBox<String> materialBox;

	private JLabel heightLabel;
	private JLabel bottomLengthLabel;
	private JLabel capMassLabel;
	private JLabel topLengthLabel;
	private JLabel safetyLabel;
	private JLabel windSpeedLabel;
	private JLabel leanLabel;
	private JLabel viewScaleLabel;
	private JLabel innerLengthLabel;
	private JTextField heightField;
	private JTextField bottomLengthField;
	private JTextField capMassField;
	private JTextField topLengthField;
	private JTextField safetyField;
	private JTextField windSpeedField;
	private JTextField leanField;
	private JTextField viewScaleField;
	private JTextField innerLengthField;
	
	private JLabel filePathLabel;
	private JLabel imagePathLabel;
	private JTextField filePathField;
	private JTextField imagePathField;
	
	private JLabel realHeightLabel;
	private JLabel realBaseLabel;
	private JTextField realHeightField;
	private JTextField realBaseField;

	private JButton arrayButton;
	private JButton textButton;
	private JButton imageButton;
	private JButton allButton;
	
	private JLabel S1;
	private JLabel S2;
	private JLabel S3;
	private JLabel S4;
	private JLabel S5;
	private JLabel S6;
	private JTextField T01;
	private JTextField T02;
	private JTextField T03;
	private JTextField T04;
	private JTextField T05;
	private JTextField T06;
	private JTextField T11;
	private JTextField T12;
	private JTextField T13;
	private JTextField T14;
	private JTextField T15;
	private JTextField T16;
	private JTextField T21;
	private JTextField T22;
	private JTextField T23;
	private JTextField T24;
	private JTextField T25;
	private JTextField T26;
	private JTextField T31;
	private JTextField T32;
	private JTextField T33;
	private JTextField T34;
	private JTextField T35;
	private JTextField T36;
	private JTextField T41;
	private JTextField T42;
	private JTextField T43;
	private JTextField T44;
	private JTextField T45;
	private JTextField T46;
	private JTextField T51;
	private JTextField T52;
	private JTextField T53;
	private JTextField T54;
	private JTextField T55;
	private JTextField T56;
	
	Frame()
	{
		setSize(WIDTH, HEIGHT);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Tower Properties"));
		add(topPanel, BorderLayout.NORTH);

		materialPanel = new JPanel();
		materialPanel.setLayout(new BorderLayout());
		topPanel.add(materialPanel, BorderLayout.NORTH);
		materialBox = new JComboBox<String>();
		materialBox.addItem("Stone");
		materialBox.addItem("Steel");
		materialBox.addItem("Concrete");
		materialBox.addItem("Brick");
		materialBox.addItem("MudBrick");
		materialBox.addItem("Aluminum");
		materialBox.addItem("Titanium");
		materialBox.addItem("Magnesium");
		materialPanel.add(materialBox);
		
		textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 3));
		topPanel.add(textPanel, BorderLayout.SOUTH);
		heightLabel = new JLabel("Maximum Height (m)");
		bottomLengthLabel = new JLabel("Maximum Base Length (m)");
		capMassLabel = new JLabel("Mass Supported at Top (kg)");
		topLengthLabel = new JLabel("Minimum Length (m)");
		safetyLabel = new JLabel("Safety Factor");
		windSpeedLabel = new JLabel("Wind Speed (m/s)");
		leanLabel = new JLabel("Lean (degrees)");
		viewScaleLabel = new JLabel("Pixel Scale (m)");
		innerLengthLabel = new JLabel("Inner Radius (m)");
		heightField = new JTextField("1000");
		bottomLengthField = new JTextField("200");
		capMassField = new JTextField("1000");
		topLengthField = new JTextField("0");
		safetyField = new JTextField("2");
		windSpeedField = new JTextField("40");
		leanField = new JTextField("2");
		viewScaleField = new JTextField("1");
		innerLengthField = new JTextField("0");
		textPanel.add(heightLabel);
		textPanel.add(bottomLengthLabel);
		textPanel.add(capMassLabel);
		textPanel.add(heightField);
		textPanel.add(bottomLengthField);
		textPanel.add(capMassField);

		textPanel.add(topLengthLabel);
		textPanel.add(safetyLabel);
		textPanel.add(windSpeedLabel);
		textPanel.add(topLengthField);
		textPanel.add(safetyField);
		textPanel.add(windSpeedField);
		
		textPanel.add(leanLabel);
		textPanel.add(viewScaleLabel);
		textPanel.add(innerLengthLabel);
		textPanel.add(leanField);
		textPanel.add(viewScaleField);
		textPanel.add(innerLengthField);

		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Tower Statistics"));
		add(middlePanel, BorderLayout.CENTER);

		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setLayout(new GridLayout(2, 4));
		middlePanel.add(infoPanel, BorderLayout.NORTH);
		realHeightLabel = new JLabel("Height (m)");
		realBaseLabel = new JLabel("Base Width (m)");
		realHeightField = new JTextField("1000");
		realBaseField = new JTextField("200");
		realHeightField.setEditable(false);
		realBaseField.setEditable(false);
		infoPanel.add(realHeightLabel);
		infoPanel.add(realBaseLabel);
		infoPanel.add(realHeightField);
		infoPanel.add(realBaseField);
		
		arrayPanel = new JPanel();
		arrayPanel.setLayout(new BorderLayout());
		arrayPanel.setLayout(new GridLayout(7, 6));
		middlePanel.add(arrayPanel, BorderLayout.SOUTH);
		S1 = new JLabel("Height");
		S2 = new JLabel("Length");
		S3 = new JLabel("Weight");
		S4 = new JLabel("Lean");
		S5 = new JLabel("Wind");
		S6 = new JLabel("Shear");
		T01 = new JTextField("");
		T02 = new JTextField("");
		T03 = new JTextField("");
		T04 = new JTextField("");
		T05 = new JTextField("");
		T06 = new JTextField("");
		T11 = new JTextField("");
		T12 = new JTextField("");
		T13 = new JTextField("");
		T14 = new JTextField("");
		T15 = new JTextField("");
		T16 = new JTextField("");
		T21 = new JTextField("");
		T22 = new JTextField("");
		T23 = new JTextField("");
		T24 = new JTextField("");
		T25 = new JTextField("");
		T26 = new JTextField("");
		T31 = new JTextField("");
		T32 = new JTextField("");
		T33 = new JTextField("");
		T34 = new JTextField("");
		T35 = new JTextField("");
		T36 = new JTextField("");
		T41 = new JTextField("");
		T42 = new JTextField("");
		T43 = new JTextField("");
		T44 = new JTextField("");
		T45 = new JTextField("");
		T46 = new JTextField("");
		T51 = new JTextField("");
		T52 = new JTextField("");
		T53 = new JTextField("");
		T54 = new JTextField("");
		T55 = new JTextField("");
		T56 = new JTextField("");
		T01.setEditable(false);
		T02.setEditable(false);
		T03.setEditable(false);
		T04.setEditable(false);
		T05.setEditable(false);
		T06.setEditable(false);
		T11.setEditable(false);
		T12.setEditable(false);
		T13.setEditable(false);
		T14.setEditable(false);
		T15.setEditable(false);
		T16.setEditable(false);
		T21.setEditable(false);
		T22.setEditable(false);
		T23.setEditable(false);
		T24.setEditable(false);
		T25.setEditable(false);
		T26.setEditable(false);
		T31.setEditable(false);
		T32.setEditable(false);
		T33.setEditable(false);
		T34.setEditable(false);
		T35.setEditable(false);
		T36.setEditable(false);
		T41.setEditable(false);
		T42.setEditable(false);
		T43.setEditable(false);
		T44.setEditable(false);
		T45.setEditable(false);
		T46.setEditable(false);
		T51.setEditable(false);
		T52.setEditable(false);
		T53.setEditable(false);
		T54.setEditable(false);
		T55.setEditable(false);
		T56.setEditable(false);
		arrayPanel.add(S1);
		arrayPanel.add(S2);
		arrayPanel.add(S3);
		arrayPanel.add(S4);
		arrayPanel.add(S5);
		arrayPanel.add(S6);
		arrayPanel.add(T01);
		arrayPanel.add(T02);
		arrayPanel.add(T03);
		arrayPanel.add(T04);
		arrayPanel.add(T05);
		arrayPanel.add(T06);
		arrayPanel.add(T11);
		arrayPanel.add(T12);
		arrayPanel.add(T13);
		arrayPanel.add(T14);
		arrayPanel.add(T15);
		arrayPanel.add(T16);
		arrayPanel.add(T21);
		arrayPanel.add(T22);
		arrayPanel.add(T23);
		arrayPanel.add(T24);
		arrayPanel.add(T25);
		arrayPanel.add(T26);
		arrayPanel.add(T31);
		arrayPanel.add(T32);
		arrayPanel.add(T33);
		arrayPanel.add(T34);
		arrayPanel.add(T35);
		arrayPanel.add(T36);
		arrayPanel.add(T41);
		arrayPanel.add(T42);
		arrayPanel.add(T43);
		arrayPanel.add(T44);
		arrayPanel.add(T45);
		arrayPanel.add(T46);
		arrayPanel.add(T51);
		arrayPanel.add(T52);
		arrayPanel.add(T53);
		arrayPanel.add(T54);
		arrayPanel.add(T55);
		arrayPanel.add(T56);
				
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);

		pathPanel = new JPanel();
		pathPanel.setLayout(new BorderLayout());
		pathPanel.setLayout(new GridLayout(4, 1));
		bottomPanel.add(pathPanel, BorderLayout.NORTH);
		filePathLabel = new JLabel("File Path");
		imagePathLabel = new JLabel("Image Path");
		filePathField = new JTextField("C:\\Users\\Riley\\Desktop\\TaperTower.txt");
		imagePathField = new JTextField("C:\\Users\\Riley\\Desktop\\TaperTower.png");
		pathPanel.add(filePathLabel);
		pathPanel.add(filePathField);
		pathPanel.add(imagePathLabel);
		pathPanel.add(imagePathField);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setLayout(new GridLayout(1, 4));
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		arrayButton = new JButton("Generate Tower");
		textButton = new JButton("Generate File");
		imageButton = new JButton("Generate Image");
		allButton = new JButton("Generate All");
		buttonPanel.add(arrayButton);
		buttonPanel.add(textButton);
		buttonPanel.add(imageButton);
		buttonPanel.add(allButton);
		ActionListener arrayListener = new ArrayListener();
		arrayButton.addActionListener(arrayListener);
		ActionListener textListener = new TextListener();
		textButton.addActionListener(textListener);
		ActionListener imageListener = new ImageListener();
		imageButton.addActionListener(imageListener);
		ActionListener allListener = new AllListener();
		allButton.addActionListener(allListener);
	}
	
	public void generateArrays()
	{
		tower.setMaterial(materialBox.getSelectedItem().toString());
		tower.height(Integer.parseInt(heightField.getText()));
		tower.baseLength(Integer.parseInt(bottomLengthField.getText()));
		tower.safety(Integer.parseInt(safetyField.getText()));
		tower.viewScale(Integer.parseInt(viewScaleField.getText()));
		tower.capMass(Integer.parseInt(capMassField.getText()));
		tower.topLength(Integer.parseInt(topLengthField.getText()));
		tower.airSpeed(Integer.parseInt(windSpeedField.getText()));
		tower.lean(Integer.parseInt(leanField.getText()));
		tower.innerLength(Integer.parseInt(innerLengthField.getText()));
		
		tower.generateArrays();
		
		realHeightField.setText(Double.toString(tower.getHeight()));
		realBaseField.setText(Double.toString(tower.getLength()));
		
		double[][] array = tower.getSnapShot();
		T01.setText(Double.toString(array[0][0]));
		T11.setText(Double.toString(array[0][1]));
		T21.setText(Double.toString(array[0][2]));
		T31.setText(Double.toString(array[0][3]));
		T41.setText(Double.toString(array[0][4]));
		T51.setText(Double.toString(array[0][5]));
		T02.setText(Double.toString(array[1][0]));
		T12.setText(Double.toString(array[1][1]));
		T22.setText(Double.toString(array[1][2]));
		T32.setText(Double.toString(array[1][3]));
		T42.setText(Double.toString(array[1][4]));
		T52.setText(Double.toString(array[1][5]));
		T03.setText(Double.toString(array[2][0]));
		T13.setText(Double.toString(array[2][1]));
		T23.setText(Double.toString(array[2][2]));
		T33.setText(Double.toString(array[2][3]));
		T43.setText(Double.toString(array[2][4]));
		T53.setText(Double.toString(array[2][5]));
		T04.setText(Double.toString(array[3][0]));
		T14.setText(Double.toString(array[3][1]));
		T24.setText(Double.toString(array[3][2]));
		T34.setText(Double.toString(array[3][3]));
		T44.setText(Double.toString(array[3][4]));
		T54.setText(Double.toString(array[3][5]));
		T05.setText(Double.toString(array[4][0]));
		T15.setText(Double.toString(array[4][1]));
		T25.setText(Double.toString(array[4][2]));
		T35.setText(Double.toString(array[4][3]));
		T45.setText(Double.toString(array[4][4]));
		T55.setText(Double.toString(array[4][5]));
		T06.setText(Double.toString(array[5][0]));
		T16.setText(Double.toString(array[5][1]));
		T26.setText(Double.toString(array[5][2]));
		T36.setText(Double.toString(array[5][3]));
		T46.setText(Double.toString(array[5][4]));
		T56.setText(Double.toString(array[5][5]));
	}
	
	public void generateText()
	{
		tower.filePath(filePathField.getText());
		try { tower.generateFile(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
	}
	
	public void generateImage()
	{
		tower.imagePath(imagePathField.getText());
		try { tower.generateImage(); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	class ArrayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			generateArrays();
		}
	}
	
	class TextListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			generateText();
		}
	}
	
	class ImageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			generateImage();
		}
	}
	
	class AllListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{

			generateArrays();
			generateText();
			generateImage();
		}
	}
}
