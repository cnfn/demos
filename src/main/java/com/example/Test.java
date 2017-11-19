package com.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Cnfn
 * @date 2017/11/18
 */
public class Test {
    private static final String API_HOST = "http://ipinfo.io/";

    public static void main(String[] args) throws InterruptedException, IOException {
        String ipAddress = "8.8.8.8";

        onlyUseRetrofitSync(ipAddress);
        onlyUseRetrofitAsync(ipAddress);
        useRetrofitAndRxJava(ipAddress);
    }

    private static void useRetrofitAndRxJava(String ipAddress) throws InterruptedException {
        System.out.println("===begin RxJava=======");

        Retrofit retrofit = new Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

        IpService ipService = retrofit.create(IpService.class);
        ipService.getIpInfoWithRxJava(ipAddress)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.trampoline())
            .subscribe(ipInfo -> System.out.println("ip RxJava: " + ipInfo));

        System.out.println("===end RxJava=======");

        TimeUnit.SECONDS.sleep(2);
    }

    private static void onlyUseRetrofitSync(String ipAddress) throws IOException {
        System.out.println("===begin Sync=======");

        Retrofit retrofit = new Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpInfo> call = ipService.getIpInfo(ipAddress);
        System.out.println("in sync: " + call.execute().body());
        System.out.println("===end Sync=======");
    }

    private static void onlyUseRetrofitAsync(String ipAddress) {
        System.out.println("===begin Async=======");

        Retrofit retrofit = new Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpInfo> call = ipService.getIpInfo(ipAddress);

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
