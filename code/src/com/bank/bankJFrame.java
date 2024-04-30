package com.bank;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class bankJFrame extends JFrame {

    //最大需求资源数
    public int[][] max = new int[5][4];

    //当前占有数
    public int[][] allocation = new int[5][4];

    public int[] available = new int[4];

    public int[] request = new int[4];

    public int[][] need = new int[5][4];

    public boolean[] finish = new boolean[5];

    public bankJFrame() {
        initJFrame();

        initLabel();

        initTxt();

        this.getContentPane().repaint();

    }

    //输入框设置
    public void initTxt() {
        JTextField[] A1 = new JTextField[5];
        JTextField[] B1 = new JTextField[5];
        JTextField[] C1 = new JTextField[5];
        JTextField[] D1 = new JTextField[5];

        A1[0] = new JTextField("0");
        A1[1] = new JTextField("1");
        A1[2] = new JTextField("2");
        A1[3] = new JTextField("0");
        A1[4] = new JTextField("0");
        for (int i = 0; i < A1.length; i++) {
            A1[i].setBounds(50, 40 + 20 * i, 30, 20);
            this.getContentPane().add(A1[i]);
        }

        B1[0] = new JTextField("0");
        B1[1] = new JTextField("7");
        B1[2] = new JTextField("3");
        B1[3] = new JTextField("6");
        B1[4] = new JTextField("6");
        for (int i = 0; i < B1.length; i++) {
            B1[i].setBounds(80, 40 + 20 * i, 30, 20);
            this.getContentPane().add(B1[i]);
        }

        C1[0] = new JTextField("1");
        C1[1] = new JTextField("5");
        C1[2] = new JTextField("5");
        C1[3] = new JTextField("5");
        C1[4] = new JTextField("5");
        for (int i = 0; i < C1.length; i++) {
            C1[i].setBounds(110, 40 + 20 * i, 30, 20);
            this.getContentPane().add(C1[i]);
        }

        D1[0] = new JTextField("3");
        D1[1] = new JTextField("0");
        D1[2] = new JTextField("6");
        D1[3] = new JTextField("2");
        D1[4] = new JTextField("6");
        for (int i = 0; i < D1.length; i++) {
            D1[i].setBounds(140, 40 + 20 * i, 30, 20);
            this.getContentPane().add(D1[i]);
        }

        JTextField[] A2 = new JTextField[5];
        JTextField[] B2 = new JTextField[5];
        JTextField[] C2 = new JTextField[5];
        JTextField[] D2 = new JTextField[5];

        A2[0] = new JTextField("0");
        A2[1] = new JTextField("1");
        A2[2] = new JTextField("1");
        A2[3] = new JTextField("0");
        A2[4] = new JTextField("0");
        for (int i = 0; i < A2.length; i++) {
            A2[i].setBounds(220, 40 + 20 * i, 30, 20);
            this.getContentPane().add(A2[i]);
        }

        B2[0] = new JTextField("0");
        B2[1] = new JTextField("0");
        B2[2] = new JTextField("3");
        B2[3] = new JTextField("6");
        B2[4] = new JTextField("0");
        for (int i = 0; i < B2.length; i++) {
            B2[i].setBounds(250, 40 + 20 * i, 30, 20);
            this.getContentPane().add(B2[i]);
        }

        C2[0] = new JTextField("1");
        C2[1] = new JTextField("0");
        C2[2] = new JTextField("5");
        C2[3] = new JTextField("3");
        C2[4] = new JTextField("1");
        for (int i = 0; i < C2.length; i++) {
            C2[i].setBounds(280, 40 + 20 * i, 30, 20);
            this.getContentPane().add(C2[i]);
        }

        D2[0] = new JTextField("2");
        D2[1] = new JTextField("0");
        D2[2] = new JTextField("4");
        D2[3] = new JTextField("2");
        D2[4] = new JTextField("4");
        for (int i = 0; i < D2.length; i++) {
            D2[i].setBounds(310, 40 + 20 * i, 30, 20);
            this.getContentPane().add(D2[i]);
        }

        JTextField A3 = new JTextField("1");
        JTextField B3 = new JTextField("5");
        JTextField C3 = new JTextField("2");
        JTextField D3 = new JTextField("1");
        A3.setBounds(390, 50, 30, 20);
        B3.setBounds(420, 50, 30, 20);
        C3.setBounds(450, 50, 30, 20);
        D3.setBounds(480, 50, 30, 20);
        this.getContentPane().add(A3);
        this.getContentPane().add(B3);
        this.getContentPane().add(C3);
        this.getContentPane().add(D3);

        JTextField A4 = new JTextField("0");
        JTextField B4 = new JTextField("4");
        JTextField C4 = new JTextField("2");
        JTextField D4 = new JTextField("0");
        A4.setBounds(390, 100, 30, 20);
        B4.setBounds(420, 100, 30, 20);
        C4.setBounds(450, 100, 30, 20);
        D4.setBounds(480, 100, 30, 20);
        this.getContentPane().add(A4);
        this.getContentPane().add(B4);
        this.getContentPane().add(C4);
        this.getContentPane().add(D4);

        JTextField pp = new JTextField("2");
        pp.setBounds(355, 100, 20, 20);
        this.getContentPane().add(pp);

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
        start.setBounds(420, 130, 60, 20);
        start.addActionListener(e -> {
            try {

                FileWriter fw = new FileWriter("code\\data\\bank.txt");

                //初始化finish数组
                Arrays.fill(finish, false);

                //初始化最大资源数max
                for (int i = 0; i < max.length; i++) {
                    max[i][0] = Integer.parseInt(A1[i].getText());
                    max[i][1] = Integer.parseInt(B1[i].getText());
                    max[i][2] = Integer.parseInt(C1[i].getText());
                    max[i][3] = Integer.parseInt(D1[i].getText());
                }

                //初始化当前allocation
                for (int i = 0; i < allocation.length; i++) {
                    allocation[i][0] = Integer.parseInt(A2[i].getText());
                    allocation[i][1] = Integer.parseInt(B2[i].getText());
                    allocation[i][2] = Integer.parseInt(C2[i].getText());
                    allocation[i][3] = Integer.parseInt(D2[i].getText());
                }

                //初始化available
                available[0] = Integer.parseInt(A3.getText());
                available[1] = Integer.parseInt(B3.getText());
                available[2] = Integer.parseInt(C3.getText());
                available[3] = Integer.parseInt(D3.getText());

                //初始化request
                request[0] = Integer.parseInt(A4.getText());
                request[1] = Integer.parseInt(B4.getText());
                request[2] = Integer.parseInt(C4.getText());
                request[3] = Integer.parseInt(D4.getText());

                //初始化进程名
                int p = Integer.parseInt(pp.getText()) - 1;
                if (p < 0 || p > 5) {
                    new MyJDialog("P值不合理");
                    return;
                }

                //allocation合理化判断
                //need初始化
                for (int i = 0; i < max.length; i++) {
                    for (int j = 0; j < max[i].length; j++) {
                        if (allocation[i][j] > max[i][j]) {
                            fw.write("allocation大于max" + '\n');
                            fw.close();
                            BufferedReader fr = new BufferedReader(new FileReader("code\\data\\bank.txt"));
                            dataTxt.setText("");
                            String line;
                            while ((line = fr.readLine()) != null) {
                                dataTxt.append(line + "\n");
                            }
                            fr.close();
                            this.getContentPane().repaint();
                            return;
                        }
                        //初始化need
                        need[i][j] = max[i][j] - allocation[i][j];
                    }
                }

                //request合理性判断
                for (int i = 0; i < need[p].length; i++) {
                    if (request[i] > need[p][i]) {
                        fw.write("请求大于需求！" + '\n');
                        fw.close();
                        BufferedReader fr = new BufferedReader(new FileReader("code\\data\\bank.txt"));
                        dataTxt.setText("");
                        String line;
                        while ((line = fr.readLine()) != null) {
                            dataTxt.append(line + "\n");
                        }
                        fr.close();
                        this.getContentPane().repaint();
                        return;
                    }
                }

                //判断request是否大于available
                for (int i = 0; i < available.length; i++) {
                    if (request[i] > available[i]) {
                        fw.write("请求大于剩余！" + '\n');
                        fw.close();
                        BufferedReader fr = new BufferedReader(new FileReader("code\\data\\bank.txt"));
                        dataTxt.setText("");
                        String line;
                        while ((line = fr.readLine()) != null) {
                            dataTxt.append(line + "\n");
                        }
                        fr.close();
                        this.getContentPane().repaint();
                        return;
                    }
                }

                //试探分配
                //更新available
                for (int i = 0; i < available.length; i++) {
                    available[i] -= request[i];
                }
                //更新allocation
                for (int i = 0; i < allocation[p].length; i++) {
                    allocation[p][i] += request[i];
                }
                //更新need
                for (int i = 0; i < need[p].length; i++) {
                    need[p][i] -= request[i];
                }

                out(allocation, need, max, available, fw);
                //安全性检测
                //序列集合
                ArrayList<Integer> list = new ArrayList<>();
                boolean flag = false, fflag = true;
                while (!flag) {
                    flag = true;
                    int pi = getP(finish, need, available);
                    if (pi == -1) {
                        for (boolean b : finish) {
                            if (!b) {
                                fflag = false;
                                flag = true;
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < allocation[pi].length; i++) {
                            available[i] += allocation[pi][i];
                        }
                        //给完成的进程置零
                        setZero(allocation, need, max, pi);
                        //展示数组
                        out(allocation, need, max, available, fw);
                        finish[pi] = true;
                        list.add(pi);
                        flag = false;
                    }
                }
                //展示数组
                out(allocation, need, max, available, fw);

                if (fflag) {
                    fw.write("存在安全序列：" + '\n');
                    for (Integer integer : list) {
                        fw.write(" P" + (integer + 1));
                        fw.write('\n');
                    }
                } else {
                    fw.write("不存在安全序列" + '\n');
                }
                fw.close();
                BufferedReader fr = new BufferedReader(new FileReader("code\\data\\bank.txt"));
                dataTxt.setText("");
                String line;
                while ((line = fr.readLine()) != null) {
                    dataTxt.append(line + "\n");
                }
                fr.close();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            this.getContentPane().repaint();
        });
        this.getContentPane().add(start);
        this.getContentPane().repaint();
    }


    //输出信息
    public void out(int[][] allocation, int[][] need, int[][] max, int[] available, FileWriter fw) throws IOException {
        fw.write("A  B  C  D" + '\n');
        fw.write("max: ");
        fw.write("                 allocation: ");
        fw.write("   need: " + '\n');
        for (int i = 0; i < allocation.length; i++) {
            fw.write("P" + (i + 1));


            for (int j = 0; j < max[i].length; j++) {
                fw.write("  " + max[i][j]);
            }

            fw.write("    ");
            for (int j = 0; j < allocation[i].length; j++) {
                fw.write("  " + allocation[i][j]);
            }

            fw.write("    ");
            for (int j = 0; j < need[i].length; j++) {
                fw.write("  " + need[i][j]);
            }
            fw.write('\n');
        }
        fw.write("available: " + '\n');
        for (int i = 0; i < available.length; i++) {
            fw.write("  " + available[i]);
        }
        fw.write('\n');
        fw.write('\n');
    }

    //置零函数
    public void setZero(int[][] allocation, int[][] need, int[][] max, int p) {
        IntStream.range(0, allocation[p].length).forEach(i -> allocation[p][i] = 0);
        IntStream.range(0, need[p].length).forEach(i -> need[p][i] = 0);
        IntStream.range(0, max[p].length).forEach(i -> max[p][i] = 0);
    }

    //获取符合条件的进程
    public int getP(boolean[] finish, int[][] need, int[] available) {
        boolean flag = true;
        int i = 0;
        for (; i < finish.length; i++) {
            if (!finish[i]) {
                flag = true;
                for (int j = 0; j < need[i].length; j++) {
                    if (need[i][j] > available[j])
                        flag = false;
                }
                if (flag)
                    break;
            } else flag = false;
        }
        if (flag)
            return i;
        return -1;
    }

    //标签设置
    public void initLabel() {
        JLabel max = new JLabel("max");
        max.setBounds(80, 0, 50, 20);
        this.getContentPane().add(max);

        JLabel A = new JLabel("A");
        A.setBounds(50, 10, 100, 30);
        this.getContentPane().add(A);

        JLabel B = new JLabel("B");
        B.setBounds(80, 10, 100, 30);
        this.getContentPane().add(B);

        JLabel C = new JLabel("C");
        C.setBounds(110, 10, 100, 30);
        this.getContentPane().add(C);

        JLabel D = new JLabel("D");
        D.setBounds(140, 10, 100, 30);
        this.getContentPane().add(D);

        JLabel allocation = new JLabel("allocation");
        allocation.setBounds(250, 0, 100, 20);
        this.getContentPane().add(allocation);

        JLabel A1 = new JLabel("A");
        A1.setBounds(220, 10, 100, 30);
        this.getContentPane().add(A1);

        JLabel B1 = new JLabel("B");
        B1.setBounds(250, 10, 100, 30);
        this.getContentPane().add(B1);

        JLabel C1 = new JLabel("C");
        C1.setBounds(280, 10, 100, 30);
        this.getContentPane().add(C1);

        JLabel D1 = new JLabel("D");
        D1.setBounds(310, 10, 100, 30);
        this.getContentPane().add(D1);

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

        JLabel request = new JLabel("request");
        request.setBounds(400, 70, 100, 20);
        this.getContentPane().add(request);

        JLabel p = new JLabel("P");
        p.setBounds(345, 100, 60, 20);
        this.getContentPane().add(p);

        JLabel A2 = new JLabel("A");
        A2.setBounds(390, 80, 100, 30);
        this.getContentPane().add(A2);

        JLabel B2 = new JLabel("B");
        B2.setBounds(420, 80, 100, 30);
        this.getContentPane().add(B2);

        JLabel C2 = new JLabel("C");
        C2.setBounds(450, 80, 100, 30);
        this.getContentPane().add(C2);

        JLabel D2 = new JLabel("D");
        D2.setBounds(480, 80, 100, 30);
        this.getContentPane().add(D2);


        JLabel available = new JLabel("available");
        available.setBounds(400, 20, 100, 20);
        this.getContentPane().add(available);

        JLabel A3 = new JLabel("A");
        A3.setBounds(390, 30, 100, 30);
        this.getContentPane().add(A3);

        JLabel B3 = new JLabel("B");
        B3.setBounds(420, 30, 100, 30);
        this.getContentPane().add(B3);

        JLabel C3 = new JLabel("C");
        C3.setBounds(450, 30, 100, 30);
        this.getContentPane().add(C3);

        JLabel D3 = new JLabel("D");
        D3.setBounds(480, 30, 100, 30);
        this.getContentPane().add(D3);


        this.getContentPane().repaint();
    }

    //基础设置
    public void initJFrame() {
        //设置大小
        this.setSize(600, 500);
        //设置名称
        this.setTitle("银行家算法模拟");
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
