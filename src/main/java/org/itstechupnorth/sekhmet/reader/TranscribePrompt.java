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

public class TranscribePrompt implements Runnable {

    private final TranscriptBuilder transcript;
    private final Prompts prompts;
    private final boolean extraEol;

    public TranscribePrompt(TranscriptBuilder transcript, Prompts prompts,
            boolean extraEol) {
        super();
        this.transcript = transcript;
        this.prompts = prompts;
        this.extraEol = extraEol;
    }

    public void run() {
        if (extraEol) {
            transcript.eol();
        }
        transcript.prompt(prompts.next());
    }

}
