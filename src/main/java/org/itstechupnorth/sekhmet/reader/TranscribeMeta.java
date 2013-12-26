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

import java.text.DateFormat;
import java.util.Date;

public class TranscribeMeta implements Runnable {

    private final TranscriptBuilder builder;
    private final Instructions instructions;

    public TranscribeMeta(TranscriptBuilder builder, Instructions instructions) {
        super();
        this.builder = builder;
        this.instructions = instructions;
    }

    public void run() {
        builder.comment();
        builder.meta("text", instructions.getDescription());
        if (instructions.getSource() != null) {
            builder.meta("from", instructions.getSource());
        }
        builder.meta("author", instructions.getAuthor());
        builder.meta("license", instructions.getLicense().getName());
        builder.meta("date", DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(new Date()));
        builder.comment();
    }

}
