package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This class stores PluriLockEvents when the device is offline.
 * It is an abstraction of the local Android database.
 *
 * This class relies on the manifest permission WRITE_EXTERNAL_STORAGE
 * It is assumed to be enabled prior to class methods being called
 *
 * See the UML Diagram for more implementation details.
 */
public class OfflineDatabaseUtil {
    Context context;
    int cacheSize;
    File file;

    public OfflineDatabaseUtil(Context context, int cacheSize){
        this.context = context;
        this.cacheSize = cacheSize;

        file = new File(context.getFilesDir(), "offlineCacheData");
    }

    public boolean save(JSONObject obj){
        FileOutputStream outputStream;

        try{
            if(isFilePresent("offlineCacheData")){
                File file = context.getFileStreamPath("offlineCacheData");
                outputStream = new FileOutputStream(file, true);
                outputStream.write(obj.toString().getBytes());
                outputStream.close();
//                FileWriter fw = new FileWriter(file);
//                fw.write(obj.toString());
//                fw.close();
                Log.d("File save", "Append data");
                return true;
            }
            else {
                outputStream = context.openFileOutput("offlineCacheData", Context.MODE_PRIVATE);
                outputStream.write(obj.toString().getBytes());
                outputStream.close();
                Log.d("File save", "Save data");
                return true;
            }
        }
        catch (FileNotFoundException e){
            Log.d("No offline cache file", e.getMessage());
        }
        catch (IOException e){
            Log.d("Stream error", e.getMessage());
        }
        return false;
    }

    public List<JSONObject> loadPending(){
        try {
            if(isFilePresent("offlineCacheData")){
                File file = context.getFileStreamPath("offlineCacheData");
                FileInputStream stream = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            else{
                Log.d("No file", "File not found");
            }
        }
        catch (IOException e){
            Log.d("Stream error", e.getMessage());
        }
        return null;
    }

    public void deleteCache(){
        if(isFilePresent("offlineCacheData")) {
            File file = context.getFileStreamPath("offlineCacheData");
            file.delete();
        }
        else
            Log.d("file not deleted", "No file to delete");
    }

    public int getCacheSize(){
        //TODO
        return cacheSize;
    }

    private boolean isFilePresent(String fileName){
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }

}
