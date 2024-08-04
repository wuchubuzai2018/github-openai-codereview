package cn.aijavapro.middleware.sdk;

import cn.aijavapro.middleware.sdk.types.utils.CheckLocalCodeUtil;
import cn.aijavapro.middleware.sdk.types.utils.GitWriteLogUtil;
import cn.aijavapro.middleware.sdk.types.utils.JavaCodeReviewHttpUtil;

/**
 * @author Wuchubuzai
 * @version 1.0
 * 2024年07月21日 11:26
 */
public class OpenAiCodeReview {
    
    public static void main(String[] args) throws Exception {
        System.out.println("openai 代码评审，测试执行");
        
        // 加载Github Action环境下的环境变量信息，即用户的token信息
        String token = System.getenv("GIT_USER_TOKEN");
        if (null == token || token.isEmpty()) {
            throw new RuntimeException("token is null");
        }
        
        // 1.执行本地命令进行代码检出的操作
        String code = CheckLocalCodeUtil.checkGitChangeCode();
        // 2.调用原生网络请求发送变更代码评审结果
        String result = JavaCodeReviewHttpUtil.codeReview(code);
        // 3.写入评审结果到日志文件中
        GitWriteLogUtil.writeLog(token, result);
    }
    
}
