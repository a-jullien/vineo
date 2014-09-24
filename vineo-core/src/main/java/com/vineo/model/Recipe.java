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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    //==================================================================================================================
    // Constants
    //==================================================================================================================

    private static final long serialVersionUID = 1601111772904687716L;

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private long id;
    private String recipeName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Harmony> wines;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public Recipe() {
        super();
        this.wines = new ArrayList<>();
    }

    public Recipe(final String recipeName) {
        this();
        this.recipeName = recipeName;
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================

    public long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<Harmony> getWines() {
        return wines;
    }

    //==================================================================================================================
    // Setters
    //==================================================================================================================

    public void setRecipeName(final String recipeName) {
        this.recipeName = recipeName;
    }

    public void setWines(final List<Harmony> wines) {
        this.wines = wines;
    }

    //==================================================================================================================
    // Public
    //==================================================================================================================

    public void addWine(final Wine wine) {
        final Harmony harmony = new Harmony();
        harmony.setRecipe(this);
        harmony.setWine(wine);

        this.wines.add(harmony);
    }

    public Optional<Harmony> getHarmonyFor(final Wine wine) {
        return this.wines.stream().filter((harmony) -> harmony.getWine().equals(wine)).findAny();
    }

    //==================================================================================================================
    // Override
    //==================================================================================================================

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                '}';
    }
}
