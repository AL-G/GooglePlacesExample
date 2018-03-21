package com.buzzmove.placesapiexample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static String readJsonFile (Class<?> mClass, String filename) throws IOException {

        ClassLoader classLoader = mClass.getClassLoader();
        URL resource = classLoader.getResource(filename);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getPath())));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }

        return sb.toString();
    }

}
