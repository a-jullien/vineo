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

import com.vineo.dao.RecipeRepository;
import com.vineo.model.Recipe;
import com.vineo.model.RecipesDescriptor;
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
public class RecipeController {

    //==================================================================================================================
    // Attributes
    //==================================================================================================================
    private final RecipeRepository recipeRepository;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    @Autowired
    public RecipeController(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Lists all available recipes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipesDescriptor getRecipes() {
        final Stream<Recipe> stream = StreamSupport.stream(this.recipeRepository.findAll().spliterator(), false);

        final List<Recipe> recipes = stream.collect(Collectors.toList());
        return new RecipesDescriptor(recipes);
    }

    /**
     * Returns a recipe identified the specified identifier
     */
    @RequestMapping(method = RequestMethod.GET, value = "/recipes/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> getRecipeById(@PathVariable final Long recipeId) {
        final Recipe recipe = this.recipeRepository.findOne(recipeId);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    /**
     * Save or update a wine
     */
    @RequestMapping(method = RequestMethod.POST, value = "/recipes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> saveOrUpdateRecipe(@RequestBody final Recipe recipe) {
        final Recipe savedRecipe = this.recipeRepository.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
    }

    /**
     * Delete a recipe identified by its identifier
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/recipes/{recipeId}")
    public void deleteRecipe(@PathVariable final Long recipeId) {
        this.recipeRepository.delete(recipeId);
    }
}
