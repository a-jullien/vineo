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

package com.vineo;

import com.vineo.dao.RecipeRepository;
import com.vineo.dao.WineRepository;
import com.vineo.model.Harmony;
import com.vineo.model.Opinion;
import com.vineo.model.Recipe;
import com.vineo.model.Wine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RecipeWineHarmonyTests {
    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private WineRepository wineRepository;

    @Before
    public void setUp() throws Exception {
        this.recipeRepository.deleteAll();
        this.wineRepository.deleteAll();
    }

    //==================================================================================================================
    // Tests
    //==================================================================================================================


    @Test
    public void shouldSaveSuccessfullyHarmony() throws Exception {
        final Recipe recipe = new Recipe("moussaka");
        final Recipe savedMoussaka = this.recipeRepository.save(recipe);

        final Wine wine = new Wine("Minervois");
        final Wine savedWine = this.wineRepository.save(wine);
        savedMoussaka.addWine(savedWine);

        this.recipeRepository.save(savedMoussaka);

        final Recipe loadedRecipe = this.recipeRepository.findOne(savedMoussaka.getId());
        final List<Harmony> associatedWines = loadedRecipe.getWines();
        assertThat(associatedWines.size()).isEqualTo(1);
        final Harmony harmony = associatedWines.get(0);
        assertThat(harmony.getRecipe()).isEqualTo(savedMoussaka);
        assertThat(harmony.getWine()).isEqualTo(savedWine);
    }

    @Test
    public void shouldAddSuccessfullyAnOpinionToAnHarmony() {
        final Recipe recipe = new Recipe("moussaka");
        final Recipe savedRecipe = this.recipeRepository.save(recipe);

        final Wine minervois = new Wine("Minervois");
        final Wine savedMinervois = this.wineRepository.save(minervois);

        savedRecipe.addWine(savedMinervois);

        this.recipeRepository.save(savedRecipe);

        final Recipe loadedRecipe = this.recipeRepository.findOne(savedRecipe.getId());
        final Optional<Harmony> harmonyFor = loadedRecipe.getHarmonyFor(savedMinervois);
        assertThat(harmonyFor.isPresent());

        final Harmony harmony = harmonyFor.get();
        final Opinion excellent = new Opinion(4, "Excellent");
        harmony.addOpinion(excellent);

        this.recipeRepository.save(loadedRecipe);

        final Recipe moussaka = this.recipeRepository.findOne(loadedRecipe.getId());
        final List<Opinion> opinions = moussaka.getHarmonyFor(minervois).get().getOpinions();
        assertThat(opinions.size()).isEqualTo(1);
        assertThat(opinions.get(0).getStars()).isEqualTo(4);
        assertThat(opinions.get(0).getComment()).isEqualTo("Excellent");
    }
}
