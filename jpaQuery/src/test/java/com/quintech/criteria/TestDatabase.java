package com.quintech.criteria;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;

public class TestDatabase
{
    private final String ddlFilename;
    private final String dataFilename;
    
    public TestDatabase(String ddlFilename, String dataFilename)
    {
        super();
        this.ddlFilename = ddlFilename;
        this.dataFilename = dataFilename;
    }
    

    public void setup()
    {
        System.out.println("Setting test database using " + ddlFilename + " and " + dataFilename);
        try
        {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");

            buildDatabase(conn, ddlFilename);

            populateData(conn, dataFilename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void populateData(Connection conn, String dataFilename) throws Exception
    {
        InputStream instrm = getClass().getClassLoader().getResourceAsStream(dataFilename);
        if (instrm != null)
        {
            XmlDataSet ds = new XmlDataSet(instrm);
            new InsertIdentityOperation(DatabaseOperation.CLEAN_INSERT).execute(new DatabaseConnection(conn), ds);
        }

    }

    private File getDDLFile(final String ddlFilename)
    {
        URL thisPath = getClass().getClassLoader().getResource(".");

        File sqlDir = new File(thisPath.getFile());

        File[] sqlFiles = sqlDir.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.equals(ddlFilename);
            }
        });
        return sqlFiles[0];
    }

    private void buildDatabase(Connection connection, final String ddlFilename)
    {
        try
        {
            Statement stmt = connection.createStatement();

            File sqlFile = getDDLFile(ddlFilename);

            StringBuffer sql = new StringBuffer();

            BufferedReader reader = new BufferedReader(new FileReader(sqlFile));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sql.append(line);
                sql.append("\n");
            }

            stmt.execute(sql.toString());
            
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
}
