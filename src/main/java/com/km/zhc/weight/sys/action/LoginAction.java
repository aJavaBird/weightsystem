package com.km.zhc.weight.sys.action;

import com.km.zhc.weight.sys.service.UserService;
import com.km.zhc.weight.sys.util.GeneralUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

/** 登录注册操作 */
public class LoginAction {

    UserService userService = new UserService();

    /** 登录用户 */
    private static String loginUser = null;

    private final static String LOGIN_CACHE_FILE_NAMW="zhc_weight_login_cache.tmp";

    public static String getLoginUser(){
        return loginUser;
    }

    /** 显示登录框 */
    public void showLoginPanel(){
        final JFrame jf = new JFrame("用户登录");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("登录用户"));
        final JTextField userNameField = new JTextField(15);
        panel01.add(userNameField);

        // 第 2 个 JPanel, 使用默认的浮动布局
        JPanel panel02 = new JPanel();
        panel02.add(new JLabel("登录密码"));
        final JPasswordField passwordField = new JPasswordField(15);
        panel02.add(passwordField);

        try {
            // 如果上次登录过，则直接将用户名密码带出来
            List<String> userLoginCache = GeneralUtil.readTempLineByLine(LOGIN_CACHE_FILE_NAMW);
            if(userLoginCache.size()==2){
                userNameField.setText(userLoginCache.get(0));
                passwordField.setText(userLoginCache.get(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginBtn = new JButton("登录");
        panel03.add(loginBtn);
//        JButton registerBtn = new JButton("去注册");
//        registerBtn.setPreferredSize(new Dimension(80,20)); //设置尺寸
//        panel03.add(registerBtn);
        loginBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                if(userName.length()==0 || passwordField.getPassword().length==0){
                    // 消息对话框无返回, 仅做通知作用
                    JOptionPane.showMessageDialog(
                            jf,"用户名和密码 不能为空！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                String password = String.copyValueOf(passwordField.getPassword());
                System.out.println(String.format("userLogin: userName = %s , password = %s",userName,password));
                Boolean isLoginOK = userService.checkUser(userName,password);
                if(!isLoginOK){
                    JOptionPane.showMessageDialog(
                            jf,"用户名或密码错误！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                loginUser = userName;
//                JOptionPane.showMessageDialog(
//                        jf,"登录成功！",
//                        "提示",
//                        JOptionPane.INFORMATION_MESSAGE
//                );
                try {
                    // 将成功登录用户写入缓存文件，方便下次直接获取登录
                    GeneralUtil.writeToTemp(LOGIN_CACHE_FILE_NAMW,userName+"\n"+password,false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                jf.dispose();
                new FunctionAction().showFunctionPanel();
            }
        });
        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel toRegisterBtn = new JLabel("去注册");
        toRegisterBtn.setForeground(new Color(49,15,216));
        panel04.add(toRegisterBtn);

        toRegisterBtn.addMouseListener(new MouseListener(){

            public void mouseClicked(MouseEvent e) {
                showRegisterPanel();
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
        vBox.add(panel02);
        vBox.add(panel03);
        vBox.add(panel04);

        // 父 JPanel, 使用默认的浮动布局
        JPanel panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelParent.add(vBox);

        jf.setContentPane(panelParent);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    /** 显示注册弹框 */
    private void showRegisterPanel(){
        final JFrame jf = new JFrame("用户注册");
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("登录用户"));
        final JTextField userNameField = new JTextField(15);
        panel01.add(userNameField);

        // 第 2 个 JPanel, 使用默认的浮动布局
        JPanel panel02 = new JPanel();
        panel02.add(new JLabel("输入密码"));
        final JPasswordField passwordField = new JPasswordField(15);
        panel02.add(passwordField);

        JPanel panel03 = new JPanel();
        panel03.add(new JLabel("确认密码"));
        final JPasswordField passwordField2 = new JPasswordField(15);
        panel03.add(passwordField2);

        // 第 4 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerBtn = new JButton("注册");
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                if(userName.length()==0 || passwordField.getPassword().length==0 || passwordField2.getPassword().length==0){
                    // 消息对话框无返回, 仅做通知作用
                    JOptionPane.showMessageDialog(
                            jf,"用户名、密码 和 确认密码都不能为空！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                String password = String.copyValueOf(passwordField.getPassword());
                String password2 = String.copyValueOf(passwordField2.getPassword());
                System.out.println(String.format("userRegister: userName = %s , password = %s , password2 = %s",userName,password,password2));
                if(!password.equals(password2)){
                    JOptionPane.showMessageDialog(
                            jf,"两次输入密码不一致！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                try {
                    userService.registerUser(userName,password);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(
                            jf,e1.getMessage(),
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                JOptionPane.showMessageDialog(
                        jf,"注册成功！",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
                jf.dispose();
            }
        });
        panel04.add(registerBtn);

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);
        vBox.add(panel03);
        vBox.add(panel04);

        jf.setContentPane(vBox);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

}
