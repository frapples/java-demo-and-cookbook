package io.github.frapples.javademoandcookbook.springboot.web.resolver;

import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@NoArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        UserId userIdannotation = parameter.getParameterAnnotation(UserId.class);
        Object id = webRequest.getAttribute("_session_user_id", 1);
        if (userIdannotation == null) {
            return null;
        } else if (id == null && userIdannotation.required()) {
            throw new IllegalArgumentException("No login");
        } else {
            return id;
        }
    }
}
