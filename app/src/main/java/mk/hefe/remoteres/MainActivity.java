package mk.hefe.remoteres;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HttpHandler handler;
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        startTrans();
    }

    private void findViews(){
        txvResult = (TextView)findViewById(R.id.txvResult);
    }

    private void startTrans(){
        handler = new HttpHandler(this);

        HashMap<String, String> params = new HashMap<>();
        //TODO 填入參數
        //params.put("name", "tom");

        //TODO 填入URL
        new Thread(
                new SendDataRunnable(handler, "http://www.example.com", params)
        ).start();
    }

    static class HttpHandler extends Handler {

        WeakReference<Activity> weakReference;

        public HttpHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity main = (MainActivity)weakReference.get();
            if(msg.what == 0) {
                //TODO 依what判斷後續處理
                main.txvResult.setText((String)msg.obj);
            }
        }
    }

}
