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
package org.itstechupnorth.sekhmet.reader;

public class Task implements Runnable {

    public static final int MILLIS_IN_A_SECOND = 1000;

    private final Runnable job;
    private final int delayInMillis;
    private final int repeats;

    public Task(final Runnable job, final int delay) {
        this(job, delay, 1);
    }

    public Task(final Runnable job, final int delayInSeconds, final int repeats) {
        super();
        this.job = job;
        this.delayInMillis = delayInSeconds * MILLIS_IN_A_SECOND;
        this.repeats = repeats;
    }

    public void run() {
        for (int i = 0; i < repeats; i++) {
            job.run();
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delay(final int delayInSeconds) {
        try {
            Thread.sleep(delayInSeconds * MILLIS_IN_A_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
