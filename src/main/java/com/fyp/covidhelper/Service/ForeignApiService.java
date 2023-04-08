package com.fyp.covidhelper.Service;

import com.fyp.covidhelper.Annotation.Logging;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional(rollbackFor = Exception.class)
public class ForeignApiService {

    public String callForeignApi (String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder sb=new StringBuilder();
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        InputStream inputStream = http.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        sb.setLength(0);
        while ((str = bufferedReader.readLine()) != null) {
            sb.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        http.disconnect();
        return sb.toString();
    }


}
