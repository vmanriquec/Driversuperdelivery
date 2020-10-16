package com.driversuperdelivery.driversuperdelivery.modelos;

public class Driver {
    int iddriver;
    String nombresdriver, apellidosdriver,dnidriver,telefonodriver,contrasenadriver,direcciondriver,placadriver,fotodriver,idfirebase,estadodriver;
    String latituddriver, longituddriver, referenciadriver,foto;

    public String getLatituddriver() {
        return latituddriver;
    }

    public void setLatituddriver(String latituddriver) {
        this.latituddriver = latituddriver;
    }

    public String getLongituddriver() {
        return longituddriver;
    }

    public void setLongituddriver(String longituddriver) {
        this.longituddriver = longituddriver;
    }

    public String getReferenciadriver() {
        return referenciadriver;
    }

    public void setReferenciadriver(String referenciadriver) {
        this.referenciadriver = referenciadriver;
    }

    public int getIddriver() {
        return iddriver;
    }

    public void setIddriver(int iddriver) {
        this.iddriver = iddriver;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "nombresdriver='" + nombresdriver + '\'' +
                ", dnidriver='" + dnidriver + '\'' +
                ", direcciondriver='" + direcciondriver + '\'' +
                ", placadriver='" + placadriver + '\'' +
                '}';
    }

    public String getNombresdriver() {
        return nombresdriver;
    }

    public void setNombresdriver(String nombresdriver) {
        this.nombresdriver = nombresdriver;
    }

    public String getApellidosdriver() {
        return apellidosdriver;
    }

    public void setApellidosdriver(String apellidosdriver) {
        this.apellidosdriver = apellidosdriver;
    }

    public String getDnidriver() {
        return dnidriver;
    }

    public void setDnidriver(String dnidriver) {
        this.dnidriver = dnidriver;
    }

    public String getTelefonodriver() {
        return telefonodriver;
    }

    public void setTelefonodriver(String telefonodriver) {
        this.telefonodriver = telefonodriver;
    }

    public String getContrasenadriver() {
        return contrasenadriver;
    }

    public void setContrasenadriver(String contrasenadriver) {
        this.contrasenadriver = contrasenadriver;
    }

    public String getDirecciondriver() {
        return direcciondriver;
    }

    public void setDirecciondriver(String direcciondriver) {
        this.direcciondriver = direcciondriver;
    }

    public String getPlacadriver() {
        return placadriver;
    }

    public void setPlacadriver(String placadriver) {
        this.placadriver = placadriver;
    }

    public String getFotodriver() {
        return fotodriver;
    }

    public void setFotodriver(String fotodriver) {
        this.fotodriver = fotodriver;
    }

    public String getEstadodriver() {
        return estadodriver;
    }

    public void setEstadodriver(String estadodriver) {
        this.estadodriver = estadodriver;
    }

    public String getIdfirebase() {
        return idfirebase;
    }

    public void setIdfirebase(String idfirebase) {
        this.idfirebase = idfirebase;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Driver(int iddriver, String nombresdriver, String apellidosdriver, String dnidriver, String telefonodriver, String contrasenadriver, String direcciondriver, String placadriver, String fotodriver, String idfirebase, String estadodriver, String latituddriver, String longituddriver, String referenciadriver, String foto ) {
        this.iddriver = iddriver;
        this.nombresdriver = nombresdriver;
        this.apellidosdriver = apellidosdriver;
        this.dnidriver = dnidriver;
        this.telefonodriver = telefonodriver;
        this.contrasenadriver = contrasenadriver;
        this.direcciondriver = direcciondriver;
        this.placadriver = placadriver;
        this.fotodriver = fotodriver;
        this.estadodriver = estadodriver;
        this.idfirebase=idfirebase;
               this.latituddriver=latituddriver;
        this.longituddriver=longituddriver;
        this.referenciadriver=referenciadriver;
        this.foto=foto;
    }
}
