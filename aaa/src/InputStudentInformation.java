package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * 录入球员信息类，负责提供录入球员信息的界面
 */
public class InputStudentInformation extends JPanel implements ActionListener {
	private Player player = null;// 球员对象
	private HashMap<String, Player> informationTable = null;
	private JTextField numberTField, nameTField, gradeTField, birthdayTField;
	private JButton picButton;// 选择照片按钮
	private JLabel promptLabel;//提示信息
	private JComboBox<String> majorComBox; // 球队列表框
	private JRadioButton maleRButton, femaleRButton;// 单选按钮，选择黑或者白
	private ButtonGroup buttonGroup = null;
	private JButton inputButton, resetButton;// 输入按钮、重置按钮
	private FileInputStream fileInputStream = null;// 文件输入流对象
	private ObjectInputStream objectInputStream = null;// 对象输入流对象
	private FileOutputStream fileOutputStream = null;// 文件输出流对象
	private ObjectOutputStream objectOutputStream = null;// 对象输出流对象
	private File systemFile, imagePic;
	private JPanel putButtonPanel;//录入和重置按钮的容器
	private JPanel messPanel,picPanel;//基本信息和照片的容器
	/**
	 *构造方法,初始化录入界面
	 */
	public InputStudentInformation(File file) {
		systemFile = file;	
		informationTable = new HashMap<String,Player>();
		promptLabel = new JLabel("请输入以下信息：",JLabel.LEFT);
		promptLabel.setFont(new Font("宋体",Font.BOLD,13));//设置提示信息的字体
		promptLabel.setForeground(Color.RED);
		promptLabel.setOpaque(true);//设置为不透明
		promptLabel.setBackground(new Color(216,224,231));//设置背景颜色	
		initMessPanel();
		initPutButtonJPanel();
		initPicPanel();
		setLayout(new BorderLayout());
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messPanel, picPanel);	
		add(promptLabel,BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
		add(putButtonPanel, BorderLayout.SOUTH);
		validate();
	}
	/**
	 * 初始化显示信息界面
	 */
	public void initMessPanel(){
		JLabel numberLabel = new JLabel("球员id:", JLabel.CENTER);
		numberTField = new JTextField(5);
		Box numberBox = Box.createHorizontalBox(); // 添加水平box
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		JLabel nameLabel = new JLabel("姓名:", JLabel.CENTER);
		nameTField = new JTextField(5);
		Box nameBox = Box.createHorizontalBox(); // 添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel skinLabel = new JLabel("肤色:", JLabel.CENTER);
		maleRButton = new JRadioButton("黑", true);
		femaleRButton = new JRadioButton("白", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRButton);
		buttonGroup.add(femaleRButton);
		Box sexBox = Box.createHorizontalBox(); // 添加水平box
		sexBox.add(skinLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel = new JLabel("位置:", JLabel.CENTER);
		majorComBox = new JComboBox<String>();
		try {
			// ，加入到组合框中
			FileReader fileReader = new FileReader("位置.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s = null;
			int i = 0;
			while ((s = bufferedReader.readLine()) != null)
				majorComBox.addItem(s);
			fileReader.close();
			bufferedReader.close();
		} catch (IOException exp) {// 如有异常
			majorComBox.addItem("后卫");
			majorComBox.addItem("前锋");
			majorComBox.addItem("中锋");
		}
		Box majorBox = Box.createHorizontalBox(); // 添加水平box
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel = new JLabel("得分:", JLabel.CENTER);
		gradeTField = new JTextField(5);
		Box gradeBox = Box.createHorizontalBox(); // 添加水平box
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("球队:", JLabel.CENTER);
		birthdayTField = new JTextField(5);
		Box birthdayBox = Box.createHorizontalBox(); // 添加水平box
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
		boxH.add(numberBox);
		boxH.add(nameBox);
		boxH.add(sexBox);
		boxH.add(majorBox);
		boxH.add(gradeBox);
		boxH.add(birthdayBox);
		boxH.add(Box.createVerticalGlue()); // 添加垂直胶水
		messPanel = new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * 初始化照片部分的界面
	 */
	public void initPicPanel(){
		JLabel picLabel = new JLabel("照片：",JLabel.LEFT);
		picButton = new JButton("选择照片");
		picButton.addActionListener(this);	
		picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(picButton,BorderLayout.SOUTH);
	}
	/**
	 * 初始化录入、重置按钮界面
	 */
	public void initPutButtonJPanel(){
		inputButton = new JButton("录入");
		resetButton = new JButton("重置");
		inputButton.addActionListener(this); // 添加事件监听对象
		resetButton.addActionListener(this); // 添加事件监听对象
		putButtonPanel = new JPanel();
		putButtonPanel.setBackground(new Color(216,224,231));
		putButtonPanel.add(inputButton);
		putButtonPanel.add(resetButton);
	}
	/**
	 * 当点击录入按钮、重置按钮和选择照片按钮时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputButton) {// 如果点击录入按钮
			String number = "";
			number = numberTField.getText();// 读取球员id信息
			if (number.length() > 0) {
				try {// 从文件中读取信息
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap<String, Player>) objectInputStream.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) { // 如果该学号存在 ，显示警告信息
					String warning = "该球员基本信息已存在,请到修改页面修改!";
					JOptionPane.showMessageDialog(this, warning, "警告",
							JOptionPane.WARNING_MESSAGE);
				} else { // 如果信息不存在，将输入的数据保存
					String m = "确定录入该球员信息？";
					int ok = JOptionPane.showConfirmDialog(this, m, "确认",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (ok == JOptionPane.YES_OPTION) {
						String name = nameTField.getText();
						String major = (String) majorComBox.getSelectedItem();
						String grade = gradeTField.getText();
						String birth = birthdayTField.getText();
						String sex = null;
						if (maleRButton.isSelected())
							sex = maleRButton.getText();
						else
							sex = femaleRButton.getText();
						player = new Player();
						player.setNumber(number);
						player.setName(name);
						player.setMajor(major);
						player.setGrade(grade);
						player.setBirthday(birth);
						player.setskin(sex);
						player.setImagePic(imagePic);
						try { // 将信息保存在文件中
							fileOutputStream = new FileOutputStream(systemFile);
							objectOutputStream = new ObjectOutputStream(
									fileOutputStream);
							informationTable.put(number, player);
							objectOutputStream.writeObject(informationTable);
							objectOutputStream.close();
							fileOutputStream.close();
							clearMessage();
						} catch (Exception ee) {
						}
					}
				}
			} else {
				String warning = "必须要输入球员id!";
				JOptionPane.showMessageDialog(this, warning, "警告",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == picButton) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");
			chooser.setFileFilter(filter);
			int state = chooser.showOpenDialog(null);
			File choiceFile = chooser.getSelectedFile();
			if (choiceFile != null && state == JFileChooser.APPROVE_OPTION) {
				picButton.setText("重新选择");
				imagePic = choiceFile;
			}
		} else if (e.getSource() == resetButton) {
			clearMessage();
		}
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		numberTField.setText(null);
		nameTField.setText(null);
		gradeTField.setText(null);
		birthdayTField.setText(null);
		picButton.setText("选择照片");
		imagePic = null;
		
	}
}
