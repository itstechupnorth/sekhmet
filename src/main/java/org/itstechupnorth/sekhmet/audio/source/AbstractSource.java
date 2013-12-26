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
import org.itstechupnorth.sekhmet.base.AbstractRunner;


public abstract class AbstractSource extends AbstractRunner {

    private final BlockingQueue<SignalData> queue;

    protected abstract void start() throws IOException;

    protected abstract AudioData read() throws IOException;

    protected abstract void prepare();

    public AbstractSource(BlockingQueue<SignalData> queue) {
        super();
        this.queue = queue;
        this.isStopping = false;
        this.isRunning = false;
    }

    @Override
    protected void work() {
        try {
            start();
            SignalData signal;
            final AudioData next = read();
            signal = SignalData.build(next);
            while (!put(signal))
                ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean put(SignalData signal) {
        try {
            queue.put(signal);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void posthumous() {
        queue.add(SignalData.END);
    }

}