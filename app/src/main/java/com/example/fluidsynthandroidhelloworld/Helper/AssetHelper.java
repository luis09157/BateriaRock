package com.example.fluidsynthandroidhelloworld.Helper;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetHelper {

    public static String copyAssetToTmpFile(Context context, String fileName) throws IOException {
        try (InputStream is = context.getAssets().open(fileName)) {
            String tempFileName = "tmp_" + fileName;
            try (FileOutputStream fos = context.openFileOutput(tempFileName, Context.MODE_PRIVATE)) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                while ((bytes_read = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes_read);
                }
            }
            return context.getFilesDir() + "/" + tempFileName;
        }
    }
}
