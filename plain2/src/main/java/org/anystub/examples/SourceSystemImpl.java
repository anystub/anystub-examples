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
        return somePrivateImplementation(arg);
    }

    private String somePrivateImplementation(String arg) throws IOException {
        URL myURL = new URL("https://quotes.rest/qod?language=en");
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.setRequestProperty("accept","application/json");
        myURLConnection.connect();


        try(InputStream inputStream = myURLConnection.getInputStream();
            Reader reader = new InputStreamReader(inputStream)) {
            return new BufferedReader(reader)
                    .lines()
                    .collect(Collectors.joining());
        }
    }
}
