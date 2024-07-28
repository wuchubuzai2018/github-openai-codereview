package cn.aijavapro.middleware.sdk;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Wuchubuzai
 * @version 1.0
 * 2024年07月21日 11:26
 */
public class OpenAiCodeReview {
    
    public static void main(String[] args) throws Exception {
        System.out.println("开始执行CodeReview");
        
        // 1.执行本地命令进行代码检出的操作
        String code =checkGitChangeCode();
    }
    
    
    private static String checkGitChangeCode() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD-1", "HEAD");
        processBuilder.directory(new File("."));
        Process process = processBuilder.start();
        String diffCode = IOUtils.toString(process.getInputStream());
        int result = process.waitFor();
        System.out.println("执行退出:" + result);
        System.out.println("变更代码:" + diffCode);
        return diffCode;
    }
    
    
}
