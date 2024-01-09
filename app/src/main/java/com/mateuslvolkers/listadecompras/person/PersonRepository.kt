package com.mateuslvolkers.listadecompras.person

import com.mateuslvolkers.listadecompras.person.model.Person

class PersonRepository {
    fun getPersons(): List<Person> {
        return List(500) { index ->
            Person(
                img = index,
                name = "Nome $index",
                description = "Descricao $index"
            )
        }
    }
}