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
package org.itstechupnorth.sekhmet.audio.sink;

import java.util.concurrent.BlockingQueue;

import org.itstechupnorth.sekhmet.audio.data.AudioData;
import org.itstechupnorth.sekhmet.audio.data.SignalData;
import org.itstechupnorth.sekhmet.base.AbstractRunner;


public abstract class AbstractSink extends AbstractRunner {

    private final BlockingQueue<SignalData> queue;

    protected abstract void use(final AudioData buffer);

    public AbstractSink(BlockingQueue<SignalData> queue) {
        super();
        this.queue = queue;
    }

    @Override
    protected void prepare() throws Exception {
    }

    @Override
    protected void work() {
        try {
            final SignalData data = queue.take();
            switch (data.getType()) {
                case DATA:
                    final AudioData buffer = data.getBuffer();
                    use(buffer);
                    break;
                case END:
                    stop();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}