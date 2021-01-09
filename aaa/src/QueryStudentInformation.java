package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * ��ѯ��Ա��Ϣ�࣬�����ṩ��ѯ��Ա��Ϣ�Ľ���
 */
public class QueryStudentInformation extends JPanel implements ActionListener {
	private Player player = null;// ��Ա����
	private HashMap<String, Player> informationTable = null;
	private JTextField numberTField, nameTField, skinTField, majorTField,
			gradeTField, birthdayTField;
	private JButton queryButton;// ��ѯ��ť
	private FileInputStream fileInputStream = null;// �ļ�����������
	private ObjectInputStream objectInputStream = null;// ��������������
	private File systemFile, imagePic;
	private JPanel messPanel = null;// ��ʾ������Ϣ������
	/**
	 * ���췽������ʼ����ѯ��Ա��Ϣ����
	 */
	public QueryStudentInformation(File file) {
		systemFile = file;
		informationTable = new HashMap<String,Player>();
		JLabel numberLabel = new JLabel("����Ҫ��ѯ����Աid:", JLabel.CENTER);
		numberTField = new JTextField(10);
		queryButton = new JButton("��ѯ");
		queryButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box numberBox = Box.createHorizontalBox(); // ���ˮƽbox
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		numberBox.add(queryButton);
		initMessPanel();
		JLabel picLabel = new JLabel("��Ƭ�� ", JLabel.LEFT);
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
	 * ��ʼ����ʾ��Ա��Ϣ���ֽ���
	 */
	public void initMessPanel() {
		JLabel nameLabel = new JLabel("����:", JLabel.CENTER);
		nameTField = new JTextField(10);
		nameTField.setEditable(false);
		Box nameBox = Box.createHorizontalBox(); // ���ˮƽbox
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel sexLabel = new JLabel("��ɫ:", JLabel.CENTER);
		skinTField = new JTextField(10);
		skinTField.setEditable(false);
		Box sexBox = Box.createHorizontalBox(); // ���ˮƽbox
		sexBox.add(sexLabel);
		sexBox.add(skinTField);
		JLabel majorLabel = new JLabel("λ��:", JLabel.CENTER);
		majorTField = new JTextField(10);
		majorTField.setEditable(false);
		Box majorBox = Box.createHorizontalBox(); // ���ˮƽbox
		majorBox.add(majorLabel);
		majorBox.add(majorTField);
		JLabel gradeLabel = new JLabel("�÷�:", JLabel.CENTER);
		gradeTField = new JTextField(10);
		gradeTField.setEditable(false);
		Box gradeBox = Box.createHorizontalBox(); // ���ˮƽbox
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("���:", JLabel.CENTER);
		birthdayTField = new JTextField(10);
		birthdayTField.setEditable(false);
		Box birthdayBox = Box.createHorizontalBox(); // ���ˮƽbox
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
		boxH.add(nameBox);
		boxH.add(sexBox);
		boxH.add(majorBox);
		boxH.add(gradeBox);
		boxH.add(birthdayBox);
		boxH.add(Box.createVerticalGlue()); // ��Ӵ�ֱ��ˮ
		messPanel = new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * �������ѯ��ť����ѧ���ı����лس�ʱִ�еĲ���
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
					String warning = "����Աid������!";
					JOptionPane.showMessageDialog(this, warning, "����",
							JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			} else {
				String warning = "����Ҫ������Աid!";
				JOptionPane.showMessageDialog(this, warning, "����",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	/**
	 * ����ʾ����Ϣ���
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
