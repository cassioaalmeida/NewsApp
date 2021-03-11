package com.example.newsapp

fun main() {
    val repository = ContactRepository()

    repository.add(Contact(null, "Cássio", "Filho", "Chique", "15997471314"))
    repository.add(Contact(null, "Cássio2", "Filho", null, "15997471314"))
    repository.add(Contact(null, "Cássio3", "Filho", "Chique2", "15997471314"))
    repository.add(Contact(null, "Cássio4", "Filho", "aaaa", "15997471314"))
    repository.add(Contact(null, "Pedro", "Almeida", "vvvv", "15997471314"))
    repository.add(Contact(null, "Cássio6", "Alves", "bbbbb", "15997471314"))

    val contacts = repository.getAll()

    println("Contatos:")
    contacts.forEach { u -> println(u) }

    println("")

    println("Buscando contato ID 3:")
    println(repository.getById(3))

    println("")

    println("Buscando contato com Nome Pedro")
    println(repository.searchContacts("Pedro"))

    println("")

    println("Buscando contato com Sobrenome Alves")
    println(repository.searchContacts("Alves"))

    println("")

    println("Buscando contato com Apelido Chique")
    println(repository.searchContacts("Chique"))

    println("")

    println("Alterando o Nome do contato 3")
    var contact = repository.getById(3)
    contact!!.FirstName = "Lucas"
    repository.update(contact)
    println("Buscando contato ID 3:")
    println(repository.getById(3))

    println("")

    println("Deletando contato ID 5")
    repository.delete(5)
    println("Buscando contato ID 5:")
    contact = repository.getById(5)
    if (contact == null) {
        println("Contato não localizado")
    } else {
        println(contact)
    }

}

data class Contact(
        var Id: Int?,
        var FirstName: String,
        var LastName: String,
        var NickName: String?,
        var PhoneNumber: String
)

class ContactRepository() {
    val contacts: MutableMap<Int, Contact> = mutableMapOf()

    fun add(contact: Contact) {
        val lastId = getLastId()

        if (lastId == null) {
            contact.Id = 1;
        } else {
            contact.Id = lastId + 1;
        }

        contacts.put(contact.Id!!, contact)
    }

    fun update(contact: Contact) {
        contacts.put(contact.Id!!, contact)
    }

    fun delete(id: Int) {
        contacts.remove(id)
    }

    fun getAll(): List<Contact> {
        return contacts.values.toList()
    }

    fun getById(id: Int): Contact? {
        return contacts.get(id)
    }

    fun getLastId(): Int? {
        return contacts.get(contacts.size)?.Id
    }

    fun searchContacts(search: String): List<Contact> {
        var searchField = search.toLowerCase()
        return contacts.filterValues { c ->
            c.FirstName.toLowerCase().contains(searchField) ||
                    c.LastName.toLowerCase().contains(searchField) ||
                    c.NickName?.toLowerCase()?.contains(searchField) ?: false
        }.values.toList()
    }
}