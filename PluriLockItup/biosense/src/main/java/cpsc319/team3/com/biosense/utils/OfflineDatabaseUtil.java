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

    //The directory of the cache file on the phone
    private static final String CACHE_DIR = "offlineCacheData";

    /**
     * Constructor for Offline Database Util
     * @param context Context of the application
     * @param cacheSize value (in bytes) of the max cache size
     */
    public OfflineDatabaseUtil(Context context, int cacheSize){
        this.context = context;
        this.cacheSize = cacheSize;
    }

    /**
     * Constructor for Offline Database Util with default cache size
     * Default size is 1MB as recommended by Android
     * @param context Context of the application
     */
    public OfflineDatabaseUtil(Context context){
        this.context = context;
        this.cacheSize = 1000000; //1MB
    }

    /**
     * Saves json object to internal app cache file.
     * File is set to PRIVATE and can not be read outside of the app
     * If no cache file is found, one will be made to store the data
     * If cache size is smaller than the remaining file space, no new data is added
     * @param obj The JSON object to be stored
     * @return True if object was saved successfully
     */
    public boolean save(JSONObject obj){
        FileOutputStream outputStream;

        try{
            if(!isCacheFull(obj.toString())){
                if (isFilePresent(CACHE_DIR)) {
                    // Gets file
                    File file = context.getFileStreamPath(CACHE_DIR);
                    outputStream = new FileOutputStream(file, true);
                    writeData(outputStream, obj);
                    Log.d("File save", "Append data");
                }
                else {
                    // Creates file
                    outputStream = context.openFileOutput(CACHE_DIR, Context.MODE_PRIVATE);
                    writeData(outputStream, obj);
                    Log.d("File save", "Save data");
                }
                //closes stream when complete
                outputStream.flush();
                outputStream.close();
                return true;
            }
            else{
                Log.d("Cache not added", "Cache full");
            }
        }
        catch (FileNotFoundException e){
            Log.e("Cache file error", e.getMessage());
        }
        catch (IOException e){
            Log.e("Stream write error", e.getMessage());
        }
        return false;
    }

    /**
     * Saves a list of JSON objects to the cache.
     * This method is recommended if multiple items are cached at once as it saves from closing
     * and opening the file over and over.
     * File is set to PRIVATE and can not be read outside of the app
     * If no cache file is found, one will be made to store the data
     * If cache size is smaller than the remaining file space, no new data is added
     * @param objList List of JSONObjects to be cached
     * @return True if save was sucessful
     */
    public boolean save(List<JSONObject> objList){
        FileOutputStream outputStream;
        try{
            if(!isCacheFull(objList.toString())){
                if (isFilePresent(CACHE_DIR)) {
                    File file = context.getFileStreamPath(CACHE_DIR);
                    outputStream = new FileOutputStream(file, true);
                    for (JSONObject obj: objList) {
                        writeData(outputStream, obj);
                        Log.d("File save", "Append data");
                    }
                }
                else {
                    outputStream = context.openFileOutput(CACHE_DIR, Context.MODE_PRIVATE);
                    for(JSONObject obj: objList) {
                        writeData(outputStream, obj);
                        Log.d("File save", "Save data");
                    }
                }
                outputStream.flush();
                outputStream.close();
                return true;
            }
            else{
                Log.d("Cache not added", "Cache full");
            }
        }
        catch (FileNotFoundException e){
            Log.e("Cache file error", e.getMessage());
        }
        catch (IOException e){
            Log.e("Stream write error", e.getMessage());
        }
        return false;
    }

    /**
     * Reads the cache file and parses all the strings back to JSON objects
     * WARNING: Once the cache list is returned, the file is deleted
     * @return List of JSON objects saved in the cache
     */
    public List<JSONObject> loadPending(){
        List<JSONObject> pendingList = new ArrayList<>();
        try {
            if(isFilePresent(CACHE_DIR)){
                String line;
                JSONObject obj;

                //Creates stream to read
                FileInputStream stream = context.openFileInput(CACHE_DIR);
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                while ((line = bufferedReader.readLine()) != null) {
                    Log.d("Cache read", line);
                    //reject line if not json proper format
                    if((obj = makeJsonObj(line))!=null)
                        pendingList.add(obj);
                }
                //Delete cache when objects are returned
                deleteCache();
            }
            else{
                Log.e("No file", "File not found");
            }
        }
        catch (IOException e){
            Log.e("Stream error", e.getMessage());
        }
        return pendingList;
    }

    /**
     * Deletes the cache file if it exists
     */
    public void deleteCache(){
        if(isFilePresent(CACHE_DIR)) {
            File file = context.getFileStreamPath(CACHE_DIR);
            file.delete();
            Log.d("File deleted", CACHE_DIR);
        }
        else
            Log.d("file not deleted", "No file to delete");
    }

    /**
     * Returns the cache size
     * @return specified cache size
     */
    public int getCacheSize(){
        return cacheSize;
    }

    /**
     * Sets the cache size
     * @param size sets the cache size
     */
    public void setCacheSize(int size){
        this.cacheSize = size;
    }

    /**
     * Checks if an internal file has been created on the device
     * @param fileName the file name
     * @return true if the file exists on the device
     */
    private boolean isFilePresent(String fileName){
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }

    /**
     * Checks if the cache is full
     * It is full if the remaining allowed cache size is less than the new text size
     * It is full if the unused file space + the text size exceeds the specified cache size
     * @param textToAdd the text to be added to the cache
     * @return true if the cache is full
     */
    private boolean isCacheFull(String textToAdd){
        //if file does not exist, cache is not full
        if(!isFilePresent(CACHE_DIR))
            return false;
        File file = context.getFileStreamPath(CACHE_DIR);
        long allocatedFileSize = file.getUsableSpace();
        int textSize = textToAdd.getBytes().length;
        long usedFileSize = file.length();
        long remainingSize = allocatedFileSize - usedFileSize;
        return ((remainingSize < textSize) || (usedFileSize + textSize) > cacheSize);
    }

    /**
     * Helper function that opens a stream to write the new data to the file
     * This data will be appended to the file if data exists in the cache already
     * @param outputStream the stream to write to (the file)
     * @param obj the data to write
     * @throws IOException
     */

    private void writeData(FileOutputStream outputStream, JSONObject obj) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(obj.toString());
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * Ensure only json formatted strings are returned
     * @param jsonString string from file to turn to JSON object
     * @return the JSONObject if correct format, null otherwise
     */
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
