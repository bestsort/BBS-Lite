package cn.bestsort.bbslite.dto;

import lombok.Data;

@Data
public class UploadResultDto {
    private int success;
    private String message;
    private String url;
    public UploadResultDto success(String url){
        this.success = 1;
        this.url = url;
        return this;
    }

    public UploadResultDto fail(){
        this.success = 1;
        this.message = "图片上传失败,请稍后再试";
        return this;
    }
}
