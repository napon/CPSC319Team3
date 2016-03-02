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

    private static final String CACHE_DIR = "offlineCacheData";

    public OfflineDatabaseUtil(Context context, int cacheSize){
        this.context = context;
        this.cacheSize = cacheSize;
    }

    public boolean save(JSONObject obj){
        FileOutputStream outputStream;

        try{
            if(isFilePresent(CACHE_DIR) && !isCacheFull(obj.toString())){
                File file = context.getFileStreamPath(CACHE_DIR);
                outputStream = new FileOutputStream(file, true);
                writeData(outputStream, obj);
                Log.d("File save", "Append data");
                return true;
            }
            else {
                outputStream = context.openFileOutput(CACHE_DIR, Context.MODE_PRIVATE);
                writeData(outputStream, obj);
                Log.d("File save", "Save data");
                return true;
            }
        }
        catch (FileNotFoundException e){
            Log.d("Cache file error", e.getMessage());
        }
        catch (IOException e){
            Log.d("Stream write error", e.getMessage());
        }
        return false;
    }

    public List<JSONObject> loadPending(){
        List<JSONObject> pendingList = new ArrayList<JSONObject>();
        try {
            if(isFilePresent(CACHE_DIR)){
                FileInputStream stream = context.openFileInput(CACHE_DIR);
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                JSONObject obj;
                while ((line = bufferedReader.readLine()) != null) {
                    if((obj = makeJsonObj(line))!=null)
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
        if(isFilePresent(CACHE_DIR)) {
            File file = context.getFileStreamPath(CACHE_DIR);
            file.delete();
            Log.d("File deleted", CACHE_DIR);
        }
        else
            Log.d("file not deleted", "No file to delete");
    }

    public int getCacheSize(){
        return cacheSize;
    }

    public void setCacheSize(int size){
        this.cacheSize = size;
    }

    private boolean isFilePresent(String fileName){
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }

    private boolean isCacheFull(String textToAdd){
        if(!isFilePresent(CACHE_DIR))
            return false;
        File file = context.getFileStreamPath(CACHE_DIR);
        long allocatedFileSize = file.getUsableSpace();
        int textSize = textToAdd.getBytes().length;
        long usedFileSize = file.length();
        long remainingSize = allocatedFileSize - usedFileSize;
        return ((remainingSize < textSize) || (usedFileSize + textSize) > cacheSize);
    }

    private void writeData(FileOutputStream outputStream, JSONObject obj) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(obj.toString());
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
