package com.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
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
//		String mainpath = "D:\\SpringTool\\examportal\\src\\main\\resources\\subjectfiles";
//		File folder = new File(mainpath, subject.getSubjectId() + "");
//		List<SubjectFileBean> subjectFileBeans = new ArrayList<>();
//		folder.mkdir();
//
//		for (int i = 0; i < files.size(); i++) {
//			String name = files.get(i).getFileName();
//			SubjectFileBean subjectFileBean = new SubjectFileBean();
//			subjectFileBean.setFileString(name);
//			subjectFileBean.setSubject(subject);
//			subjectFileBean.setFileName(files.get(i).getFileName());
//			subjectFileBeans.add(subjectFileBean);
//			SubjectFileBean subjectFileBean2 = subjectFileRepo.findByFileName(files.get(i).getFileName());
//			if (subjectFileBean2 == null) {
//				files.get(i).setSubject(subject);
//				System.out.println(files.get(i).getFileString());
//			} else {
//				files.remove(i);
//			}
//
//		}
//		List<SubjectFileBean> returnfile = subjectFileRepo.saveAll(files);
//		return returnfile;
//	}

	public List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) throws IOException {
		String mainpath = "D:\\SpringTool\\examportal\\src\\main\\resources\\subjectfiles";
		File folder = new File(mainpath, subject.getSubjectId() + "");
		folder.mkdir();
		for (int i = 0; i < files.size(); i++) {
			SubjectFileBean subjectFileBean = subjectFileRepo.findByFileNameAndSubject(files.get(i).getFileName(),
					subject);
			if (subjectFileBean == null) {
				String name = files.get(i).getFileName();

				String filestring = files.get(i).getFileString().replaceAll("data:application/pdf;base64,", "");
				File newfile = new File(folder, name);
				byte[] data = DatatypeConverter.parseBase64Binary(filestring);
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newfile));
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
				String filename = name.replace(".pdf", "");
				files.get(i).setFileName(filename);
				files.get(i).setUrl("src/main/resources/subjectfiles/" + subject.getSubjectId() + "/" + filename);
				files.get(i).setFileString(null);
				files.get(i).setSubject(subject);
				File newFile = new File(
						"src/main/resources/subjectfiles/" + subject.getSubjectId() + "/" + filename + ".pdf");
				PDDocument pdfDocument = PDDocument.load(newFile);

				PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
				BufferedImage img = pdfRenderer.renderImage(0);
				ImageIO.write(img, "JPEG", new File(folder, filename + ".png"));
				pdfDocument.close();
			} else {
				files.remove(i);
			}

		}
		List<SubjectFileBean> returnfile = subjectFileRepo.saveAll(files);
		return returnfile;
	}

}
