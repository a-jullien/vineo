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

package com.vineo.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class HarmonyId implements Serializable{

    //==================================================================================================================
    // Constants
    //==================================================================================================================

    private static final long serialVersionUID = -8374898017973213679L;

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Wine wine;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public HarmonyId() {
        super();
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================


    public Recipe getRecipe() {
        return recipe;
    }

    public Wine getWine() {
        return wine;
    }

    //==================================================================================================================
    // Setters
    //==================================================================================================================

    public void setWine(final Wine wine) {
        this.wine = wine;
    }

    public void setRecipe(final Recipe recipe) {
        this.recipe = recipe;
    }
}
