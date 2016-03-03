package cpsc319.team3.com.biosense.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class OfflineDatabaseUtilTests {
    final static String FILE_NAME = "OfflineTestCache";
    @Before
    public void setup() {
        //delete cache file if present
        OfflineDatabaseUtil.deleteCache(Mockito.mock(Context.class), FILE_NAME);
    }
    @Test
    public void constructorDefaultTest() throws Exception {
        Context context = Mockito.mock(Context.class);
        OfflineDatabaseUtil offline = new OfflineDatabaseUtil(context);
        int oneMB = 1000000;
        int cacheSize = offline.getCacheSize();

        assertTrue(offline.getClass() == OfflineDatabaseUtil.class);
        assertTrue(cacheSize == oneMB);
    }

    @Test
    public void constructorCacheTest() throws Exception{
        int cacheSize = 100;
        Context context = Mockito.mock(Context.class);
        OfflineDatabaseUtil offline = new OfflineDatabaseUtil(context, cacheSize);

        assertTrue(offline.getClass() == OfflineDatabaseUtil.class);
        assertTrue(offline.getCacheSize() == cacheSize);
    }

    //Can not mock text without physically saving file...
    @Test
    public void saveTest() throws Exception {

    }

}
