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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompositeTask implements Runnable {

    public static Runnable sequence(Task first, Task second) {
        final List<Task> tasks = new ArrayList<Task>();
        tasks.add(first);
        tasks.add(second);
        return new CompositeTask(tasks);
    }

    private final Collection<Task> tasks;

    public CompositeTask(Collection<Task> tasks) {
        super();
        this.tasks = tasks;
    }

    public void run() {
        for (Task task : tasks) {
            task.run();
        }
    }
}
