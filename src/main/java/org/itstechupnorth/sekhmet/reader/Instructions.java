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

import java.util.Map;

public class Instructions {

    private static final String PREFORMATTED = "preformatted";
    private static final String DELAY_END = "delay.end";
    private static final String DELAY_START = "delay.start";
    private static final String NO = "no";
    private static final String SINGLE = "single";
    private static final String SPACE = "space";
    private static final int DEFAULT_INT_PROPERTY_VALUE = 0;
    private static final String SOURCE = "source";
    private static final String REMOVE_DUPLICATES = "remove_duplicates";
    private static final String REPEAT = "repeat";
    private static final String SELECTION = "selection";
    private static final String DESCRIPTION = "description";
    private static final String YES = "yes";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_DOB = "author.dob";
    private static final String AUTHOR_DOD = "author.dod";
    private static final String LICENSE = "license";
    private static final String DELAY = "delay.prompt";
    private static final String PREAMBLE_DELAY = "delay.preamble";

    public static Instructions build(Map<String, String> props) {
        final int delay = parseInt(props, DELAY);
        final int preambleDelay = parseInt(props, PREAMBLE_DELAY);
        final License license = License.toLicense(props.get(LICENSE));
        final String author = props.get(AUTHOR);
        final boolean removeDuplicates = YES.equalsIgnoreCase(props
                .get(REMOVE_DUPLICATES));
        final boolean preformatted = YES.equalsIgnoreCase(props
                .get(PREFORMATTED));
        final String description = props.get(DESCRIPTION);
        final int repeat = parseInt(props, REPEAT);
        final int dob = parseInt(props, AUTHOR_DOB);
        final int dod = parseInt(props, AUTHOR_DOD);
        final String source = props.get(SOURCE);
        final boolean singleSpace = SINGLE.equalsIgnoreCase(props.get(SPACE));
        final boolean startDelay = !NO.equalsIgnoreCase(props.get(DELAY_START));
        final boolean endDelay = !NO.equalsIgnoreCase(props.get(DELAY_END));
        final SelectionMethod method = SelectionMethod.toSelectionMethod(props
                .get(SELECTION));
        return new Instructions(delay, preambleDelay, license, author,
                removeDuplicates, description, repeat, method, dob, dod,
                source, singleSpace, startDelay, endDelay, preformatted);
    }

    private static int parseInt(Map<String, String> props, final String prop) {
        final String value = props.get(prop);
        if (value == null) {
            return DEFAULT_INT_PROPERTY_VALUE;
        }
        return Integer.parseInt(value);
    }

    private final int preambleDelay;
    private final int delay;
    private final License license;
    private final String authorName;
    private final boolean removeDuplicates;
    private final String description;
    private final int repeat;
    private final SelectionMethod selection;
    private final int dateOfBirth;
    private final int dateOfDeath;
    private final String source;
    private final String author;
    private final boolean singleSpace;
    private final boolean endDelay;
    private final boolean startDelay;
    private final boolean preformatted;

    public Instructions(int delay, int preambleDelay, License license,
            String author, boolean removeDuplicates, String description,
            int repeat, SelectionMethod selection, int dateOfBirth,
            int dateOfDeath, String book, boolean singleSpace,
            boolean startDelay, boolean endDelay, boolean preformatted) {
        super();
        this.preambleDelay = preambleDelay;
        this.delay = delay;
        this.license = license;
        this.authorName = author;
        this.removeDuplicates = removeDuplicates;
        this.description = description;
        this.repeat = repeat;
        this.selection = selection;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.source = book;
        this.author = buildAuthor(author, dateOfBirth, dateOfDeath).toString();
        this.singleSpace = singleSpace;
        this.endDelay = endDelay;
        this.startDelay = startDelay;
        this.preformatted = preformatted;
    }

    private StringBuilder buildAuthor(String author, int dateOfBirth,
            int dateOfDeath) {
        final StringBuilder authorBuilder = new StringBuilder(author);
        if (dateOfBirth > 0) {
            authorBuilder.append(' ').append(dateOfBirth).append("-");
        } else if (dateOfDeath > 0) {
            authorBuilder.append("-");
        }
        if (dateOfDeath > 0) {
            authorBuilder.append(dateOfDeath);
        }
        return authorBuilder;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public int getDateOfDeath() {
        return dateOfDeath;
    }

    public String getSource() {
        return source;
    }

    public int getDelay() {
        return delay;
    }

    public License getLicense() {
        return license;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isRemoveDeplicates() {
        return removeDuplicates;
    }

    public String getDescription() {
        return description;
    }

    public int getRepeat() {
        return repeat;
    }

    public boolean isSingleSpace() {
        return singleSpace;
    }

    public boolean isEndDelay() {
        return endDelay;
    }

    public boolean isStartDelay() {
        return startDelay;
    }

    public SelectionMethod getSelection() {
        return selection;
    }

    public int getPreambleDelay() {
        return preambleDelay;
    }

    public boolean isPreFormatted() {
        return preformatted;
    }

    @Override
    public String toString() {
        return "Instructions [author=" + author + ", authorName=" + authorName
                + ", dateOfBirth=" + dateOfBirth + ", dateOfDeath="
                + dateOfDeath + ", delay=" + delay + ", description="
                + description + ", license=" + license + ", preambleDelay="
                + preambleDelay + ", removeDuplicates=" + removeDuplicates
                + ", repeat=" + repeat + ", selection=" + selection
                + ", source=" + source + "]";
    }
}
