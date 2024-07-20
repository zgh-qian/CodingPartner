package com.codingpartner.codingpartnerbackend.aop;

import com.codingpartner.codingpartnerbackend.annotation.AuthCheck;
import com.codingpartner.codingpartnerbackend.annotation.AuthQuestion;
import com.codingpartner.codingpartnerbackend.common.ErrorCode;
import com.codingpartner.codingpartnerbackend.constant.UserConstant;
import com.codingpartner.codingpartnerbackend.exception.BusinessException;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.codingpartner.codingpartnerbackend.model.enums.UserRoleEnum;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;
import com.codingpartner.codingpartnerbackend.service.QuestionService;
import com.codingpartner.codingpartnerbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.codingpartner.codingpartnerbackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 权限校验 AOP
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    /**
     * 执行拦截 @AuthCheck 注解的方法
     * 必须要有权限才可以访问
     *
     * @param joinPoint 执行方法
     * @param authCheck 权限注解
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(authCheck)")
    public Object authCheckInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        UserVO loginUser = userService.getLoginUserVO(request);
        // 必须有该权限才通过
        if (StringUtils.isNotBlank(mustRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustUserRoleEnum == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getRole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!mustRole.equals(userRole)) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }

    /**
     * 执行拦截 @AuthSelf 注解的方法
     * 必须是本人或者管理员才可以访问
     *
     * @param joinPoint 执行方法
     * @param authQuestion  权限注解
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(authQuestion)")
    public Object authSelfInterceptor(ProceedingJoinPoint joinPoint, AuthQuestion authQuestion) throws Throwable {
        String param = authQuestion.param();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User loginUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (param.equals("id")) {
            Long questionId = (Long) joinPoint.getArgs()[0];
            authSelfMethod(questionId, loginUser);
        } else if (param.equals("ids")) {
            Long[] questionIds = (Long[]) joinPoint.getArgs()[0];
            for (Long questionId : questionIds) {
                authSelfMethod(questionId, loginUser);
            }
        } else if (param.equals("question")) {
            Object questionObj = joinPoint.getArgs()[0];
            Question question = new Question();
            BeanUtils.copyProperties(questionObj, question);
            authSelfMethod(question.getId(), loginUser);
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }

    private void authSelfMethod(Long questionId, User loginUser) {
        Question question = questionService.getById(questionId);
        // 必须是本人或者管理员才可以访问
        if (!loginUser.getId().equals(question.getUserId()) && !loginUser.getRole().equals(UserConstant.ADMIN_ROLE)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
    }

}
