/**
 * Copyright (c) 2014 Antoine Jullien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vineo.utils;

import com.vineo.model.Harmony;
import com.vineo.model.Recipe;
import com.vineo.model.Wine;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    //==================================================================================================================
    // Constants
    //==================================================================================================================
    private static final String SEPARATOR = "\t";


    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    private final String filePath;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public CSVParser(final String filePath) {
        this.filePath = filePath;
    }

    public List<Harmony> extractHarmonies() throws FileNotFoundException {
        final List<Harmony> harmonies = new ArrayList<>();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(this.filePath)));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                final String[] record = line.split(SEPARATOR);

                final Harmony harmony = this.createHarmony(record);
                harmonies.add(harmony);
            }


        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        return harmonies;
    }

    //==================================================================================================================
    // Private
    //==================================================================================================================

    private Harmony createHarmony(final String[] record) {
        final Harmony harmony = new Harmony();
        final Recipe recipe = new Recipe(record[0]);
        harmony.setRecipe(recipe);
        harmony.setWine(new Wine(record[2]));
        return harmony;
    }

    //==================================================================================================================
    // MAIN
    //==================================================================================================================

    public static void main(final String[] args) throws FileNotFoundException {
        final URL resource = CSVParser.class.getClass().getResource("/accords.csv");
        final CSVParser csvParser = new CSVParser(resource.getFile());
        final List<Harmony> harmonies = csvParser.extractHarmonies();
        harmonies.forEach(System.out::println);
    }
}
