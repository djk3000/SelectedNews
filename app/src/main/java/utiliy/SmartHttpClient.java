package utiliy;

import android.os.Handler;
import android.os.Message;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SmartHttpClient {
    public void SyncGet(String url, Handler handler) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        doHttp(client, request, handler);
    }

    private void doHttp(OkHttpClient client, Request request, Handler handler) throws IOException {
        final Message message = Message.obtain();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                message.what = FAILURE_CODE;
                message.obj = e.getMessage();//失败的信息
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data = response.body().string();
                message.what = SUCCESS_CODE;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
    }
    OkHttpClient client = new OkHttpClient();

    private final int FAILURE_CODE = 1001;//失败
    private final int SUCCESS_CODE = 1000;//成功
}
