package com.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bean.forms.PdfBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;
import com.bean.forms.SubjectFileBean2;
import com.repository.SubjectFileRepository;

@Service
public class SubjectFileService {
	
	@Autowired
	SubjectFileRepository subjectFileRepo;

	public void addfiles(List<PdfBean> files, SubjectBean subject) {
		System.out.println("----------------------------------------------------------");
		int subjectId = (subject.getSubjectId());
		String mainpath="D:\\SpringTool\\examportal\\src\\main\\resources\\subjectfiles";
		File folder = new File(mainpath,subjectId+"");
		folder.mkdir();
		int num = subjectfile.length;
		for (int i = 0; i < num; i++) {
			File newfile = new File(folder,subjectfile[i].getOriginalFilename());
			try {
				byte []b = subjectfile[i].getBytes();
				FileOutputStream fos = new FileOutputStream(newfile);
				fos.write(b);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			SubjectFileBean subjectFileBean = new SubjectFileBean();
			subjectFileBean.setUrl("resources/images/"+subjectId+"/"+subjectfile[i].getOriginalFilename());
			subjectFileBean.setSubject(subject);
			subjectFileRepo.save(subjectFileBean);
		}
		
	}

}
