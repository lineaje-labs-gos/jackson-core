package com.fasterxml.jackson.core.read;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.exc.StreamConstraintsException;

public class CVETests extends com.fasterxml.jackson.core.BaseTest {
    public void testDeepNesting() throws Exception {
        final String DOC = createDeepNestedDoc(1050);
        JsonParser jp = null;
        try {
            jp = createParserUsingStream(new JsonFactory(), DOC, "UTF-8");
            JsonToken jt;
            while ((jt = jp.nextToken()) != null) {
                // consume tokens
            }
            fail("expected StreamConstraintsException");
        } catch (StreamConstraintsException e) {
            assertEquals("Depth (1001) exceeds the maximum allowed nesting depth (1000)", e.getMessage());
        } finally {
            if (jp != null) {
                jp.close();
            }
        }
    }

    private String createDeepNestedDoc(final int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < depth; i++) {
            sb.append("{ \"a\": [");
        }
        sb.append(" \"val\" ");
        for (int i = 0; i < depth; i++) {
            sb.append("]}");
        }
        sb.append("]");
        return sb.toString();
    }

}