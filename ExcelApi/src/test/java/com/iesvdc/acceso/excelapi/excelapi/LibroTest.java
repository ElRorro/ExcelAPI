/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.excelapi.excelapi;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matinal
 */
public class LibroTest {
    
    public LibroTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNombreArchivo method, of class Libro.
     * método que obtiene el nombre del archivo
     */
    @Test
    public void testGetNombreArchivo() {
        System.out.println("getNombreArchivo");
        Libro instance = new Libro();
        String expResult = "nuevo.xlsx";
        String result = instance.getNombreArchivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setNombreArchivo method, of class Libro.
     * método que como bien dice la palabra set sirve para modificar el nombre del archivo
     */
    @Test
    public void testSetNombreArchivo() {
        System.out.println("setNombreArchivo");
        String nombreArchivo = "unNombre.xlsx";
        Libro instance = new Libro();
        instance.setNombreArchivo(nombreArchivo);
        assertEquals(nombreArchivo, instance.getNombreArchivo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addHoja method, of class Libro.
     * método para añadir una hoja al libro
     */
    @Test
    public void testAddHoja() throws ExcelAPIException {
        System.out.println("addHoja");
        int filas = 20, columnas = 30;
        Hoja hoja = new Hoja("pepe", filas, columnas);
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                hoja.setDatos((char)('A'+j)+" "+(i+1), i, j);
            }
        }
        
        Libro instance = new Libro();
        instance.addHoja(hoja);

        try {
            assertEquals(instance.indexHoja(0).compare(hoja), true);
        } catch (ExcelAPIException ex) {
            fail("No pude acceder a la hoja");
        }
    }

    /**
     * Test of removeHoja method, of class Libro.
     * método para eliminar una hoja del libro
     */
    @Test
    public void testRemoveHoja() throws Exception {
        System.out.println("removeHoja");
        int index = 0;
        Libro instance = new Libro("nuevo_libro.xlsx");
        Hoja expResult = new Hoja("Hoja 1", 5, 5);
        
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                expResult.setDatos((char)('A'+j)+" "+(i+1), i, j);
            }
        }
        instance.addHoja(expResult);
        Hoja result = instance.removeHoja(index);
        assertEquals("Error en removeHoja()", result.compare(expResult), true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of indexHoja method, of class Libro.
     * Ya está comprobado en TestAddHoja();
    /*@Test
    public void testIndexHoja() throws Exception {
        System.out.println("indexHoja");
        int index = 0;
        Libro instance = new Libro();
        Hoja expResult = null;
        Hoja result = instance.indexHoja(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }*/

    /**
     * Test of load method, of class Libro.
     */
    @Test
    public void testLoad_0args() throws ExcelAPIException {
        System.out.println("load");
        Libro instance = new Libro();
        instance.load("test.xlsx");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class Libro.
     */
    @Test
    public void testLoad_String() throws ExcelAPIException {
        System.out.println("load");
        String filename = "test.xlsx";
        Libro instance = new Libro();
        instance.load(filename);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class Libro.
     * método que guarda hojas y que son añadidas al libro
     */
    @Test
    public void testSave_0args() throws Exception {
        System.out.println("save");
        Libro instance = new Libro("test.xlsx");
        Hoja h1 = new Hoja("Hoja 1", 6, 6);
        Hoja h2 = new Hoja("Hoja 2", 10, 10);
        
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                h1.setDatos((char)('A'+j)+" "+(i+1), i, j);
            }
        }
            
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                h2.setDatos((char)('A'+j)+" "+(i+1), i, j);
            }
        }   
        
        instance.addHoja(h1);
        instance.addHoja(h2);
        instance.save();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    } 
}
