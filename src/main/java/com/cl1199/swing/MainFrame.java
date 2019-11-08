package com.cl1199.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.NumberFormatter;

import com.cl1199.service.Api;

public class MainFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String betBonusGroup = "1998";//奖金组
	
	//上子组件 投注配置组件
    private JPanel topPanel = new JPanel(new FlowLayout());
    //下子组件 日志组件
    private JPanel bottomPanel = new JPanel();;

    private JPanel loginPanel = new JPanel(new FlowLayout());;
    //创建选择投注号码位置 1 2 3 4 5 6 7 8 9 10 选择那一条马
    private JPanel horseNumberPanel = new JPanel(new FlowLayout());;

    //选择哪个赛道去跑
    private JPanel horseRoadPanel = new JPanel(new FlowLayout());
    //圆角分组件
    private JPanel moneyUnitPanel = new JPanel();
    //日志框
    private JTextArea logArea = new JTextArea(20,80);

    //赛道
    private JComboBox<String> roadBox;

    //下注金额
    private JFormattedTextField  betField;
    
    private Thread thread = null;
    
    private JRadioButton yuanRadio = new JRadioButton("元");
    
    private JRadioButton jiaoRadio = new JRadioButton("角");
    
    private JRadioButton fenRadio = new JRadioButton("分",true);
    
    private JCheckBox item1 = new JCheckBox("1");
    private JCheckBox item2 = new JCheckBox("2");
    private JCheckBox item3 = new JCheckBox("3");
    private JCheckBox item4 = new JCheckBox("4");
    private JCheckBox item5 = new JCheckBox("5");
    private JCheckBox item6 = new JCheckBox("6");
    private JCheckBox item7 = new JCheckBox("7");
    private JCheckBox item8 = new JCheckBox("8");
    private JCheckBox item9 = new JCheckBox("9");
    private JCheckBox item10 = new JCheckBox("10");
    public MainFrame(){
        //上下布局
        this.setLayout(new FlowLayout());
        //左上角
        this.setLocation(0,0);
        //设置不可扩大 缩小界面
        this.setResizable(true);
        //设置窗口 宽 高
        this.setSize(1265,500);
        //设置关闭退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置整体ui
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //设置标题
        this.setTitle("飞艇自动投注工具V0.1");
        //初始化子组件
        init();
    }

    private void init() {
    	NumberFormat longFormat = NumberFormat.getIntegerInstance();
    	NumberFormatter numberFormatter = new NumberFormatter(longFormat);
    	numberFormatter.setValueClass(Long.class);
    	numberFormatter.setAllowsInvalid(false);
    	numberFormatter.setMinimum(1l);
    	logArea.setEditable(false);
    	betField = new JFormattedTextField(numberFormatter);
    	betField.setPreferredSize(new Dimension(100, 25));
        initTopPanel();
        initBottomPanel();
        add(topPanel,BorderLayout.NORTH);
        add(bottomPanel,BorderLayout.CENTER);
        //设置可见
        this.setVisible(true);
    }

    private void initBottomPanel() {
        JScrollPane scrollPane = new JScrollPane(logArea);
        this.bottomPanel.add(scrollPane);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        betField.setText("1");
        JTextField userText = new JTextField("lin2732903",10);
        JPasswordField passText = new JPasswordField("a123456",10);
        //创建登录账号密码配置区域 username password
        createLoginPanel(userText, passText);
        //创建选择投注号码位置 1 2 3 4 5 6 7 8 9 10 选择那一条马
        createHorseNumberPanel();
        //创建下拉某一赛道 冠军 亚军 第三名  第四名 .... 第十名  选择在哪条赛道上跑
        createHorseRoadPanel();
        //创建圆角分选择
        createMoneyUnitPanel();

        JLabel betLabel = new JLabel("下注金额:");
        JButton startButton = new JButton("开始");
        JButton startButtonEnd = new JButton("结束");
        
        Api api = new Api(betBonusGroup);
        //监听事件开始
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {         	
            	if (thread == null) {
            		String moneny = "";
                	if (yuanRadio.isSelected()) {
    					moneny = "5";
    				}
                	if (jiaoRadio.isSelected()) {
                		moneny = "6";
    				}
                	if (fenRadio.isSelected()) {
                		moneny = "7";
    				}
                	StringBuilder codes = new StringBuilder();
                	if (item1.isSelected()) {
                		codes.append(1+" ");
    				}
    				if (item2.isSelected()) {
    					codes.append(2+" ");				
    				}
    				if (item3.isSelected()) {
    					codes.append(3+" ");
    				}
    				if (item4.isSelected()) {
    					codes.append(4+" ");
    				}
    				if (item5.isSelected()) {
    					codes.append(5+" ");
    				}
    				if (item6.isSelected()) {
    					codes.append(6+" ");
    				}
    				if (item7.isSelected()) {
    					codes.append(7+" ");
    				}
    				if (item8.isSelected()) {
    					codes.append(8+" ");
    				}
    				if (item9.isSelected()) {
    					codes.append(9+" ");
    				}
    				if (item10.isSelected()) {
    					codes.append(10+" ");
    				}
                	api.startByMark(userText.getText(), new String(passText.getPassword()), logArea, (String)roadBox.getSelectedItem(), codes.toString(), moneny, betField.getText());
            		thread = new Thread(new Runnable() {					
    					@Override
    					public void run() {
    						api.doIt();
    					}
    				});
                	thread.start();
				}else {
					logArea.append("==============程序已启动，请结束后重新开始!!===================\n");
				}
            }
        });
        
        //监听事件结束
        startButtonEnd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	api.stopByMark();
            	thread = null;
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
            	logArea.append("======================程序已结束=========================\n");
            }
        });

        topPanel.add(betLabel);
        topPanel.add(betField);
        this.topPanel.add(startButton);
        this.topPanel.add(startButtonEnd);
        //开始按钮
    }

    private void createMoneyUnitPanel() {
        moneyUnitPanel.setBorder(BorderFactory.createTitledBorder("金钱:"));
        
        ButtonGroup group = new ButtonGroup();
        group.add(yuanRadio);
        group.add(jiaoRadio);
        group.add(fenRadio);
        //垂直
        Box box = Box.createHorizontalBox();
        box.add(yuanRadio);
        box.add(jiaoRadio);
        box.add(fenRadio);
        this.moneyUnitPanel.add(box);
        this.topPanel.add(moneyUnitPanel);
    }

    private void createHorseRoadPanel() {
        horseRoadPanel.setBorder(BorderFactory.createTitledBorder("赛道:"));
        roadBox = new JComboBox<String>();
        roadBox.addItem("冠军");
        roadBox.addItem("亚军");
        roadBox.addItem("季军");
        roadBox.addItem("第四名");
        roadBox.addItem("第五名");
        roadBox.addItem("第六名");
        roadBox.addItem("第七名");
        roadBox.addItem("第八名");
        roadBox.addItem("第九名");
        roadBox.addItem("第十名");
        horseRoadPanel.add(roadBox);
        this.topPanel.add(horseRoadPanel);

    }

    private void createHorseNumberPanel() {
        horseNumberPanel.setBorder(BorderFactory.createTitledBorder("跑马选择:"));
        
        horseNumberPanel.add(item1);
        horseNumberPanel.add(item2);
        horseNumberPanel.add(item3);
        horseNumberPanel.add(item4);
        horseNumberPanel.add(item5);
        horseNumberPanel.add(item6);
        horseNumberPanel.add(item7);
        horseNumberPanel.add(item8);
        horseNumberPanel.add(item9);
        horseNumberPanel.add(item10);
        topPanel.add(horseNumberPanel);
    }

    /**
     * 创建登录账号密码配置区域 username password
     */
    private void createLoginPanel(JTextField userText, JPasswordField passText) {
        loginPanel.setBorder(BorderFactory.createTitledBorder("账号配置:"));
        JLabel username = new JLabel("账号:");
        JLabel password = new JLabel("密码:");
        Box usernameBox = Box.createHorizontalBox();
        Box passwdBox = Box.createHorizontalBox();
        usernameBox.add(username);
        usernameBox.add(userText);
        passwdBox.add(password);
        passwdBox.add(passText);
        Box loginBox = Box.createVerticalBox();
        loginBox.add(usernameBox);
        loginBox.add(passwdBox);
        loginPanel.add(loginBox);
        topPanel.add(loginPanel);
    }


}
