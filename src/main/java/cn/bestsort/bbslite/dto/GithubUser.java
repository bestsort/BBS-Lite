/**
 * @ClassName
 * @Description Github Bean
 * @Author bestsort
 * @Date 19-8-22 下午8:32
 * @Version 1.0
 */

package cn.bestsort.bbslite.dto;
import lombok.Data;

@Data
public class GithubUser {
    private String user;
    private Long id;
    private String bio;
    private String login;
    private String avatarUrl;
}
