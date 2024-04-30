package com.pcb;

import javax.swing.*;
import java.io.*;

public class SystemJFrame extends JFrame {

    public SystemJFrame() throws IOException {
        initJFrame();
        //进程信息输入框
        initLabel();
        JTextField[] order = new JTextField[5];
        JTextField[] arrivalTime = new JTextField[5];
        JTextField[] needTime = new JTextField[5];

        order[0] = new JTextField("3");
        order[0].setBounds(50, 40, 30, 20);
        arrivalTime[0] = new JTextField("0");
        arrivalTime[0].setBounds(100, 40, 30, 20);
        needTime[0] = new JTextField("5");
        needTime[0].setBounds(180, 40, 30, 20);
        this.getContentPane().add(order[0]);
        this.getContentPane().add(arrivalTime[0]);
        this.getContentPane().add(needTime[0]);

        order[1] = new JTextField("5");
        order[1].setBounds(50, 60, 30, 20);
        arrivalTime[1] = new JTextField("0");
        arrivalTime[1].setBounds(100, 60, 30, 20);
        needTime[1] = new JTextField("10");
        needTime[1].setBounds(180, 60, 30, 20);
        this.getContentPane().add(order[1]);
        this.getContentPane().add(arrivalTime[1]);
        this.getContentPane().add(needTime[1]);

        order[2] = new JTextField("1");
        order[2].setBounds(50, 80, 30, 20);
        arrivalTime[2] = new JTextField("6");
        arrivalTime[2].setBounds(100, 80, 30, 20);
        needTime[2] = new JTextField("2");
        needTime[2].setBounds(180, 80, 30, 20);
        this.getContentPane().add(order[2]);
        this.getContentPane().add(arrivalTime[2]);
        this.getContentPane().add(needTime[2]);

        order[3] = new JTextField("2");
        order[3].setBounds(50, 100, 30, 20);
        arrivalTime[3] = new JTextField("6");
        arrivalTime[3].setBounds(100, 100, 30, 20);
        needTime[3] = new JTextField("6");
        needTime[3].setBounds(180, 100, 30, 20);
        this.getContentPane().add(order[3]);
        this.getContentPane().add(arrivalTime[3]);
        this.getContentPane().add(needTime[3]);

        order[4] = new JTextField("8");
        order[4].setBounds(50, 120, 30, 20);
        arrivalTime[4] = new JTextField("10");
        arrivalTime[4].setBounds(100, 120, 30, 20);
        needTime[4] = new JTextField("4");
        needTime[4].setBounds(180, 120, 30, 20);
        this.getContentPane().add(order[4]);
        this.getContentPane().add(arrivalTime[4]);
        this.getContentPane().add(needTime[4]);

        JTextArea dataTxt = new JTextArea();
        dataTxt.setText("");
        this.getContentPane().repaint();
        //文本显示框
        dataTxt.setBounds(10, 150, 550, 300);
        dataTxt.setLineWrap(true);
        //设置滚轮
        JScrollPane jScrollPane = new JScrollPane(dataTxt);
        jScrollPane.setBounds(10, 150, 550, 300);
        this.getContentPane().add(jScrollPane);

        JButton start = new JButton("开始");
        start.setBounds(230,120,60,20);
        start.addActionListener(e -> {
            int[][] arr = new int[5][3];
            for (int i = 0; i < 5; i++) {
                arr[i][0] =Integer.parseInt(order[i].getText());
                arr[i][1] =Integer.parseInt(arrivalTime[i].getText());
                arr[i][2] =Integer.parseInt(needTime[i].getText());
            }
            try {
                new PCBSystem(arr);
                dataTxt.setText("");
                String line;
                BufferedReader fr = new BufferedReader(new FileReader("code\\data\\pcb.txt"));
                while ((line = fr.readLine()) != null) {
                    dataTxt.append(line + "\n");
                }
                fr.close();
            } catch (Exception exception){
                exception.printStackTrace();
            }
            this.getContentPane().repaint();
        });
        this.getContentPane().add(start);
        this.getContentPane().repaint();

    }

    public void initLabel() {
        JLabel order = new JLabel("order");
        order.setBounds(50, 10, 50, 30);
        this.getContentPane().add(order);

        JLabel arrivalTime = new JLabel("arrivalTime");
        arrivalTime.setBounds(100, 10, 100, 30);
        this.getContentPane().add(arrivalTime);

        JLabel needTime = new JLabel("needTime");
        needTime.setBounds(180, 10, 100, 30);
        this.getContentPane().add(needTime);

        JLabel p1 = new JLabel("P1");
        p1.setBounds(10, 40, 60, 20);
        this.getContentPane().add(p1);

        JLabel p2 = new JLabel("P2");
        p2.setBounds(10, 60, 60, 20);
        this.getContentPane().add(p2);

        JLabel p3 = new JLabel("P3");
        p3.setBounds(10, 80, 60, 20);
        this.getContentPane().add(p3);

        JLabel p4 = new JLabel("P4");
        p4.setBounds(10, 100, 60, 20);
        this.getContentPane().add(p4);

        JLabel p5 = new JLabel("P5");
        p5.setBounds(10, 120, 60, 20);
        this.getContentPane().add(p5);
    }

    public void initJFrame() {
        //设置大小
        this.setSize(600, 500);
        //设置名称
        this.setTitle("进程调度模拟");
        //设置居中
        this.setLocationRelativeTo(null);
        //设置关闭模式(点击×就关闭窗口)
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置显示
        this.setVisible(true);
        //取消JLabel居中放置
        this.setLayout(null);
    }
}
