package com.example;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhixiao.mzx
 * @date 2017/11/19
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = DemoApplication.class)
public class Retrofit2InterfaceGenerator {
    @Bean
    public IpService ipServiceOnlyWithRetrofit2(@Value(value = "${ipInfo.host}") String host) {
        //HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //httpLoggingInterceptor.setLevel(Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(host)
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(IpService.class);
    }

    @Bean
    public IpService ipServiceWithRetrofit2AndRxJava2(@Value(value = "${ipInfo.host}") String host) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

        return retrofit.create(IpService.class);
    }
}
