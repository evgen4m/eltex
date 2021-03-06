package com.esoft.eltex.app;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String ACP = "android-client:password";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.header("No-Authentication") == null) {
            request = request.newBuilder()
                    .addHeader(AUTHORIZATION, BASIC + Base64.encodeToString((ACP).getBytes("UTF-8"), Base64.NO_WRAP))
                    .build();
        }

        Response response = chain.proceed(request);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                switch (response.code()) {
                    case 200:
                        EventBus.getDefault().post(new ErrorMessageEvent("200"));
                        break;
                    case 401:
                        EventBus.getDefault().post(new ErrorMessageEvent("401"));
                        break;
                    case 404:
                        EventBus.getDefault().post(new ErrorMessageEvent("404"));
                        break;
                    case 400:
                        EventBus.getDefault().post(new ErrorMessageEvent("400"));
                        break;
                }
            }
        });

        return response;
    }
}

