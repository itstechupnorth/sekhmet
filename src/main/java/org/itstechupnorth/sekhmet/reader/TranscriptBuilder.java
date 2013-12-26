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

public class TranscriptBuilder {

    private static final int INITIAL_BUFFER_CAPACITY = 1024;
    private final StringBuilder builder;

    public TranscriptBuilder() {
        builder = new StringBuilder(INITIAL_BUFFER_CAPACITY);
    }

    public TranscriptBuilder meta(final String name, final String value) {
        comment(name + ": " + value);
        return this;
    }

    public TranscriptBuilder comment(final String comment) {
        builderComment().append(' ').append(comment);
        builderEol();
        return this;
    }

    private StringBuilder builderComment() {
        return builder.append('#');
    }

    public TranscriptBuilder preamble(final String text) {
        return prompt(text).eol();
    }

    public TranscriptBuilder prompt(final String text) {
        return print(text).eol();
    }

    protected TranscriptBuilder print(final String text) {
        builder.append(text);
        System.out.print(text);
        return this;
    }

    protected TranscriptBuilder eol() {
        builderEol();
        System.out.println();
        return this;
    }

    private void builderEol() {
        builder.append('\r');
    }

    public String transcript() {
        return builder.toString();
    }

    public TranscriptBuilder comment() {
        builderComment();
        builderEol();
        return this;
    }
}
