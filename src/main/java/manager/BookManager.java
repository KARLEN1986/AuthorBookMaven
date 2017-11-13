package manager;

import db.DBConnectionProvider;
import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    private DBConnectionProvider provider;
    private Connection connection;

    AuthorManager authorManager;

    public BookManager() {
        provider = DBConnectionProvider.getInstance();
        connection = provider.getConnection();
        authorManager = new AuthorManager();
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO `book`(`title`,`description`,`author_id`) VALUES " +
                "('" + book.getTitle() + "','" + book.getDescription() + "',"
                + book.getAuthor().getId() + ");";
        Statement statement = connection.createStatement();
        long id = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        book.setId(id);
    }

    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE `book` SET `title` = " + book.getTitle() + ", " +
                "`description` = " + book.getDescription() +
                "`author_id` = " + book.getAuthor().getId();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void removeBook(long id) throws SQLException {
        String query = "DELETE FROM `author` WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public List<Book> getAllBooksAndAuthors() throws SQLException {
        String query = "SELECT * FROM book;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setDescription(resultSet.getString(3));
            book.setAuthor(authorManager.getAuthorById(resultSet.getLong(4)));
            book.setTimestamp(resultSet.getString(5));

            books.add(book);

        }
        return books;
    }


}
