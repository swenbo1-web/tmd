package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 * ɾ����Ա��Ϣ�࣬�����ṩɾ����Ա��Ϣ�Ľ���
 */
public class DeleteStudentInformation extends JPanel implements ActionListener {
	private HashMap<String, Player> informationTable = null; // ������Ϣ��
	private JTextField numberTField, nameTField, majorTField, gradeTField,
			birthdayTField;
	private JRadioButton maleRadioButton, femaleRadioButton;
	private JButton deleteButton;// ɾ����ť
	private ButtonGroup buttonGroup = null;
	private FileInputStream fileInputStream = null;
	private ObjectInputStream objectInputStream = null;
	private FileOutputStream fileOutputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private File systemFile = null;
	private JPanel messPanel;// ��ʾ������Ϣ������
	/**
	 * ���췽������ʼ��ɾ����Ա��Ϣ����
	 */
	public DeleteStudentInformation(File file) {
		systemFile = file;
		informationTable = new HashMap<String,Player>();
		initMessPanel();
		add(messPanel);
		validate();
	}
	/**
	 * ��ʼ����ʾ��Ա��Ϣ���ֽ���
	 */
	public void initMessPanel() {
		JLabel deleteLabel = new JLabel("��Աid:", JLabel.CENTER);
		numberTField = new JTextField(10);
		deleteButton = new JButton("ɾ��");
		deleteButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box box1 = Box.createHorizontalBox();
		box1.add(deleteLabel);
		box1.add(numberTField);
		box1.add(deleteButton);
		JLabel nameLabel = new JLabel("����:", JLabel.CENTER);
		nameTField = new JTextField(10);
		nameTField.setEditable(false);
		Box box2 = Box.createHorizontalBox();
		box2.add(nameLabel);
		box2.add(nameTField);
		JLabel skinLabel = new JLabel("��ɫ:", JLabel.CENTER);
		maleRadioButton = new JRadioButton("��", false);
		femaleRadioButton = new JRadioButton("��", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRadioButton);
		buttonGroup.add(femaleRadioButton);
		Box box3 = Box.createHorizontalBox();
		box3.add(skinLabel);
		box3.add(maleRadioButton);
		box3.add(femaleRadioButton);
		JLabel majorLabel = new JLabel("λ��:", JLabel.CENTER);
		majorTField = new JTextField(10);
		majorTField.setEditable(false);
		Box box4 = Box.createHorizontalBox();
		box4.add(majorLabel);
		box4.add(majorTField);
		JLabel gradeLabel = new JLabel("�÷�:", JLabel.CENTER);
		gradeTField = new JTextField(10);
		gradeTField.setEditable(false);
		Box box5 = Box.createHorizontalBox();
		box5.add(gradeLabel);
		box5.add(gradeTField);
		JLabel birthdayLabel = new JLabel("���:", JLabel.CENTER);
		birthdayTField = new JTextField(10);
		birthdayTField.setEditable(false);
		Box box6 = Box.createHorizontalBox();
		box6.add(birthdayLabel);
		box6.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(box5);
		boxH.add(box6);
		boxH.add(Box.createVerticalGlue());
		messPanel = new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * �����ɾ����ť������Ա�ı����лس�ʱִ�еĲ���
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteButton || e.getSource() == numberTField) {
			String number = "";
			number = numberTField.getText();
			if (number.length() > 0) {
				try {
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap) objectInputStream.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {
					Player stu = (Player) informationTable.get(number);
					nameTField.setText(stu.getName());
					majorTField.setText(stu.getMajor());
					gradeTField.setText(stu.getGrade());
					birthdayTField.setText(stu.getBirthday());
					if (stu.getskin().equals("��"))
						maleRadioButton.setSelected(true);
					else
						femaleRadioButton.setSelected(true);
					String m = "ȷ��Ҫɾ������Աid��ȫ����Ϣ��?";
					int ok = JOptionPane.showConfirmDialog(this, m, "ȷ��",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					;
					if (ok == JOptionPane.YES_OPTION) {
						informationTable.remove(number);
						try {
							fileOutputStream = new FileOutputStream(systemFile);
							objectOutputStream = new ObjectOutputStream(
									fileOutputStream);
							objectOutputStream.writeObject(informationTable);
							objectOutputStream.close();
							fileOutputStream.close();
							clearMessage();
						} catch (Exception ee) {
						}
					} else if (ok == JOptionPane.NO_OPTION) {
						clearMessage();
					}
				} else {
					String warning = "����Աid������!";
					JOptionPane.showMessageDialog(this, warning, "����",
							JOptionPane.WARNING_MESSAGE);
					numberTField.setText(null);
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
		majorTField.setText(null);
		gradeTField.setText(null);
		birthdayTField.setText(null);
	}
}
