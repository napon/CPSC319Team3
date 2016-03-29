package cpsc319.team3.com.biosense.utils;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import cpsc319.team3.com.biosense.PluriLockConfig;

import static org.junit.Assert.assertEquals;
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
        PluriLockConfig config = new PluriLockConfig();

        OfflineDatabaseUtil offline = new OfflineDatabaseUtil(context, config);

        assertEquals(OfflineDatabaseUtil.class, offline.getClass());
        assertEquals(config.getCacheSize(), offline.getCacheSize());
    }

    //Can not mock text without physically saving file...
    @Test
    public void saveTest() throws Exception {

    }

}
