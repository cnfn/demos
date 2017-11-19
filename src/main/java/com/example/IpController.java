package com.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author zhixiao.mzx
 * @date 2017/11/19
 */
@RestController
public class IpController {
    @Autowired
    private IpService ipServiceOnlyWithRetrofit2;

    @Autowired
    private IpService ipServiceWithRetrofit2AndRxJava2;

    @RequestMapping(value = "/{ipAddress:.+}")
    public void ipInfo(@PathVariable String ipAddress) throws IOException, InterruptedException {
        onlyUseRetrofitSync(ipAddress);
        onlyUseRetrofitAsync(ipAddress);
        useRetrofitAndRxJava(ipAddress);
    }

    private void useRetrofitAndRxJava(String ipAddress) throws InterruptedException {
        System.out.println("===begin RxJava=======");

        ipServiceWithRetrofit2AndRxJava2.getIpInfoWithRxJava(ipAddress)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.trampoline())
            .subscribe(ipInfo -> System.out.println("ip RxJava: " + ipInfo));

        System.out.println("===end RxJava=======");

        TimeUnit.SECONDS.sleep(2);
    }

    private void onlyUseRetrofitSync(String ipAddress) throws IOException {
        System.out.println("===begin Sync=======");

        Call<IpInfo> call = ipServiceOnlyWithRetrofit2.getIpInfo(ipAddress);
        System.out.println("in sync: " + call.execute().body());
        System.out.println("===end Sync=======");
    }

    private void onlyUseRetrofitAsync(String ipAddress) {
        System.out.println("===begin Async=======");

        Call<IpInfo> call = ipServiceOnlyWithRetrofit2.getIpInfo(ipAddress);

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
