package hu.norbisquest.nagbase.common.engine;

import hu.norbisquest.nagbase.common.gui.GameGUI;
import hu.norbisquest.nagbase.game.Stage;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class ScreenContextTest {

    @Test
    public void exposesRequiredDependencies() {
        GameGUI gui = fakeGui();
        ScreenNavigation navigation = fakeNavigation();

        ScreenContext context = new ScreenContext(gui, navigation);

        assertSame(gui, context.gui());
        assertSame(navigation, context.navigation());
    }

    @Test
    public void rejectsNullGui() {
        assertRejected(null, fakeNavigation());
    }

    @Test
    public void rejectsNullNavigation() {
        assertRejected(fakeGui(), null);
    }

    private void assertRejected(GameGUI gui, ScreenNavigation navigation) {
        try {
            new ScreenContext(gui, navigation);
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
            public void navigateTo(Stage.Id id) {
            }

            @Override
            public void navigateTo(Stage.Id id, boolean clear) {
            }
        };
    }
}
