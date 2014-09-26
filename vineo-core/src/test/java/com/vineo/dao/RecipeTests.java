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

package com.vineo.dao;

import com.vineo.Application;
import com.vineo.dao.RecipeRepository;
import com.vineo.model.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RecipeTests {
    //==================================================================================================================
    // Attributes
    //==================================================================================================================


    @Autowired
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        this.recipeRepository.deleteAll();
    }

    //==================================================================================================================
    // Tests
    //==================================================================================================================

    @Test
    public void shouldSaveSuccessfullyARecipe() {
        final Recipe recipe = new Recipe("moussaka");
        final Recipe savedMoussaka = this.recipeRepository.save(recipe);
        assertThat(savedMoussaka).isNotNull();
        assertThat(savedMoussaka.getId()).isGreaterThan(0);

        assertThat(this.recipeRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteSuccessfullyARecipe() {
        final Recipe recipe = new Recipe("moussaka");
        final Recipe savedMoussaka = this.recipeRepository.save(recipe);
        assertThat(this.recipeRepository.count()).isEqualTo(1);

        this.recipeRepository.delete(savedMoussaka);
        assertThat(this.recipeRepository.count()).isEqualTo(0);
    }

    @Test
    public void shouldUpdateSuccessfullyARecipe() {
        final Recipe recipe = new Recipe("moussaka");
        final Recipe savedMoussaka = this.recipeRepository.save(recipe);
        assertThat(this.recipeRepository.count()).isEqualTo(1);

        savedMoussaka.setRecipeName("moussaka à la reine");
        this.recipeRepository.save(savedMoussaka);

        final Recipe updatedRecipe = this.recipeRepository.findOne(savedMoussaka.getId());
        assertThat(updatedRecipe.getRecipeName()).isEqualTo("moussaka à la reine");
    }
}
