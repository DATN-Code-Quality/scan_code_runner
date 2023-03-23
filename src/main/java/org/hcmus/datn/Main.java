package org.hcmus.datn;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.services.ScannerService;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    static final String _HOST = "https://af2c-54-151-141-45.ap.ngrok.io";
    static final String _USERNAME = "admin";
    static final String _PASSWORD = "123456";

    public static void main(String[] args) {
        ScannerService scannerService = new ScannerService("http://localhost:9000", _USERNAME, _PASSWORD);
//        System.out.println(scannerService.createNewProject("Api_Java"));
//        System.out.println(scannerService.generateNewToken("Api_Java"));
        System.out.println( ScannerService.generateID("19120721","CSC0001"));
    }

}