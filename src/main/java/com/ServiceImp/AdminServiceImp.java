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

	@Override
	public ResponseBean<?> listUser() {
		List<UserBean> users = userRepo.findAll();
		ResponseBean<List<UserBean>> res = new ResponseBean<>();
		res.setData(users);
		res.setMsg("users fetch successfully..");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> deleteUser(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		try {
			List<SubjectBean> subject = subjectRepo.findByUsers(user);
			List<ExamBean> exam = examRepo.findByUsers(user);
			List<ResultBean> result = resultRepo.findByUser(user);
			if (subject != null) {
				for (int i = 0; i < subject.size(); i++) {
					subject.get(i).getUsers().remove(user);
				}
				subjectRepo.saveAll(subject);
			}
			if (exam != null && !exam.isEmpty()) {
				for (int i = 0; i < exam.size(); i++) {
					exam.get(i).getUsers().remove(user);
					examRepo.save(exam.get(i));
				}
			}
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					resultRepo.delete(result.get(i));
				}
			}
			userRepo.deleteById(userId);
		} catch (Exception e) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(500);
			res.setMsg("Technical error occoured");
			res.setApicode(400);
			return res;
		}
		ResponseBean<Integer> res = new ResponseBean<>();
		res.setData(userId);
		res.setMsg("user deleted successfully..");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> checkForDelete(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("user not found");
			res.setApicode(404);
			return res;
		} else {
			try {
				List<SubjectBean> subject = subjectRepo.findByUsers(user);
				List<ExamBean> exam = examRepo.findByUsers(user);

				if (subject == null && exam == null) {
					ResponseBean<Integer> res = new ResponseBean<>();
					res.setData(userId);
					res.setMsg("No dependency found");
					res.setApicode(200);
					return res;
				} else {
					ResponseBean<Map<String, List<?>>> res = new ResponseBean<>();
					Map<String, List<?>> ress = new HashMap<>();
					ress.put("exam", exam);
					ress.put("subject", subject);
					res.setData(ress);
					res.setMsg("Subject And exam found for user");
					res.setApicode(200);
					return res;
				}
			} catch (Exception e) {
				ResponseBean<Integer> res = new ResponseBean<>();
				res.setData(500);
				res.setMsg("Technical error occoured");
				res.setApicode(500);
				return res;
			}
		}
	}

	@Override
	public ResponseBean<?> isActive(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("User Not Found..");
			res.setApicode(404);
			return res;
		} else {
			if (user.getActive().booleanValue()) {
				user.setActive(false);
			} else {
				user.setActive(true);
			}
			userRepo.save(user);
			ResponseBean<Boolean> res = new ResponseBean<>();
			res.setData(user.getActive());
			res.setMsg("status updated successfully..");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> findUserById(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		if (user != null) {
			ResponseBean<UserBean> res = new ResponseBean<>();
			res.setData(user);
			res.setMsg("get user successfully..");
			res.setApicode(200);
			return res;
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("User Not Found..");
			res.setApicode(404);
			return res;
		}
	}

}
