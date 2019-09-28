/**
 * @ClassName
 * @Description Github Bean
 * @Author bestsort
 * @Date 19-8-22 下午8:32
 * @Version 1.0
 */

package cn.bestsort.bbslite.dao.create;
import lombok.Data;

@Data
public class GithubUser {
    private String id;
    private String bio;
    private String htmlUrl;
    private String email;
    private String login;
    private String avatarUrl;
}
