package manager;

import db.DBConnectionProvider;
import model.Author;
import model.Gender;

import java.sql.*;


public class AuthorManager {

    private DBConnectionProvider provider;
    private Connection connection;

    public AuthorManager() {
        provider = DBConnectionProvider.getInstance();
        connection = provider.getConnection();
    }

    public void addAuthor(Author author) throws SQLException {
        String query = "INSERT INTO `author`(`name`,`surname`,`gander`,`age`) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setString(2, author.getSurname());
        preparedStatement.setString(3, author.getGender().name());
        preparedStatement.setInt(4, author.getAge());

        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if(generatedKeys.next()){
            author.setId(generatedKeys.getLong(1));
        }
    }

    public void updateAuthor(Author author) throws SQLException {
        String query = "UPDATE `author` SET `name` = " + author.getName() + ", " +
                "`surname` = " + author.getSurname() +
                "`gender` = " + author.getGender().name() +
                "`age` = " + author.getAge() + " WHERE id = " + author.getId();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void removeAuthor(long id) throws SQLException {
        String query = "DELETE FROM `author` WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public Author getAuthorById(long id) throws SQLException {
        String query = "SELECT * FROM `author` WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Author author = null;
        if (resultSet.next()) {
            author = new Author();
            author.setId(resultSet.getLong(1));
            author.setName(resultSet.getString(2));
            author.setSurname(resultSet.getString(3));
            author.setGender(Gender.valueOf(resultSet.getString(4)));
            author.setAge(resultSet.getInt(5));
        }
        return author;
    }


}
