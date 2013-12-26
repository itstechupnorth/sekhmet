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

import java.io.File;
import java.io.IOException;

public class Storage {

    private final File directory;

    public Storage(File directory) {
        super();
        this.directory = directory;
    }

    private Document open(final String name) {
        directory.mkdirs();
        return new Document(name, directory);
    }

    public Storage store(String name, String text) throws IOException {
        open(name).write(text).close();
        return this;
    }

    public Storage license(String text) throws IOException {
        store(Global.LICENSE_TXT, text);
        return this;
    }

    public Storage transcript(String text) throws IOException {
        store(Global.TRANSCRIPT_TXT, text);
        return this;
    }
}
