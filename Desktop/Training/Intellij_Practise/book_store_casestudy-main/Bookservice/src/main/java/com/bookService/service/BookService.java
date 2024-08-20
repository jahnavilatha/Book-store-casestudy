package com.bookService.service;

import com.bookService.model.Book;
import com.bookService.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> getAllBooks(){
        return bookRepo.findAll();
    }
    public Book getBook(long id){
        return bookRepo.findById(id).orElseThrow();
    }
    public Book addBook(Book book){
        return bookRepo.save(book);
    }
    public Book updateBook(Long id, Book book){
        Book existingBook=getBook(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setStock(book.getStock());
        return bookRepo.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
    public boolean checkStock(Long id,Integer quantity ){
        Book book = getBook(id);
        return book.getStock()>=quantity;
    }
}

