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
package org.itstechupnorth.sekhmet.audio.format;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.apache.commons.io.IOUtils;
import org.itstechupnorth.sekhmet.audio.data.AudioData;


class StreamReader extends BufferingReader implements AudioReader {

    private final AudioInputStream in;
    private final int bufferSize;

    private long startFrame = 0;

    public StreamReader(AudioInputStream in, int bufferSize) {
        super();
        this.in = in;
        this.bufferSize = bufferSize;
    }

    @Override
    public AudioReader close() {
        IOUtils.closeQuietly(in);
        return this;
    }

    public AudioData read() throws IOException {
        final byte[] buffer = new byte[bufferSize];
        final int numberOfBytesRead = in.read(buffer);
        final AudioFormat format = in.getFormat();
        final AudioData data = build(format, startFrame, buffer,
                numberOfBytesRead);
        startFrame = nextFrame(data);
        return data;
    }
}
