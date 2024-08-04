package cn.aijavapro.middleware.sdk.types.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author Wuchubuzai
 * @version 1.0 2024年07月30日 10:00
 */
public class CheckLocalCodeUtil {
    
    public static String checkGitChangeCode() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));
        Process process = processBuilder.start();
        
        String diffCode = IOUtils.toString(new BufferedReader(new InputStreamReader(new BufferedInputStream(process.getInputStream()))));
        System.out.println("变更代码:" + diffCode);
        int result = process.waitFor();
        System.out.println("执行退出:" + result);
        return diffCode;
    }
    
    
}
