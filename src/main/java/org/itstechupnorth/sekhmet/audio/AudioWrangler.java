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
package org.itstechupnorth.sekhmet.audio;

import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

import org.itstechupnorth.sekhmet.audio.source.mic.Microphone;


public class AudioWrangler {

    private final Set<Microphone> microphones;

    public AudioWrangler() {
        microphones = new HashSet<Microphone>();
    }

    public Set<Microphone> getMicrophones() {
        if (microphones.isEmpty()) {
            // Discover some
            for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
                final Mixer mixer = AudioSystem.getMixer(mixerInfo);
                for (Line.Info lineInfo : mixer.getTargetLineInfo()) {
                    if (lineInfo instanceof DataLine.Info) {
                        final Microphone microphone = new Microphone(
                                (DataLine.Info) lineInfo, mixerInfo);
                        microphones.add(microphone);
                    }
                }
            }
        }
        return microphones;
    }

    public void close() {
        for (final Microphone microphone : microphones) {
            microphone.close();
        }

        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            final Mixer mixer = AudioSystem.getMixer(mixerInfo);
            mixer.close();
        }
    }

    public Microphone aMic() {
        return getMicrophones().iterator().next();
    }
}
