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
import java.util.List;

public class AppPlan implements Runnable {

    public static AppPlan build(final Instructions instructions, Global global,
            final PlainText preamble, final PlainText main,
            TranscriptBuilder builder) {
        final List<Task> tasks = new ArrayList<Task>();

        tasks.add(new Task(new TranscribeMeta(builder, instructions), 0));
        tasks.add(buildTask(preamble, builder, 0, 0,
                SelectionMethod.SEQUENTIAL, false, false));
        tasks.add(buildSpacer(builder, instructions.getPreambleDelay()));
        tasks.add(buildTask(main, builder, instructions.getDelay(),
                instructions.getRepeat(), instructions.getSelection(),
                !instructions.isSingleSpace(), instructions.isPreFormatted()));

        final int startDelay = instructions.isStartDelay() ? global
                .getStartDelay() : 0;
        final int endDelay = instructions.isEndDelay() ? global.getEndDelay()
                : 0;
        final AppPlan result = new AppPlan(tasks, startDelay, endDelay);
        return result;
    }

    private static Task buildSpacer(TranscriptBuilder builder, int delay) {
        return new Task(new LineSpacer(builder), delay);
    }

    private static Task buildTask(final PlainText preamble,
            TranscriptBuilder builder, final int delay, int repeat,
            SelectionMethod selection, boolean addEol, boolean preserveSpace) {
        final List<String> lines;
        if (preserveSpace) {
            lines = preamble.plain();
        } else {
            lines = preamble.content();
        }
        final Prompts prompts = new Prompts(lines);
        if (repeat == 0) {
            repeat = prompts.getSize();
        }
        prompts.setMethod(selection);
        return new Task(new TranscribePrompt(builder, prompts, addEol), delay,
                repeat);
    }

    private final List<Task> tasks;
    private final int startDelay;
    private final int endDelay;

    public AppPlan(List<Task> tasks, int startDelay, int endDelay) {
        super();
        this.tasks = tasks;
        this.startDelay = startDelay;
        this.endDelay = endDelay;
    }

    public void run() {
        Task.delay(startDelay);
        for (Task task : tasks) {
            task.run();
        }
        Task.delay(endDelay);
    }
}
