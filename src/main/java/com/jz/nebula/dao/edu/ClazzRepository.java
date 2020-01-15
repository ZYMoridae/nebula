/*
 * Copyright (c) 2019. Nebula Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jz.nebula.dao.edu;

import com.jz.nebula.entity.edu.Clazz;
import com.jz.nebula.entity.edu.ClazzCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ClazzRepository extends PagingAndSortingRepository<Clazz, Long> {
    Page<Clazz> findAllByOrderByIdAsc(Pageable pageable);

    @Query("select c from Clazz c where c.clazzCategoryId = ?1 and lower(c.name) like %?2%")
    Page<Clazz> findByClazzCategoryIdAndNameContaining(@Param("clazzCategoryId") long clazzCategoryId, @Param("keyword") String keyword, Pageable pageable);
}
