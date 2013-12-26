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

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

import org.itstechupnorth.sekhmet.audio.data.SignalData;


public class InputStreamSink extends InputStream {

    private static final int END_OF_STREAM = -1;

    private final BlockingQueue<SignalData> queue;

    private InputStream delegate;
    private boolean endOfStream = false;

    public InputStreamSink(BlockingQueue<SignalData> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public int read() throws IOException {
        if (endOfStream)
            return END_OF_STREAM;
        if (delegate == null) {
            next();
        }

        if (endOfStream)
            return END_OF_STREAM;
        final int result = delegate.read();
        if (result == END_OF_STREAM) {
            return read();
        }
        return result;
    }

    private void next() {
        try {
            SignalData data = queue.take();
            switch (data.getType()) {
                case END:
                    endOfStream = true;
                    break;
                case DATA:
                    delegate = data.getBuffer().inputStream();
                    break;
                default:
                    next();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            next();
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (endOfStream)
            return END_OF_STREAM;
        if (delegate == null) {
            next();
        }

        if (endOfStream)
            return END_OF_STREAM;
        final int result = delegate.read();
        if (result == END_OF_STREAM) {
            return read(b, off, len);
        }
        return result;
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (endOfStream)
            return END_OF_STREAM;
        if (delegate == null) {
            next();
        }

        if (endOfStream)
            return END_OF_STREAM;
        final int result = delegate.read();
        if (result == END_OF_STREAM) {
            return read(b);
        }
        return result;
    }
}
