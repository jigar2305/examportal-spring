package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;

@Service
public interface ResultService {

	ResponseBean<?> getResultsById(Integer userId);

	ResponseBean<?> getResultById(Integer resultId);

	ResponseBean<?> getAllQuestionByExamIdAndUserId(Integer userId, Integer examId);

	ResponseBean<?> getAllResultByExamId(Integer examId);

}
