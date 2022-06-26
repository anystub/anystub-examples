package org.anystub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

/**
 */

public class SourceSystemImpl implements SourceSystem {

    private final String url;

    public SourceSystemImpl(String externalSystemUrl) {
        this.url = externalSystemUrl;
    }

    @Override
    public String get() throws IOException {
        URL myURL = new URL(url);
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.connect();


        InputStream inputStream = myURLConnection.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining());
    }

    private int random = 0;

    @Override
    public Integer rand(int seed) {
        return seed + random++;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() throws URISyntaxException {
        URI uri = new URI(getUrl());
        return uri.toASCIIString();
    }
}
