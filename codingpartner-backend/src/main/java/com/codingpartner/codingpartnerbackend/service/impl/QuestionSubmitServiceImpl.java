package com.codingpartner.codingpartnerbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingpartner.codingpartnerbackend.common.BaseContext;
import com.codingpartner.codingpartnerbackend.common.ErrorCode;
import com.codingpartner.codingpartnerbackend.constant.CommonConstant;
import com.codingpartner.codingpartnerbackend.constant.UserConstant;
import com.codingpartner.codingpartnerbackend.exception.BusinessException;
import com.codingpartner.codingpartnerbackend.mapper.QuestionMapper;
import com.codingpartner.codingpartnerbackend.mapper.UserMapper;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.codingpartner.codingpartnerbackend.model.vo.QuestionSubmit.QuestionSubmitVO;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.codingpartner.codingpartnerbackend.model.enums.QuestionSubmitStatusEnum;
import com.codingpartner.codingpartnerbackend.model.vo.question.QuestionVO;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;
import com.codingpartner.codingpartnerbackend.mq.producer.QuestionSubmitProducer;
import com.codingpartner.codingpartnerbackend.service.JudgeService;
import com.codingpartner.codingpartnerbackend.service.QuestionSubmitService;
import com.codingpartner.codingpartnerbackend.mapper.QuestionSubmitMapper;
import com.codingpartner.codingpartnerbackend.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.codingpartner.codingpartnerbackend.constant.CommonConstant.EXIST_PARAM_NULL;
import static com.codingpartner.codingpartnerbackend.constant.QuestionSubmitConstant.*;
import static com.codingpartner.codingpartnerbackend.constant.RabbitMqConstant.*;

