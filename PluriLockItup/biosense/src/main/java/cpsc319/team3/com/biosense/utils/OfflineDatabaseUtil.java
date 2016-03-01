package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

    public OfflineDatabaseUtil(Context context, int cacheSize){
        this.context = context;
        this.cacheSize = cacheSize;
    }

    public boolean save(JSONObject obj){
        FileOutputStream outputStream;

        try{
            if(isFilePresent("offlineCacheData")){
                File file = context.getFileStreamPath("offlineCacheData");
                outputStream = new FileOutputStream(file, true);
                writeData(outputStream, obj);
                Log.d("File save", "Append data");
                return true;
            }
            else {
                outputStream = context.openFileOutput("offlineCacheData", Context.MODE_PRIVATE);
                writeData(outputStream, obj);
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
        List<JSONObject> pendingList = new ArrayList<JSONObject>();
        try {
            if(isFilePresent("offlineCacheData")){
                FileInputStream stream = context.openFileInput("offlineCacheData");
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                String[] array = sb.toString().split("~~");
                for(int i = 0; i < array.length; i++){
                    JSONObject obj;
                    if ((obj = makeJsonObj(array[i])) != null)
                        pendingList.add(obj);
                }
                for(JSONObject o:pendingList)
                    System.out.println(o.toString());
            }
            else{
                Log.d("No file", "File not found");
            }
        }
        catch (IOException e){
            Log.d("Stream error", e.getMessage());
        }
        return pendingList;
    }

    public void deleteCache(){
        if(isFilePresent("offlineCacheData")) {
            File file = context.getFileStreamPath("offlineCacheData");
            file.delete();
            Log.d("File deleted", "offlineCacheData");
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

    private void writeData(FileOutputStream outputStream, JSONObject obj) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(obj.toString());
        bw.append("~~");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    private JSONObject makeJsonObj(String jsonString){
        JSONObject obj = null;
        try{
            obj = new JSONObject(jsonString);
        }
        catch (JSONException e) {
            Log.e("JSON error", "Could not make json object from " + jsonString);
        }
        return obj;
    }

}
