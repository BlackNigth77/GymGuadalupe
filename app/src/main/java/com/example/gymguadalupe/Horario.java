package com.example.gymguadalupe;

public class Horario {
    private String id_horariro;
    private String horario;
    private String turno;

    public String getId_horariro() {
        return id_horariro;
    }

    public void setId_horariro(String id_horariro) {
        this.id_horariro = id_horariro;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return horario;
    }

}
