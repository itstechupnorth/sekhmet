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

import java.io.IOException;

public class Global {

    private static final String MAIN_TXT = "main.txt";
    private static final String BOILER_TXT = "boiler_en.txt";
    private static final String PROMPTS_TXT = "prompts_en.txt";

    public static String toPath(String base) {
        return base.replace('.', '/') + "/";
    }

    private final int startDelay = 5;
    private final int endDelay = 5;
    static final String TRANSCRIPT_TXT = "transcript.txt";
    static final String LICENSE_TXT = "LICENSE.txt";
    static final String UTF_8 = "UTF-8";

    public int getStartDelay() {
        return startDelay;
    }

    public int getEndDelay() {
        return endDelay;
    }

    PlainText readLicense(final String path) throws IOException {
        return PlainText.read(path + LICENSE_TXT);
    }

    PlainText readPrompts(final String path2) throws IOException {
        return PlainText.read(path2 + Global.PROMPTS_TXT);
    }

    PlainText readBoiler(final String path2) throws IOException {
        return PlainText.read(path2 + Global.BOILER_TXT);
    }

    PlainText readMain(final String path2) throws IOException {
        return PlainText.read(path2 + Global.MAIN_TXT);
    }
}
