package be.skenteridis.contacts.mapper;

import be.skenteridis.contacts.dto.ContactInputDTO;
import be.skenteridis.contacts.dto.ContactOutputDTO;
import be.skenteridis.contacts.model.Contact;

public class ContactMapper {
    public Contact toContact(ContactInputDTO dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        return contact;
    }

    public ContactOutputDTO toDTO(Contact contact) {
        ContactOutputDTO dto = new ContactOutputDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        return dto;
    }
}
