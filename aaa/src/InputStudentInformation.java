package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * ¼����Ա��Ϣ�࣬�����ṩ¼����Ա��Ϣ�Ľ���
 */
public class InputStudentInformation extends JPanel implements ActionListener {
	private Player player = null;// ��Ա����
	private HashMap<String, Player> informationTable = null;
	private JTextField numberTField, nameTField, gradeTField, birthdayTField;
	private JButton picButton;// ѡ����Ƭ��ť
	private JLabel promptLabel;//��ʾ��Ϣ
	private JComboBox<String> majorComBox; // ����б��
	private JRadioButton maleRButton, femaleRButton;// ��ѡ��ť��ѡ��ڻ��߰�
	private ButtonGroup buttonGroup = null;
	private JButton inputButton, resetButton;// ���밴ť�����ð�ť
	private FileInputStream fileInputStream = null;// �ļ�����������
	private ObjectInputStream objectInputStream = null;// ��������������
	private FileOutputStream fileOutputStream = null;// �ļ����������
	private ObjectOutputStream objectOutputStream = null;// �������������
	private File systemFile, imagePic;
	private JPanel putButtonPanel;//¼������ð�ť������
	private JPanel messPanel,picPanel;//������Ϣ����Ƭ������
	/**
	 *���췽��,��ʼ��¼�����
	 */
	public InputStudentInformation(File file) {
		systemFile = file;	
		informationTable = new HashMap<String,Player>();
		promptLabel = new JLabel("������������Ϣ��",JLabel.LEFT);
		promptLabel.setFont(new Font("����",Font.BOLD,13));//������ʾ��Ϣ������
		promptLabel.setForeground(Color.RED);
		promptLabel.setOpaque(true);//����Ϊ��͸��
		promptLabel.setBackground(new Color(216,224,231));//���ñ�����ɫ	
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
	 * ��ʼ����ʾ��Ϣ����
	 */
	public void initMessPanel(){
		JLabel numberLabel = new JLabel("��Աid:", JLabel.CENTER);
		numberTField = new JTextField(5);
		Box numberBox = Box.createHorizontalBox(); // ���ˮƽbox
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		JLabel nameLabel = new JLabel("����:", JLabel.CENTER);
		nameTField = new JTextField(5);
		Box nameBox = Box.createHorizontalBox(); // ���ˮƽbox
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel skinLabel = new JLabel("��ɫ:", JLabel.CENTER);
		maleRButton = new JRadioButton("��", true);
		femaleRButton = new JRadioButton("��", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRButton);
		buttonGroup.add(femaleRButton);
		Box sexBox = Box.createHorizontalBox(); // ���ˮƽbox
		sexBox.add(skinLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel = new JLabel("λ��:", JLabel.CENTER);
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
		} catch (IOException exp) {// �����쳣
			majorComBox.addItem("����");
			majorComBox.addItem("ǰ��");
			majorComBox.addItem("�з�");
		}
		Box majorBox = Box.createHorizontalBox(); // ���ˮƽbox
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel = new JLabel("�÷�:", JLabel.CENTER);
		gradeTField = new JTextField(5);
		Box gradeBox = Box.createHorizontalBox(); // ���ˮƽbox
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("���:", JLabel.CENTER);
		birthdayTField = new JTextField(5);
		Box birthdayBox = Box.createHorizontalBox(); // ���ˮƽbox
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
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
	 * ��ʼ����Ƭ���ֵĽ���
	 */
	public void initPicPanel(){
		JLabel picLabel = new JLabel("��Ƭ��",JLabel.LEFT);
		picButton = new JButton("ѡ����Ƭ");
		picButton.addActionListener(this);	
		picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(picButton,BorderLayout.SOUTH);
	}
	/**
	 * ��ʼ��¼�롢���ð�ť����
	 */
	public void initPutButtonJPanel(){
		inputButton = new JButton("¼��");
		resetButton = new JButton("����");
		inputButton.addActionListener(this); // ����¼���������
		resetButton.addActionListener(this); // ����¼���������
		putButtonPanel = new JPanel();
		putButtonPanel.setBackground(new Color(216,224,231));
		putButtonPanel.add(inputButton);
		putButtonPanel.add(resetButton);
	}
	/**
	 * �����¼�밴ť�����ð�ť��ѡ����Ƭ��ťʱִ�еĲ���
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputButton) {// ������¼�밴ť
			String number = "";
			number = numberTField.getText();// ��ȡ��Աid��Ϣ
			if (number.length() > 0) {
				try {// ���ļ��ж�ȡ��Ϣ
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap<String, Player>) objectInputStream.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) { // �����ѧ�Ŵ��� ����ʾ������Ϣ
					String warning = "����Ա������Ϣ�Ѵ���,�뵽�޸�ҳ���޸�!";
					JOptionPane.showMessageDialog(this, warning, "����",
							JOptionPane.WARNING_MESSAGE);
				} else { // �����Ϣ�����ڣ�����������ݱ���
					String m = "ȷ��¼�����Ա��Ϣ��";
					int ok = JOptionPane.showConfirmDialog(this, m, "ȷ��",
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
						try { // ����Ϣ�������ļ���
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
				String warning = "����Ҫ������Աid!";
				JOptionPane.showMessageDialog(this, warning, "����",
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
				picButton.setText("����ѡ��");
				imagePic = choiceFile;
			}
		} else if (e.getSource() == resetButton) {
			clearMessage();
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
		picButton.setText("ѡ����Ƭ");
		imagePic = null;
		
	}
}
