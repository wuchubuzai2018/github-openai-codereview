package cn.aijavapro.middleware.sdk.types.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.io.File;
import java.util.Date;

/**
 * @author Wuchubuzai
 * @version 1.0 2024年08月04日 22:45
 */
public class GitWriteLogUtil {
    
    public static String writeLog(String token, String result) throws Exception {
        // 克隆GitHub仓库到本地目录 "repo"
        Git git = Git.cloneRepository()
                .setURI("https://github.com/wuchubuzai2018/github-openai-codereview-log.git")
                .setDirectory(new File("repo"))
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""))
                .call();
        
        // 获取当前日期，并创建相应的文件夹
        String dateFolderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateFolder = new File("repo/" + dateFolderName);
        if (!dateFolder.exists()) {
            dateFolder.mkdirs(); // 如果文件夹不存在，则创建
        }
        
        // 生成随机文件名，并创建新文件
        String fileName = generateRandomString(12) + ".md";
        File newFile = new File(dateFolder, fileName);
        try (FileWriter writer = new FileWriter(newFile)) {
            writer.write(result); // 将结果写入文件
        }
        
        // 将新文件添加到Git仓库中
        git.add().addFilepattern(dateFolderName + "/" + fileName).call();
        // 提交变更
        git.commit().setMessage("Add new file via GitHub Actions").call();
        // 推送到远程仓库
        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, "")).call();
        
        // 打印推送成功的消息
        System.out.println("Changes have been pushed to the repository.");
        
        // 返回新文件在GitHub仓库中的URL
        return "https://github.com/wuchubuzai2018/github-openai-codereview-log/blob/master/" + dateFolderName + "/" + fileName;
    }
    
    // 生成指定长度的随机字符串
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
}
