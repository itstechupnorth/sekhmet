/*
 *  Copyright 2010-2013 Robert Burrell Donkin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.itstechupnorth.sekhmet.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainText {

    private static final int DEFAULT_LINES = 1024;

    public static PlainText read(String name) throws IOException {
        final PlainText result = new PlainText();
        result.load(ClassLoader.getSystemResourceAsStream(name));
        return result;
    }

    private final List<Line> lines;

    public PlainText() {
        super();
        lines = new ArrayList<Line>(DEFAULT_LINES);
    }

    public Map<String, String> properties() {
        final Map<String, String> result = new HashMap<String, String>(
                lines.size());
        for (Line line : lines) {
            line.addNormal(result);
        }
        return result;
    }

    public List<String> content() {
        final List<String> result = new ArrayList<String>(lines.size());
        for (Line line : lines) {
            line.addContent(result);
        }
        return result;
    }

    public List<String> plain() {
        final List<String> result = new ArrayList<String>(lines.size());
        for (Line line : lines) {
            line.addPlain(result);
        }
        return result;
    }

    public List<String> normal() {
        final List<String> result = new ArrayList<String>(lines.size());
        for (Line line : lines) {
            line.addNormal(result);
        }
        return result;
    }

    public void load(final InputStream in) throws IOException {
        lines.clear();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(
                in, Charset.forName(Global.UTF_8)));
        int count = 0;
        String text = null;
        do {
            text = reader.readLine();
            if (text != null) {
                lines.add(new Line(text, count++));
            }
        } while (text != null);
    }

    public class Line {
        // EOL stripped
        private final String content;
        // Zero based
        private final int lineNumber;

        private boolean isComment;

        public Line(String content, int lineNumber) {
            super();
            this.content = content;
            this.isComment = content.startsWith("#");
            this.lineNumber = lineNumber;
        }

        public void addPlain(List<String> result) {
            if (!isComment()) {
                result.add(content);
            }
        }

        public void addContent(List<String> result) {
            if (!isComment()) {
                result.add(content.trim());
            }
        }

        public void addNormal(Map<String, String> result) {
            if (!isComment()) {
                final int equalsPosition = content.indexOf('=');
                if (equalsPosition > 0) {
                    result.put(
                            content.substring(0, equalsPosition).trim()
                                    .toLowerCase(),
                            content.substring(equalsPosition + 1,
                                    content.length()).trim());
                }
            }
        }

        public void addNormal(final Collection<String> result) {
            if (!isComment()) {
                result.add(content.trim().toLowerCase());
            }
        }

        public String getContent() {
            return content;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public boolean isComment() {
            return isComment;
        }

        public void setComment(boolean isComment) {
            this.isComment = isComment;
        }

        @Override
        public String toString() {
            return "Line [content=" + content + "]";
        }
    }

    public String all() {
        final StringBuilder builder = new StringBuilder(1024);
        for (Line line : lines) {
            final String content = line.getContent();
            builder.append(content);
            builder.append('\r');
        }
        return builder.toString();
    }
}