/**
 * @author zgh
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2024-04-05 09:54:56
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    private QuestionSubmitMapper questionSubmitMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    @Lazy
    private JudgeService judgeService;

    @Resource
    private QuestionSubmitProducer questionSubmitProducer;

    /**
     * 添加题目提交记录
     *
     * @param questionSubmitAddRequest 题目提交请求参数
     */
    @Override
    public Long addQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest) {
        if (questionSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        // 校验题目是否存在
        QueryWrapper<Question> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", questionSubmitAddRequest.getQuestionId());
        Question question = questionMapper.selectOne(queryWrapper1);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_NOT_EXIST);
        }
        // 如果该用户存在该题目正在判题中，则不能再重复提交代码，直到判题结束
        QueryWrapper<QuestionSubmit> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2
                .eq("user_id", BaseContext.getUserId())
                .eq("question_id", questionSubmitAddRequest.getQuestionId())
                .eq("judge_status", QuestionSubmitStatusEnum.WAITING.getValue());
        if (questionSubmitMapper.selectOne(queryWrapper2) != null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, QUESTION_SUBMIT_CODE_RUNNING);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);
        questionSubmit.setUserId(BaseContext.getUserId());
        questionSubmit.setCreateTime(LocalDateTime.now());
        questionSubmit.setUpdateTime(LocalDateTime.now());
        questionSubmit.setIsDelete(0);
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, QUESTION_SUBMIT_CREATED_FAILED);
        }
        Long questionSubmitId = questionSubmit.getId();
        // 线程异步执行判题服务
        /*CompletableFuture.runAsync(() -> {
            judgeService.doJudge(questionSubmitId);
        });*/
        // 队列异步执行判题服务
        questionSubmitProducer.sendMessage(RABBITMQ_EXCHANGE_NAME, RABBITMQ_ROUTING_KEY, String.valueOf(questionSubmitId));
        return questionSubmitId;
    }

    /**
     * 删除题目提交记录
     *
     * @param ids 题目提交ids
     */
    @Override
    public void deleteQuestionSubmit(Long[] ids) {
        if (ids == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        for (Long id : ids) {
            QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id).eq("is_delete", 0);
            QuestionSubmit questionSubmit = questionSubmitMapper.selectOne(queryWrapper);
            if (questionSubmit == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_SUBMIT_NOT_EXIST);
            }
            questionSubmit.setIsDelete(1);
            int delete = questionSubmitMapper.update(questionSubmit, queryWrapper);
            if (delete != 1) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, QUESTION_SUBMIT_DELETED_FAILED);
            }
        }
    }

    /**
     * 根据id获取脱敏题目提交记录
     *
     * @param id 题目提交id
     * @return 题目提交记录
     */
    @Override
    public QuestionSubmitVO getQuestionSubmitVOById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        QuestionSubmit questionSubmit = this.getById(id);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        return voSetUserAndQuestion(questionSubmit, questionSubmitVO);
    }

    /**
     * 分页查询脱敏题目提交记录
     *
     * @param questionSubmitQueryRequest 查询请求参数
     * @return 分页查询结果
     */
    @Override
    public Page<QuestionSubmitVO> getAllQuestionSubmitVO(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        Page<QuestionSubmit> questionSubmitPage = getAllQuestionSubmit(questionSubmitQueryRequest);
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>();
        List<QuestionSubmitVO> records = new ArrayList<>();
        for (QuestionSubmit questionSubmit : questionSubmitPage.getRecords()) {
            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
            records.add(voSetUserAndQuestion(questionSubmit, questionSubmitVO));
        }
        questionSubmitVOPage.setRecords(records);
        questionSubmitVOPage.setTotal(questionSubmitPage.getTotal());
        questionSubmitVOPage.setSize(questionSubmitPage.getSize());
        questionSubmitVOPage.setCurrent(questionSubmitPage.getCurrent());
        return questionSubmitVOPage;
    }

    /**
     * 根据id获取题目提交记录
     *
     * @param id 题目提交id
     * @return 题目提交记录
     */
    @Override
    public QuestionSubmit getQuestionSubmitById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        QuestionSubmit questionSubmit = this.getById(id);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return questionSubmit;
    }

    /**
     * 分页查询题目提交记录
     *
     * @param questionSubmitQueryRequest 查询请求参数
     * @return 分页查询结果
     */
    @Override
    public Page<QuestionSubmit> getAllQuestionSubmit(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        long current = questionSubmitQueryRequest.getCurrent();
        long pageSize = questionSubmitQueryRequest.getPageSize();
        QueryWrapper<QuestionSubmit> queryWrapper = getQueryWrapper(questionSubmitQueryRequest);
        Page<QuestionSubmit> page = new Page<>(current, pageSize);
        Page<QuestionSubmit> questionSubmitPage = this.page(page, queryWrapper);
        if (questionSubmitPage == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_SUBMIT_SEARCHE_FAILED);
        }
        return questionSubmitPage;
    }

    /**
     * 设置vo中的user和question
     *
     * @param questionSubmit   题目提交记录
     * @param questionSubmitVO 题目提交记录VO
     */
    private QuestionSubmitVO voSetUserAndQuestion(QuestionSubmit questionSubmit, QuestionSubmitVO questionSubmitVO) {
        UserVO userVO = UserVO.objToVo(userMapper.selectById(questionSubmit.getUserId()));
        if (userVO == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        questionSubmitVO.setUserVO(userVO);
        QuestionVO questionVO = QuestionVO.objToVo(questionMapper.selectById(questionSubmit.getQuestionId()));
        if (questionVO == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        questionSubmitVO.setQuestionVO(questionVO);
        return questionSubmitVO;
    }

    /**
     * 获取查询条件的QueryWrapper
     *
     * @param questionSubmitQueryRequest 查询请求参数
     * @return 查询条件的QueryWrapper
     */
    private QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        String language = questionSubmitQueryRequest.getLanguage();
        String result = questionSubmitQueryRequest.getResult();
        Integer judgeStatus = questionSubmitQueryRequest.getJudgeStatus();
        Integer isPass = questionSubmitQueryRequest.getIsPass();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        // 拼接查询条件
        User user = userMapper.selectById(BaseContext.getUserId());
        // 管理员可以查看所有提交记录
        if (user.getRole().equals(UserConstant.ADMIN_ROLE)) {
            queryWrapper.eq("user_id", BaseContext.getUserId());
        }
        queryWrapper
                .eq(StringUtils.isNotBlank(language), "language", language)
                .eq(StringUtils.isNotBlank(result), "result", result)
                .eq(QuestionSubmitStatusEnum.getEnumByValue(judgeStatus) != null, "judge_status", judgeStatus)
                .eq(isPass != null, "is_pass", isPass)
                .eq("is_delete", 0)
                .orderBy(SqlUtil.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




