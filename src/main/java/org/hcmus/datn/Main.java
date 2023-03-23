package org.hcmus.datn;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.hcmus.datn.handlers.FileHandler;
import org.hcmus.datn.services.HttpService;
import org.hcmus.datn.utils.Utils;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String requestUrl="https://www.clickdimensions.com/links/TestPDFfile.pdf";
        Response response= HttpService.excuteRequest(HttpService.newGetRequest(requestUrl,new HashMap<>(),new HashMap<>()));
        if(response!=null)
        {

            ResponseBody body=response.body();
                System.out.println(body.contentType());
                System.out.println(body.contentLength());
                String destinationPath="E:\\temp\\a";
            try {

                FileHandler.getFileFromStream(body.byteStream(),destinationPath, Utils.getFileNameFromUrl(requestUrl));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}