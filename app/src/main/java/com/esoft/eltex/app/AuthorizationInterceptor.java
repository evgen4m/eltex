package com.esoft.eltex.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.widget.Toast;

import com.esoft.eltex.data.PreferenceDataSource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceInterceptor implements Interceptor {

    private String finalToken = null;

    private Context context;
    public PreferenceDataSource preferenceDataSource;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String ACP = "android-client:password";

    public ServiceInterceptor(Context context) {
        this.context = context;
        preferenceDataSource = new PreferenceDataSource(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.header("No-Authentication") == null) {
            finalToken = preferenceDataSource.getPrefString("tokenType") + preferenceDataSource.getPrefString("token");
            request = request.newBuilder()
                    .addHeader(AUTHORIZATION, BASIC + Base64.encodeToString((ACP).getBytes("UTF-8"), Base64.NO_WRAP))
                    .addHeader(AUTHORIZATION, finalToken)
                    .build();
        }

        Response response = chain.proceed(request);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(context, "Успешная аутентификация", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(context, "Ошибка аутентификации", Toast.LENGTH_SHORT).show();
                        preferenceDataSource.clearPref();
                        break;
                    case 404:
                        Toast.makeText(context, "Данные не найдены", Toast.LENGTH_SHORT).show();
                        break;
                    case 400:
                        Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return response;
    }
}
