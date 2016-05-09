package com.springapp.mvc.services;

import com.springapp.mvc.model.OutboundRequest;
import com.springapp.mvc.util.StaticFunctions;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by vivek on 7/5/16.
 */
@Service
public class CurlingService {
    public String makeCurlCall(OutboundRequest outboundRequest) throws IOException {
        URL obj = new URL(outboundRequest.build());
        System.out.println(outboundRequest.build());
        URLConnection urlConnection = obj.openConnection();
        HttpURLConnection con = outboundRequest.getProtocol() == OutboundRequest.Protocol.HTTP ? (HttpURLConnection) urlConnection : (HttpsURLConnection) urlConnection;

        for (Map.Entry<String, String> entry : outboundRequest.getHeaders().entrySet()) {
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }

        con.setRequestMethod(outboundRequest.getRequestMethod());
        con.setDoInput(true);
        boolean connected = false;

        if (outboundRequest.getRequestMethod().equals("POST") && StaticFunctions.isStringNonNull(outboundRequest.getRequestBody())) {
            con.setDoOutput(true);
            con.connect();
            connected = true;
            OutputStreamWriter outputStream = new OutputStreamWriter(con.getOutputStream());
            outputStream.write(outboundRequest.getRequestBody());
            outputStream.flush();
            outputStream.close();
        }

        if (!connected) {
            con.connect();
        }

        System.out.println("Response Code: " + con.getResponseCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
