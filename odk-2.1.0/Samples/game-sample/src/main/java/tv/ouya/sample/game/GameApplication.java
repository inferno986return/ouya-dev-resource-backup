package tv.ouya.sample.game;

import android.app.Application;
import android.os.Bundle;
import tv.ouya.console.api.OuyaFacade;

import java.io.IOException;
import java.io.InputStream;

public class GameApplication extends Application {

    private static final String DEVELOPER_ID = "310a8f51-4d6e-4ae5-bda0-b93878e5f5d0";

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * Load the application key. This is used to decrypt encrypted receipt responses. This should be replaced with the
         * application key obtained from the OUYA developers website.
         */
        byte[] applicationKey = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.key);
            applicationKey = new byte[inputStream.available()];
            inputStream.read(applicationKey);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bundle developerInfo = new Bundle();
        developerInfo.putString(OuyaFacade.OUYA_DEVELOPER_ID, DEVELOPER_ID);
        developerInfo.putByteArray(OuyaFacade.OUYA_DEVELOPER_PUBLIC_KEY, applicationKey);

        OuyaFacade.getInstance().init(this, developerInfo);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        OuyaFacade.getInstance().shutdown();
    }
}
