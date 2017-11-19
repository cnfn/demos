package com.example;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Cnfn
 * @date 2017/11/18
 */
public interface IpService {
    @GET("/{ip}/json")
    Call<IpInfo> getIpInfo(@Path("ip") String ip);

    @GET("/{ip}/json")
    Observable<IpInfo> getIpInfoWithRxJava(@Path("ip") String ip);
}
