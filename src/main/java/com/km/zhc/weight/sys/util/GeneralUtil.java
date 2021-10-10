package com.km.zhc.weight.sys.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/** 常见工具 */
public class GeneralUtil {

    /** 判断是否是数字 */
    public static boolean isNumber(String string) {
        if (string == null || string.length()==0)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(string).matches();
    }

    /** 判断是否是正数 */
    public static boolean isPositiveNumber(String string) {
        if (string == null || string.length()==0)
            return false;
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        return pattern.matcher(string).matches();
    }

    /** 往指定临时文件中写入内容 */
    public static void writeToTemp(String pFilename,String writeStr,boolean isAppend) throws IOException {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File dir = new File(tempDir);
        File tempFile = new File(tempDir+File.separator+pFilename);
        FileWriter fileWriter = new FileWriter(tempFile,isAppend);
        System.out.println("写入文件: "+tempFile.getPath());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(writeStr);
        bw.close();
    }

    /** 往指定临时文件中逐行读出内容 */
    public static List<String> readTempLineByLine(String pFilename) throws IOException {
        List<String> resultList = new ArrayList<String>();
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File dir = new File(tempDir);
        File tempFile = new File(tempDir+File.separator+pFilename);
        if(!tempFile.exists()){
            return resultList;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile), "UTF-8"));//构造一个BufferedReader类来读取文件
        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            resultList.add(s);
        }
        br.close();
        return resultList;
    }

    /**获得选定日期后多少天的时间*/
    public static Date getDayLater(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

}
