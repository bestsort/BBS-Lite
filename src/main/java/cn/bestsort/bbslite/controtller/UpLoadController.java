package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.dto.UploadResultDto;
import cn.bestsort.bbslite.manager.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UpLoadController {
    @Autowired
    private FileUpload fileUpload;
    @ResponseBody
    @PostMapping("/upload")
    public UploadResultDto upload(HttpServletRequest request){
        try {
            String url = fileUpload.upload(request);
            if (!url.isEmpty()) {
                return new UploadResultDto().success(url);
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            return new UploadResultDto().fail();
        }
    }
}
