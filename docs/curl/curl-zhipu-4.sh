curl -X POST \
        -H "Authorization: Bearer " \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "今天北京的天气怎么样"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions