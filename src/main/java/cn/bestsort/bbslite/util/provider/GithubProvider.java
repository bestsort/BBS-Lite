/**
 * @ClassName
 * @Description 处理从 Gitub 接受到的JSON数据
 * @Author bestsort
 * @Date 19-8-22 下午7:53
 * @Version 1.0
 */

package cn.bestsort.bbslite.util.provider;


import cn.bestsort.bbslite.pojo.dto.AccessTokenDto;
import cn.bestsort.bbslite.pojo.vo.GithubUserVo;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = FormBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = Objects.requireNonNull(response.body()).string();
            return str.split("&")[0].split("=")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUserVo getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(str, GithubUserVo.class);
        }catch (IOException ignored){ }
        return null;
    }


}
