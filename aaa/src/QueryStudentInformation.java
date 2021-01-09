package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * 查询球员信息类，负责提供查询球员信息的界面
 */
public class QueryStudentInformation extends JPanel implements ActionListener {
	private Player player = null;// 球员对象
	private HashMap<String, Player> informationTable = null;
	private JTextField numberTField, nameTField, skinTField, majorTField,
			gradeTField, birthdayTField;
	private JButton queryButton;// 查询按钮
	private FileInputStream fileInputStream = null;// 文件输入流对象
	private ObjectInputStream objectInputStream = null;// 对象输入流对象
	private File systemFile, imagePic;
	private JPanel messPanel = null;// 显示基本信息的容器
	/**
	 * 构造方法，初始化查询球员信息界面
	 */
	public QueryStudentInformation(File file) {
		systemFile = file;
		informationTable = new HashMap<String,Player>();
		JLabel numberLabel = new JLabel("输入要查询的球员id:", JLabel.CENTER);
		numberTField = new JTextField(10);
		queryButton = new JButton("查询");
		queryButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box numberBox = Box.createHorizontalBox(); // 添加水平box
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		numberBox.add(queryButton);
		initMessPanel();
		JLabel picLabel = new JLabel("照片： ", JLabel.LEFT);
		JPanel picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel, BorderLayout.NORTH);
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messPanel, picPanel);
		setLayout(new BorderLayout());
		add(numberBox, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
		validate();
	}
	/**
	 * 初始化显示球员信息部分界面
	 */
	public void initMessPanel() {
		JLabel nameLabel = new JLabel("姓名:", JLabel.CENTER);
		nameTField = new JTextField(10);
		nameTField.setEditable(false);
		Box nameBox = Box.createHorizontalBox(); // 添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel sexLabel = new JLabel("肤色:", JLabel.CENTER);
		skinTField = new JTextField(10);
		skinTField.setEditable(false);
		Box sexBox = Box.createHorizontalBox(); // 添加水平box
		sexBox.add(sexLabel);
		sexBox.add(skinTField);
		JLabel majorLabel = new JLabel("位置:", JLabel.CENTER);
		majorTField = new JTextField(10);
		majorTField.setEditable(false);
		Box majorBox = Box.createHorizontalBox(); // 添加水平box
		majorBox.add(majorLabel);
		majorBox.add(majorTField);
		JLabel gradeLabel = new JLabel("得分:", JLabel.CENTER);
		gradeTField = new JTextField(10);
		gradeTField.setEditable(false);
		Box gradeBox = Box.createHorizontalBox(); // 添加水平box
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("球队:", JLabel.CENTER);
		birthdayTField = new JTextField(10);
		birthdayTField.setEditable(false);
		Box birthdayBox = Box.createHorizontalBox(); // 添加水平box
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
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
	 * 当点击查询按钮和在学号文本框中回车时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == queryButton || e.getSource() == numberTField) {
			String number = "";
			number = numberTField.getText();
			if (number.length() > 0) {
				try {
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap<String, Player>) objectInputStream
							.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {
					player = informationTable.get(number);
					nameTField.setText(player.getName());
					majorTField.setText(player.getMajor());
					gradeTField.setText(player.getGrade());
					birthdayTField.setText(player.getBirthday());
					skinTField.setText(player.getskin());
				} else {
					String warning = "该球员id不存在!";
					JOptionPane.showMessageDialog(this, warning, "警告",
							JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			} else {
				String warning = "必须要输入球员id!";
				JOptionPane.showMessageDialog(this, warning, "警告",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		numberTField.setText(null);
		nameTField.setText(null);
		gradeTField.setText(null);
		skinTField.setText(null);
		birthdayTField.setText(null);
		majorTField.setText(null);
	}
}
