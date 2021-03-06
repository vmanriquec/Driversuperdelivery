
package com.driversuperdelivery.driversuperdelivery.modelos;

/**
 * Created by Perseo on 05/08/2014.
 */
public class Productos {


    private int idproducto;
    private String nombreproducto,estadoproducto,ingredientes,descripcion,foto;
    private Double precventa;


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Productos(int idproducto, String nombreproducto, String estadoproducto, String ingredientes, Double precventa, String descripcion , String foto)
    {
        super();
        this.idproducto=idproducto;
        this.nombreproducto=nombreproducto;
        this.estadoproducto=estadoproducto;
        this.ingredientes=ingredientes;
        this.precventa=precventa;
        this.descripcion=descripcion;
        this.foto=foto;

    }
       public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getEstadoproducto() {
        return estadoproducto;
    }

    public void setEstadoproducto(String estadoproducto) {
        this.estadoproducto = estadoproducto;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIdproducto(String ingredientes) {
        this.ingredientes = ingredientes;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Double getPrecventa() {
        return precventa;
    }

    public void setPrecventa(Double precventa) {
        this.precventa = precventa;
    }




@Override
    public String toString(){

    return this.idproducto+"  ."+this.nombreproducto+" S/.  "+this.precventa;
}



}

