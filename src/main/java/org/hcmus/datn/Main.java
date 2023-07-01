package org.hcmus.datn;

import org.hcmus.datn.common.Config;
import org.hcmus.datn.services.DatabaseService;
import org.hcmus.datn.services.ScannerService;
import org.hcmus.datn.services.SonarSensor;
import org.hcmus.datn.temporal.model.request.Submission;
import org.hcmus.datn.temporal.model.response.Project;
import org.hcmus.datn.temporal.worker.ScannerWorker;
import org.hcmus.datn.utils.ProjectType;
import org.hcmus.datn.utils.SubmissionStatus;
import org.hcmus.datn.worker.SonarWorker;

import java.io.IOException;
import java.text.ParseException;

public class Main {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        ScannerWorker scannerWorker = new ScannerWorker();
        scannerWorker.setupAndStart();
//        StringBuilder rootPath = new StringBuilder();
//        try{
//            ProjectType type = SonarSensor.getLanguage("C:\\Users\\Admin\\OneDrive - VNU-HCMUS\\Desktop\\code\\test\\1753021_025_112 - Copy", rootPath);
//            System.out.println(type);
//            System.out.println(rootPath);
//
//        }catch (Exception e){
//            System.out.println(e);
//        }


//        Submission  submission = DatabaseService.findSubmsisonById("29076df9-68e9-4b5f-b209-e73a400e386d");
//        System.out.println(submission.toString());
//        DatabaseService.updateSubmisionStatus(submission.getId(), SubmissionStatus.SCANNING);
//        submission = DatabaseService.findSubmsisonById("29076df9-68e9-4b5f-b209-e73a400e386d");
//        System.out.println(submission.toString());
//        System.out.println(DatabaseService.getConfigOfAssignment("47789143-3fca-4745-90ec-efa761780a2d"));
//        ScannerService scannerService = new ScannerService(Config.get("SONARCLOUD_URL"));
//        String result = scannerService.getResultFromCloud("130981a9-894a-4c9c-bd02-73953cdd0410_47789143-3fca-4745-90ec-efa761780a2d", "47789143-3fca-4745-90ec-efa761780a2d");
//        System.out.println(result);
    }


}
