package cn.bestsort.bbslite.manager;

import cn.bestsort.bbslite.pojo.model.User;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Random;

/**
 * @author bestsort
 */
@Slf4j
@Service
public class FileUpload {
    /**
     * Endpoint以杭州为例，其它Region请按实际情况填写。
     * */
    @Value("${bbs.oss.endpoint:}")
    private String endpoint;
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
     * 强烈建议您创建并使用RAM账号进行API访问或日常运维
     * 请登录<a href="https://ram.console.aliyun.com">阿里云</a> 创建RAM账号。
     */
    @Value("${bbs.oss.accessKeyId:}")
    private String accessKeyId;

    @Value("${bbs.oss.accessKeySecret:}")
    private String accessKeySecret;

    @Value("${bbs.oss.bucketName:}")
    private String bucketName;
    /**
     * <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
     */

    @Value("${bbs.oss.objectName:}")
    private String objectName;

    @Value("${bbs.oss.callbackUrl:}")
    private String callback;
    public String upload(HttpServletRequest request) {
        if ("".equals(callback)){
            callback = "https://" + bucketName + "." + endpoint;
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        try {
            MultipartRequest multipartRequest = (MultipartRequest) request;
            MultipartFile file = multipartRequest.getFile("editormd-image-file");
            String fileName = Calendar.getInstance().get(Calendar.YEAR)+
                    "/"+Calendar.getInstance().get(Calendar.MONTH) +
                    "/"+ ((Math.abs(new Random().nextInt())))+"_"+file.getOriginalFilename();
            ossClient.putObject(bucketName, fileName,file.getInputStream());
            return callback + "/" +fileName;
        } catch (Exception e) {
            log.error("{}：imagine upload failed , caused by {}",this.getClass().getName(),(User)request.getSession().getAttribute("user"));
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

    }
}
