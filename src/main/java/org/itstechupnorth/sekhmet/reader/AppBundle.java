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

import java.io.IOException;
import java.util.Map;

public class AppBundle {

    private final String name;
    private final String path;
    private final Global global;
    private final Directory directory;

    public AppBundle(String name, final Global global, final Directory directory) {
        super();
        this.name = name;
        this.global = global;
        this.directory = directory;
        path = Global.toPath(name);
    }

    @Override
    public String toString() {
        return "AppBundle [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppBundle other = (AppBundle) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public void start() throws IOException {
        final PlainText main = global.readMain(path);
        final Map<String, String> props = main.properties();

        final Instructions instructions = Instructions.build(props);
        // System.out.println(instructions);
        final TranscriptBuilder builder = new TranscriptBuilder();
        final AppPlan plan = AppPlan.build(instructions, global,
                global.readBoiler(path), global.readPrompts(path), builder);
        final Storage storage = directory.newStorage(name);

        plan.run();

        storage.transcript(builder.transcript());
        storage.license(global.readLicense(path).all());
    }
}
