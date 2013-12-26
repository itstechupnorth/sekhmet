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

public enum SelectionMethod {
    RANDOM("Pick random elements", "random"), SEQUENTIAL("Pick in sequence",
            "seq");

    public static SelectionMethod toSelectionMethod(String value) {
        for (SelectionMethod method : SelectionMethod.values()) {
            if (method.getCode().equalsIgnoreCase(value)) {
                return method;
            }
        }
        return null;
    }

    private final String name;
    private final String code;

    private SelectionMethod(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
