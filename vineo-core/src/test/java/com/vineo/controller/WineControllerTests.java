/*
 * *
 *  * Copyright (c) 2014 Antoine Jullien
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.vineo.controller;

import com.vineo.Application;
import com.vineo.dao.WineRepository;
import com.vineo.model.Wine;
import com.vineo.model.WineCategory;
import com.vineo.model.WinesDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WineControllerTests {

    //==================================================================================================================
    // Constants
    //==================================================================================================================
    final String BASE_URL = "http://localhost:8089/wines/";

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Autowired
    private WineRepository wineRepository;

    private TestRestTemplate rest;

    @Before
    public void setUp() throws Exception {
        this.rest = new TestRestTemplate();
        this.wineRepository.deleteAll();
    }

    //==================================================================================================================
    // Tests
    //==================================================================================================================

    @Test
    public void shouldCreateSuccessfullyAWine() {
        final Wine wine = createWine("Condrieu");
        final ResponseEntity<Wine> response =
                this.rest.postForEntity(BASE_URL, wine, Wine.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isGreaterThan(0);
        assertThat(response.getBody().getWineName()).isEqualTo("Condrieu");
        assertThat(response.getBody().getWineCategory()).isEqualTo(WineCategory.WHITE);
    }

    @Test
    public void shouldListWinesAfterCreatedOne() throws Exception {
        final Wine wine = createWine("Condrieu");

        this.rest.postForEntity(BASE_URL, wine, Wine.class);

        final ResponseEntity<WinesDescriptor> winesDescriptorResponse = this.rest.getForEntity(BASE_URL, WinesDescriptor.class);
        assertThat(winesDescriptorResponse).isNotNull();
        assertThat(winesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final List<Wine> wines = winesDescriptorResponse.getBody().getWines();
        assertThat(wines.size()).isEqualTo(1);
        assertThat(wines.get(0).getWineName()).isEqualTo("Condrieu");
        assertThat(wines.get(0).getWineCategory()).isEqualTo(WineCategory.WHITE);
        assertThat(wines.get(0).getId()).isGreaterThan(0);
    }

    @Test
    public void shouldUpdateSuccessfullyAnExistingWine() {
        final Wine wine = createWine("Condrieu");

        final ResponseEntity<Wine> wineResponseEntity = this.rest.postForEntity(BASE_URL, wine, Wine.class);
        final Wine savedWine = wineResponseEntity.getBody();

        // update the wine
        savedWine.setWineCategory(WineCategory.RED);

        this.rest.postForEntity(BASE_URL, savedWine, Wine.class);

        final ResponseEntity<Wine> winesDescriptorResponse = this.rest.getForEntity(BASE_URL + savedWine.getId(), Wine.class);
        assertThat(winesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(winesDescriptorResponse.getBody().getWineCategory()).isEqualTo(WineCategory.RED);
    }

    @Test
    public void shouldDeleteSuccessfullyAnExistingWine() {
        final Wine wine = createWine("Condrieu");

        final ResponseEntity<Wine> wineResponseEntity = this.rest.postForEntity(BASE_URL, wine, Wine.class);
        final Wine savedWine = wineResponseEntity.getBody();

        final ResponseEntity<Wine> winesDescriptorResponse = this.rest.getForEntity(BASE_URL + savedWine.getId(), Wine.class);
        assertThat(winesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        this.rest.delete(BASE_URL + savedWine.getId());

        final ResponseEntity<Wine> reloadedResponse = this.rest.getForEntity(BASE_URL + savedWine.getId(), Wine.class);
        assertThat(reloadedResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //==================================================================================================================
    // Private
    //==================================================================================================================

    private Wine createWine(final String wineName) {
        final Wine wine = new Wine(wineName);
        wine.setWineCategory(WineCategory.WHITE);
        return wine;
    }
}
