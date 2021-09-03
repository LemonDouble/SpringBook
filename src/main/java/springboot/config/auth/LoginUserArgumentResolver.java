package springboot.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import springboot.config.auth.dto.SessionUser;

import javax.servlet.http.HttpSession;

/*
 * SessionUser user = (SessionUser) httpSession.getAttribute("user");
 * 위 코드는 Session 필요한 Controller 마다 중복적으로 작성해야 한다.
 * 따라서 어노테이션을 사용해 위 코드를 간결하게 만든다.
 */

// HandlerMethodArgumentResolver 의 구현체
// 조건에 맞는 경우, 메소드가 있다면 HandlerMethodArgumentResolver 가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있음.

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    // Controller 메소드의 특정 파라미터를 지원하는지 판단.
    // 이 경우, 파라미터에 @LoginUser 어노테이션이 붙어 있고
    // AND 파라미터 클래스 타입이 SessionUser.class 인 경우 true 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    // 파라미터에 전달할 객체를 생성합니다.
    // 여기서는 세션에서 객체를 가져옵니다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
