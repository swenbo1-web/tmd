package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
/**
 * ����ϵͳ�����������ĳ�ʼ��
 */
public class InformationWindow extends JFrame implements ActionListener {
	private InputStudentInformation inputStudentInformation; // ¼����Ϣ
	private ModifyStudentInformation modifyStudentInformation; // �޸���Ϣ
	private QueryStudentInformation queryStudentInformation; // ��ѯ��Ϣ
	private DeleteStudentInformation deleteStudentInformation; // ɾ����Ϣ
	private JMenuBar bar;// �˵���
	private JMenu fileMenu;
	private JMenuItem inputItem, modifyItem, queryItem, deleteItem,
			welcomeItem;// ���˵���
	private HashMap<String, Player> informationTable = null; // ��Ա��Ϣ��
	private File file = null;
	private CardLayout card = null;
	private JLabel label = null;
	private JPanel pCenter;
	/**
	 * ���췽������ʼ��������
	 */
	public InformationWindow() {
		informationTable = new HashMap<String,Player>();
		initFrame();
		setVisible(true);
		setBounds(100, 50, 380, 350);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "ȷ���˳���?", "ȷ�϶Ի���",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setResizable(false);
		validate();
	}
	/**
	 * ��ʼ��������ĸ������
	 */
	public void initFrame() {
		inputItem = new JMenuItem("¼��");
		modifyItem = new JMenuItem("�޸�");
		queryItem = new JMenuItem("��ѯ");
		deleteItem = new JMenuItem("ɾ��");
		welcomeItem = new JMenuItem("��ӭ����");
		bar = new JMenuBar();
		fileMenu = new JMenu("�˵�ѡ��");
		fileMenu.add(inputItem);
		fileMenu.add(modifyItem);
		fileMenu.add(queryItem);
		fileMenu.add(deleteItem);
		fileMenu.add(welcomeItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
		label = new JLabel("��Ա����ϵͳ", JLabel.CENTER);
		label.setIcon(new ImageIcon("welcome.jpg"));
		label.setFont(new Font("����", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.red);
		informationTable = new HashMap<String, Player>();
		inputItem.addActionListener(this);
		modifyItem.addActionListener(this);
		queryItem.addActionListener(this);
		deleteItem.addActionListener(this);
		welcomeItem.addActionListener(this);
		card = new CardLayout();
		pCenter = new JPanel();
		pCenter.setLayout(card);
		file = new File("������Ϣ.txt");
		if (!file.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				ObjectOutputStream objectOut = new ObjectOutputStream(out);
				objectOut.writeObject(informationTable);
				objectOut.close();
				out.close();
			} catch (IOException e) {
			}
		}
		inputStudentInformation = new InputStudentInformation(file);
		modifyStudentInformation = new ModifyStudentInformation(file);
		queryStudentInformation = new QueryStudentInformation(file);
		deleteStudentInformation = new DeleteStudentInformation(file);
		pCenter.add("��ӭ����", label);
		pCenter.add("¼�����", inputStudentInformation);
		pCenter.add("ɾ������", deleteStudentInformation);
		pCenter.add("��ѯ����", queryStudentInformation);
		pCenter.add("�޸Ľ���", modifyStudentInformation);
		add(pCenter, BorderLayout.CENTER);
	}
	/**
	 * �����¼�롢�޸ġ���ѯ��ɾ������ӭ�˵���ʱִ�еĲ���
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputItem) {
			inputStudentInformation.clearMessage();
			card.show(pCenter, "¼�����");
		} else if (e.getSource() == modifyItem) {
			modifyStudentInformation.clearMessage();
			card.show(pCenter, "�޸Ľ���");
		} else if (e.getSource() == queryItem) {
			queryStudentInformation.clearMessage();
			card.show(pCenter, "��ѯ����");
		} else if (e.getSource() == deleteItem)
			card.show(pCenter, "ɾ������");
		else if (e.getSource() == welcomeItem)
			card.show(pCenter, "��ӭ����");
	}
	/**
	 * ����ϵͳ
	 */
	public static void main(String args[]) {
		new InformationWindow();
	}
}
