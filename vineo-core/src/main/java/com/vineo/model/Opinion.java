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

package com.vineo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "opinion")
public class Opinion implements Serializable {

    //==================================================================================================================
    // Constants
    //==================================================================================================================

    private static final long serialVersionUID = -4529446584994109664L;

    //==================================================================================================================
    // Attributes
    //==================================================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int stars; // stars between 0 and 4, 0 is not a good opinion, 4 is very good
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Harmony harmony;

    //==================================================================================================================
    // Constructors
    //==================================================================================================================

    public Opinion() {
        super();
    }

    public Opinion(final int stars, final String comment) {
        this();
        this.stars = stars;
        this.comment = comment;
    }

    //==================================================================================================================
    // Getters
    //==================================================================================================================


    public Long getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public Harmony getHarmony() {
        return harmony;
    }

    //==================================================================================================================
    // Setters
    //==================================================================================================================


    public void setId(final Long id) {
        this.id = id;
    }

    public void setStars(final int stars) {
        this.stars = stars;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setHarmony(final Harmony harmony) {
        this.harmony = harmony;
    }
}
