package com.codingpartner.codingpartnerbackend.constant;

public interface QuestionConstant {
    Integer QUESTION_DEFAULT_NUM = 0;

    String QUESTION_TITLE_TOO_LONG = "题目标题过长";

    String QUESTION_TAGS_TOO_LONG = "标签过长";

    String QUESTION_CONTENT_TOO_LONG = "题目内容过长";

    String QUESTION_JUDGE_CONFIG_TOO_LONG = "判题配置过长";

    String QUESTION_JUDGE_CASE_TOO_LONG = "判题用例过长";

    String QUESTION_ANSWER_TOO_LONG = "答案过长";

    String QUESTION_CREATE_SUCCESS = "创建成功";

    String QUESTION_UPDATE_SUCCESS = "更新成功";

    String QUESTION_DELETE_SUCCESS = "删除成功";
    String QUESTION_DELETE_FAILUER = "删除失败";

    String QUESTION_NOT_FOUND = "未找到该题目";

    String QUESTION_NOT_EXIST = "该题目不存在";

    String QUESTION_ALREADY_EXIST = "该题目已存在";

    String QUESTION_SUBMISSION_COUNT = "submission_count";

    String QUESTION_ACCEPTED_COUNT = "accepted_count";

    String QUESTION_THUMB_COUNT = "thumb_count";

    String QUESTION_FAVOUR_COUNT = "favour_count";

    String QUESTION_COMMENT_COUNT = "comment_count";
}
