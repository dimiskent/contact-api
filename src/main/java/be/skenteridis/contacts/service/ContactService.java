package be.skenteridis.contacts.service;

import be.skenteridis.contacts.dto.ContactInputDTO;
import be.skenteridis.contacts.dto.ContactOutputDTO;
import be.skenteridis.contacts.exception.ContactNotFoundException;
import be.skenteridis.contacts.exception.DuplicateContactInformationException;
import be.skenteridis.contacts.mapper.ContactMapper;
import be.skenteridis.contacts.model.Contact;
import be.skenteridis.contacts.repository.ContactRepository;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository repository;
    private final ContactMapper mapper;
    public ContactService(ContactRepository repository) {
        this.repository = repository;
        mapper = new ContactMapper();
    }

    public ContactOutputDTO addContact(ContactInputDTO dto) {
        try {
            return mapper.toDTO(
                    repository.save(
                            mapper.toContact(dto)
                    )
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateContactInformationException(e.getMessage());
        }
    }

    public ContactOutputDTO updateContact(Long id, ContactInputDTO dto) {
        Contact contact = repository.findById(id).orElseThrow(() -> new ConcurrencyFailureException("Contact not found!"));
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        try {
            return mapper.toDTO(repository.save(contact));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateContactInformationException(e.getMessage());
        }
    }

    public void deleteContact(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ContactNotFoundException("Contact not found!");
        }
    }

    public List<ContactOutputDTO> getContacts() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ContactOutputDTO getContactById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(() -> new ContactNotFoundException("Contact not found!"));
    }
}
