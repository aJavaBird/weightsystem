package com.km.zhc.weight.sys.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.km.zhc.weight.sys.service.WeightService;
import com.km.zhc.weight.sys.util.GeneralUtil;

/** 体重操作 */
public class WeightAction {

    WeightService weightService = new WeightService();

    /** 显示体重填写框 */
    public void showWeightPanel(){
        final JFrame jf = new JFrame("记录体重");
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 第 1 个 JPanel, 使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("当前体重（kg）"));
        final JTextField weightField = new JTextField(15);
        panel01.add(weightField);

        // 第 2 个 JPanel, 使用默认的浮动布局
        JPanel panel02 = new JPanel();
        panel02.add(new JLabel("当前腰围（cm）"));
        final JTextField waistlineField = new JTextField(15);
        panel02.add(waistlineField);

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveBtn = new JButton("保存");
        panel03.add(saveBtn);
//        JButton registerBtn = new JButton("去注册");
//        registerBtn.setPreferredSize(new Dimension(80,20)); //设置尺寸
//        panel03.add(registerBtn);
        saveBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String weightStr = weightField.getText();
                String waistlineStr = waistlineField.getText();
                System.out.println(String.format("weight: weight = %s , waistline = %s",weightStr,waistlineStr));
                if(weightStr.length()==0 && waistlineStr.length()==0){
                    // 消息对话框无返回, 仅做通知作用
                    JOptionPane.showMessageDialog(
                            jf,"体重和腰围 必须至少填一项！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                Double weight = null,waistline=null;
                if(weightStr.length()>0 && GeneralUtil.isPositiveNumber(weightStr)){
                    weight = Double.parseDouble(weightStr);
                }
                if(waistlineStr.length()>0 && GeneralUtil.isPositiveNumber(waistlineStr)){
                    waistline = Double.parseDouble(waistlineStr);
                }
                if(weight==null && waistline==null){
                    JOptionPane.showMessageDialog(
                            jf,"体重和腰围 必须至少正确填写一项（正数）！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                if(LoginAction.getLoginUser()==null){
                    JOptionPane.showMessageDialog(
                            jf,"用户未登录！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                try {
                    weightService.addWeight(LoginAction.getLoginUser(),weight,waistline);
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
                        jf,"录入成功！",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
                jf.dispose();
            }
        });

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);
        vBox.add(panel03);

        // 父 JPanel, 使用默认的浮动布局
        JPanel panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelParent.add(vBox);

        jf.setContentPane(panelParent);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    /** 显示体重历史框 */
    public void showWeightHistoryPanel(){
        final JFrame jf = new JFrame("近3个月体重历史");
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 创建内容面板，使用边界布局
        JPanel panel = new JPanel(new BorderLayout());

        // 表头（列名）
        Object[] columnNames = { "日期", "体重（kg）", "腰围（cm）", "变化" };
        List<Map<String,Object>> resList = weightService.getRecent90DaysList(LoginAction.getLoginUser());
//        System.out.println(resList);
        // 行数据
        Object[][] resRowData = getFormatRowData(resList);

        // 创建一个表格，指定 所有行数据 和 表头
        JTable table = new JTable(resRowData, columnNames);

        // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        // 把 表格内容 添加到容器中心
        panel.add(table, BorderLayout.CENTER);

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel);

        // 父 JPanel, 使用默认的浮动布局
        JPanel panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelParent.add(vBox);

        jf.setContentPane(panelParent);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    /** 格式化，将list转为数组 */
    private Object[][] getFormatRowData(List<Map<String,Object>> resList){
        Object[][] resRowData = null;
        if(resList!=null && resList.size()>0){
            resRowData = new Object[resList.size()][];
            for(int i=0,l=resList.size();i<l;i++){
                Map<String,Object> resMap = resList.get(i);
                resRowData[i] = new Object[4];
                resRowData[i][0] = resMap.get("statistics_date");
                resRowData[i][1] = resMap.get("weight");
                resRowData[i][2] = resMap.get("waistline");
                if(i<l-1){
                    Map<String,Object> resMap2 = resList.get(i+1);
                    BigDecimal weightN = (BigDecimal) (resMap.get("weight")==null?new BigDecimal(-1):resMap.get("weight"));
                    BigDecimal waistlineN = (BigDecimal) (resMap.get("waistline")==null?new BigDecimal(-1):resMap.get("waistline"));
                    BigDecimal weightO = (BigDecimal) (resMap2.get("weight")==null?new BigDecimal(-1):resMap2.get("weight"));
                    BigDecimal waistlineO = (BigDecimal) (resMap2.get("waistline")==null?new BigDecimal(-1):resMap2.get("waistline"));
                    String weightTrend = "<span style=\"font-weight: 700;\">-</span>";
                    if(weightN.intValue()>0 && weightO.intValue()>0){
                        int compareRes = weightN.compareTo(weightO);
                        if(compareRes==0){
                            weightTrend = "<span style=\"color:blue;font-weight: 700;\">=</span>";
                        }else if(compareRes>0){
                            weightTrend = "<span style=\"color:red;font-weight: 700;\">↑</span>";
                        }else{
                            weightTrend = "<span style=\"color:green;font-weight: 700;\">↓</span>";
                        }
                    }
                    String waistlineTrend = "<span style=\"font-weight: 700;\">-</span>";
                    if(waistlineN.intValue()>0 && waistlineO.intValue()>0){
                        int compareRes = waistlineN.compareTo(waistlineO);
                        if(compareRes==0){
                            waistlineTrend = "<span style=\"color:blue;font-weight: 700;\">=</span>";
                        }else if(compareRes>0){
                            waistlineTrend = "<span style=\"color:red;font-weight: 700;\">↑</span>";
                        }else{
                            waistlineTrend = "<span style=\"color:green;font-weight: 700;\">↓</span>";
                        }
                    }
                    resRowData[i][3] = "<html><body>"+weightTrend+"&nbsp;&nbsp;&nbsp;&nbsp;"+waistlineTrend+"</body></html>";
                }else{
                    resRowData[i][3] = "<html><body><span style=\"font-weight: 700;\">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-<span></body></html>";
                }
            }
        }else{
            resRowData = new Object[0][];
        }
        return resRowData;
    }

}
