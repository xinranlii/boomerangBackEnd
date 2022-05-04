package com.groupproject.boomerang.dao;

import com.groupproject.boomerang.model.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void register(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

//            String password = user.getPassword();
//            password = encryptPassword(String.valueOf(user.getId()), password);
//            user.setPassword(password);
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            assert session != null;
            session.getTransaction().rollback();
            throw e;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    // by userName 没想好 前端 是怎么call //long id)
    public User getUserById(long id){
        User user;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"), id)); // userName
            user = session.createQuery(criteriaQuery).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    // by userName 没想好 前端 是怎么call //long id)
    public User getUserByUserName(String userName){
        User user;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(builder.equal(root.get("userName"), userName)); // userName
            //user = session.createQuery(criteriaQuery).getSingleResult();

            user = (User) session.createQuery(criteriaQuery).getSingleResult();
        }catch (Exception e){ // 没有 应该catch 住 NOResult Exception
            //e.printStackTrace();
            return null;
            //throw e;
        }
        return user;
    }

    public static String encryptPassword(String userId, String password) throws IOException
    {
        return DigestUtils.md5Hex(userId + DigestUtils.md5Hex(password)).toLowerCase();
        // salt 防 反查md5 值  即使密码 一样 userId 不一样 ， 防彩虹字典

    }
}
