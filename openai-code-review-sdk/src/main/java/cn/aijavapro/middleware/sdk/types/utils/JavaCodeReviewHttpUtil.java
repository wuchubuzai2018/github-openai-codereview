package cn.aijavapro.middleware.sdk.types.utils;

import cn.aijavapro.middleware.sdk.domain.model.ChatCompletionRequest;
import cn.aijavapro.middleware.sdk.domain.model.ChatCompletionSyncResponse;
import cn.aijavapro.middleware.sdk.domain.model.Model;
import com.alibaba.fastjson2.JSON;
import com.zhipu.oapi.utils.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * @author Wuchubuzai
 * @version 1.0 2024年07月30日 09:56
 */
public class JavaCodeReviewHttpUtil {
    
    public static String codeReview(String diffCode) throws Exception {
        String zhipuKey = System.getenv("CHATGPT_KEY");
        if (StringUtils.isEmpty(zhipuKey)) {
            return zhipuKey;
        }
        String apiKeySecret = zhipuKey;
        String token = BearerTokenUtils.getToken(apiKeySecret);
        
        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);
        
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(Model.GLM_4_FLASH.getCode());
        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;
            
            {
                add(new ChatCompletionRequest.Prompt("user",
                        "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));
                add(new ChatCompletionRequest.Prompt("user", diffCode));
            }
        });
        
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(chatCompletionRequest).getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }
        
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        
        in.close();
        connection.disconnect();
        
        System.out.println("评审结果：" + content.toString());
        
        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
        
    }
}
