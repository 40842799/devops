package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App a;

    @BeforeAll
    static void init()
    {
         a = new App();
        // Connect to database
        // Prefer environment variables for DB in Docker; fallback to db:3306
        String dbHost = System.getenv().getOrDefault("DB_HOST", "localhost");
        String dbPort = System.getenv().getOrDefault("DB_PORT", "33060");

        a.connect(dbHost + ":" + dbPort, 30000);
    }

    @Test
    void testGetEmployee()
    {
        com.napier.sem.Employee empInstance = new com.napier.sem.Employee();

        Employee result = empInstance.getEmployee(a.con, 499524);

        assertEquals(result.emp_no, 499524);
        assertEquals(result.first_name, "Jagoda");
        assertEquals(result.last_name, "Schahn");
    }
}