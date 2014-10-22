package com.cf.controller.core.spring.mvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.cf.controller.dto.EmployeeTypeDeserializer;
import com.cf.controller.dto.EmployeeTypeDto;
import com.cf.controller.dto.EmployeeTypeSerializer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import java.io.*;
import java.nio.charset.Charset;

public class GsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private Gson gson = new Gson();

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public GsonHttpMessageConverter(){
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {

        try{
            // Configure Gson
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(EmployeeTypeDto.class, new EmployeeTypeDeserializer());
            gson = gsonBuilder.create();
            return gson.fromJson(convertStreamToString(inputMessage.getBody()), clazz);
        }catch(JsonSyntaxException e){
            throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage(), e);
        }

    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        //TODO: adapt this to be able to receive a list of json objects too
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EmployeeTypeDto.class, new EmployeeTypeSerializer());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        String json = gson.toJson(t);
        outputMessage.getBody().write(json.getBytes());
    }

    //TODO: move this to a more appropriated utils class
    public String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the Reader.read(char[]
         * buffer) method. We iterate until the Reader return -1 which means
         * there's no more data to read. We use the StringWriter class to
         * produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

}
