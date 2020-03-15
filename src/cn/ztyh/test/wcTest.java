package cn.ztyh.test;

import cn.ztyh.wc.wc;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/*
    单元测试
    空文件：D:\chromeDownload\IO\blank.txt
    只有一个字符的文件：D:\chromeDownload\IO\char.txt
    只有一个词的文件：D:\chromeDownload\IO\word.txt
    只有一行的文件：D:\chromeDownload\IO\line.txt
    一个典型的源文件：D:\chromeDownload\IO\blank
 */

public class wcTest {

    public static String fileName = "D:\\chromeDownload\\IO\\line.txt";

    @Test
    public void testcharacterCount(){
        int result = wc.characterCount(fileName);
        Assert.assertEquals(18,result);
    }

    @Test
    public void testlineCount(){
        int result = wc.lineCount(fileName);
        Assert.assertEquals(1,result);
    }

    @Test
    public void testwordCount(){
        int result = wc.wordCount(fileName);
        Assert.assertEquals(4,result);
    }

    @Test
    public void testotherCount(){
        String string = wc.otherCount(fileName);
        String[] arr = string.split(",");
        //空行
        Assert.assertEquals(0,Long.parseLong(arr[0]));
        //注释行
        Assert.assertEquals(0,Long.parseLong(arr[1]));
        //代码行
        Assert.assertEquals(1,Long.parseLong(arr[2]));
    }

    @Test
    public void testtraverseList(){
        wc.traverseList("D:\\chromeDownload\\IO");
    }

    @Test
    public void searchFileList(){
        List<String> list = wc.searchFileList("D:\\chromeDownload\\IO");
        for(String s : list){
            System.out.println(s);
        }
    }


}
