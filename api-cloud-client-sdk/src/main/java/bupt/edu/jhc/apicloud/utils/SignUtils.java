package bupt.edu.jhc.apicloud.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @Description: 签名工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/5
 */
public class SignUtils {
    /**
     * 加密生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String generateSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
