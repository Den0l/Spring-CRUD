package com.example.crud.controller;


import com.example.crud.entity.Book;
import com.example.crud.entity.Author;
import com.example.crud.service.BookService;
import com.example.crud.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;



    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "list-books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "edit-book";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/authors")
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "list-authors";
    }

    @GetMapping("/authors/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "add-author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String showEditAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/authors/edit/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute Author author) {
        authorService.updateAuthor(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}