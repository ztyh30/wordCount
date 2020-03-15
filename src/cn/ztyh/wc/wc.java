package cn.ztyh.wc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wc {
    public static void main(String[] args) {
        while(true){
            System.out.println("**** -c   [文件名]  返回指定文件字符数");
            System.out.println("**** -w   [文件名]  返回指定文件词数");
            System.out.println("**** -l   [文件名]  返回指定文件行数");
            System.out.println("**** -s   [文件夹名] 返回和处理符合条件的文件");
            System.out.println("**** -a   [文件名]  返回指定文件的代码行、空行、注释行");
            System.out.println("如果使用 -s 功能，则输入文件夹名：");
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入文件名：");
            String filename = sc.nextLine();
            System.out.println("请选择你的操作：");
            String choice = sc.next();
            switch(choice){
                case "-c":  int characters = characterCount(filename);
                    System.out.println("字符数："+characters);
                    break;

                case "-w":  int words = wordCount(filename);
                    System.out.println("单词数："+words);
                    break;

                case "-l":  int lines = lineCount(filename);
                    System.out.println("行数："+lines);
                    break;

                case "-a":  String result = otherCount(filename);
                    String arr[] = result.split(",");
                    System.out.println("空行："+arr[0]);
                    System.out.println("注释行："+arr[1]);
                    System.out.println("代码行："+arr[2]);
                    break;
                case "-s":  traverseList(filename);
                    break;
                default: System.out.println("输入操作错误！"); break;
            }

        }
    }



    public static int characterCount(String file){
        int character = 0; //字符数
        String s = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
            while((s = br.readLine())!=null){
                s = s.replaceAll(" ","");
                character += s.length();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return character;
    }

    public static int lineCount(String file){
        int line = 0; //行数
        String s = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
            while((s = br.readLine()) != null){
                line++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    public static int wordCount(String file){
        int word = 0; //单词
        String s = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
            Pattern pattern = Pattern.compile("[a-zA-Z]+");//连续字母即为单词
            while((s = br.readLine()) != null){
                Matcher matcher = pattern.matcher(s);
                while(matcher.find()){
                    word++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return word;
    }

    public static String otherCount(String file){
        int spaceline = 0; //空行
        int codeline = 0; //代码行
        int noteline = 0; //注释行
        boolean flag = false; //标记
        String s = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
            while((s = br.readLine()) != null){
                if(flag == false){
                    if(s.contains("/*") && !s.contains("*/")){
                        flag = true;
                    }
                    if(s.trim().indexOf("//") == 0  || s.trim().indexOf("}//") == 0 || s.trim().indexOf("/*") == 0){
                        noteline++;
                    }
                    else if(s.trim().length() > 1){
                        codeline++;
                    }
                    else{
                        spaceline++;
                    }
                }else{
                    if(s.indexOf("*/") == s.length()-2){
                        flag = false;
                    }
                    noteline++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String result = spaceline+","+noteline+","+codeline;
        return result;
    }
    //定义集合，用来收集递归收集的文件
    public  static List<String> fileList = new ArrayList<>();

    public static List<String> searchFileList(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        if(files == null || files.length == 0){
            System.out.println("ERROR");
            return null;
        }else{
            for(File f : files){
                if(f.isDirectory()){
                    searchFileList(f.getAbsolutePath());//递归处理
                }else if(f.canRead() && f.isFile()){
                    fileList.add(f.getAbsolutePath());//如果是文件，则加入集合
                }else{
                    continue;
                }
            }
        }
        return fileList;
    }

    public static void traverseList(String filename) {
        List<String> fileList = new ArrayList<>();
        fileList = searchFileList(filename);
        if(fileList == null){
            System.out.println("ERROR");
            return;
        }
        for (String s : fileList) {
            int characters = 0;
            int wordCount = 0;
            int lineCount = 0;
            characters = characterCount(s);
            wordCount = wordCount(s);
            lineCount = lineCount(s);
            String str = otherCount(s);
            String[] arr = str.split(",");
            System.out.println("文件" + s + "的信息如下：");
            System.out.println("字符总数：" + characters);
            System.out.println("单词总数：" + wordCount);
            System.out.println("总行数：" + lineCount);
            System.out.println("空行数：" + arr[0]);
            System.out.println("代码行数：" + arr[2]);
            System.out.println("注释总行数：" + arr[1]);
        }
    }
}
