package com.thoughtprocess.bookstore.service;

import com.thoughtprocess.bookstore.model.Book;
import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<BookDTO> findByTitle(String bookTitle) {
        List<Book> books = bookRepo.findByTitle(bookTitle);
        //Transfers the book objects, to a List of items
        // This then collects the items from the method fromBookDTO to a List
        // Simplified to an inline variable return
        return books.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> listBooksByAuthor(String author) {
        List<Book> books = bookRepo.findByAuthor(author);
        // Use Java Streams to map each Book entity to a BookDTO
        return books.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> findAll() {
        List<Book> bookList = (List<Book>) bookRepo.findAll();
        // Use Java Streams to map each Book entity to a BookDTO
        return bookList.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public void save(BookDTO bookDTO)
    {
       Book book = toBookDTO(bookDTO);
       bookRepo.save(book);
    }

    public boolean doesTitleExist(String title)
    {
        return bookRepo.existsByTitle(title);
    }

    public BookDTO fromBookDTO(Book book) {
        return new BookDTO(book.getCost(), book.getTitle(), book.getAuthor());
    }
    public Book toBookDTO(BookDTO bookDTO)
    {
        Book book = new Book();
        book.setCost(bookDTO.getCost());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        return book;
    }

}
