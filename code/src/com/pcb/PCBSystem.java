package com.pcb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PCBSystem {
    //时间（以时间片为单位）
    public int time;

    //进程运行需要的资源IO
    public int io;

    //时间片
    public int timeSlice;

    //cpu个数
    public int cpu;

    //占用cpu的进程
    public PCB cpuP;

    //就绪队列
    public List<PCB> readyList;

    //等待队列
    public List<PCB> waitList;

    //完成进程
    public List<PCB> finishList;

    //进程数组
    public PCB[] p;

    public PCBSystem(int[][] arr) throws IOException {

        FileWriter fw = new FileWriter("code\\data\\pcb.txt");

        initSystem(arr);
        int num = p.length;
        while (finishList.size() < num) {
            //分配资源判断IO资源是否为0，如果不为零则将IO资源分配给等待队列，更新就绪队列
            //如果IO为零则跳过这一步
            //函数内已经有判断这一步
            allotIO(fw);

            //处理到达的进程，有IO资源则进入就绪队列，没有则进入等待队列，
            //函数内已有判断
            arriveP(fw);

            //判断cpu是否空闲
            //如果空闲则从就绪队列中选出优先级最高的进程占用cpu
            //如果不空闲则比较占有cpu的进程和就绪队列中的进程的优先级，判断是否发生抢占
            if (cpu > 0) {
                fw.write("cpu空闲"+'\n');
                cpuP = selectHighest();
                //移出就绪队列
                removeReady(cpuP);
                cpuP.setState('E');
                cpu--;
                fw.write(cpuP.getName() + "获得cpu"+'\n');
            } else {
                //判断是否发生抢占
                PCB highestRPCB = selectHighest();
                //判断就绪队列是否为空
                if (highestRPCB != null) {
                    if (cpuP.getOrder() > highestRPCB.getOrder()) {
                        //发生抢占，将原来的占有cpu的进程的cpu占有时间置0
                        //System.out.println(selectHighest().getName() + "抢占cpu");
                        fw.write(selectHighest().getName() + "抢占"+cpuP.getName()+"的cpu"+'\n');
                        cpuP.setCpuHave(0);
                        cpuP.setState('R');
                        //将原先的进程移入就绪队列
                        readyList.add(cpuP);
                        //将新进程移出就绪队列
                        cpuP = highestRPCB;
                        cpuP.setState('E');
                        removeReady(cpuP);
                        //输出运行进程、就绪队列、等待队列、完成进程以及各个进程的 PCB
                        //outAll();
                    }
                }
            }

            //总运行时间+1
            cpuP.setHaveTime(cpuP.getHaveTime() + 1);
            //占有cpu时间+1
            cpuP.setCpuHave(cpuP.getCpuHave() + 1);

            //判断总运行时间是否达到需要运行时间
            if (cpuP.getHaveTime() == cpuP.getNeedTime()) {
                //运行完成，进入完成队列
                finishList.add(cpuP);
                cpuP.setCpuHave(0);
                cpuP.setState('F');
                io++;
                fw.write(cpuP.getName() + "运行完成，进入完成队列"+'\n');
                cpuP = null;
                cpu++;
            } else {
                //运行未完成
                //判断已占有cpu时间是否达到一个时间片
                if (cpuP.getCpuHave() == timeSlice) {
                    //运行时间已达到一个时间片，放弃cpu，优先级降低，进入就绪队列
                    fw.write(cpuP.getName() + "运行时间已达一个时间片，放弃cpu，优先级降低，进入就绪队列"+'\n');
                    cpu++;
                    cpuP.setCpuHave(0);
                    cpuP.setOrder(cpuP.getOrder() + 1);
                    readyList.add(cpuP);
                    cpuP.setState('R');
                    cpuP = null;
                }
            }
            //输出运行进程、就绪队列、等待队列、完成进程以及各个进程的 PCB
            outAll(fw);
            time++;
        }
        fw.close();
    }

    //输出PCB信息
    public void outPCB() {
    }

    //输出就绪队列，等待队列，各个进程的PCB
    public void outAll(FileWriter fw) throws IOException {

        //输出运行进程
        if (cpuP != null){
            fw.write("正在运行进程：" + cpuP.getName()+'\n');
        }

        else {
            fw.write("正在运行进程："+'\n');
        }
        //输出就绪队列
        if (readyList.isEmpty()){
            fw.write("就绪队列："+'\n');
        }
        else {
            fw.write("就绪队列：" + readyList.get(0).getName());
            for (int i = 1; i < readyList.size(); i++) {
                fw.write("，" + readyList.get(i).getName());
            }
            fw.write('\n');
        }

        //输出等待队列
        if (waitList.isEmpty()){
            fw.write("等待队列："+'\n');
        }
        else {
            fw.write("等待队列：" + waitList.get(0).getName());
            for (int i = 1; i < waitList.size(); i++) {
                fw.write("，" + waitList.get(i).getName());
            }
            fw.write('\n');
        }

        //输出完成队列
        if (finishList.isEmpty()){
            fw.write("完成队列："+'\n');
        }
        else {
            fw.write("完成队列：" + finishList.get(0).getName());
            for (int i = 1; i < finishList.size(); i++) {
                fw.write("，" + finishList.get(i).getName());
            }
            fw.write('\n');
        }

        fw.write("name"+" "+"order"+" "+"state"+" "+"arrivalTime"+" "+"needTime"+" "+"haveTime"+'\n');
        for (int i = 0; i < p.length; i++) {
            fw.write(p[i].toString()+'\n');
        }

    }

    //将进程移出就绪队列
    public void removeReady(PCB cpuP) {
        for (int i = 0; i < readyList.size(); i++) {
            if (readyList.get(i).getName().equals(cpuP.getName())) {
                readyList.remove(i);
                break;
            }
        }
    }

    //3、从就绪队列中选出优先级最高的进程
    public PCB selectHighest() {
        if (readyList.isEmpty())
            return null;
        PCB cpuP = readyList.get(0);
        int len = readyList.size();
        for (int i = 0; i < len; i++) {
            //比较优先级
            if (readyList.get(i).getOrder() < cpuP.getOrder())
                cpuP = readyList.get(i);
        }
        //返回优先级最高的进程
        return cpuP;
    }

    //1、为等待队列内的进程分配IO资源
    public void allotIO(FileWriter fw) throws IOException {
        for (int i = 0; i < waitList.size(); i++) {
            if (io > 0) {
                //分配资源
                waitList.get(i).setState('R');
                fw.write(waitList.get(i).getName() + "得到了IO资源，移出等待队列，进入就绪队列"+'\n');
                //加入就绪队列
                readyList.add(waitList.get(i));
                //移出等待队列
                waitList.remove(i);
                i--;
                io--;
            } else break;
        }
    }

    //2、处理到达的进程
    public void arriveP(FileWriter fw) throws IOException {
        //进程初始态为R
        //判断进程到达时是否有资源可供分配，如果有则保持就绪态，如果没有则变为阻塞态等待资源
        int len = p.length;
        for (int i = 0; i < len; i++) {
            if (p[i].getArrivalTime() == time) {
                if (io > 0) {
                    p[i].setState('R');
                    fw.write(p[i].getName() + "到达系统并获得了资源，进入就绪队列"+'\n');
                    readyList.add(p[i]);
                    io--;
                } else {
                    fw.write(p[i].getName() + "到达系统未获得资源，进入等待队列"+'\n');
                    p[i].setState('W');
                    waitList.add(p[i]);
                }
            }
        }
    }

    //系统初始化方法
    public void initSystem(int[][] arr) {
        //初始化cpu数量
        cpu = 1;
        //初始化系统时间
        time = 0;
        //初始化时间片
        timeSlice = 2;
        //初始化IO资源
        io = 3;
        readyList = new ArrayList<>();
        waitList = new ArrayList<>();
        finishList = new ArrayList<>();
        p = new PCB[5];
        p[0] = new PCB("P1", arr[0][0], arr[0][1], arr[0][2], ' ');
        p[1] = new PCB("P2", arr[1][0], arr[1][1], arr[1][2], ' ');
        p[2] = new PCB("P3", arr[2][0], arr[2][1], arr[2][2], ' ');
        p[3] = new PCB("P4", arr[3][0], arr[3][1] ,arr[3][2], ' ');
        p[4] = new PCB("P5", arr[4][0], arr[4][1], arr[4][2], ' ');
    }
}
