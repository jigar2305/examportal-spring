package com.Service;

import org.springframework.stereotype.Service;

@Service
public interface ResultService {

	Object getResultsById(Integer userId) throws Exception;

	Object getResultById(Integer resultId) throws Exception;

	Object getAllQuestionByExamIdAndUserId(Integer userId, Integer examId) throws Exception;

	Object getAllResultByExamId(Integer examId) throws Exception;

}
