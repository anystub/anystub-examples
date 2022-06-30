package org.anystub.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class SourceSystemImpl implements SourceSystem {
    @Override
    public String get(String arg) throws IOException {
        URL myURL = new URL("http://localhost:8080/");
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.addRequestProperty("X-arg", arg);
        myURLConnection.connect();


        try(InputStream inputStream = myURLConnection.getInputStream();
            Reader reader = new InputStreamReader(inputStream)) {
            return new BufferedReader(reader)
                    .lines()
                    .collect(Collectors.joining());
        }
    }
}
