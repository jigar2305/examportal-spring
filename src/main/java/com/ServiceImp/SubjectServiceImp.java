package com.ServiceImp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.Getsubjectfile;
import com.Bean.ResponseBean;
import com.Entity.QuestionBean;
import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;
import com.Repositoy.CustomNativeRepository;
import com.Repositoy.QuestionRepository;
import com.Repositoy.SubjectFileRepository;
import com.Repositoy.SubjectRepository;
import com.Service.SubjectFileService;
import com.Service.SubjectService;

@Service
public class SubjectServiceImp implements SubjectService {

	private static final String TECHNICAL_ERROR = "Technical error occurred";
	private static final String NOT_FOUND = "Subject not Found.";

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	SubjectFileService subjectFileService;

	@Autowired
	SubjectFileRepository fileRepo;

	@Autowired
	CustomNativeRepository customNativeRepo;

	@Override
	public Object addSubject(Getsubjectfile subjectfile) throws IOException {
		SubjectBean subject = subjectfile.getSubject();
		List<SubjectFileBean> files = subjectfile.getFiles();

		SubjectBean subjectBean = subjectRepo.findBySubjectName(subject.getSubjectName());

		if (subjectBean == null) {

			try {
				SubjectBean subjectres = subjectRepo.save(subject);
				List<SubjectFileBean> rsfile = subjectFileService.addfiles(files, subjectres);
				if (rsfile == null) {
					subjectRepo.deleteById(subjectres.getSubjectId());
					return new ResponseBean(subjectBean, TECHNICAL_ERROR, 500);
				} else {
					return new ResponseBean(subjectres, "subject added successfully", 200);
				}
			} catch (Exception e) {
				e.printStackTrace();
				subjectRepo.delete(subjectRepo.findBySubjectName(subject.getSubjectName()));
				return new ResponseBean(e, TECHNICAL_ERROR, 500);
			}
		} else {
			return new ResponseBean(subjectBean, "subject Already exist", 404);
		}
	}

	@Override
	public Object updateSubject(Getsubjectfile subjectfile) throws IOException {
		SubjectBean subject = subjectfile.getSubject();
		List<SubjectFileBean> files = subjectfile.getFiles();
		SubjectBean subjectres = subjectRepo.save(subject);
		try {
			List<SubjectFileBean> rsfile = subjectFileService.addfiles(files, subjectres);
			if (rsfile == null) {
				subjectRepo.deleteById(subjectres.getSubjectId());
				return new ResponseBean(subjectfile.getSubject(), TECHNICAL_ERROR, 500);
			} else {
				return new ResponseBean(subjectres, subject.getSubjectName() + "Updated Successfully", 200);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseBean(subjectfile.getSubject(), TECHNICAL_ERROR, 500);
		}
	}

	@Override
	public Object listSubject() {
		List<SubjectBean> subjects = (List<SubjectBean>) subjectRepo.findAll();
		return new ResponseBean(subjects, "Subject listed successfully", 200);
	}

	@Override
	public Object deleteSubject(Integer subjectId) {
		SubjectBean subject = subjectRepo.findBySubjectId(subjectId);
		if (subject == null) {
			return new ResponseBean(subjectId, NOT_FOUND, 404);
		} else {
			customNativeRepo.deleteenrolesubjectBysubject(subjectId);
			List<QuestionBean> questions = questionRepo.findBySubject(subject);
			if (questions != null) {
				for (int i = 0; i < questions.size(); i++) {
					questionRepo.deleteById(questions.get(i).getQuestionId());
				}
			}
			subjectRepo.deleteById(subjectId);
			return new ResponseBean(subjectId, subject.getSubjectName() + " deleted successfully", 200);
		}
	}

	@Override
	public Object findSubjectById(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			return new ResponseBean(subjectId, NOT_FOUND, 404);
		} else {
			return new ResponseBean(subject, "get subject successfully", 200);
		}
	}

	@Override
	public Object checkForDelete(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			return new ResponseBean(subjectId, NOT_FOUND, 404);
		} else {
			List<QuestionBean> questionBeans = questionRepo.findBySubject(subject);
			return new ResponseBean(questionBeans.size(), "get question successfully", 200);
		}
	}

	@Override
	public Object getSubjectFile(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			return new ResponseBean(subjectId, NOT_FOUND, 404);
		} else {
			List<SubjectFileBean> subjectfile = fileRepo.findBySubject(subject);
			for (SubjectFileBean subjectFileBean : subjectfile) {
				subjectFileBean.setFileString(null);
				subjectFileBean.setUrl(null);
			}
			return new ResponseBean(subjectfile, "get file successfully", 200);
		}
	}

	@Override
	public Object deleteFile(Integer subjectfileId) {
		try {
			SubjectFileBean fileBean = fileRepo.getReferenceById(subjectfileId);
			if (fileBean != null) {
				fileRepo.delete(fileBean);
				return new ResponseBean(subjectfileId, fileBean.getFileName() + "  deleted successfully", 200);
			} else {
				return new ResponseBean(subjectfileId, "file not found", 404);
			}
		} catch (Exception e) {
			return new ResponseBean(e, TECHNICAL_ERROR, 500);
		}
	}

}
