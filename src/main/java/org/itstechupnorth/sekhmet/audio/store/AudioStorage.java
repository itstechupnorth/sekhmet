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
package org.itstechupnorth.sekhmet.audio.store;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioFileFormat;

public class AudioStorage {

    public static final AudioStorage buildDefault() {
        return new AudioStorage();
    }

    private final Set<GenericAudioRecord> records;

    private AudioStorage() {
        super();
        records = new HashSet<GenericAudioRecord>(512);
    }

    public AudioRecord aRecord() throws IOException {
        final String name = "test";
        return fileRecord(name);
    }

    private AudioRecord fileRecord(final String name) throws IOException {
        return new FileAudioRecord(new File(name + ".wav"),
                AudioFileFormat.Type.WAVE);
    }

    public void close() {
        for (final AudioRecord record : records) {
            record.close();
        }
    }

}
