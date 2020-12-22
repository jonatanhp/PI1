/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author jonatan
 */
public class Tipo {
    private int idTipo;
    private String nomTipo;
    
    public Tipo(){
        
    }
    
    public Tipo(String nomTipo) {
        this.nomTipo= nomTipo;
       
    }
    public String toString() {
        return nomTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomTipo() {
        return nomTipo;
    }

    public void setNomTipo(String nomTipo) {
        this.nomTipo = nomTipo;
    }
    
    
}
