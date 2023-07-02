package org.hcmus.datn.services;

import org.hcmus.datn.temporal.model.request.Submission;
import org.hcmus.datn.temporal.model.response.Assignment;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.temporal.model.response.ResponseObject;
import org.hcmus.datn.temporal.model.response.Result;
import org.hcmus.datn.utils.HibernateUtils;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Date;

public class DatabaseService {
    public static Project createProject(Project project) {
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

    public static void updateSubmisionLink(String submissionId, String link){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

//        Project project = null;
        try {
            session.getTransaction().begin();

            String sql = "update " + Submission.class.getName() + " submission "
                    + "set submission.link = :link "
                    + "where submission.id = :submissionId";
            Query<Project> query = session.createQuery(sql);

            // Tạo đối tượng Query.
//            submission.setStatus(status);
//            session.save(submission);
//            session.getTransaction().commit();
            query.setParameter("link", link);
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

    public static void updateProjectType(String projectId, ProjectType type){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

//        Project project = null;
        try {
            session.getTransaction().begin();

            String sql = "update " + Project.class.getName() + " project "
                    + "set project.type = :type "
                    + "where project.id = :projectId";
            Query<Project> query = session.createQuery(sql);

            // Tạo đối tượng Query.
//            submission.setStatus(status);
//            session.save(submission);
//            session.getTransaction().commit();
            query.setParameter("type", type);
            query.setParameter("projectId", projectId);

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

    public static String getConfigOfAssignment(String assignmentId){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        String config = null;
        try {
            session.getTransaction().begin();

            String sql = "Select assignment.config from " + Assignment.class.getName() + " assignment "
                    + "where assignment.id = :id";

            // Tạo đối tượng Query.
            Query<String> query = session.createQuery(sql);
            query.setParameter("id", assignmentId);

            // Thực hiện truy vấn.
            config = query.getSingleResultOrNull();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return config;
    }

    public static String getCourseId(String assignmentId){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        String courseId = null;
        try {
            session.getTransaction().begin();

            String sql = "Select assignment.courseId from " + Assignment.class.getName() + " assignment "
                    + "where assignment.id = :id";

            // Tạo đối tượng Query.
            Query<String> query = session.createQuery(sql);
            query.setParameter("id", assignmentId);

            // Thực hiện truy vấn.
            courseId = query.getSingleResultOrNull();

//             Commit dữ liệu
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return courseId;
    }


    public static Result getResultBySubmisisonId(String submissionId){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        Result result = null;
        try {
            session.getTransaction().begin();

            String sql = "Select result from " + Result.class.getName() + " result "
                    + "where result.submissionId = :submissionId";

            // Tạo đối tượng Query.
            Query<Result> query = session.createQuery(sql);
            query.setParameter("submissionId", submissionId);

            // Thực hiện truy vấn.
            result = query.getSingleResultOrNull();

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
    public static Result createResult(Result result) {
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

    public static void updateResult(Result result){
        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.getTransaction().begin();

            String sql = "update " + Result.class.getName() + " result "
                    + "set result.total = :total , "
                    + "result.codeSmell = :codeSmell , "
                    + "result.bug = :bug , "
                    + "result.vulnerabilities = :vulnerabilities , "
                    + "result.blocker = :blocker , "
                    + "result.critical = :critical , "
                    + "result.major = :major , "
                    + "result.minor = :minor , "
                    + "result.info = :info , "
                    + "result.duplicatedLinesDensity = :duplicatedLinesDensity , "
                    + "result.coverage = :coverage , "
                    + "result.updatedAt = :updatedAt "

                    + "where result.submissionId = :submissionId";
            Query<Project> query = session.createQuery(sql);

            query.setParameter("total", result.getTotal());
            query.setParameter("codeSmell", result.getCodeSmell());
            query.setParameter("bug", result.getBug());
            query.setParameter("vulnerabilities", result.getVulnerabilities());
            query.setParameter("blocker", result.getBlocker());
            query.setParameter("critical", result.getCritical());
            query.setParameter("major", result.getMajor());
            query.setParameter("minor", result.getMinor());
            query.setParameter("info", result.getInfo());
            query.setParameter("duplicatedLinesDensity", result.getDuplicatedLinesDensity());
            query.setParameter("coverage", result.getCoverage());
            query.setParameter("updatedAt", new Date());
            query.setParameter("submissionId", result.getSubmissionId());


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
