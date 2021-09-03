package springboot.config.auth.dto;

import jdk.dynalink.beans.StaticClass;
import lombok.Builder;
import lombok.Getter;
import springboot.domain.user.Role;
import springboot.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*
    Naver API 응답 예시 ( https://developers.naver.com/docs/login/profile/profile.md )
    유저의 Unique한 id는 response/id
            {
          "resultcode": "00",
          "message": "success",
          "response": {
            "email": "openapi@naver.com",
            "nickname": "OpenAPI",
            "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
            "age": "40-49",
            "gender": "F",
            "id": "32742776",
            "name": "오픈 API",
            "birthday": "10-01",
            "birthyear": "1900",
            "mobile": "010-0000-0000"
          }
        }
        Spring 에서는 user_name 을 최상위 필드만 지정 가능,
        하지만 네이버의 최상위 필드는 resultCode, message, response
        application-oauth.properties 에서
        spring.security.oauth2.client.provider.naver.user_name_attribute=response
        로 user_name을 일단은 ID가 있는 response로 잡고,
     */

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String,Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
