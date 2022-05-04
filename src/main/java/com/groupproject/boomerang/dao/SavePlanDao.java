package com.groupproject.boomerang.dao;

import com.groupproject.boomerang.model.pojo.Plan;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SavePlanDao {

    @Autowired
    SessionFactory sessionFactory;

    public void addPlan(Plan plan)
    {
        Session session = null;

        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.saveOrUpdate(plan);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assert session!=null;
            session.getTransaction().rollback();
        }
        finally {
            if(session!=null)
            {
                session.close();
            }
        }
    }

    /*** 是否是 eagerly fetched ***/
    public List<Plan> getPlanByUserId(long userID)
    {
        List<Plan> planResults = new ArrayList<Plan>();

        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            //   Criteria criteria = session.createCriteria(Plan.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Plan> query = builder.createQuery(Plan.class);
            Root<Plan> root = query.from(Plan.class);
            query.select(root).where(builder.equal(root.get("user_id"),userID));

            planResults = session.createQuery(query).getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return planResults;
    }

    /*** 1. 是否是 eagerly fetched
     *   2. 是否要create username 的 index ***/

    public List<Plan> getPlanByUserName(String userName) {

        List<Plan> planResults = new ArrayList<Plan>();

        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();

            //   Criteria criteria = session.createCriteria(Plan.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Plan> query = builder.createQuery(Plan.class);
            Root<Plan> root = query.from(Plan.class);
            query.select(root).where(builder.equal(root.get("userName"),userName));

            planResults = session.createQuery(query).getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return planResults;
    }
}
