package org.hcmus.datn;

import org.hcmus.datn.temporal.worker.ScannerWorker;
import org.hcmus.datn.worker.SonarWorker;

import java.io.IOException;
import java.text.ParseException;

public class Main {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        ScannerWorker scannerWorker = new ScannerWorker();
        scannerWorker.setupAndStart();

    }
}
