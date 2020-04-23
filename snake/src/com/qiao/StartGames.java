package com.qiao;

import javax.swing.*;

/**
 * @Author qiaoZengLu
 * @Date 2020/4/23 9:31
 * @Version 1.0
 */
public class StartGames {
    public static void main(String[] args) {
        //1、绘制一个静态窗口
        JFrame jFrame = new JFrame("贪吃蛇");
        //设置界面的大小
        jFrame.setBounds(10,10,900,720);
        jFrame.setResizable(false);//窗口大小不可改变
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new GamePanel());
        jFrame.setVisible(true);
    }
}
