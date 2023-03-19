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
					return new ResponseBean<>(subjectBean, TECHNICAL_ERROR, 500);
				} else {
					return new ResponseBean<>(subjectres, "subject added successfully", 200);
				}
			} catch (Exception e) {
				e.printStackTrace();
				subjectRepo.delete(subjectRepo.findBySubjectName(subject.getSubjectName()));
				return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
			}
		} else {
			return new ResponseBean<>(subjectBean, "subject Already exist", 404);
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
				return new ResponseBean<>(subjectfile.getSubject(), TECHNICAL_ERROR, 500);
			} else {
				return new ResponseBean<>(subjectres, subject.getSubjectName() + "Updated Successfully", 200);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseBean<>(subjectfile.getSubject(), TECHNICAL_ERROR, 500);
		}
	}

	@Override
	public Object listSubject() {
		List<SubjectBean> subjects = (List<SubjectBean>) subjectRepo.findAll();
		ResponseBean<List<SubjectBean>> res = new ResponseBean<>();
		res.setData(subjects);
		res.setMsg("Subject listed successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public Object deleteSubject(Integer subjectId) {
		SubjectBean subject = subjectRepo.findBySubjectId(subjectId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (subject == null) {
			res.setData(subjectId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			customNativeRepo.deleteenrolesubjectBysubject(subjectId);
			List<QuestionBean> questions = questionRepo.findBySubject(subject);
			if (questions != null) {
				for (int i = 0; i < questions.size(); i++) {
					questionRepo.deleteById(questions.get(i).getQuestionId());
				}
			}
			subjectRepo.deleteById(subjectId);
			res.setData(subjectId);
			res.setMsg(subject.getSubjectName() + " deleted successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public Object findSubjectById(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<Optional<SubjectBean>> res = new ResponseBean<>();
			res.setData(subject);
			res.setMsg("get subject successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public Object checkForDelete(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			List<QuestionBean> questionBeans = questionRepo.findBySubject(subject);
			res.setData(questionBeans.size());
			res.setMsg("get question successfully");
			return res;
		}
	}

	@Override
	public Object getSubjectFile(Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<List<SubjectFileBean>> res = new ResponseBean<>();
			List<SubjectFileBean> subjectfile = fileRepo.findBySubject(subject);
			for (SubjectFileBean subjectFileBean : subjectfile) {
				subjectFileBean.setFileString(null);
				subjectFileBean.setUrl(null);
			}
			res.setData(subjectfile);
			res.setMsg("get file successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public Object deleteFile(Integer subjectfileId) {
		ResponseBean<Integer> res = new ResponseBean<>();
		try {
			SubjectFileBean fileBean = fileRepo.getReferenceById(subjectfileId);
			if (fileBean != null) {
				fileRepo.delete(fileBean);
				res.setData(subjectfileId);
				res.setMsg(fileBean.getFileName() + "  deleted successfully");
				res.setApicode(200);
				return res;
			} else {
				res.setData(subjectfileId);
				res.setMsg("file not found");
				res.setApicode(404);
				return res;
			}
		} catch (Exception e) {
			return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
		}
	}

}
