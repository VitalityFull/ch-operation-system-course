package com.pcb;

/*
 * 进程控制块*/

public class PCB {
    //进程名
    private String name;

    //优先级数
    private int order;

    //占有cpu时间
    private int cpuHave;

    //到达时间(以时间片为单位)
    private int arrivalTime;

    //需要运行时间(以时间片为单位)
    private int needTime;

    //已经运行时间(以时间片为单位)
    private int haveTime;

    //状态(执行E、就绪R、等待W、完成F)
    private char state;


    public PCB() {
    }


    public PCB(String name, int order, int arrivalTime, int needTime, char state) {
        this.name = name;
        this.order = order;
        this.arrivalTime = arrivalTime;
        this.needTime = needTime;
        this.haveTime = 0;
        this.state = state;
        this.cpuHave = 0;
    }

    public int getCpuHave() {
        return cpuHave;
    }

    public void setCpuHave(int cpuHave) {
        this.cpuHave = cpuHave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public int getHaveTime() {
        return haveTime;
    }

    public void setHaveTime(int haveTime) {
        this.haveTime = haveTime;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return name+"        "+order+"        "+state+"            "+arrivalTime+'\t'+"   "+needTime+"          "+haveTime;
    }
}
