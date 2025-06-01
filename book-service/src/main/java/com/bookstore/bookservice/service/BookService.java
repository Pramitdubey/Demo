package com.bookstore.bookservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.bookservice.model.Book;
import com.bookstore.bookservice.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = bookRepository.findById(id).orElseThrow();
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setCategory(updatedBook.getCategory());
        existing.setPrice(updatedBook.getPrice());
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

