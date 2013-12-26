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
package org.itstechupnorth.sekhmet.audio.source;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.itstechupnorth.sekhmet.audio.data.AudioData;
import org.itstechupnorth.sekhmet.audio.data.SignalData;
import org.itstechupnorth.sekhmet.audio.format.AudioReader;


public class AudioReaderSource extends AbstractSource {

    private final AudioReader reader;

    public AudioReaderSource(BlockingQueue<SignalData> queue,
            final AudioReader reader) {
        super(queue);
        this.reader = reader;
    }

    @Override
    protected AudioData read() throws IOException {
        return reader.read();
    }

    @Override
    protected void prepare() {
        try {
            reader.open();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void start() throws IOException {
        reader.start();
    }
}
