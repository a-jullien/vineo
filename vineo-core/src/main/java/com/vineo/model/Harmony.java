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

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harmony")
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "harmony", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Opinion> opinions;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public Harmony() {
        super();
        this.pk = new HarmonyId();
        this.opinions = new ArrayList<>();
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================

    public Wine getWine() {
        return this.pk.getWine();
    }

    public Recipe getRecipe() {
        return this.pk.getRecipe();
    }

    public List<Opinion> getOpinions() {
        return opinions;
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

    public void setOpinions(final List<Opinion> opinions) {
        this.opinions = opinions;
    }

    //==================================================================================================================
    // Public
    //==================================================================================================================

    public void addOpinion(final Opinion opinion) {
        opinion.setHarmony(this);
        this.opinions.add(opinion);
    }

}
