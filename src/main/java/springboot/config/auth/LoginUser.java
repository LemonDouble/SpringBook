package springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* SessionUser user = (SessionUser) httpSession.getAttribute("user");
* 위 코드는 Session 필요한 Controller 마다 중복적으로 작성해야 한다.
* 따라서 어노테이션을 사용해 위 코드를 간결하게 만든다.
 */



@Target(ElementType.PARAMETER) //어노테이션이 생성될 수 있는 위치, 메소드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME)
// RetentionPolicy : 3가지, SOURCE, CLASS, RUNTIME -> LifeCycle 결정
// SOURCE : Source Code 까지 남아있는다. 자바 파일 내에서만 적용
// CLASS (Default) : 클래스 파일(.class) 까지 남아있는다.
// RUNTIME : RUNTIME 까지 남아있는다.
public @interface LoginUser {
}
