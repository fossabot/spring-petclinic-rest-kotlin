/*
 * Copyright 2016-2017 the original author or authors.
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

package petclinic.api.owner

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@CrossOrigin(exposedHeaders = ["errors, content-type"])
@RequestMapping("/api/owners")
class OwnerController(val ownerService: OwnerService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getOwners() =
        ownerService.findAllOwners()

    @GetMapping("/*/lastname/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    fun getOwnersList(@PathVariable("lastName") ownerLastName: String) =
        ownerService.findOwnerByLastName(ownerLastName)

    @GetMapping("/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOwner(@PathVariable("ownerId") ownerId: Int) =
        ownerService.findOwnerById(ownerId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping
    fun addOwner(
        @RequestBody @Valid owner: Owner,
        ucBuilder: UriComponentsBuilder
    ): ResponseEntity<Owner> {
        ownerService.saveOwner(owner)
        val headers = HttpHeaders()
        headers.location = ucBuilder.path("/api/owners/{id}").buildAndExpand(owner.id).toUri()
        return ResponseEntity(owner, headers, HttpStatus.CREATED)
    }

    @PutMapping("/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateOwner(
        @PathVariable("ownerId") ownerId: Int,
        @RequestBody @Valid owner: Owner
    ): Owner = ownerService.findOwnerById(ownerId)?.apply {
        address = owner.address
        city = owner.city
        firstName = owner.firstName
        lastName = owner.lastName
        telephone = owner.telephone
    } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @DeleteMapping("/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deleteOwner(@PathVariable("ownerId") ownerId: Int) {
        val owner = ownerService.findOwnerById(ownerId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        ownerService.deleteOwner(owner)
    }
}