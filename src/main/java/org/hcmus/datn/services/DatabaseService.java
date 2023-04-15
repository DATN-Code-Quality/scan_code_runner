package org.hcmus.datn.services;

import org.hcmus.datn.common.ErrorCode;
import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.temporal.model.response.Result;
import org.hcmus.datn.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
    public static Result createResult(Result result){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Result savedResult = null;

        try {
            session.getTransaction().begin();

            result.setCreatedAt(new Date());
            result.setUpdatedAt(new Date());

            session.save(result);

            session.getTransaction().commit();

            savedResult = result;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }

        return savedResult;
    }

    public static List<Project> findResultByProject(String projectId){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        List<Project> result = null;
        try {
            session.getTransaction().begin();

            String sql = "Select result from " + Result.class.getName() + " result "
                    + "where result.projectId = :projectId";

            // Tạo đối tượng Query.
            Query<Project> query = session.createQuery(sql);
            query.setParameter("projectId", projectId);

            // Thực hiện truy vấn.
            result = query.getResultList();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return result;
    }
    public static ResponseObject saveProjectAndResult(ScannerService scannerService, Project project) throws InterruptedException {
        try
        {
            Project savedProject = DatabaseService.findProjectByKey(project.getKey());
            if (savedProject == null){
                savedProject = DatabaseService.createProject(project);
            }

            Thread.sleep(20000);
            Result result = scannerService.getResultOverview( savedProject.getId(), savedProject.getKey());

            Result savedResult = DatabaseService.createResult(result);


            if(savedResult == null){
                return new ResponseObject(ErrorCode.FAILED.getValue(), "Cann't save result");
            }
        }
        catch (IOException e){
            System.out.println(e);
            return new ResponseObject(ErrorCode.FAILED.getValue(), e.getMessage());
        }
        return ResponseObject.SUCCESS;
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
