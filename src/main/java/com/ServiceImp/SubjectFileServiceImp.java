package com.ServiceImp;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.EnroleSubjectFilesBean;
import com.Bean.ResponseBean;
import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;
import com.Entity.UserBean;
import com.Repositoy.SubjectFileRepository;
import com.Repositoy.SubjectRepository;
import com.Repositoy.UserRepository;
import com.Service.SubjectFileService;

@Service
public class SubjectFileServiceImp implements SubjectFileService {

	@Autowired
	SubjectFileRepository subjectFileRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Override
	public List<SubjectFileBean> addfiles(List<SubjectFileBean> files, SubjectBean subject) throws IOException {
		String mainpath = "D:\\Exam-Portal-Spring\\src\\main\\resources\\subjectfiles";
		File folder = new File(mainpath, subject.getSubjectId() + "");
		folder.mkdir();
		for (int i = 0; i < files.size(); i++) {
			SubjectFileBean subjectFileBean = subjectFileRepo.findByFileNameAndSubject(files.get(i).getFileName(),
					subject);
			if (subjectFileBean == null) {
				String name = files.get(i).getFileName();
				String filestring = files.get(i).getFileString().replace("data:application/pdf;base64,", "");
				File newfile = new File(folder, name);
				byte[] data = DatatypeConverter.parseBase64Binary(filestring);
				try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newfile));) {
					outputStream.write(data);
					outputStream.flush();
				}
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
		return subjectFileRepo.saveAll(files);

	}

	@Override
	public ResponseBean<?> sendToUser(EnroleSubjectFilesBean filesBean) throws Exception {
		for (int i = 0; i < filesBean.getSubjectId().size(); i++) {
			SubjectBean subject = subjectRepo.findBySubjectId(filesBean.getSubjectId().get(i));
			if (subject != null) {
				for (int j = 0; j < filesBean.getUserId().size(); j++) {
					UserBean user = userRepo.findByUserId(filesBean.getUserId().get(j));
					if (user != null) {
						subject.getUsers().add(user);
					}
				}
				subjectRepo.save(subject);
			}
		}
		System.out.println("hello");
		ResponseBean<Boolean> res = new ResponseBean<>();
		res.setData(true);
		res.setMsg("files successfully send to users");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> getAllFileForUser(Integer userId) throws IOException {
		UserBean user = userRepo.findByUserId(userId);
		if (user != null) {

			List<SubjectBean> subjects = subjectRepo.findByUsers(user);
			List<SubjectFileBean> files = new ArrayList<>();
			for (int i = 0; i < subjects.size(); i++) {
				if (subjectFileRepo.findBySubject(subjects.get(i)) != null) {
					files.addAll(subjectFileRepo.findBySubject(subjects.get(i)));
				}
			}
			for (SubjectFileBean subjectFileBean : files) {
				byte[] image = Files.readAllBytes(new File(subjectFileBean.getUrl() + ".png").toPath());
				subjectFileBean.setPdfimage(image);
				subjectFileBean.setUrl(null);
			}
			ResponseBean<List<SubjectFileBean>> res = new ResponseBean<>();
			res.setData(files);
			res.setMsg("files Get successfully");
			res.setApicode(200);
			return res;
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("User Not Found");
			res.setApicode(404);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getFile(Integer subjectfileId) throws IOException {
		SubjectFileBean file = subjectFileRepo.findBySubjectfileId(subjectfileId);
		if (file == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectfileId);
			res.setMsg("file not found");
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<SubjectFileBean> res = new ResponseBean<>();
			byte[] pdf = Files.readAllBytes(new File(file.getUrl() + ".pdf").toPath());
			String base64EncodedImageBytes = Base64.getEncoder().encodeToString(pdf);
			file.setFileString(base64EncodedImageBytes);
			file.setUrl(null);
			res.setData(file);
			res.setMsg(file.getFileName() + "get successfully");
			res.setApicode(200);
			return res;
		}
	}

}
