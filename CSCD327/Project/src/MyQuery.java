/*****************************
 Query the University Database
 *****************************/
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.Date;
import java.lang.String;

public class MyQuery {

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public MyQuery(Connection c)throws SQLException
    {
        conn = c;
        // Statements allow to issue SQL queries to the database
        statement = conn.createStatement();
    }

    public void findFall2009Students() throws SQLException
    {
        String query  = "select distinct name from student natural join takes where semester = \'Fall\' and year = 2009;";

        resultSet = statement.executeQuery(query);
    }

    public void printFall2009Students() throws IOException, SQLException
    {
        System.out.println("******** Query 0 ********");
        System.out.println("name");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            String name = resultSet.getString(1);
            System.out.println(name);
        }
    }

    public void findGPAInfo() throws SQLException
    {
        String query1 = "CREATE TEMPORARY TABLE temp\n" +
                "  SELECT *\n" +
                "  FROM takes;\n";
        String query2 = "ALTER TABLE temp\n" +
                "    ADD COLUMN grade_val VARCHAR(4);\n";
        String query3 = "UPDATE temp SET grade_val = (CASE\n" +
                "    WHEN grade = 'A' THEN 4.0\n" +
                "    WHEN grade = 'A-' THEN 3.67\n" +
                "    WHEN grade = 'B+' THEN 3.33\n" +
                "    WHEN grade = 'B' THEN 3.0\n" +
                "    WHEN grade = 'B-' THEN 2.67\n" +
                "    WHEN grade = 'C+' THEN 2.33\n" +
                "    WHEN grade = 'C' THEN 2.0\n" +
                "    WHEN grade = 'C-' THEN 1.67\n" +
                "    WHEN grade = 'D+' THEN 1.33\n" +
                "    WHEN grade = 'D' THEN 1.0\n" +
                "    WHEN grade = 'D-' THEN 0.67\n" +
                "    WHEN grade = 'F' then 0.0 END)\n" +
                "WHERE grade IS NOT NULL;\n";
        String query4 = "SELECT temp.ID, name, sum(grade_val * credits) / sum(credits) as GPA\n" +
                "  from temp NATURAL join student natural JOIN course GROUP BY student.ID;\n";
        statement.executeUpdate(query1);
        statement.executeUpdate(query2);
        statement.executeUpdate(query3);
        resultSet = statement.executeQuery(query4);
    }

    public void printGPAInfo() throws IOException, SQLException
    {
        System.out.println("******** Query 1 ********");
        System.out.format("%-11s %-11s %-11s\n", "ID", "Name", "GPA");
        while(resultSet.next()){
            System.out.format("%-11s %-11s %-11s\n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
    }

    public void findMorningCourses() throws SQLException
    {
        String query1 = "SELECT course_id, title, sec_id, semester, year, name, count(DISTINCT takes.ID) as enrollment\n" +
                "    FROM course NATURAL JOIN section natural JOIN teaches natural JOIN\n" +
                "instructor NATURAL JOIN time_slot JOIN takes USING (course_id, sec_id, semester, year)\n" +
                "WHERE start_hr <= 12\n" +
                "GROUP BY course_id, sec_id, semester, year\n" +
                "having enrollment <> 0;\n";
        resultSet = statement.executeQuery(query1);
    }

    public void printMorningCourses() throws IOException, SQLException
    {
        System.out.println("******** Query 2 ********");
        System.out.format("%")      //finish this
    }

    public void findBusyInstructor() throws SQLException
    {

    }

    public void printBusyInstructor() throws IOException, SQLException
    {
        System.out.println("******** Query 3 ********");
    }

    public void findPrereq() throws SQLException
    {

    }

    public void printPrereq() throws IOException, SQLException
    {
        System.out.println("******** Query 4 ********");
    }

    public void updateTable() throws SQLException
    {

    }

    public void printUpdatedTable() throws IOException, SQLException
    {
        System.out.println("******** Query 5 ********");
    }

    public void findFirstLastSemester() throws SQLException
    {

    }

    public void printFirstLastSemester() throws IOException, SQLException
    {
        System.out.println("******** Query 6 ********");
    }

    public void findHeadCounts() throws SQLException
    {
        System.out.println("******** Query 7 ********");
    }
}
