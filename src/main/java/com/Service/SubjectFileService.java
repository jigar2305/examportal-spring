package com.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Bean.EnroleSubjectFilesBean;
import com.Bean.ResponseBean;
import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;

@Service
public interface SubjectFileService {
	
	List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) throws IOException;
	
	ResponseBean<?> sendToUser(EnroleSubjectFilesBean filesBean)throws Exception;
	
	ResponseBean<?> getAllFileForUser(Integer userId) throws IOException;
	
	ResponseBean<?> getFile(Integer subjectfileId) throws IOException;

}
