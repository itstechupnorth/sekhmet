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
package org.itstechupnorth.sekhmet.audio.data;

public class SignalData {

    public static final SignalData START = new SignalData(Signal.START, null);
    public static final SignalData END = new SignalData(Signal.END, null);

    public static final SignalData build(final AudioData buffer) {
        return new SignalData(Signal.DATA, buffer);
    }

    private final Signal type;
    private final AudioData buffer;
    private final long created;

    private SignalData(Signal type, final AudioData buffer) {
        super();
        this.type = type;
        this.buffer = buffer;
        created = System.nanoTime();
    }

    public Signal getType() {
        return type;
    }

    public AudioData getBuffer() {
        return buffer;
    }

    public long getCreated() {
        return created;
    }
}
