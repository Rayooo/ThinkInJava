package io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by Ray on 2017/5/12
 */
public class PostUtil {

    private static String host = "http://myserver:8080/";

    public static void sendPostRequest(String api, LinkedHashMap<String, Object> params){
        try {
            URL url = new URL(host + api);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }


//            ObjectMapper mapper = new ObjectMapper();
//            Object json = mapper.readValue(in,Object.class);
//            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//            System.out.println(indented);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
