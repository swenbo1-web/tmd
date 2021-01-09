package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;

/**
 * �޸���ԱϢ�࣬�����ṩ�޸���Ա��Ϣ�Ľ���
 */
public class ModifyStudentInformation extends JPanel implements ActionListener {
	private HashMap<String, Player> informationTable = null;
	private JTextField numberTField, nameTField, gradeTField, birthdayTField;
	private JComboBox majorComBox;
	private JButton picButton;
	private JRadioButton maleRButton, femaleRButton;
	private JButton beginModifyButton, modifyButton, resetButton;
	private ButtonGroup buttonGroup = null;
	private Player player = null;
	private FileInputStream inOne = null;
	private ObjectInputStream inTwo = null;
	private FileOutputStream outOne = null;
	private ObjectOutputStream outTwo = null;
	private File systemFile, imagePic;
	private JPanel messPanel;// ��ʾ������Ϣ������

	/**
	 * ���췽������ʼ���޸���Ա��Ϣ����
	 */
	public ModifyStudentInformation(File file) {
		systemFile = file;
		informationTable = new HashMap<String,Player>();
		initMessPanel();
		picButton = new JButton("ѡ����Ƭ");
		picButton.addActionListener(this);
		JPanel picPanel = new JPanel();// ѡ����Ա��Ƭ������
		picPanel.add(picButton);
		modifyButton = new JButton("�޸�");
		resetButton = new JButton("����");
		modifyButton.addActionListener(this); // ����¼���������
		resetButton.addActionListener(this); // ����¼���������
		JPanel putButtonJPanel = new JPanel();
		putButtonJPanel.add(modifyButton);
		putButtonJPanel.add(resetButton);
		setLayout(new BorderLayout());
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messPanel, picPanel);
		add(splitH, BorderLayout.CENTER);
		add(putButtonJPanel, BorderLayout.SOUTH);
		validate();
	}

	/**
	 * ��ʼ����ʾ��Ա��Ϣ���ֽ���
	 */
	public void initMessPanel() {
		JLabel numberLabel = new JLabel("(��)��Աid:", JLabel.CENTER);
		numberTField = new JTextField(5);
		beginModifyButton = new JButton("��ʼ�޸�");
		beginModifyButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box numberBox = Box.createHorizontalBox(); // ����ˮƽbox
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		numberBox.add(beginModifyButton);
		JLabel nameLabel = new JLabel("���£�����:", JLabel.CENTER);
		nameTField = new JTextField(5);
		Box nameBox = Box.createHorizontalBox(); // ����ˮƽbox
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel sexLabel = new JLabel("���£���ɫ:", JLabel.CENTER);
		maleRButton = new JRadioButton("��", true);
		femaleRButton = new JRadioButton("��", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRButton);
		buttonGroup.add(femaleRButton);
		Box sexBox = Box.createHorizontalBox(); // ����ˮƽbox
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel = new JLabel("���£�λ��:", JLabel.CENTER);
		majorComBox = new JComboBox<String>();
		try {
			// �����뵽��Ͽ���
			FileReader fileReader = new FileReader("λ��.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s = null;
			int i = 0;
			while ((s = bufferedReader.readLine()) != null)
				majorComBox.addItem(s);
			fileReader.close();
			bufferedReader.close();
		} catch (IOException exp) {// �����쳣��
			majorComBox.addItem("����");
			majorComBox.addItem("ǰ��");
			majorComBox.addItem("�з�");
		}
		Box majorBox = Box.createHorizontalBox(); // ����ˮƽbox
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel = new JLabel("���£��÷�:", JLabel.CENTER);
		gradeTField = new JTextField(5);
		Box gradeBox = Box.createHorizontalBox(); // ����ˮƽbox
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("���£����:", JLabel.CENTER);
		birthdayTField = new JTextField(5);
		Box birthdayBox = Box.createHorizontalBox(); // ����ˮƽbox
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);

		Box boxH = Box.createVerticalBox(); // ������ֱbox
		boxH.add(numberBox);
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
	 * �������ʼ�޸İ�ť���޸İ�ť��ѡ����Ƭ��ť����id�ı����лس�ʱִ�еĲ���
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == beginModifyButton || e.getSource() == numberTField) {
			String number = "";
			imagePic = null;
			 player= null;
			number = numberTField.getText();
			if (number.length() > 0) {// ��������Աid
				try {
					inOne = new FileInputStream(systemFile);
					inTwo = new ObjectInputStream(inOne);
					informationTable = (HashMap<String, Player>) inTwo
							.readObject();
					inOne.close();
					inTwo.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {// ����Ա����
					modifyButton.setEnabled(true);
					picButton.setEnabled(true);
					player= informationTable.get(number);
					nameTField.setText(player.getName());
					if (player.getskin().equals("��"))
						maleRButton.setSelected(true);
					else
						femaleRButton.setSelected(true);
					gradeTField.setText(player.getGrade());
					birthdayTField.setText(player.getBirthday());
					imagePic = player.getImagePic();
				} else {// ������Աid������
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					String warning = "����Աid������!";
					JOptionPane.showMessageDialog(this, warning, "����",
							JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			} else {// û��������Աid
				modifyButton.setEnabled(false);
				picButton.setEnabled(false);
				String warning = "��������Աid!";
				JOptionPane.showMessageDialog(this, warning, "����",
						JOptionPane.WARNING_MESSAGE);
				clearMessage();
			}
		} else if (e.getSource() == modifyButton) {// �����޸İ�ť
			String number = "";
			number = numberTField.getText();
			if (number.length() > 0) {// ��������Աid
				try {
					inOne = new FileInputStream(systemFile);
					inTwo = new ObjectInputStream(inOne);
					informationTable = (HashMap<String, Player>) inTwo
							.readObject();
					inOne.close();
					inTwo.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {// ��ѧ�Ŵ���,���޸�
					String question = "ȷ���޸ĸ���Ա�Ļ�����Ϣ��?";
					JOptionPane.showMessageDialog(this, question, "����",
							JOptionPane.QUESTION_MESSAGE);
					String m = "������Ϣ�����޸�!";
					int ok = JOptionPane.showConfirmDialog(this, m, "ȷ��",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (ok == JOptionPane.YES_OPTION) {// �޸ģ�����޸ĺ����Ϣ
						String name = nameTField.getText();
						if (name.length() == 0)
							name = player.getName();
						String sex = null;
						if (maleRButton.isSelected())
							sex = maleRButton.getText();
						else
							sex = femaleRButton.getText();
						String major = (String) majorComBox.getSelectedItem();
						if (major == null)
							major = player.getMajor();
						String grade = gradeTField.getText();
						if (grade.length() == 0)
							grade = player.getGrade();
						String birth = birthdayTField.getText();
						if (birth.length() == 0)
							birth = player.getBirthday();
						if (imagePic == null)
							imagePic = player.getImagePic();
						Player stu = new Player();
						stu.setNumber(number);
						stu.setName(name);
						stu.setMajor(major);
						stu.setGrade(grade);
						stu.setBirthday(birth);
						stu.setskin(sex);
						stu.setImagePic(imagePic);
						try {// �����޸ĺ����Ϣ
							outOne = new FileOutputStream(systemFile);
							outTwo = new ObjectOutputStream(outOne);
							informationTable.put(number, stu);
							outTwo.writeObject(informationTable);

							outTwo.close();
							outOne.close();
							clearMessage();
						} catch (Exception ee) {
						}
						modifyButton.setEnabled(false);
						picButton.setEnabled(false);
					} else if (ok == JOptionPane.NO_OPTION) {
						modifyButton.setEnabled(true);
						picButton.setEnabled(true);
					}
				} else {// ����Ա������
					String warning = "û�и���Աid��Ա�Ļ�����Ϣ!";
					JOptionPane.showMessageDialog(this, warning, "����",
							JOptionPane.WARNING_MESSAGE);
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					clearMessage();
				}
			} else {// ��ԱidΪ��
				String warning = "����Ҫ������Աid!";
				JOptionPane.showMessageDialog(this, warning, "����",
						JOptionPane.WARNING_MESSAGE);
				modifyButton.setEnabled(false);
				clearMessage();
				picButton.setEnabled(false);
			}
		} else if (e.getSource() == picButton) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");
			chooser.setFileFilter(filter);
			int state = chooser.showOpenDialog(null);
			File choiceFile = chooser.getSelectedFile();
			if (choiceFile != null && state == JFileChooser.APPROVE_OPTION) {
				picButton.setText("����ѡ��");
				imagePic = choiceFile;			}
		} else if (e.getSource() == resetButton) {
			clearMessage();
			modifyButton.setEnabled(false);
			picButton.setEnabled(false);
		}
	}

	/**
	 * ����ʾ����Ϣ���
	 */
	public void clearMessage() {
		numberTField.setText(null);
		nameTField.setText(null);
		gradeTField.setText(null);
		birthdayTField.setText(null);
		picButton.setText("ѡ��");
		imagePic = null;
	}
}
