package cn.aijavapro.middleware.sdk;

import cn.aijavapro.middleware.sdk.types.utils.CheckLocalCodeUtil;
import cn.aijavapro.middleware.sdk.types.utils.JavaCodeReviewHttpUtil;
import cn.aijavapro.middleware.sdk.types.utils.ZhipuSdkHttpUtil;
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
        String code = CheckLocalCodeUtil.checkGitChangeCode();
//        // 2.发送给智普AI进行代码评审
//        String result = ZhipuSdkHttpUtil.sendToZhipuAI(code);
//        System.out.println("AI代码评审结果:" + result);
        // 3.原生Java请求发送
        String result = JavaCodeReviewHttpUtil.codeReview(code);
        
    }
    
    
    
    
    
    
    
}
