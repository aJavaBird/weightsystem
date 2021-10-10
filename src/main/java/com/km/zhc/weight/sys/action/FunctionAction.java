package com.km.zhc.weight.sys.action;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** 功能操作 */
public class FunctionAction {

    WeightAction weightAction = new WeightAction();

    /** 显示功能框 */
    public void showFunctionPanel(){
        final JFrame jf = new JFrame("weight工具");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel showInfo = new JLabel("请选择操作类型");
        showInfo.setForeground(new Color(8,8,8));
        panel01.add(showInfo);

        JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel weightBtn = new JLabel("记录体重");
        weightBtn.setForeground(new Color(49,15,216));
        panel02.add(weightBtn);

        weightBtn.addMouseListener(new MouseListener(){

            public void mouseClicked(MouseEvent e) {
                weightAction.showWeightPanel();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });

        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel weightHistoryBtn = new JLabel("近3月体重历史");
        weightHistoryBtn.setForeground(new Color(49,15,216));
        panel03.add(weightHistoryBtn);

        weightHistoryBtn.addMouseListener(new MouseListener(){

            public void mouseClicked(MouseEvent e) {
                weightAction.showWeightHistoryPanel();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel03);
        vBox.add(panel02);

        // 父 JPanel, 使用默认的浮动布局
        JPanel panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(30, 80, 30, 80));
        panelParent.add(vBox);

        jf.setContentPane(panelParent);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
