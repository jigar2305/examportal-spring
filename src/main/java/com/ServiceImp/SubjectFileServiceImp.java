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
	public Object sendToUser(EnroleSubjectFilesBean filesBean) throws Exception {
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
		return new ResponseBean(true, "files successfully send to users", 200);
	}

	@Override
	public Object getAllFileForUser(Integer userId) throws IOException {
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
			return new ResponseBean(files, "files Get successfully", 200);
		} else {
			return new ResponseBean(userId, "User Not Found", 404);
		}
	}

	@Override
	public Object getFile(Integer subjectfileId) throws IOException {
		SubjectFileBean file = subjectFileRepo.findBySubjectfileId(subjectfileId);
		if (file == null) {
			return new ResponseBean(subjectfileId, "file not found", 404);
		} else {
			byte[] pdf = Files.readAllBytes(new File(file.getUrl() + ".pdf").toPath());
			String base64EncodedImageBytes = Base64.getEncoder().encodeToString(pdf);
			file.setFileString(base64EncodedImageBytes);
			file.setUrl(null);
			return new ResponseBean(file, file.getFileName() + "get successfully", 200);
		}
	}

	public byte[] getImage(String URL) throws Exception {

		byte[] image;
		try {
			image = Files.readAllBytes(new File(URL).toPath());
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
