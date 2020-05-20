package com.auth0.net.multipart;

import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RecordedMultipartRequest {

    private Map<String, KeyValuePart> parts = new HashMap<>();
    private String boundary;

    public RecordedMultipartRequest(RecordedRequest request) throws IOException {
        parseBody(request.getBody());
    }

    public KeyValuePart getKeyValuePart(String name) {
        return parts.get(name);
    }

    public FilePart getFilePart(String name) {
        return (FilePart) parts.get(name);
    }

    public int getPartsCount() {
        return parts.size();
    }

    public String getBoundary() {
        return boundary;
    }

    private void parseBody(Buffer body) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(body.inputStream()));
        //Obtain the boundary
        String separator = br.readLine();
        String eof = separator + "--";
        boundary = separator.replaceFirst("--", "");

        String strLine;
        while (!eof.equals(strLine = br.readLine())) {
            if (strLine.equals(separator) || strLine.isEmpty()) {
                continue;
            }

            //Start reading the content description
            String[] sections = strLine.split("; ");

            String keyName = null;
            String keyValue = null;
            String keyFilename = null;
            String keyContentType = null;
            int keyContentLength = 0;
            for (String section : sections) {
                String[] secParts = section.split("=");
                if ("name".equals(secParts[0])) {
                    keyName = secParts[1];
                    keyName = keyName.substring(1, keyName.length() - 1);
                } else if ("filename".equals(secParts[0])) {
                    keyFilename = secParts[1];
                    keyFilename = keyFilename.substring(1, keyFilename.length() - 1);
                }
            }
            //Try to find type
            strLine = br.readLine();
            if (strLine.startsWith("Content-Type: ")) {
                keyContentType = strLine.split("Content-Type: ")[1];
                strLine = br.readLine();
            }
            //Try to find length
            if (strLine.startsWith("Content-Length: ")) {
                keyContentLength = Integer.parseInt(strLine.split("Content-Length: ")[1]);
                strLine = br.readLine();
            }
            //Try to find value
            if (strLine.isEmpty()) {
                //Start reading the keyValue
                char[] valueBuffer = new char[keyContentLength];
                br.read(valueBuffer, 0, keyContentLength);
                keyValue = new String(valueBuffer);
                if (keyValue.length() != keyContentLength) {
                    System.out.println("-" + keyValue + "-");
                    throw new IllegalStateException("Content-Length value and the found size do not match");
                }
            }

            //Write the part
            KeyValuePart part;
            if (keyFilename != null) {
                part = new FilePart(keyName, keyValue, keyFilename, keyContentType);
            } else {
                part = new KeyValuePart(keyName, keyValue);
            }
            parts.put(keyName, part);
        }
    }
}
