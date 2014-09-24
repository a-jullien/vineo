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

import com.vineo.model.Wine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WineRepository extends CrudRepository<Wine, Long> {

    /**
     * Returns the wine(s) from the specified name
     *
     * @param wineName the wine name
     * @return the list of wine(s) matching the name
     */
    List<Wine> findByWineName(final String wineName);
}
