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

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "wine")
public class Wine implements Serializable {

    //==================================================================================================================
    // Constants
    //==================================================================================================================

    private static final long serialVersionUID = -6470874174661491262L;

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wine_id")
    private Long id;
    private String wineName;
    private WineCategory wineCategory;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.wine", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Harmony> recipes;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public Wine() {
        super();
        this.recipes = new ArrayList<>();
    }

    public Wine(final String wineName) {
        this();
        this.wineName = wineName;
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================

    public String getWineName() {
        return wineName;
    }

    public Long getId() {
        return id;
    }

    public WineCategory getWineCategory() {
        return wineCategory;
    }

    public List<Harmony> getRecipes() {
        return recipes;
    }

    //==================================================================================================================
    // Setters
    //==================================================================================================================

    public void setId(final Long id) {
        this.id = id;
    }

    public void setWineName(final String wineName) {
        this.wineName = wineName;
    }

    public void setWineCategory(final WineCategory wineCategory) {
        this.wineCategory = wineCategory;
    }

    public void setRecipes(final List<Harmony> recipes) {
        this.recipes = recipes;
    }

    //==================================================================================================================
    // Public
    //==================================================================================================================

    public void addRecipe(final Recipe recipe) {
        final Harmony harmony = new Harmony();
        harmony.setRecipe(recipe);
        harmony.setWine(this);

        this.recipes.add(harmony);
    }

    //==================================================================================================================
    // Override
    //==================================================================================================================


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Wine wine = (Wine) o;

        if (id != null ? !id.equals(wine.id) : wine.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Wine{" +
                "id=" + id +
                ", wineName='" + wineName + '\'' +
                '}';
    }
}
