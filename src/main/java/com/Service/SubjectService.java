package com.Service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.Bean.Getsubjectfile;
import com.Bean.ResponseBean;

@Service
public interface SubjectService {
	
	ResponseBean<?> addSubject(Getsubjectfile subjectfile) throws IOException;
	
	ResponseBean<?> updateSubject(Getsubjectfile subjectfile) throws IOException;
    
    ResponseBean<?> listSubject();
    
    ResponseBean<?> deleteSubject(Integer subjectId);
    
    ResponseBean<?> findSubjectById(Integer subjectId);
    
    ResponseBean<?> checkForDelete(Integer subjectId);
    
    ResponseBean<?> getSubjectFile(Integer subjectId);
    
    ResponseBean<?> deleteFile(Integer subjectfileId);
    


}
