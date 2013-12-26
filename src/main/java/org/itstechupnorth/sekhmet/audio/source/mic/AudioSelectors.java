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

import java.util.Comparator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;

public class AudioSelectors {

    private final static Comparator<AudioFormat> CHANNELS = new Comparator<AudioFormat>() {

        public int compare(AudioFormat audio1, AudioFormat audio2) {
            return audio1.getChannels() - audio2.getChannels();
        }

    };

    private final static Comparator<AudioFormat> PCM_UNSIGNED = new Comparator<AudioFormat>() {

        public int compare(AudioFormat audio1, AudioFormat audio2) {
            final Encoding encoding1 = audio1.getEncoding();
            final Encoding encoding2 = audio2.getEncoding();
            if (encoding1 == encoding2)
                return 0;
            if (encoding1 == Encoding.PCM_UNSIGNED) {
                return -1;
            } else {
                return 1;
            }
        }

    };

    private final static Comparator<AudioFormat> FRAME_RATE = new Comparator<AudioFormat>() {

        public int compare(AudioFormat audio1, AudioFormat audio2) {
            if (audio1.getFrameRate() == audio2.getFrameRate())
                return 0;
            if (audio1.getFrameRate() == AudioSystem.NOT_SPECIFIED)
                return 1;
            return audio1.getFrameRate() > audio2.getFrameRate() ? -1 : 1;
        }

    };

    private final static Comparator<AudioFormat> SAMPLE_RATE = new Comparator<AudioFormat>() {

        public int compare(AudioFormat audio1, AudioFormat audio2) {
            if (audio1.getSampleRate() == audio2.getSampleRate())
                return 0;
            if (audio1.getSampleRate() == AudioSystem.NOT_SPECIFIED)
                return 1;
            return audio1.getSampleRate() > audio2.getSampleRate() ? -1 : 1;
        }

    };

    private final static Comparator<AudioFormat> HIGH_MONO_PCM_UNSIGNED = new Comparator<AudioFormat>() {

        public int compare(AudioFormat audio1, AudioFormat audio2) {
            final int channels = CHANNELS.compare(audio1, audio2);
            if (channels == 0) {
                final int format = PCM_UNSIGNED.compare(audio1, audio2);
                if (format == 0) {
                    return SAMPLE_RATE.compare(audio1, audio2);
                }
                return format;
            }
            return channels;
        }

    };

    public static Comparator<AudioFormat> channels() {
        return CHANNELS;
    }

    public static Comparator<AudioFormat> pcmUnsigned() {
        return PCM_UNSIGNED;
    }

    public static Comparator<AudioFormat> frameRate() {
        return FRAME_RATE;
    }

    public static Comparator<AudioFormat> sampleRate() {
        return SAMPLE_RATE;
    }

    public static Comparator<AudioFormat> highMonoPCM() {
        return HIGH_MONO_PCM_UNSIGNED;
    }
}
