package com.Service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.Bean.Getsubjectfile;

@Service
public interface SubjectService {
	
	Object addSubject(Getsubjectfile subjectfile) throws IOException;
	
	Object updateSubject(Getsubjectfile subjectfile) throws IOException;
    
    Object listSubject();
    
    Object deleteSubject(Integer subjectId);
    
    Object findSubjectById(Integer subjectId);
    
    Object checkForDelete(Integer subjectId);
    
    Object getSubjectFile(Integer subjectId);
    
    Object deleteFile(Integer subjectfileId);
    


}
