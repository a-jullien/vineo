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
import com.vineo.dao.RecipeRepository;
import com.vineo.model.Recipe;
import com.vineo.model.RecipesDescriptor;
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
public class RecipeControllerTests {

    //==================================================================================================================
    // Constants
    //==================================================================================================================
    final String BASE_URL = "http://localhost:8089/recipes/";

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Autowired
    private RecipeRepository recipeRepository;

    private TestRestTemplate rest;

    @Before
    public void setUp() throws Exception {
        this.rest = new TestRestTemplate();
        this.recipeRepository.deleteAll();
    }

    //==================================================================================================================
    // Tests
    //==================================================================================================================

    @Test
    public void shouldCreateSuccessfullyARecipe() {
        final Recipe recipe = createRecipe("Moussaka");
        final ResponseEntity<Recipe> response =
                this.rest.postForEntity(BASE_URL, recipe, Recipe.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isGreaterThan(0);
        assertThat(response.getBody().getRecipeName()).isEqualTo("Moussaka");
    }

    @Test
    public void shouldListRecipesAfterCreatedOne() throws Exception {
        final Recipe recipe = createRecipe("Moussaka");

        this.rest.postForEntity(BASE_URL, recipe, Recipe.class);

        final ResponseEntity<RecipesDescriptor> winesDescriptorResponse = this.rest.getForEntity(BASE_URL, RecipesDescriptor.class);
        assertThat(winesDescriptorResponse).isNotNull();
        assertThat(winesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final List<Recipe> recipes = winesDescriptorResponse.getBody().getRecipes();
        assertThat(recipes.size()).isEqualTo(1);
        assertThat(recipes.get(0).getRecipeName()).isEqualTo("Moussaka");
        assertThat(recipes.get(0).getId()).isGreaterThan(0);
    }

    @Test
    public void shouldUpdateSuccessfullyAnExistingRecipe() {
        final Recipe recipe = createRecipe("Moussaka");

        final ResponseEntity<Recipe> recipeResponseEntity = this.rest.postForEntity(BASE_URL, recipe, Recipe.class);
        final Recipe savedRecipe = recipeResponseEntity.getBody();

        // update the wine
        savedRecipe.setRecipeName("Moussaka de l'espace");

        this.rest.postForEntity(BASE_URL, savedRecipe, Recipe.class);

        final ResponseEntity<Recipe> recipesDescriptorResponse = this.rest.getForEntity(BASE_URL + savedRecipe.getId(), Recipe.class);
        assertThat(recipesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recipesDescriptorResponse.getBody().getRecipeName()).isEqualTo("Moussaka de l'espace");
    }

    @Test
    public void shouldDeleteSuccessfullyAnExistingRecipe() {
        final Recipe recipe = createRecipe("Condrieu");

        final ResponseEntity<Recipe> recipeResponseEntity = this.rest.postForEntity(BASE_URL, recipe, Recipe.class);
        final Recipe savedRecipe = recipeResponseEntity.getBody();

        final ResponseEntity<Recipe> recipesDescriptorResponse = this.rest.getForEntity(BASE_URL + savedRecipe.getId(), Recipe.class);
        assertThat(recipesDescriptorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        this.rest.delete(BASE_URL + savedRecipe.getId());

        final ResponseEntity<Recipe> reloadedResponse = this.rest.getForEntity(BASE_URL + savedRecipe.getId(), Recipe.class);
        assertThat(reloadedResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //==================================================================================================================
    // Private
    //==================================================================================================================

    private Recipe createRecipe(final String recipeName) {
        final Recipe recipe = new Recipe(recipeName);

        return recipe;
    }
}
