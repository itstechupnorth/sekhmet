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
package org.itstechupnorth.sekhmet.base;

public abstract class AbstractRunner implements Runnable {

    protected volatile boolean isStopping;

    protected abstract void work();

    protected abstract void prepare() throws Exception;

    protected abstract void posthumous();

    protected volatile boolean isRunning;

    public AbstractRunner() {
        super();
    }

    public void run() {
        if (!isRunning) {
            try {
                isStopping = false;
                isRunning = true;
                prepare();
                while (!isStopping) {
                    work();
                }
                posthumous();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                isStopping = false;
                isRunning = false;
            }
        }
    }

    public void stop() {
        isStopping = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

}