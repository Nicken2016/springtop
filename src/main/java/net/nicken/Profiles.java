package net.nicken;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
        POSTGRES = "postgres",
        HSQLDB = "hsqldb",
        JDBC = "jdbc",
        JPA = "jpa",
        DATAJPA = "datajpa";


    public static final String DB_IMPLEMENTATION = DATAJPA;

    public static String getActiveDbProfile(){
        try{
//            Class.forName("org.postgresql.Driver", true, ClassUtils.getDefaultClassLoader());
            Class.forName("org.postgresql.Driver");
            return Profiles.POSTGRES;
        }catch (ClassNotFoundException ex){
            try{
//                Class.forName("org.hsqldb.jdbcDriver", true, ClassUtils.getDefaultClassLoader());
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQLDB;
            }catch (ClassNotFoundException e){
                throw new IllegalStateException("Could not resolve DB profile");
            }
        }
    }


}

