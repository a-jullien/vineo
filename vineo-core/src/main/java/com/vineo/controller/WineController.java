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

package com.vineo.controller;

import com.vineo.dao.WineRepository;
import com.vineo.model.Wine;
import com.vineo.model.WinesDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("UnusedDeclaration")
@RestController
public class WineController {

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    private final WineRepository wineRepository;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    @Autowired
    public WineController(final WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    //==================================================================================================================
    // Public
    //==================================================================================================================

    /**
     * Lists all available wines
     */
    @RequestMapping(method = RequestMethod.GET, value = "/wines", produces = MediaType.APPLICATION_JSON_VALUE)
    public WinesDescriptor wines() {
        final Stream<Wine> stream = StreamSupport.stream(this.wineRepository.findAll().spliterator(), false);

        final List<Wine> wines = stream.collect(Collectors.toList());
        return new WinesDescriptor(wines);
    }

    /**
     * Returns a wine identified the specified identifier
     */
    @RequestMapping(method = RequestMethod.GET, value = "/wines/{wineId}")
    public ResponseEntity<Wine> getWineById(@PathVariable final Long wineId) {
        final Wine wine = this.wineRepository.findOne(wineId);
        if (wine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(wine, HttpStatus.OK);
        }
    }

    /**
     * Save or update a wine
     */
    @RequestMapping(method = RequestMethod.POST, value = "/wines")
    public ResponseEntity<Wine> saveOrUpdate(@RequestBody final Wine wine) {
        final Wine savedWine = this.wineRepository.save(wine);
        return new ResponseEntity<>(savedWine, HttpStatus.OK);
    }

    /**
     * Delete a wine identified by its identifier
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/wines/{wineId}")
    public void deleteWine(@PathVariable final Long wineId) {
        this.wineRepository.delete(wineId);
    }
}
