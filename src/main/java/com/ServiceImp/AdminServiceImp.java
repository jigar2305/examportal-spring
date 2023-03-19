package com.ServiceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;
import com.Entity.ExamBean;
import com.Entity.ResultBean;
import com.Entity.SubjectBean;
import com.Entity.UserBean;
import com.Repositoy.CustomNativeRepository;
import com.Repositoy.ExamRepository;
import com.Repositoy.ResultRepository;
import com.Repositoy.SubjectRepository;
import com.Repositoy.UserRepository;
import com.Service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	ExamRepository examRepo;

	@Autowired
	ResultRepository resultRepo;

	@Autowired
	CustomNativeRepository customNativeRepo;

	@Override
	public Object listUser() throws Exception {
		List<UserBean> users = userRepo.findAll();
		return new ResponseBean<>(users, "users fetch successfully", 200);
	}

	@Override
	public Object deleteUser(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		try {
			List<ResultBean> result = resultRepo.findByUser(user);
//			List<SubjectBean> subject = subjectRepo.findByUsers(user);
//			List<ExamBean> exam = examRepo.findByUsers(user);
//			if (subject != null && !subject.isEmpty()) {
////				for (int i = 0; i < subject.size(); i++) {
////					subject.get(i).getUsers().remove(user);
////				}
////				subjectRepo.saveAll(subject);
////				subjectRepo.deleteenrolesubjectuser(userId);
//			}
//			if (exam != null && !exam.isEmpty()) {
//				for (int i = 0; i < exam.size(); i++) {
//					exam.get(i).getUsers().remove(user);
//					customNativeRepo.deleteenroleexam(exam.get(i).getExamId(), userId);
////					examRepo.save(exam.get(i));
//				}
//			}
			customNativeRepo.deleteenroleexambyuser(userId);
			customNativeRepo.deleteenrolesubjectByuser(userId);
			if (result != null && !result.isEmpty()) {
				for (int i = 0; i < result.size(); i++) {
					resultRepo.delete(result.get(i));
				}
			}
			userRepo.deleteById(userId);
		} catch (Exception e) {
			return new ResponseBean<>(e, "Technical error occoured", 400);
		}
		return new ResponseBean<>(userId, "user deleted successfully", 200);
	}

	@Override
	public Object checkForDelete(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			return new ResponseBean<>(userId, "user not found", 404);
		} else {
			try {
				List<SubjectBean> subject = subjectRepo.findByUsers(user);
				List<ExamBean> exam = examRepo.findByUsers(user);
				if (subject == null && exam == null) {
					return new ResponseBean<>(userId, "No dependency found", 200);
				} else {
					Map<String, List<?>> ress = new HashMap<>();
					ress.put("exam", exam);
					ress.put("subject", subject);
					return new ResponseBean<>(ress, null, 200);

				}
			} catch (Exception e) {
				return new ResponseBean<>(e, "Technical error occoured", 500);
			}
		}
	}

	@Override
	public Object isActive(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			return new ResponseBean<>(userId, "User Not Found", 404);
		} else {
			if (user.getActive().booleanValue()) {
				user.setActive(false);
			} else {
				user.setActive(true);
			}
			userRepo.save(user);
			return new ResponseBean<>(user.getActive(), "status updated successfully", 200);
		}
	}

	@Override
	public Object findUserById(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		if (user != null) {
			return new ResponseBean<>(user, "get user successfully", 200);
		} else {
			return new ResponseBean<>(userId, "User Not Found", 404);
		}
	}

}
