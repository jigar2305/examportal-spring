package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.forms.PdfBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;
import com.repository.SubjectFileRepository;

@Service
public class SubjectFileService {

	@Autowired
	SubjectFileRepository subjectFileRepo;

//	public List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) {
//		System.out.println("----------------------------------------------------------");
//		for (int i = 0; i < files.size(); i++) {
//			SubjectFileBean file = subjectFileRepo.findByFileName(files.get(i).getFileName());
//			System.out.println(files.get(i).getFileString().length());
//			if(file != null) {
//				files.remove(i);
//			}else {
//				files.get(i).setSubject(subject);
//			}
//		}
//		List<SubjectFileBean> returnfile = subjectFileRepo.saveAll(files);
//		return returnfile;
//	}

	public List<SubjectFileBean> addfiles(List<PdfBean> files, SubjectBean subject) {
		int subjectId = (subject.getSubjectId());
		String mainpath = "D:\\SpringTool\\examportal\\src\\main\\resources\\subjectfiles";
		File folder = new File(mainpath, subjectId + "");
		List<SubjectFileBean> subjectFileBeans = new ArrayList<>();
		folder.mkdir();
		int num = files.size();
		for (int i = 0; i < num; i++) {
			String name = files.get(i).getFileName().replace("pdf", "txt");
			File newfile = new File(folder, name);
			try {
				FileWriter fos = new FileWriter(newfile);
				fos.write(files.get(i).getFileString());
				fos.close();
				SubjectFileBean subjectFileBean = new SubjectFileBean();
				subjectFileBean.setUrl("resources/subjectfiles/" + subjectId + "/" + name);
				subjectFileBean.setSubject(subject);
				subjectFileBean.setFileName(files.get(i).getFileName());
				subjectFileBeans.add(subjectFileBean);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		List<SubjectFileBean> returnfile = subjectFileRepo.saveAll(subjectFileBeans);
		return returnfile;
	}
}
