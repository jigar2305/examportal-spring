package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	public List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) {
		String mainpath = "D:\\SpringTool\\examportal\\src\\main\\resources\\subjectfiles";
		File folder = new File(mainpath, subject.getSubjectId() + "");
		List<SubjectFileBean> subjectFileBeans = new ArrayList<>();
		folder.mkdir();

		for (int i = 0; i < files.size(); i++) {
//			String name = files.get(i).getFileName().replace("pdf", "txt");
			String name = files.get(i).getFileName();
//			File newfile = new File(folder, name);
//			try {
//				FileWriter fos = new FileWriter(newfile);
//				FileOutputStream fos = new FileOutputStream(newfile);
////				System.out.println("infile");
////				System.out.println(decodeData);
////				output.write(decodeData);
////				output.close();
//				String b64 = subjectFileBeans.get(i).getFileString();
//				if(b64 != null) {					
//					byte[] decoder = Base64.getDecoder().decode(b64);
//					fos.write(decoder);
//					fos.close();
//					System.out.println("PDF File Saved");
//				}

				SubjectFileBean subjectFileBean = new SubjectFileBean();
				subjectFileBean.setFileString(name);
				subjectFileBean.setSubject(subject);
				subjectFileBean.setFileName(files.get(i).getFileName());
				subjectFileBeans.add(subjectFileBean);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			SubjectFileBean subjectFileBean2 = subjectFileRepo.findByFileName(files.get(i).getFileName());
			if(subjectFileBean2 == null) {
				files.get(i).setSubject(subject);
				System.out.println(files.get(i).getFileString());
			}else {
				files.remove(i);
			}

		}
		List<SubjectFileBean> returnfile = subjectFileRepo.saveAll(files);
		return returnfile;
	}

	public void getsubjectfiles(List<SubjectBean> subjectBeans) {
		List<PdfBean> pdffile = new ArrayList<>();
		List<SubjectFileBean> subjectfile = new ArrayList<>();
		for (int i = 0; i < subjectBeans.size(); i++) {
			subjectfile.addAll(subjectFileRepo.findBySubject(subjectBeans.get(i)));
		}
//		for (int i = 0; i < subjectfile.size(); i++) {
//			PdfBean pdfBean = new  PdfBean();
//			 pdfBean.setFileName(subjectfile.get(i).getFileName());
////			 Path path = Paths.get(subjectfile.get(i).getUrl());
//
//			    BufferedReader reader;
//				try {
//					reader = Files.newBufferedReader(path);
//					String line = reader.readLine();
//					System.out.println(line);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//		}

	}

	public void getallsubjectfiles() {
		List<SubjectFileBean> subjectfile = subjectFileRepo.findAll();

	}
}
