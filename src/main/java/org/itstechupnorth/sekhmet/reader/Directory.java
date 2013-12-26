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

import java.io.File;

public class Directory {

    public Storage newStorage(String name) {
        final File appDirectory = new File(
                "sekhmet/user/robertburrelldonkin/audio/" + name);
        if (!appDirectory.exists()) {
            if (!appDirectory.mkdirs()) {
                throw new RuntimeException("Cannot make directory "
                        + appDirectory.getAbsolutePath());
            }
        }

        int count = 1;
        File freeSubDirectory = null;
        while (freeSubDirectory == null) {
            freeSubDirectory = new File(appDirectory, Integer.toString(count++));
            if (freeSubDirectory.exists()) {
                freeSubDirectory = null;
            }
        }

        return new Storage(freeSubDirectory);
    }
}
