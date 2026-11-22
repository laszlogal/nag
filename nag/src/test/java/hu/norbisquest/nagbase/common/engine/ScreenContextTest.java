package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.core.AudioManager;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class ScreenContextTest {

    @Test
    public void exposesRequiredDependencies() {
        GameGUI gui = fakeGui();
        ScreenNavigation navigation = fakeNavigation();
        AudioManager audioManager = new AudioManager();

        ScreenContext context = new ScreenContext(gui, navigation, audioManager);

        assertSame(gui, context.gui());
        assertSame(navigation, context.navigation());
        assertSame(audioManager, context.audioManager());
    }

    @Test
    public void rejectsNullGui() {
        assertRejected(null, fakeNavigation());
    }

    @Test
    public void rejectsNullNavigation() {
        assertRejected(fakeGui(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullAudioManager() {
        new ScreenContext(fakeGui(), fakeNavigation(), null);
    }

    private void assertRejected(GameGUI gui, ScreenNavigation navigation) {
        try {
            new ScreenContext(gui, navigation, new AudioManager());
            fail("Expected null dependency to be rejected");
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    private GameGUI fakeGui() {
        return (GameGUI) Proxy.newProxyInstance(
                GameGUI.class.getClassLoader(),
                new Class<?>[]{GameGUI.class},
                (proxy, method, arguments) -> null);
    }

    private ScreenNavigation fakeNavigation() {
        return new ScreenNavigation() {
            @Override
            public void navigateTo(ScreenId id) {
            }

            @Override
            public void navigateTo(ScreenId id, TransitionEffect effect) {
            }
        };
    }
}
