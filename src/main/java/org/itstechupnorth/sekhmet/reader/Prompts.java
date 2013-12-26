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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Prompts {

    private final Random random;
    private final List<String> prompts;
    private final int size;

    private int count;

    private SelectionMethod method = SelectionMethod.RANDOM;

    public Prompts(final Collection<String> prompts) {
        this.prompts = Collections.unmodifiableList(new ArrayList<String>(
                prompts));
        random = new Random();
        this.size = prompts.size();
        count = 0;
    }

    public SelectionMethod getMethod() {
        return method;
    }

    public void setMethod(SelectionMethod method) {
        this.method = method;
    }

    public String next() {
        switch (this.method) {
            case RANDOM:
                return prompts.get(random.nextInt(prompts.size()));
            case SEQUENTIAL:
                return prompts.get(count++ % size);
            default:
                throw new IllegalArgumentException("Unsupported");
        }
    }

    public int getSize() {
        return size;
    }
}
