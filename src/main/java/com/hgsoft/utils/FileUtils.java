package com.hgsoft.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.log4j.Logger;

import com.hgsoft.exception.ApplicationException;

/**
 * Created by 孙晓伟
 * file : FileUtils.java
 * date : 2017/7/21
 * time : 16:07
 */
public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class.getName());

    public static void main(String[] args) {
//        String path = "D:/lianJia/123";
//        List<File> list = new ArrayList<File>();
//        traverseFolder(path,list);
//        System.out.println(list.size());
        saveFile("sxw","D:/test");
    }

    //写入文件
    public static void saveFile(String text, String filePath) {

        FileWriter fw = null;

        try {
            File f = new File(filePath);

            if(f.exists()){
                return;
            }

            File f2 = new File(f.getParent());
            if (!f2.exists()) {
                f2.mkdirs();
            } else {
                if (!f2.isDirectory()) {
                    f2.mkdirs();
                }
            }
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(f), "GBK"));
            writer.write(text);
            writer.flush();
            writer.close();
            System.out.println("success");
        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if(fw != null){
                try {

                    fw.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }

    }

    /**
     * 以字节为单位读取文件
     */
    public static String readFileByBytes(String fileName) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            fis = new FileInputStream(fileName);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            String str = "";
            StringBuffer text = new StringBuffer();
            while ((str = br.readLine()) != null) {
                text.append(str + "\n");
            }
            // 当读取的一行不为空时,把读到的str的值赋给str1
            return str.toString();
        } catch (Exception e) {
            System.out.println("找不到指定文件");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String readTxtFile(File file,String encoding){
        try {
            if(StringUtil.isEmpty(encoding)){
                encoding="utf-8";
            }
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuffer text = new StringBuffer();
                while((lineTxt = bufferedReader.readLine()) != null){
                    text.append(lineTxt);
                }
                read.close();
                return text.toString();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * @param one:filePath,two:encoding
     * @return
     */
    public static String readTxtFile(Object... obj){
        File file = new File(obj[0].toString());
        try {
            String encoding="utf-8";
            if(obj.length>1){
                encoding=obj[1].toString();
            }
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuffer text = new StringBuffer();
                while((lineTxt = bufferedReader.readLine()) != null){
                    text.append(lineTxt);
                }
                read.close();
                return text.toString();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return null;

    }


    public static void nioSaveFile(String text, String filePath){
        FileChannel foChannel = null;
        try {
            File f = new File(filePath);

//            if(f.exists()){
//                return;
//            }

            File f2 = new File(f.getParent());
            if (!f2.exists()) {
                f2.mkdirs();
            } else {
                if (!f2.isDirectory()) {
                    f2.mkdirs();
                }
            }

            //nio上传
            foChannel = new FileOutputStream(filePath).getChannel();
            ByteBuffer src = ByteBuffer.wrap(text.getBytes());

            foChannel.write(src);

            System.out.println("success");
        } catch (IOException e) {
            logger.error(e.getMessage()+"nio流保存文件失败");
            e.printStackTrace();
            throw new ApplicationException("nio流保存文件失败");
        } finally {
            if (foChannel != null) {
                try {
                    foChannel.close();
                } catch (IOException e) {
                    logger.error(e.getMessage()+"nio流保存文件失败");
                    e.printStackTrace();
                    throw new ApplicationException("nio流保存文件失败");
                }
            }
        }
    }

    /**
     * 获取给定目录下所有的文件
     * @param path
     */
    public static List<File> traverseFolder(String path,List<File> fileList) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return fileList;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //文件夹 递归
                        traverseFolder(file2.getAbsolutePath(),fileList);
                    } else {
                        fileList.add(file2);
                    }
                }
            }
            return fileList;
        } else {
            System.out.println("文件不存在!");
            return fileList;
        }
    }
}
