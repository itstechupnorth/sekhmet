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

import java.util.concurrent.LinkedBlockingDeque;

import org.itstechupnorth.sekhmet.audio.data.SignalData;
import org.itstechupnorth.sekhmet.audio.sink.TraceSink;
import org.itstechupnorth.sekhmet.audio.store.AudioStorage;
import org.itstechupnorth.sekhmet.base.Constants;


public class App {
    public static void main(String[] args) throws Exception {

        System.out.println(Constants.VERSION);

        AudioWrangler wrangler = new AudioWrangler();

        AudioStorage storage = AudioStorage.buildDefault();

        try {
            LinkedBlockingDeque<SignalData> queue = new LinkedBlockingDeque<SignalData>();
            // CacheSink sink = new CacheSink(queue);
            TraceSink sink = new TraceSink(queue);
            // InputStreamSink sink = new InputStreamSink(queue);
            new Thread(sink).start();

            wrangler.aMic().open().queueTo(queue).startRecording();

            // AudioFormat format = mic.getLineFormat();

            int count = 0;
            while (count++ < 50) {
                // System.out.println(sink.read());
                // System.out.println(sink.count());
                // System.out.println(count);
                Thread.sleep(100);
            }

        } finally {

            wrangler.close();
            storage.close();
            System.out.println("Tidying");
            Thread.sleep(2000);
            System.out.println("Done");
            System.exit(0);
        }
    }
}
