package be.skenteridis.contacts.controller;

import be.skenteridis.contacts.dto.ContactInputDTO;
import be.skenteridis.contacts.dto.ContactOutputDTO;
import be.skenteridis.contacts.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Validated
public class ContactController {
    private final ContactService service;
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getContacts() {
        List<ContactOutputDTO> contacts = service.getContacts();
        return contacts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(service.getContactById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@Positive @PathVariable Long id) {
        service.deleteContact(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@Valid @RequestBody ContactInputDTO dto, @Positive @PathVariable Long id) {
        return ResponseEntity.ok(service.updateContact(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody ContactInputDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addContact(dto));
    }
}
