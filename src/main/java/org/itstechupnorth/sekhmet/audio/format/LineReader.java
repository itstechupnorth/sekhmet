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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.itstechupnorth.sekhmet.audio.data.AudioData;


class LineReader extends BufferingReader implements AudioReader {

    private final TargetDataLine line;

    public LineReader(TargetDataLine line) {
        super();
        this.line = line;
    }

    public AudioReader close() {
        line.close();
        return this;
    }

    public AudioReader open() {
        if (!line.isOpen()) {
            try {
                line.open();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    public AudioReader start() {
        if (!line.isActive()) {
            line.start();
        }
        return this;
    }

    public AudioData read() throws IOException {
        final int requestedBufferSize = line.getBufferSize();
        final AudioFormat format = line.getFormat();
        final long startFrame = line.getLongFramePosition();

        final int bufferSize = Math.min(line.getBufferSize(),
                requestedBufferSize);
        final byte[] bytes = new byte[bufferSize];
        int numberOfBytesRead = line.read(bytes, 0, bufferSize);
        while (numberOfBytesRead == 0) {
            numberOfBytesRead = line.read(bytes, 0, bufferSize);
        }

        return build(format, startFrame, bytes, numberOfBytesRead);
    }

}
