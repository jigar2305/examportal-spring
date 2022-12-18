package com.Repositoy;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomNativeRepository {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public void deleteenroleexam(Integer exam_id,Integer user_id) {
        Query query = entityManager.createNativeQuery("delete from enroleexam e where user_id = ? and exam_id = ?");
        query.setParameter(1, user_id);
        query.setParameter(2, exam_id);
        query.executeUpdate();
    }
    
    @Transactional
    public void deleteenrolesubject(Integer subject_id,Integer user_id) {
        Query query = entityManager.createNativeQuery("delete from enrolesubject e where user_id = ? and subject _id = ?");
        query.setParameter(1, user_id);
        query.setParameter(2, subject_id);
        query.executeUpdate();
    }
    
    @Transactional
    public void deleteenrolesubjectByuser(Integer user_id) {
        Query query = entityManager.createNativeQuery("delete from enrolesubject e where user_id = ?");
        query.setParameter(1, user_id);
        query.executeUpdate();
    }
    
    @Transactional
    public void deleteenrolesubjectBysubject(Integer subject_id) {
        Query query = entityManager.createNativeQuery("delete from enrolesubject e where subject_id = ?");
        query.setParameter(1, subject_id);
        query.executeUpdate();
    }
    
    @Transactional
    public void deleteenroleexambyexam(Integer exam_id) {
        Query query = entityManager.createNativeQuery("delete from enroleexam e where exam_id = ?");
        query.setParameter(1, exam_id);
        query.executeUpdate();
    }
    
    @Transactional
    public void deleteenroleexambyuser(Integer user_id) {
        Query query = entityManager.createNativeQuery("delete from enroleexam e where user_id = ?");
        query.setParameter(1, user_id);
        query.executeUpdate();
    }
}