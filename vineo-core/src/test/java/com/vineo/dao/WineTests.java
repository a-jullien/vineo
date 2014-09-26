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
import com.vineo.dao.WineRepository;
import com.vineo.model.Wine;
import com.vineo.model.WineCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class WineTests {

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Autowired
    private WineRepository wineRepository;

    @Before
    public void setUp() throws Exception {
        this.wineRepository.deleteAll();
    }

    //==================================================================================================================
    // Tests
    //==================================================================================================================

    @Test
	public void shouldSaveSuccessfullyAWine() {
        final Wine condrieu = new Wine("condrieu");

        final Wine savedWine = this.wineRepository.save(condrieu);

        assertThat(savedWine.getId()).isNotNull();
        assertThat(savedWine.getId()).isGreaterThan(0);

        assertThat(this.wineRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteSuccessfullyAWine() {
        final Wine condrieu = new Wine("condrieu");
        final Wine savedWine = wineRepository.save(condrieu);
        assertThat(this.wineRepository.count()).isEqualTo(1);

        this.wineRepository.delete(savedWine);
        assertThat(this.wineRepository.count()).isEqualTo(0);
    }

    @Test
    public void shouldUpdateSuccessfullyAWine() {
        final Wine condrieu = new Wine("condrieu");
        final Wine savedWine = wineRepository.save(condrieu);
        assertThat(this.wineRepository.count()).isEqualTo(1);
        assertThat(savedWine.getWineCategory()).isNull();

        savedWine.setWineCategory(WineCategory.WHITE);

        final Wine wineWithCategory = this.wineRepository.save(savedWine);
        assertThat(wineWithCategory.getWineCategory()).isEqualTo(WineCategory.WHITE);
    }
}
