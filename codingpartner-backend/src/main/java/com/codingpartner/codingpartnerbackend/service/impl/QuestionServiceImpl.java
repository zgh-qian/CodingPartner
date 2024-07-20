package com.codingpartner.codingpartnerbackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingpartner.codingpartnerbackend.common.BaseContext;
import com.codingpartner.codingpartnerbackend.common.ErrorCode;
import com.codingpartner.codingpartnerbackend.constant.CommonConstant;
import com.codingpartner.codingpartnerbackend.exception.BusinessException;
import com.codingpartner.codingpartnerbackend.exception.ThrowUtils;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionQueryRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeCase;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.codingpartner.codingpartnerbackend.model.vo.question.QuestionVO;
import com.codingpartner.codingpartnerbackend.service.QuestionService;
import com.codingpartner.codingpartnerbackend.mapper.QuestionMapper;
import com.codingpartner.codingpartnerbackend.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.codingpartner.codingpartnerbackend.constant.CommonConstant.EXIST_PARAM_NULL;
import static com.codingpartner.codingpartnerbackend.constant.QuestionConstant.*;

/**
 * @author zgh
 * @description 针对表【question(题目表)】的数据库操作Service实现
 * @createDate 2024-04-04 22:54:38
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    /**
     * 添加题目
     *
     * @param questionAddRequest 添加题目请求
     */
    @Override
    public void addQuestion(QuestionAddRequest questionAddRequest) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        // 设置其他值
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        List<String> language = questionAddRequest.getLanguage();
        if (language != null) {
            question.setLanguage(JSONUtil.toJsonStr(language));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        question.setSubmissionCount(QUESTION_DEFAULT_NUM);
        question.setAcceptedCount(QUESTION_DEFAULT_NUM);
        question.setThumbCount(QUESTION_DEFAULT_NUM);
        question.setFavourCount(QUESTION_DEFAULT_NUM);
        question.setCommentCount(QUESTION_DEFAULT_NUM);
        question.setUserId(BaseContext.getUserId());
        question.setCreateTime(LocalDateTime.now());
        question.setUpdateTime(LocalDateTime.now());
        // 校验参数
        questionValidate(question, true);
        int insert = questionMapper.insert(question);
        if (insert != 1) {
            ThrowUtils.throwIf(true, ErrorCode.OPERATION_ERROR, "添加题目失败");
        }
    }

    /**
     * 删除题目
     *
     * @param ids 题目ids
     */
    @Override
    public void deleteQuestion(Long[] ids) {
        Question question = new Question();
        for (Long id : ids) {
            question.setId(id);
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>(question);
            queryWrapper.eq("id", id).eq("is_delete", 0);
            Question ques = questionMapper.selectOne(queryWrapper);
            if (ques == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_NOT_EXIST);
            }
            question.setUpdateTime(LocalDateTime.now());
            question.setIsDelete(1);
            int update = questionMapper.updateById(question);
            if (update != 1) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, QUESTION_DELETE_FAILUER);
            }
        }
    }

    /**
     * 更新题目
     *
     * @param questionUpdateRequest 题目更新请求
     */
    @Override
    public void updateQuestion(QuestionUpdateRequest questionUpdateRequest) {

    }

    /**
     * 根据id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    @Override
    public Question getQuestionById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_NOT_EXIST);
        }
        return question;
    }

    /**
     * 根据id获取脱敏问题
     *
     * @param id 题目id
     * @return 脱敏问题
     */
    @Override
    public QuestionVO getQuestionByIdVO(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, QUESTION_NOT_EXIST);
        }
        return QuestionVO.objToVo(question);
    }

    /**
     * 分页查询题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 题目列表
     */
    @Override
    public Page<Question> getQuestionList(QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        String title = questionQueryRequest.getTitle();
        String difficulty = questionQueryRequest.getDifficulty();
        List<String> tags = questionQueryRequest.getTags();
        List<String> language = questionQueryRequest.getLanguage();
        String submissionOrder = questionQueryRequest.getSubmissionOrder();
        String acceptedOrder = questionQueryRequest.getAcceptedOrder();
        String thumbOrder = questionQueryRequest.getThumbOrder();
        String favourOrder = questionQueryRequest.getFavourOrder();
        String commentOrder = questionQueryRequest.getCommentOrder();
        long current = questionQueryRequest.getCurrent();
        long pageSize = questionQueryRequest.getPageSize();

        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        // 条件查询
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        if (CollUtil.isNotEmpty(language)) {
            for (String lan : language) {
                queryWrapper.like("language", "\"" + lan + "\"");
            }
        }
        if (StringUtils.isNotBlank(difficulty)) {
            queryWrapper.eq("difficulty", difficulty);
        }
        if (StringUtils.isNotBlank(submissionOrder)) {
            queryWrapper.orderBy(SqlUtil.validSortField(QUESTION_SUBMISSION_COUNT), submissionOrder.equals(CommonConstant.SORT_ORDER_ASC), QUESTION_SUBMISSION_COUNT);
        }
        if (StringUtils.isNotBlank(acceptedOrder)) {
            queryWrapper.orderBy(SqlUtil.validSortField(QUESTION_ACCEPTED_COUNT), acceptedOrder.equals(CommonConstant.SORT_ORDER_ASC), QUESTION_ACCEPTED_COUNT);
        }
        if (StringUtils.isNotBlank(thumbOrder)) {
            queryWrapper.orderBy(SqlUtil.validSortField(QUESTION_THUMB_COUNT), thumbOrder.equals(CommonConstant.SORT_ORDER_ASC), QUESTION_THUMB_COUNT);
        }
        if (StringUtils.isNotBlank(favourOrder)) {
            queryWrapper.orderBy(SqlUtil.validSortField(QUESTION_FAVOUR_COUNT), favourOrder.equals(CommonConstant.SORT_ORDER_ASC), QUESTION_FAVOUR_COUNT);
        }
        if (StringUtils.isNotBlank(commentOrder)) {
            queryWrapper.orderBy(SqlUtil.validSortField(QUESTION_COMMENT_COUNT), commentOrder.equals(CommonConstant.SORT_ORDER_ASC), QUESTION_COMMENT_COUNT);
        }
        queryWrapper.eq("is_delete", 0);
        return questionMapper.selectPage(new Page<>(current, pageSize), queryWrapper);
    }

    @Override
    public Page<QuestionVO> getQuestionVOList(QuestionQueryRequest questionQueryRequest) {
        Page<Question> questionList = getQuestionList(questionQueryRequest);
        Page<QuestionVO> questionVOList = new Page<>();
        List<QuestionVO> records = new ArrayList<>();
        for (Question question : questionList.getRecords()) {
            records.add(QuestionVO.objToVo(question));
        }
        questionVOList.setRecords(records);
        questionVOList.setTotal(questionList.getTotal());
        questionVOList.setSize(questionList.getSize());
        questionVOList.setCurrent(questionList.getCurrent());
        questionVOList.setPages(questionList.getPages());
        questionVOList.setOptimizeCountSql(questionList.optimizeCountSql());
        questionVOList.setOrders(questionList.orders());
        return questionVOList;
    }

    /**
     * 校验参数
     *
     * @param question
     * @param add
     */
    public void questionValidate(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        String title = question.getTitle();
        String tags = question.getTags();
        String content = question.getContent();
        String judgeConfig = question.getJudgeConfig();
        String judgeCase = question.getJudgeCase();
        String answer = question.getAnswer();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, tags, content, answer), ErrorCode.PARAMS_ERROR);
        }
        // 有参数时则校验参数
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_TITLE_TOO_LONG);
        }
        if (StringUtils.isNotBlank(tags) && tags.length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_TAGS_TOO_LONG);
        }
        if (StringUtils.isNotBlank(content) && content.length() > 5000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_CONTENT_TOO_LONG);
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 5000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_JUDGE_CONFIG_TOO_LONG);
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 5000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_JUDGE_CASE_TOO_LONG);
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 5000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, QUESTION_ANSWER_TOO_LONG);
        }
    }

}




