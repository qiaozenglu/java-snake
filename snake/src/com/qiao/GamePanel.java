package com.qiao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @Author qiaoZengLu
 * @Date 2020/4/23 9:39
 * @Version 1.0
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //面板：画蛇，画界面
    int lenth;
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx;
    Boolean isStart = false;
    //小蛇的速度
    Timer timer = new Timer(150, this);
    //定义食物
    int foodx;
    int foody;
    Random random = new Random();
    boolean isFail = false;

    int score;

    public GamePanel() {
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//让时间动起来
    }

    public void init() {
        lenth = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;//头部
        snakeX[1] = 75;
        snakeY[1] = 100;//第一个身体坐标
        snakeX[2] = 50;
        snakeY[2] = 100;//第二个身体坐标
        fx = "R";
        foodx = 25 + 25 * random.nextInt(34);
        foody = 75 + 25 * random.nextInt(24);
        score = 0;
    }
    //页面
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("页面");
        super.paintComponent(g);//清屏
        this.setBackground(Color.BLACK);//设置背景颜色
        Data.header.paintIcon(this, g, 25, 11);
        g.fillRect(25, 75, 850, 600);
        //画一条静态的小蛇
        if (fx.equals("R")) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("L")) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("U")) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("D")) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        //        Data.body.paintIcon(this,g,snakeX[1],snakeY[1]);
//        Data.body.paintIcon(this,g,snakeX[2],snakeY[2]);
        for (int i = 1; i < lenth; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //画积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度:" + lenth, 750, 35);
        g.drawString("积分:" + score, 750, 50);
        //画食物
        Data.food.paintIcon(this, g, foodx, foody);

        if (isStart == false) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏", 300, 300);
        }
        //失败提醒
        if (isFail) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("游戏失败,按下空格重新游戏", 200, 300);
        }
    }

    //接受键盘的输入：监听
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是那个键
        System.out.println("键盘");
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFail) {
                isFail = false;
                init();//重新初始化游戏
            } else {
                isStart = !isStart;
            }
            repaint();
        }

        //键盘控制方向
        if (keyCode == KeyEvent.VK_LEFT) {
            if(fx=="R"){
                return;
            }else{
                fx = "L";
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {

            if(fx=="L"){
                return;
            }else{
                fx = "R";
            }

        } else if (keyCode == KeyEvent.VK_UP) {

            if(fx=="D"){
                return;
            }else{
                fx = "U";
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {

            if(fx=="U"){
                return;
            }else{
                fx = "D";
            }
        }

    }

    //定时器，监听时间， 执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态,并且游戏没有结束
        System.out.println("定时器");
        if (isStart && isFail == false) {
            //右移
            for (int i = lenth - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            //通过控制方向让头部移动
            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }
            //如果小蛇的头和食物的坐标重合了
            if (snakeX[0] == foodx && snakeY[0] == foody) {
                lenth++;
                score = score + 10;
                foodx = 25 + 25 * random.nextInt(34);
                foody = 75 + 25 * random.nextInt(24);
            }
            //结束判断
            for (int i = 1; i < lenth; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
                if (snakeX[0] == 25 || snakeX[0] == 850 || snakeY[0] == 75 || snakeY[0] == 650) {
                    isFail = true;
                }
            }
            repaint();

        }
        timer.start();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

}
