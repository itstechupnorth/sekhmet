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
package org.itstechupnorth.sekhmet.audio.source.mic;

import java.util.concurrent.BlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import org.itstechupnorth.sekhmet.audio.data.SignalData;
import org.itstechupnorth.sekhmet.audio.format.AudioFormatWrangler;
import org.itstechupnorth.sekhmet.audio.source.AbstractSource;
import org.itstechupnorth.sekhmet.audio.source.AudioReaderSource;


public class Microphone {

    private final DataLine.Info lineInfo;
    private final Mixer.Info mixerInfo;
    private TargetDataLine line;
    private AbstractSource source;

    // private Comparator<AudioFormat> selector = AudioSelectors.highMonoPCM();

    public Microphone(DataLine.Info lineInfo,
            javax.sound.sampled.Mixer.Info mixerInfo) {
        super();
        this.lineInfo = lineInfo;
        this.mixerInfo = mixerInfo;
    }

    public Line.Info getLineInfo() {
        return lineInfo;
    }

    public Mixer.Info getMixerInfo() {
        return mixerInfo;
    }

    public AudioFormat getLineFormat() {
        if (line == null) {
            return null;
        }
        return line.getFormat();
    }

    @Override
    public String toString() {
        return "Microphone [lineInfo=" + lineInfo + ", mixerInfo=" + mixerInfo
                + ", active=" + dumpActiveLineInfo() + "]";
    }

    public synchronized Microphone open() throws LineUnavailableException {
        if (line == null) {
            final Line systemLine = AudioSystem.getMixer(mixerInfo).getLine(
                    lineInfo);
            if (systemLine instanceof TargetDataLine) {
                line = (TargetDataLine) systemLine;
            } else {
                throw new LineUnavailableException(
                        "Expected line to be a microphone.");
            }
        }

        if (line.isOpen()) {
            System.out.println("Already open.");
        } else {
            line.open(new AudioFormat(96000f, 24, 1, true, true),
                    lineInfo.getMaxBufferSize());

            // final AudioFormat[] formats = lineInfo.getFormats();
            // if (formats.length == 0) {
            // throw new
            // LineUnavailableException("No audio formats available.");
            // }
            // Arrays.sort(formats, selector);
            // final AudioFormat format = formats[0];
            // line.open(format, lineInfo.getMaxBufferSize());
            System.out.println(this);
            // for (AudioFormat info: lineInfo.getFormats()) {
            // System.out.print(info);
            // System.out.print("; SR:");
            // System.out.print(info.getSampleRate());
            // System.out.print("; FS:");
            // System.out.print(info.getFrameSize());
            // System.out.print("; FR: ");
            // System.out.println(info.getFrameRate());
            // }
        }
        return this;
    }

    public String dumpActiveLineInfo() {
        if (line == null) {
            return "NONE";
        }
        return "buffer: " + line.getBufferSize() + "; encoding:"
                + line.getFormat().getEncoding() + "; sample rate:"
                + line.getFormat().getSampleRate() + "; frame size:"
                + line.getFormat().getFrameSize() + "; frame rate:"
                + line.getFormat().getFrameRate() + "; channels:"
                + line.getFormat().getChannels();
    }

    public synchronized Microphone close() {
        if (line != null && line.isOpen()) {
            line.close();
            line = null;
        }
        if (source != null) {
            source.stop();
        }
        return this;
    }

    public Microphone queueTo(BlockingQueue<SignalData> queue) {
        if (source != null) {
            source.stop();
        }
        source = new AudioReaderSource(queue,
                new AudioFormatWrangler().read(line));
        return this;
    }

    public Microphone startRecording() {
        new Thread(source).start();
        return this;
    }
}
