package com.example;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Cnfn
 * @date 2017/11/18
 */
public class Test {
    private static final String API_HOST = "http://ipinfo.io/";

    public static void main(String[] args) throws InterruptedException, IOException {
        onlyUseRetrofitSync();
        onlyUseRetrofitAsync();
    }

    private static void onlyUseRetrofitSync() throws IOException {
        System.out.println("===begin Sync=======");
        Retrofit retrofit = new Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpInfo> call = ipService.getIpInfo("8.8.8.8");
        System.out.println("in sync: " + call.execute().body());
        System.out.println("===end Sync=======");
    }

    private static void onlyUseRetrofitAsync() {
        System.out.println("===begin Async=======");

        Retrofit retrofit = new Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpInfo> call = ipService.getIpInfo("8.8.8.8");

        call.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                System.out.println("in async: " + response.body());
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        System.out.println("===end Async=======");
    }
}
