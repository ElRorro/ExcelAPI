
package com.iesvdc.acceso.excelapi.excelapi;

/**
 * Esta clase almacena información del texto de una hoja de Excel
 * @author JesusRodriguez
 * @version 1.0
 */
public class Hoja {
    /**
     * Crea una hoja de calculo nueva
     */
    private String [][] datos;
    private String nombre;
    private int nFilas;
    private int nColumnas;
    
    public Hoja(){
        this.datos = new String [5][5];
        this.nFilas = 5;
        this.nColumnas = 5;
        this.nombre = "";
    }
    
    /**
     * Crea una hoja nueva de tamaño nFilas por nColumnas
     * @param nFilas el número de filas
     * @param nColumnas el número de celdas que tiene cada fila
     */
    
    public Hoja(int nFilas, int nColumnas){
        this.datos = new String [nFilas][nColumnas];
        this.nombre = nombre;
        this.nFilas = nFilas;
        this.nColumnas = nColumnas;
    }
    /**
     * Crea una hoja nueva de tamaño nFilas por nColumnas
     * @param nombre
     * @param nFilas
     * @param nColumnas 
     */

    public Hoja(String nombre, int nFilas, int nColumnas) {
        this.datos = new String [nFilas][nColumnas];
        this.nombre = nombre;
        this.nFilas = nFilas;
        this.nColumnas = nColumnas;
    }
    
    public String getDatos(int fila, int columna) throws ExcelAPIException {
        //TO-DO excepcion si accedemos a una posicion no valida con un if;
        if (fila > this.nFilas || columna > this.nColumnas || fila < 0 || columna < 0){
            throw new ExcelAPIException("Hoja:getDatos(): Posición no válida");
        }
        return datos[fila][columna];
    }

    public void setDatos(String dato, int fila, int columna) throws ExcelAPIException {
        //TO-DO excepcion si accedemos a una posicion no valida con un if;
        if (fila > this.nFilas || columna > this.nColumnas || fila < 0 || columna < 0){
            throw new ExcelAPIException("Hoja:setDatos(): Posición no válida");
        }
        this.datos[fila][columna] = dato;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFilas() {
        return nFilas;
    }

    public int getColumnas() {
        return nColumnas;
    }
    
    public boolean compare(Hoja hoja) throws ExcelAPIException{
        boolean igual = true;
        
        if(this.nColumnas == hoja.getColumnas() && this.nFilas == hoja.getFilas() && this.nombre.equals(hoja.getNombre())){
            for(int i = 0; i < this.nFilas; i++){
                for (int j = 0; j < this.nColumnas; j++){
                    if (!this.datos[i][j].equals(hoja.getDatos(i,j))){
                        igual = false;
                        break;
                    }
                }
                if(!igual) break;
            }
        } else {    
            igual = false;
        }
        return igual;   
    }
}
