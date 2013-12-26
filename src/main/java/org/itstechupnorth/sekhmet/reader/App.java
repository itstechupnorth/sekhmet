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

public class App {

    private static final String DEFAULT_PACK = "sekhmet.reader.";
    private static final String DEFAULT_APP = DEFAULT_PACK + "mono";

    public static void main(String[] args) throws Exception {
        final String bundleName;

        if (args.length == 0) {
            bundleName = DEFAULT_APP;
        } else {
            if (args[0].contains(".")) {
                bundleName = args[0];
            } else {
                bundleName = DEFAULT_PACK + args[0];
            }
        }
        new AppWrangler("sekhmet.reader").start(bundleName);
    }
}
