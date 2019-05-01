/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import basics.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Να διαβάζει απο αρχεία θα υλοποιηθεί σε επόμενα στάδια
public class FileWrapper {
    
    public static void writeArtistsToFile(String filename, ArrayList<Artist> artArr) throws ClassNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("target/" + filename + ".json"), artArr);
        System.out.println("Json Artists written to the file " + filename + ".json");

    }

    public static void writeReleasesToFile(String filename, ArrayList<Release> relArr) throws FileNotFoundException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("target/" + filename + ".json"), relArr);
        System.out.println("Json Artists written to the file " + filename + ".json");

    }

    public static ArrayList<Artist> readArtistsFromFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        return null;
    }

    public static ArrayList<Release> readReleasesFromFile(String filename) {
        return null;
    }
}
//Πηγές
//https://java2blog.com/jackson-example-read-and-write-json/
//https://www.makeinjava.com/convert-array-objects-json-jackson-objectmapper/
//http://www.davismol.net/2015/03/05/jackson-json-deserialize-a-list-of-objects-of-subclasses-of-an-abstract-class/