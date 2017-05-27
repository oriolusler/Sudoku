package Aplicacio;

import Domini.Jugador;
import Domini.Sudoku;
import Domini.Taulell;
import Persistencia.JugadorBBDD;
import Persistencia.SudokuBBDD;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ControlBBDD {

    private Map<Integer, Date> partidesRecuperades;
    private Sudoku sudoku;
    private Jugador jugador;
    private Date time;
    private JugadorBBDD jugadorBBDD;
    private SudokuBBDD sudokuBBDD;
    private boolean inciar = false;

    public ControlBBDD(String nom) {

        jugador = new Jugador(nom);
        sudoku = new Sudoku(jugador);

        this.jugadorBBDD = new JugadorBBDD();
        this.sudokuBBDD = new SudokuBBDD();
        try {
            this.partidesRecuperades = sudokuBBDD.getTimestamps(sudoku);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getInciar() {
        return inciar;
    }

    public void setInciar(boolean inciar) {
        this.inciar = inciar;
    }

    public void iniciarSudoku() throws Exception {

        time = new Date();
        this.partidesRecuperades = sudokuBBDD.getTimestamps(sudoku);
        sudoku = new Sudoku(time, getFirstIdLiure(partidesRecuperades), jugador, null);
    }

    private int getFirstIdLiure(Map<Integer, Date> recuperats) throws Exception {
        Set<Integer> IDSfromMAP = recuperats.keySet();
        for (int i = 1; i < 1000; i++) {
            if (!(IDSfromMAP.contains(i))) return i;
        }
        throw new Exception("Maxim partides guardades");
    }

    public void storeSudoku(Taulell t) throws Exception {
        sudoku.setTaulell(t);
        sudokuBBDD.storeSudoku(sudoku);
    }

    public void nouJugador(String nom) throws Exception {

        Jugador jugadorRecuperatDeDB = jugadorBBDD.getJugadorFromDB(nom);

        if (jugadorRecuperatDeDB == null) {
            jugador = new Jugador(nom, true);
            jugadorBBDD.storeJugador(jugador);

        } else if (jugadorRecuperatDeDB.getEstat() == true) {
            throw new Exception("Aquest jugador esta actualment jugant.\nPoseuvos en contacte amb l'administrador");
        } else {
            jugador.setEstat(true);
            jugadorBBDD.updateJugador(jugador);
            partidesRecuperades = sudokuBBDD.getTimestamps(sudoku);

        }
    }

    public Map<Integer, Date> getTimeStamps() throws Exception {
        return sudokuBBDD.getTimestamps(sudoku);
    }

    public void esborrarSudokuTaulell() throws Exception {
        sudokuBBDD.esborrarSudoku(sudoku);
    }

    public Map<Integer, Date> getPartidesRecuperades() {
        return this.partidesRecuperades;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setEstatJuagdor() throws Exception {

        if (!(jugador.getNom().equals("Anonim"))) {
            jugador.setEstat(false);
            jugadorBBDD.updateJugador(jugador);
        }
    }

    public void recuperarTaulellGuardat() throws Exception {
        sudokuBBDD.recuperarTaulellFromSudoku(sudoku);
    }

    public Taulell getTaulellFromSudoku() {
        return sudoku.getTaulell();
    }

}
