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

public enum License {

    CC0("CC0", "Creative Commons Public Domain 1.0"), CC_PD3("CC-PD3",
            "Creative Commons Public Domain Certification 3.0"), GPL("GPL",
            "GNU Public License");

    public static License toLicense(String value) {
        for (License license : License.values()) {
            if (license.getCode().equalsIgnoreCase(value)) {
                return license;
            }
        }
        return null;
    }

    private final String code;
    private final String name;

    private License(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
