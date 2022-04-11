package com.example.gymguadalupe;

public class Reserva {
    private String id_reserva;
    private String email_reserva;
    private String horario_reserva;
    private String fecha_reserva;

    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getEmail_reserva() {
        return email_reserva;
    }

    public void setEmail_reserva(String email_reserva) {
        this.email_reserva = email_reserva;
    }

    public String getHorario_reserva() {
        return horario_reserva;
    }

    public void setHorario_reserva(String horario_reserva) {
        this.horario_reserva = horario_reserva;
    }
    public String getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(String fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

}
