package cpsc319.team3.com.biosense;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PluriLockKeyListenerTests {
    @Test
    public void sampleTest() {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockKeyListener p = new PluriLockKeyListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockKeyListener.class);
    }

    @Test
    public void onKeyReturnTrueTest() {
        View v = new View(null);
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockKeyListener keyListener = new PluriLockKeyListener(pluriLockEventTracker);

       // assertTrue(keyListener.onKey(v, 1, null));
    }

    @Test
    public void textWatcherTest(){
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockKeyListener keyListener = new PluriLockKeyListener(pluriLockEventTracker);

        Resources resourceMock = Mockito.mock(Resources.class);
        Configuration configMock = Mockito.mock(Configuration.class);
        when(resourceMock.getConfiguration()).thenReturn(configMock);
        configMock.orientation = Configuration.ORIENTATION_PORTRAIT;

//        keyListener.onTextChanged("s", 0, 0, 1);
    }
}
