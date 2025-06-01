package com.bookstore.bookservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.bookservice.model.Book;
import com.bookstore.bookservice.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	  @Autowired
	    private BookService bookService;

	    // User & Admin: Get all books
	    @GetMapping
	    public ResponseEntity<List<Book>> getAllBooks() {
	        return ResponseEntity.ok(bookService.getAllBooks());
	    }

	    // User & Admin: Get book by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
	        return bookService.getBookById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    // Admin: Add book
	    @PostMapping
	    public ResponseEntity<?> addBook(@RequestBody Book book,
	                                     @RequestHeader("X-User-Role") String role) {
	        if (!role.equals("ADMIN")) {
	            return ResponseEntity.status(403).body("Access denied");
	        }
	        return ResponseEntity.ok(bookService.addBook(book));
	    }

	    // Admin: Update book
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateBook(@PathVariable Long id,
	                                        @RequestBody Book book,
	                                        @RequestHeader("X-User-Role") String role) {
	        if (!role.equals("ADMIN")) {
	            return ResponseEntity.status(403).body("Access denied");
	        }
	        return ResponseEntity.ok(bookService.updateBook(id, book));
	    }

	    // Admin: Delete book
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteBook(@PathVariable Long id,
	                                        @RequestHeader("X-User-Role") String role) {
	        if (!role.equals("ADMIN")) {
	            return ResponseEntity.status(403).body("Access denied");
	        }
	        bookService.deleteBook(id);
	        return ResponseEntity.ok("Book deleted");
	    }
}
