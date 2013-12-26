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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Document {

    private final String name;
    private final File directory;

    public Document(String name, File directory) {
        super();
        this.name = name;
        this.directory = directory;
    }

    private OutputStream open() throws IOException {
        final File out = new File(directory, name);
        out.createNewFile();
        return new BufferedOutputStream(new FileOutputStream(out));
    }

    public Document write(String text) throws IOException {
        final OutputStream stream = open();
        try {
            final OutputStreamWriter writer = new OutputStreamWriter(stream,
                    Global.UTF_8);
            writer.write(text);
            writer.flush();
            writer.close();
            stream.flush();
        } finally {
            try {
                stream.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return this;
    }

    public Document close() {
        return this;
    }
}
