/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package petclinic.api.pettypes

import petclinic.model.NamedEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "types")
class PetType(id: Int? = null, name: String? = null) : NamedEntity(id, name) {

    constructor(other: PetType) : this(other.id, other.name)

    override fun toString(): String {
        return """PetType[id=$id,name="$name"]"""
    }
}
