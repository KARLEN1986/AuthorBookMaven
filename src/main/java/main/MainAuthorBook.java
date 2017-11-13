package main;

import manager.AuthorManager;
import manager.BookManager;
import model.Author;
import model.Book;
import model.Gender;

import java.sql.SQLException;
import java.util.List;

public class MainAuthorBook {
    public static void main(String[] args) throws SQLException {
        AuthorManager authorManager = new AuthorManager();
        BookManager bookManager = new BookManager();
        Author author = new Author();
        author.setName("asa");
        author.setSurname("asa");
        author.setAge(22);
        author.setGender(Gender.MALE);

        authorManager.addAuthor(author);

        Book book = new Book();
        book.setTitle("asda");
        book.setDescription("dfasdfgas");
        book.setAuthor(author);
        bookManager.addBook(book);

        List<Book> allBooksAndAuthors = bookManager.getAllBooksAndAuthors();
        System.out.println(allBooksAndAuthors);
    }

}
