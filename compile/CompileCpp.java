package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CompileCpp extends Compile {
    public CompileCpp() {
        super();
    }
    public void compile(String path, String fileName, File file) {
        String sss = "";
        String aaa = peel(fileName);
        if (!get(fileName).equals(".cpp")) {
            return;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder("g++", fileName, "-o", aaa);
            pb.directory(new File(path).getAbsoluteFile());
            Process pro = pb.start();
            String line = null;

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));

            while ((line = in.readLine()) != null) { //display the error location of compiling
                rstDis.append(line + "\r\n");
            }

            pro.waitFor();
            int exitValue = pro.exitValue();

            if (exitValue == 1) {
                sss = Integer.toString(25);
                grade(sss, path);
                gra.setText(sss);
            } else {
                String command = "./"+aaa;
                pb = new ProcessBuilder(command);
                pb.directory(new File(path).getAbsoluteFile());
                pro = pb.start();
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getInputStream()));
                while ((line = in.readLine()) != null) { //display the output of the java program in console
                    rstDis.append(line + "\r\n");
                }
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getErrorStream()));
                while ((line = in.readLine()) != null) { //display the error location when running the java program
                    rstDis.append(line + "\r\n");
                }
                pro.waitFor();
                exitValue = pro.exitValue();
                if (exitValue == 1) {
                    sss = Integer.toString(50);
                    grade(sss, path);
                    gra.setText(sss);
                } else {
                    sss = Integer.toString(90);
                    grade(sss, path);
                    gra.setText(sss);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
