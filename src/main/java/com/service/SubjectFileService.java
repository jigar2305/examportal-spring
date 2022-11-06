package com.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;
import com.repository.SubjectFileRepository;

@Service
public class SubjectFileService {

	@Autowired
	SubjectFileRepository subjectFileRepo;

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
				files.get(i).setUrl("src/main/resources/subjectfiles/"+ subject.getSubjectId() + "/" + filename);
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
