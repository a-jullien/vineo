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

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipe_ingredient")
@AssociationOverrides({
        @AssociationOverride(name = "pk.recipe", joinColumns = @JoinColumn(name = "recipe_id", insertable = false, updatable = false)),
        @AssociationOverride(name = "pk.wine", joinColumns = @JoinColumn(name = "wine_id", insertable = false, updatable = false))})
public class Harmony implements Serializable {

    //==================================================================================================================
    // Constants
    //==================================================================================================================

    private static final long serialVersionUID = -6194033980540859994L;

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @EmbeddedId
    private final HarmonyId pk;
    private String comment;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public Harmony() {
        super();
        this.pk = new HarmonyId();
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================

    public String getComment() {
        return comment;
    }

    public Wine getWine() {
        return this.pk.getWine();
    }

    public Recipe getRecipe() {
        return this.pk.getRecipe();
    }

    //==================================================================================================================
    // Setters
    //==================================================================================================================


    public void setWine(final Wine wine) {
        this.pk.setWine(wine);
    }

    public void setRecipe(final Recipe recipe) {
        this.pk.setRecipe(recipe);
    }


    public void setComment(final String comment) {
        this.comment = comment;
    }
}
