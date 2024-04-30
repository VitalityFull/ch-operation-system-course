package com.bank;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class test {
    public static void main(String[] args) {
        int[][] max = {{0, 0, 1, 3}, {1, 7, 5, 0}, {2, 3, 5, 6}, {0, 6, 5, 2}, {0, 6, 5, 6}};

        int[][] allocation = {{0, 0, 1, 2}, {1, 0, 0, 0}, {1, 3, 5, 4}, {0, 6, 3, 2}, {0, 0, 1, 4}};

        int[] available = {1, 5, 2, 1};

        int[] request = {0, 4, 2, 0};

        int p = 1;

        boolean[] finish = {false, false, false, false, false};

        int[][] need = new int[5][4];

        //allocation合理化判断
        //need初始化
        for (int i = 0; i < max.length; i++) {
            for (int j = 0; j < max[i].length; j++) {
                if (allocation[i][j] > max[i][j]) {
                    System.out.println("allocation不合理");
                    return;
                }
                //初始化need
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        //request合理性判断
        for (int i = 0; i < need[p].length; i++) {
            if (request[i] > need[p][i]) {
                System.out.println("请求大于需求！");
                return;
            }
        }

        //判断request是否大于available
        for (int i = 0; i < available.length; i++) {
            if (request[i] > available[i]) {
                System.out.println("请求大于剩余！");
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

        //安全性检测
        //序列集合
        ArrayList<Integer> list = new ArrayList<>();
        boolean flag = false;
        while (!flag) {
            flag = true;
            int pi = getP(finish, need, available);
            if (pi == -1) {
                for (boolean b : finish) {
                    if (!b) {
                        flag = false;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < allocation[pi].length; i++) {
                    available[i] += allocation[pi][i];
                }
                //展示数组
                out(allocation, need, max, available);
                //给完成的进程置零
                setZero(allocation, need, max, pi);
                finish[pi] = true;
                list.add(pi);
                flag = false;
            }
        }
        //展示数组
        out(allocation, need, max, available);

        if (flag) {
            System.out.println("存在安全序列：");
            for (Integer integer : list) {
                System.out.println(integer + 1);
            }
        } else {
            System.out.println("不存在安全序列");
        }

    }

    //获取符合条件的进程
    public static int getP(boolean[] finish, int[][] need, int[] available) {
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

    //输出二维数组
    public static void out(int[][] allocation, int[][] need, int[][] max, int[] available) {
        System.out.println("A  B  C  D");
        System.out.println("allocation: ");
        for (int i = 0; i < allocation.length; i++) {
            System.out.print("P" + (i + 1));
            for (int j = 0; j < allocation[i].length; j++) {
                System.out.print(" " + allocation[i][j]);
            }
            System.out.println();
        }

        System.out.println("max: ");
        for (int i = 0; i < max.length; i++) {
            System.out.print("P" + (i + 1));
            for (int j = 0; j < max[i].length; j++) {
                System.out.print(" " + max[i][j]);
            }
            System.out.println();
        }

        System.out.println("need: ");
        for (int i = 0; i < need.length; i++) {
            System.out.print("P" + (i + 1));
            for (int j = 0; j < need[i].length; j++) {
                System.out.print(" " + need[i][j]);
            }
            System.out.println();
        }

        System.out.println("available: ");
        for (int i = 0; i < available.length; i++) {
            System.out.print(" " + available[i]);
        }
        System.out.println();
    }

    //置零函数
    public static void setZero(int[][] allocation, int[][] need, int[][] max, int p) {
        IntStream.range(0, allocation[p].length).forEach(i -> allocation[p][i] = 0);
        IntStream.range(0, need[p].length).forEach(i -> need[p][i] = 0);
        IntStream.range(0, max[p].length).forEach(i -> max[p][i] = 0);
    }

}
