/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeCase } from './JudgeCase';
import type { JudgeConfig } from './JudgeConfig';
export type QuestionUpdateRequest = {
    acceptedCount?: number;
    answer?: string;
    commentCount?: number;
    content?: string;
    difficulty?: string;
    favourCount?: number;
    id?: number;
    judgeCase?: Array<JudgeCase>;
    judgeConfig?: JudgeConfig;
    language?: Array<string>;
    submissionCount?: number;
    tags?: Array<string>;
    thumbCount?: number;
    title?: string;
    userId?: number;
};

