package hu.norbisquest.nq1.client.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NQ1IdsTest {

    @Test
    public void everyScreenIdExceptNoneHasACreator() {
        for (NQ1Ids id : NQ1Ids.values()) {
            if (id == NQ1Ids.None) {
                assertNull(id.screenCreator());
            } else {
                assertNotNull(id + " must have a screen creator", id.screenCreator());
            }
        }
    }
}
