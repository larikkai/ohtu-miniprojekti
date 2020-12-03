/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendation_library.dao;

import recommendation_library.domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anadis
 */
public class DatabaseRecommendationDao implements RecommendationDao {

    private String fileName;

    public DatabaseRecommendationDao(String filename) {
        this.fileName = filename;
        connect();
        createBookTable();
        createVideoTable();
        createTimeStampTable();
        this.createBlogTable();
        this.createPodcastTable();
    }

    private Connection connect() {
        // SQLite connection string  
        String url = "jdbc:sqlite:" + fileName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());  
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private void createBookTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id integer PRIMARY KEY,\n"
                + " author TEXT NOT NULL,\n"
                + " title TEXT NOT NULL UNIQUE,\n"
                + " description TEXT,\n"
                + " isbn TEXT,\n"
                + " pageCount integer,\n"
                + " created TEXT"
                + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createVideoTable() {
        String sql = "CREATE TABLE IF NOT EXISTS videos (\n"
                + " id integer PRIMARY KEY,\n"
                + " url TEXT NOT NULL,\n"
                + " title TEXT NOT NULL UNIQUE,\n"
                + " description TEXT,\n"
                + " created TEXT"
                + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTimeStampTable() {
        String sql = "CREATE TABLE IF NOT EXISTS timestamps (\n"
                + " id integer PRIMARY KEY,\n"
                + " timestamp TEXT NOT NULL,\n"
                + " comment TEXT,\n"
                + " video_id INTEGER REFERENCES videos"
                + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createPodcastTable() {
        String sql = "CREATE TABLE IF NOT EXISTS podcasts (\n"
                + " id integer PRIMARY KEY,\n"
                + " author TEXT NOT NULL,\n"
                + " title TEXT NOT NULL UNIQUE,\n"
                + " description TEXT,\n"
                + " name TEXT,\n"
                + " created TEXT"
                + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createBlogTable() {
        String sql = "CREATE TABLE IF NOT EXISTS blogs (\n"
                + " id integer PRIMARY KEY,\n"
                + " url TEXT NOT NULL,\n"
                + " author TEXT NOT NULL,\n"
                + " title TEXT NOT NULL UNIQUE,\n"
                + " description TEXT,\n"
                + " created TEXT"
                + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new recommendation into the database
     *
     * @param author
     * @param title
     * @param description
     * @param isbn
     */
    @Override
    public void createBookRecommendation(String author, String title, String description, String isbn, int pageCount) {
        String sql = "INSERT INTO books(author, title, description, isbn, pageCount, created) "
                + "VALUES(?,?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, author);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, isbn);
            statement.setInt(5, pageCount);
            statement.setString(6, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new recommendation into the database
     *
     * @param url
     * @param title
     * @param description
     */
    @Override
    public void createVideoRecommendation(String url, String title, String description) {
        String sql = "INSERT INTO videos (url, title, description, created) "
                + "VALUES(?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, url);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add a new timestamp into the database
     *
     * @param video_id
     * @param timestamp
     * @param comment
     */
    @Override
    public void addTimeStampToVideo(int videoId, String timestamp, String comment) {
        String sql = "INSERT INTO timestamps(timestamp, comment, video_id) "
                + "VALUES(?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, timestamp);
            statement.setString(2, comment);
            statement.setInt(3, videoId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    /**
     * Insert a new recommendation into the database
     *
     * @param url
     * @param title
     * @param author
     * @param description
     */
    @Override
    public void createBlogRecommendation(String url, String title, String author, String description) {
        String sql = "INSERT INTO blogs (url, title, author, description, created) "
                + "VALUES(?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, url);
            statement.setString(2, title);
            statement.setString(3, author);            
            statement.setString(4, description);
            statement.setString(5, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Insert a new recommendation into the database
     *
     * @param author
     * @param title
     * @param description
     * @param isbn
     */
    @Override
    public void createPodcastRecommendation(String author, String title, String description, String name) {
        String sql = "INSERT INTO podcasts(author, title, description, name, created) "
                + "VALUES(?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, author);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, name);
            statement.setString(5, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetch every recommendation from attached database
     *
     * @return List of recommendations
     */
    @Override
    public List<BookRecommendation> getAllBookRecommendations() {
        ArrayList<BookRecommendation> books = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM books");
            while (result.next()) {
                books.add(new BookRecommendation(result.getInt("id"), result.getString("author"),
                        result.getString("title"), result.getString("description"),
                        result.getString("isbn"), result.getInt("pageCount"), result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public List<VideoRecommendation> getAllVideoRecommendations() {
        ArrayList<VideoRecommendation> videos = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM videos");
            while (result.next()) {
                videos.add(new VideoRecommendation(result.getInt("id"), result.getString("url"),
                        result.getString("title"), result.getString("description"),
                        result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }

    @Override
    public List<VideoRecommendation> getAllBlogRecommendations() {
        ArrayList<VideoRecommendation> videos = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM videos");
            while (result.next()) {
                videos.add(new VideoRecommendation(result.getInt("id"), result.getString("url"),
                        result.getString("title"), result.getString("description"),
                        result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }
    
    @Override
    public List<VideoRecommendation> getAllPodcastRecommendations() {
        ArrayList<VideoRecommendation> videos = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM videos");
            while (result.next()) {
                videos.add(new VideoRecommendation(result.getInt("id"), result.getString("url"),
                        result.getString("title"), result.getString("description"),
                        result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }
    
    @Override
    public List<TimeMemory> getAllTimestampsForVideo(int videId) {
        ArrayList<TimeMemory> timestamps = new ArrayList<>();
        String sql = "SELECT * FROM timestamps WHERE video_id = ?";
        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, videId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                timestamps.add(new TimeMemory(result.getInt("id"), result.getString("timestamp"),
                        result.getString("comment"), result.getInt("video_id")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return timestamps;
    }

    @Override
    public void editBookRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE books SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editVideoRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE videos SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement preparedstmt = conn.prepareStatement(sql);
            preparedstmt.setString(1, newValue);
            preparedstmt.setString(2, title);
            preparedstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int searchVideoByTitle(String title) {
        String sql = "SELECT id FROM videos WHERE title = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public void deleteBookByTitle(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteVideoByTitle(String title) {
        int videoId = searchVideoByTitle(title);

        String deleteVideo = "DELETE FROM videos WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteVideo);
            stmt.setString(1, title);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        deleteVideoTimeStamps(videoId);
    }

    private void deleteVideoTimeStamps(int videoId) {
        String deleteTimeStamps = "DELETE FROM timestamps WHERE video_id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteTimeStamps);
            stmt.setInt(1, videoId);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void editTimestampForVideo(int videoId, int timeStampId, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE timestamps SET " + fieldToBeEdited + " = ? WHERE video_id = ? AND id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement preparedstmt = conn.prepareStatement(sql);
            preparedstmt.setString(1, newValue);
            preparedstmt.setInt(2, videoId);
            preparedstmt.setInt(3, timeStampId);
            preparedstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTimestamp(int videoId, int timeStampId) {
        String deleteTimeStamps = "DELETE FROM timestamps WHERE video_id = ? AND id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteTimeStamps);
            stmt.setInt(1, videoId);
            stmt.setInt(2, timeStampId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
