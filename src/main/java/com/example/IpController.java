package com.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Cnfn
 * @date 2017/11/19
 */
@Log4j
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
        log.warn("===begin RxJava=======");

        ipServiceWithRetrofit2AndRxJava2.getIpInfoWithRxJava(ipAddress)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.trampoline())
            .subscribe(ipInfo -> log.error("ip RxJava: " + ipInfo));

        log.warn("===end RxJava=======");

        TimeUnit.SECONDS.sleep(2);
    }

    private void onlyUseRetrofitSync(String ipAddress) throws IOException {
        log.warn("===begin Sync=======");

        Call<IpInfo> call = ipServiceOnlyWithRetrofit2.getIpInfo(ipAddress);
        log.error("in sync: " + call.execute().body());
        log.warn("===end Sync=======");
    }

    private void onlyUseRetrofitAsync(String ipAddress) {
        log.warn("===begin Async=======");

        Call<IpInfo> call = ipServiceOnlyWithRetrofit2.getIpInfo(ipAddress);

        call.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                log.error("in async: " + response.body());
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                log.error(t.getMessage());
            }
        });

        log.warn("===end Async=======");
    }
}
