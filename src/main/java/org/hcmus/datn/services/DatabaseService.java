package org.hcmus.datn.services;

import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.temporal.model.request.Submission;
import org.hcmus.datn.utils.HibernateUtils;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Date;

public class DatabaseService  {//implements Runnable {
    private ScannerService scannerService;
    private Project project;
    private Thread t;
    public DatabaseService(ScannerService scannerService, Project project) {
        this.scannerService = scannerService;
        this.project = project;
    }

    public static Project createProject(Project project){
        SessionFactory factory = HibernateUtils.getSessionFactory();

        Session session = factory.getCurrentSession();
        Project savedProject = null;

        try {
            session.getTransaction().begin();

            project.setCreatedAt(new Date());
            project.setUpdatedAt(new Date());

            session.save(project);
            session.getTransaction().commit();

            savedProject = project;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }

        return savedProject;
    }

    public static Project findProjectById(String id){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Project project = null;
        try {
            session.getTransaction().begin();

            String sql = "Select project from " + Project.class.getName() + " project "
                    + "where project.id = :id";

            System.out.println(sql);

            // Tạo đối tượng Query.
            Query<Project> query = session.createQuery(sql);
            query.setParameter("id", id);

            // Thực hiện truy vấn.
            project = query.getSingleResultOrNull();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return project;
    }
    public static Submission findSubmsisonById(String id){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Submission submisison = null;
        try {
            session.getTransaction().begin();

            String sql = "Select submission from " + Submission.class.getName() + " submission "
                    + "where submission.id = :id";

            // Tạo đối tượng Query.
            Query<Submission> query = session.createQuery(sql);
            query.setParameter("id", id);

            // Thực hiện truy vấn.
            submisison = query.getSingleResultOrNull();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return submisison;
    }

    public static Project findProjectByKey(String key){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Project project = null;
        try {
            session.getTransaction().begin();

            String sql = "Select project from " + Project.class.getName() + " project "
                    + "where project.key = :key";

            // Tạo đối tượng Query.
            Query<Project> query = session.createQuery(sql);
            query.setParameter("key", key);

            // Thực hiện truy vấn.
            project = query.getSingleResultOrNull();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return project;
    }
    public static ResponseObject upsertProject(Project project) throws InterruptedException {
        Project savedProject = DatabaseService.findProjectByKey(project.getKey());
        if (savedProject == null){
            DatabaseService.createProject(project);
        }
        return ResponseObject.SUCCESS;
    }

    public static void updateSubmisionStatus(String submissionId, SubmissionStatus status){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

//        Project project = null;
        try {
            session.getTransaction().begin();

            String sql = "update " + Submission.class.getName() + " submission "
                    + "set submission.status = :status "
                    + "where submission.id = :submissionId";
            Query<Project> query = session.createQuery(sql);

            // Tạo đối tượng Query.
//            submission.setStatus(status);
//            session.save(submission);
//            session.getTransaction().commit();
            query.setParameter("status", status);
            query.setParameter("submissionId", submissionId);

//
//            // Thực hiện truy vấn.
            query.executeUpdate();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

//    @Override
//    public void run() {
//        try {
//            DatabaseService.saveProjectAndResult(this.scannerService, this.project);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void start() {
//        if (t == null) {
//            t = new Thread(this);
//            t.start();
//        }
//    }
}
