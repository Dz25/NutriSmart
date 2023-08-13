package com.example.nutrismart.data.api;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class JsonOrPojoConverterFactory extends Converter.Factory {


    private Converter.Factory pojo;
    private Converter.Factory json;

    public JsonOrPojoConverterFactory() {
        pojo = ScalarsConverterFactory.create();
        json = GsonConverterFactory.create(new GsonBuilder().create());
    }


    @Retention(RetentionPolicy.RUNTIME)
    public @interface Json {}

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Pojo {}
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Pojo.class) {
                return pojo.responseBodyConverter(type, annotations, retrofit);
            }
            if (annotation.annotationType() == Json.class) {
                return json.responseBodyConverter(type, annotations, retrofit);
            }
        }
        return json.responseBodyConverter(type, annotations, retrofit);
    }
}
