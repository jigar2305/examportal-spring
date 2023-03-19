package com.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Bean.EnroleSubjectFilesBean;
import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;

@Service
public interface SubjectFileService {

	List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) throws IOException;

	Object sendToUser(EnroleSubjectFilesBean filesBean) throws Exception;

	Object getAllFileForUser(Integer userId) throws IOException;

	Object getFile(Integer subjectfileId) throws IOException;

	public byte[] getImage(String URL) throws IOException;

}
