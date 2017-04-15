import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JCheckBox;

public class Draw extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Draw frame = new Draw();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static int width = (int)(848);
	static int height = (int)(678);
	static BufferedImage image;
	static JLabel screen;
	public static Thread drawUpdateThread;
	public static Thread drawUpdateThread2;
	public static Thread tickerThread;
	private JTextArea mapList;
	private JTextArea lightList;
	private JTextArea objList;
	private JTextArea worldLoaderList;
	private JTextField lightBrightness;
	private JTextField lightCast;
	private JTextField lightX;
	private JTextField lightY;
	private JTextField lightShadows;
	private JTextField lightRed;
	private JTextField lightGreen;
	private JTextField lightBlue;
	private JButton btnRemoveLight;
	private JTextField lightRemoveIndex;
	private JTextField objectPath;
	private JTextField objectY;
	private JTextField objectX;
	private JTextField remObjIndex;
	private JTextField wLoadX;
	private JTextField wLoadY;
	private JTextField wlRemIndex;
	private JTextField wLoadWidth;
	private JTextField wLoadHeight;
	private JTextField nextWorldPath;
	private JTextField isObjectAnimated;
		
	public Draw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 717);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		screen = new JLabel("screen");
		screen.setBounds(0, 0, width, height);
		contentPane.add(screen);
		screen.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				MouseHandler.mouseMoved(arg0);
			}
		});
		
		JTextArea objectList = new JTextArea();
		objectList.setEditable(false);
		objectList.setBounds(865, 0, 290, 111);
		contentPane.add(objectList);
		
		objectList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				KeyHandler.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				KeyHandler.keyReleased(e);
			}
			@Override
			public void keyTyped(KeyEvent e) {
				KeyHandler.keyTyped(e);;
			}
		});
		
		mapList = new JTextArea();
		mapList.setEditable(false);
		mapList.setBounds(865, 123, 290, 64);
		contentPane.add(mapList);
		
		lightBrightness = new JTextField();
		lightBrightness.setToolTipText("Light Brightness 0-255");
		lightBrightness.setBounds(911, 238, 36, 20);
		contentPane.add(lightBrightness);
		lightBrightness.setColumns(10);
		
		lightCast = new JTextField();
		lightCast.setToolTipText("Light cast distance");
		lightCast.setBounds(865, 238, 36, 20);
		contentPane.add(lightCast);
		lightCast.setColumns(10);
		
		lightX = new JTextField();
		lightX.setToolTipText("Light X");
		lightX.setBounds(957, 238, 36, 20);
		contentPane.add(lightX);
		lightX.setColumns(10);
		
		lightY = new JTextField();
		lightY.setToolTipText("Light Y");
		lightY.setBounds(1003, 238, 36, 20);
		contentPane.add(lightY);
		lightY.setColumns(10);
		
		lightShadows = new JTextField();
		lightShadows.setToolTipText("Cast Shadows 1 or 0");
		lightShadows.setBounds(1049, 238, 36, 20);
		contentPane.add(lightShadows);
		lightShadows.setColumns(10);
		
		lightRed = new JTextField();
		lightRed.setToolTipText("Light Red 0 - 255");
		lightRed.setBounds(865, 269, 36, 20);
		contentPane.add(lightRed);
		lightRed.setColumns(10);
		
		lightGreen = new JTextField();
		lightGreen.setToolTipText("Light green 0 - 255");
		lightGreen.setBounds(911, 269, 36, 20);
		contentPane.add(lightGreen);
		lightGreen.setColumns(10);
		
		lightBlue = new JTextField();
		lightBlue.setToolTipText("Light blue 0 - 255");
		lightBlue.setBounds(957, 269, 36, 20);
		contentPane.add(lightBlue);
		lightBlue.setColumns(10);
		
		JButton btnAddLight = new JButton("Add Light");
		btnAddLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopDrawThread();
				boolean shadows = lightShadows.getText().equals("0") ? false : true;
				try{
				LayerManager.addLight(Integer.parseInt(lightCast.getText()), Integer.parseInt(lightBrightness.getText()), Integer.parseInt(lightX.getText()), Integer.parseInt(lightY.getText()), shadows, Integer.parseInt(lightRed.getText()), Integer.parseInt(lightGreen.getText()), Integer.parseInt(lightBlue.getText()));
				} catch (NumberFormatException e2) {
					System.out.println("Number Fields Left Blank!");
				}
				startDrawThread();
				updateLightList();
			}
		});
		btnAddLight.setBounds(1003, 268, 82, 23);
		contentPane.add(btnAddLight);
		
		btnRemoveLight = new JButton("Remove Light");
		btnRemoveLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopDrawThread();
				try{
					LayerManager.removeLight(Integer.parseInt(lightRemoveIndex.getText()));
				} catch (NumberFormatException e2){
					System.out.println("Number Fields Left Blank!");
				}
				startDrawThread();
				updateLightList();
			}
		});
		btnRemoveLight.setBounds(865, 300, 128, 23);
		contentPane.add(btnRemoveLight);
		
		lightRemoveIndex = new JTextField();
		lightRemoveIndex.setBounds(1003, 301, 82, 20);
		contentPane.add(lightRemoveIndex);
		lightRemoveIndex.setColumns(10);
		
		JButton addObject = new JButton("Add Object");
		addObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopDrawThread();
				stopTickThread();
				try{
					if(Integer.parseInt(isObjectAnimated.getText()) == 1)
						LayerManager.addObject(objectPath.getText(), Integer.parseInt(objectX.getText()), Integer.parseInt(objectY.getText()), true);
					else LayerManager.addObject(objectPath.getText(), Integer.parseInt(objectX.getText()), Integer.parseInt(objectY.getText()), false);
				} catch (NumberFormatException e){
					System.out.println("Number Fields Left Blank!");
				}
				startTickThread();
				startDrawThread();
				updateObjectList();
			}
		});
		addObject.setBounds(865, 191, 89, 23);
		contentPane.add(addObject);
		
		objectPath = new JTextField();
		objectPath.setToolTipText("Object File Path");
		objectPath.setBounds(957, 192, 198, 20);
		contentPane.add(objectPath);
		objectPath.setColumns(10);
		
		objectY = new JTextField();
		objectY.setToolTipText("Object Y");
		objectY.setBounds(1211, 192, 36, 20);
		contentPane.add(objectY);
		objectY.setColumns(10);
		
		objectX = new JTextField();
		objectX.setToolTipText("Object X");
		objectX.setBounds(1165, 192, 36, 20);
		contentPane.add(objectX);
		objectX.setColumns(10);
		
		JButton removeObject = new JButton("RemObj");
		removeObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopDrawThread();
				stopTickThread();
				try{
					LayerManager.remObject(Integer.parseInt(remObjIndex.getText()));
				} catch (NumberFormatException e){
					System.out.println("Number Fields Left Blank!");
				}
				startTickThread();
				startDrawThread();
				updateObjectList();
			}
		});
		removeObject.setBounds(1165, 124, 82, 23);
		contentPane.add(removeObject);
		
		remObjIndex = new JTextField();
		remObjIndex.setBounds(1165, 158, 82, 20);
		contentPane.add(remObjIndex);
		remObjIndex.setColumns(10);
		
		lightList = new JTextArea();
		lightList.setBounds(865, 327, 220, 64);
		contentPane.add(lightList);
		
		objList = new JTextArea();
		objList.setBounds(1165, 0, 109, 111);
		contentPane.add(objList);
		
		
		
		
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setAccelerationPriority(1);
		new LayerManager();
		new ObjectManager();
		objectList.setText(AssetLoader.getObjectLiet());
		mapList.setText(AssetLoader.getMapLiet());
		
		JCheckBox drawShade = new JCheckBox("DrawShade");
		drawShade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LayerManager.drawShade = drawShade.isSelected();
				System.out.println(drawShade.isSelected());
				
			}
		});
		drawShade.setSelected(true);
		drawShade.setBounds(1091, 237, 97, 23);
		contentPane.add(drawShade);
		
		JCheckBox drawWorldLoaders = new JCheckBox("DrawWLoads");
		drawWorldLoaders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerManager.drawWorldLoaders = drawWorldLoaders.isSelected();
			}
		});
		drawWorldLoaders.setBounds(1091, 268, 97, 23);
		contentPane.add(drawWorldLoaders);
		
		JButton addWorldLoader = new JButton("AddWLoad");
		addWorldLoader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopDrawThread();
				stopTickThread();
				try{
					LayerManager.addLoader(Integer.parseInt(wLoadWidth.getText()), Integer.parseInt(wLoadHeight.getText()), Integer.parseInt(wLoadX.getText()), Integer.parseInt(wLoadY.getText()), nextWorldPath.getText());
				} catch (NumberFormatException e2){
					System.out.println("Number Fields Left Blank!");
				}
				startTickThread();
				startDrawThread();
				updateWLoaderList();
			}
		});
		addWorldLoader.setBounds(865, 402, 82, 23);
		contentPane.add(addWorldLoader);
		
		wLoadX = new JTextField();
		wLoadX.setToolTipText("X position");
		wLoadX.setBounds(957, 403, 29, 20);
		contentPane.add(wLoadX);
		wLoadX.setColumns(10);
		
		wLoadY = new JTextField();
		wLoadY.setToolTipText("Y position");
		wLoadY.setBounds(985, 403, 29, 20);
		contentPane.add(wLoadY);
		wLoadY.setColumns(10);
		
		worldLoaderList = new JTextArea();
		worldLoaderList.setBounds(865, 427, 220, 64);
		contentPane.add(worldLoaderList);
		
		JButton btnRemwload = new JButton("RemWLoad");
		btnRemwload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopDrawThread();
				stopTickThread();
				try{
					LayerManager.removeLoader(Integer.parseInt(wlRemIndex.getText()));
				} catch (NumberFormatException e2){
					System.out.println("Number Fields Left Blank!");
				}
				startTickThread();
				startDrawThread();
				updateWLoaderList();
			}
		});
		btnRemwload.setBounds(865, 502, 89, 23);
		contentPane.add(btnRemwload);
		
		wlRemIndex = new JTextField();
		wlRemIndex.setToolTipText("Index to remove");
		wlRemIndex.setBounds(957, 503, 128, 20);
		contentPane.add(wlRemIndex);
		wlRemIndex.setColumns(10);
		
		wLoadWidth = new JTextField();
		wLoadWidth.setToolTipText("width");
		wLoadWidth.setBounds(1028, 403, 29, 20);
		contentPane.add(wLoadWidth);
		wLoadWidth.setColumns(10);
		
		wLoadHeight = new JTextField();
		wLoadHeight.setToolTipText("height");
		wLoadHeight.setBounds(1056, 403, 29, 20);
		contentPane.add(wLoadHeight);
		wLoadHeight.setColumns(10);
		
		nextWorldPath = new JTextField();
		nextWorldPath.setToolTipText("Path to next world");
		nextWorldPath.setBounds(865, 531, 220, 20);
		contentPane.add(nextWorldPath);
		nextWorldPath.setColumns(10);
		
		isObjectAnimated = new JTextField();
		isObjectAnimated.setToolTipText("Is object animated 1 or 0");
		isObjectAnimated.setBounds(1257, 192, 17, 20);
		contentPane.add(isObjectAnimated);
		isObjectAnimated.setColumns(10);
		
		
		updateLightList();
		updateObjectList();
		startTickThread();
		startDrawThread();

		//-----------------ENTRY POINT FOR GAME START---------------
		
		//----------------------------------------------------------
	}
	
	public static void update(){
		ImageIcon icon = new ImageIcon(image);
		screen.setIcon(icon);
	}
	
	public static void setPixel(int x, int y, int rgb){
		image.setRGB(x, height-y, rgb);
	}
	
	public static void clear(){
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public static void startDrawThread(){
		drawUpdateThread = new Thread(new UpdateDraw());
		drawUpdateThread.start();
	}
	
	public static void stopDrawThread(){
		drawUpdateThread.stop();
	}
	
	public static void startTickThread(){
		tickerThread = new Thread(new Ticker());
		tickerThread.start();
	}
	
	public static void stopTickThread(){
		tickerThread.stop();
	}
	
	public void updateLightList(){
		String buff = "";
		for(int i=0; i<LayerManager.lights.length; i++){
			buff+=i+". "+LayerManager.lights[i];
			buff+="\n";
		}
		lightList.setText(buff);
	}
	
	public void updateObjectList(){
		String buff = "";
		for(int i=0; i<LayerManager.objects.length; i++){
			buff+=i+". "+LayerManager.objects[i];
			buff+="\n";
		}
		objList.setText(buff);
	}
	
	public void updateWLoaderList(){
		String buff = "";
		for(int i=0; i<LayerManager.wChange.length; i++){
			buff+=i+". "+LayerManager.wChange[i];
			buff+="\n";
		}
		worldLoaderList.setText(buff);
	}
}
