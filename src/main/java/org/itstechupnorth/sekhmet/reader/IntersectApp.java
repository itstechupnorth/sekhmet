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

import java.util.List;

public class IntersectApp {

    public static void main(String[] args) throws Exception {
        final List<String> one = words("guten.txt");
        final List<String> two = words("5k.txt");
        for (String word : one) {
            if (two.contains(word)) {
                System.out.println(word);
            }
        }
    }

    public static List<String> words(String name) throws Exception {
        final PlainText text = PlainText.read(name);
        final List<String> words = text.content();
        return words;
    }
}
