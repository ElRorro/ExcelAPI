/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.excelapi.excelapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Esta clase almacena informacion de libros para generar ficheros de excel.
 * Un libro de excel está compuesto por hojas
 * @author JesusRodriguez
 * @version 1.0
 */
public class Libro {
    private List<Hoja> hojas;
    private String nombreArchivo;
    private Hoja hoja = new Hoja();

    public Libro() {
        this.hojas = new ArrayList<>();
        this.nombreArchivo = "nuevo.xlsx";
    }

    public Libro(String nombreArchivo) {
        this.hojas = new ArrayList<>();
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public boolean addHoja(Hoja hoja){
        return this.hojas.add(hoja);
    }
            
    public Hoja removeHoja(int index) throws ExcelAPIException {
        if(index < 0 || index > this.hojas.size()){
            throw new ExcelAPIException("Libro:removeHoja(): Posición no válida");
        }
       return this.hojas.remove(index);
    }
                     
    public Hoja indexHoja(int index) throws ExcelAPIException {
        if(index < 0 || index > this.hojas.size()){
            throw new ExcelAPIException("Libro:indexHoja(): Posición no válida");
        }
        return this.hojas.get(index);
    }
    
    public void load() throws ExcelAPIException {
        //Indicamos a null el fichero de entrada
        FileInputStream libroPOI = null;
        try {
            //ficheroEntrada lo igualamos al nombre que introduzcamos
            File ficheroEntrada = new File(this.nombreArchivo);
            //le pasamos ficheroEntrada al nuevo fichero
            libroPOI = new FileInputStream(ficheroEntrada);
            
            //Buscamos el libro instanciado por XLSX archivo
            XSSFWorkbook wb = new XSSFWorkbook(libroPOI);

            if (this.hojas != null){
                if (this.hojas.size() > 0){
                    this.hojas.clear();
                }
            } else {
                this.hojas = new ArrayList<>();
            }
            
            //Hacemos un bucle para recorrer el numero de hojas
            for (int i = 0; i < wb.getNumberOfSheets(); i++){
               Sheet hojaXlsx = wb.getSheetAt(i);
               
               int numFilas = hojaXlsx.getLastRowNum()+1;
               int numColumnas = 0;

               for (int j = 0; j < hojaXlsx.getLastRowNum(); j++){
                   Row filaXlsx = hojaXlsx.getRow(j);

                   if (numColumnas < filaXlsx.getLastCellNum()){
                       numColumnas = filaXlsx.getLastCellNum();
                   }
               }

               System.out.println("Libro.load():: dataSheet=" + hojaXlsx.getSheetName());
               Hoja nuevaHoja = new Hoja(hojaXlsx.getSheetName(), numFilas, numColumnas);

               for (int j = 0; j < numFilas; j++){
                   Row filaXlsx = hojaXlsx.getRow(j);
                   for (int k = 0; k < filaXlsx.getLastCellNum(); k++){
                       Cell celdaXlsx = filaXlsx.getCell(k);
                       String dato = " ";
                       
                       if (celdaXlsx != null){
                           //Con este switch comprobamos el tipo de dato que hay en la celda
                           switch (celdaXlsx.getCellType()){
                               case Cell.CELL_TYPE_STRING:
                                   dato = celdaXlsx.getStringCellValue();
                                   break;

                                   case Cell.CELL_TYPE_NUMERIC:
                                   dato += celdaXlsx.getNumericCellValue();
                                   break;

                                   case Cell.CELL_TYPE_BOOLEAN:
                                   dato += celdaXlsx.getBooleanCellValue();
                                   break;

                                   case Cell.CELL_TYPE_FORMULA:
                                   dato += celdaXlsx.getCellFormula();
                                   break;

                                   default:
                                   dato = " ";
                           }
                           //Mostramos los datos que hay
                           System.out.println("Libro.load() = " + j + "k= " + k + " dato = " + dato);
                           nuevaHoja.setDatos(dato,j,k);
                       }
                   }

                   this.hojas.add(nuevaHoja);
               }
            }
        
        } catch (IOException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcelAPIException("Libro IO Error cuando cargas el archivo");
        } finally {
            try{
                if (libroPOI != null) {
                    libroPOI.close();
                }
            } catch (IOException ex) {
             throw new ExcelAPIException("Libro IO Error cuando cargas el archivo");
            }
        }
    }
    
    public void load(String filename) throws ExcelAPIException{
        this.nombreArchivo = filename;
        this.load();
    }
    
    public void save() throws ExcelAPIException{
        SXSSFWorkbook wb = new SXSSFWorkbook();
        
        //Sheet sh = wb.createSheet("HOLA MUNDO");
        for (Hoja hoja : this.hojas) {
            Sheet sh = wb.createSheet(hoja.getNombre());
            for (int i = 0; i < hoja.getFilas(); i++) {
                Row row = sh.createRow(i);
                for (int j = 0; j < hoja.getColumnas(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(hoja.getDatos(i, j));                
                }
            }
        }
        
        try (FileOutputStream out = new FileOutputStream(this.nombreArchivo)) {   
            wb.write(out);
            //out.close();                        
        } catch (IOException ex) {
            throw new ExcelAPIException("Error al guardar el archivo");
        } finally {
            wb.dispose();
        }
    }
    
    public void save(String filename) throws ExcelAPIException{
        this.nombreArchivo = filename;
        this.save();
        
    }
    
    private void testExtension(){
        String extension = "";
        String extensionArchivo = "xlsx";
        int i = this.nombreArchivo.lastIndexOf('.');
        if (i > 0) {
            extension = this.nombreArchivo.substring(i+1);
            if (extension != "xlsx"){
                nombreArchivo = this.nombreArchivo+extensionArchivo;
            }
        }
    }
}