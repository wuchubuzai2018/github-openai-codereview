package cn.aijavapro.middleware.sdk;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
        // 2.发送给智普AI进行代码评审
        String result = sendToZhipuAI(code);
        System.out.println("AI代码评审结果:" + result);
    }
    
    private static String checkGitChangeCode() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));
        Process process = processBuilder.start();
        
        String diffCode = IOUtils.toString(new BufferedReader(new InputStreamReader(new
                BufferedInputStream(process.getInputStream()))));
        System.out.println("变更代码:" + diffCode);
        int result = process.waitFor();
        System.out.println("执行退出:" + result);
        return diffCode;
    }
    
    private static String sendToZhipuAI(String diffCode) {
        List<ChatMessage> messages = new ArrayList<>();
        String systemMessage = "你是一个非常有经验的Java架构师，非常擅长代码评审，请对用户提供的Git Diff格式的代码进行评审";
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);
        String userMessage = "这是我的变更代码，请进行评审吧：" + diffCode;
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("glm-4-flash")
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        String key = System.getProperty("GLM_KEY");
        if(key == null || key.trim().length() == 0){
            System.out.println("未发现秘钥信息");
            return "";
        }
        ClientV4 client = new ClientV4.Builder(key).build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        try {
            System.out.println(JSON.toJSONString(invokeModelApiResp));
            String content = invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
}
