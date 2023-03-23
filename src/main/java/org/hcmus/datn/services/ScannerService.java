package org.hcmus.datn.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScannerService {
    public void scanProject(String projectPath,String projectKey,String token){
        projectKey="API";
        token="439841a5cc19b0f477bb761bc7b02e2c5acd54a8";
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "d: && cd "+projectPath+" && sonar-scanner.bat -D\"sonar.projectKey=" +projectKey+
                "\" -D\"sonar.sources=.\" -D\"sonar.host.url=http://localhost:9000\" -D\"sonar.login=" + token + "\"");
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
