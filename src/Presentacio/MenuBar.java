package Presentacio;

import Aplicacio.Control;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

    private JMenu menuPartida, menuPersistencia, recuperarPArtides;
    private JMenu submenuPartida;
    private JMenuItem Sudoku0, SudokiIniciat, GuardarSudokuNou, GenerarSudokuAleatori, CrearSudokuUsuari;
    private JLabel nomJugadorActual = new JLabel("");
    private Presentacio presentacioClasse;
    private Control controlClasse;

    private JRadioButton[] fontButtons;
    private Map<Integer, Date> recuperats;

    private String nom;
    private String nomJugadorPerJLbael;

    public MenuBar(Presentacio presentacio, Control control) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex1) {
        }

        this.presentacioClasse = presentacio;
        this.controlClasse = control;
        this.nom = controlClasse.getJugador().getNom();

        CrearSudokuUsuari = new JMenuItem(" Iniciar Sudoku");

        // ////////////////////// MENU PARTIDA ////////////////////////

        menuPartida = new JMenu("Partida");

        // SUBMENU PARTIDA //

        submenuPartida = new JMenu("Crear sudoku: ");

        Sudoku0 = new JMenuItem("Buit");
        submenuPartida.add(Sudoku0);

        SudokiIniciat = new JMenuItem("Estandard");
        submenuPartida.add(SudokiIniciat);
        menuPartida.add(submenuPartida);

        // FI SUBMENU //

        menuPartida.addSeparator();
        GenerarSudokuAleatori = new JMenuItem("Sudoku Aleatori");
        menuPartida.add(GenerarSudokuAleatori);

        // FI MENU PARTIDA

        // ////////////////////// MENU GUARDAR ////////////////////////

        menuPersistencia = new JMenu("Persistencia");

        GuardarSudokuNou = new JMenuItem("Guardar");
        menuPersistencia.add(GuardarSudokuNou);

        // ///////////////////// RECUPERAR PARTIDES MENU ///////////////////////

        recuperarPArtides = new JMenu("Recuperar partides");

        nomJugadorPerJLbael = "Jugador: " + nom;

        this.add(menuPartida);
        this.add(menuPersistencia);
        this.add(recuperarPArtides);
        this.add(nomJugadorActual);
        this.add(CrearSudokuUsuari);

        if ((!controlClasse.estaBuit()))
            CrearSudokuUsuari.setVisible(false);
        else {
            setMenuPersistenica(false);
            setSudokuAleatori(false);
        }

        if (controlClasse.getJugador().getNom().equals("Anonim")) {
            setMenuPersistenica(false);
            recuperarPArtides.setEnabled(false);
        } else {
            nomJugadorActual.setText(nomJugadorPerJLbael + " ID: " + getIdSudokuActual());

        }

        recuperarPArtides.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                try {
                    mostratpartidesrecuperades();

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        CrearSudokuUsuari.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (presentacioClasse.getNumeroEntrades() >= 17)
                    try {
                        setSudokuAleatori(true);
                        controlClasse.iniciarUsuari();
                        presentacioClasse.actualitzar();
                        actualitzarNom();
                        CrearSudokuUsuari.setVisible(false);
                        if (!(nom.equals("Anonim")))
                            setMenuPersistenica(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                    }
                else {
                    setMenuPersistenica(false);
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No es posible crear el taulell, has de tenir" + " 17 o mes numeros introduits"
                                    + "\n(Quantitat de numeros introduits : " + presentacioClasse.getNumeroEntrades()
                                    + ")");
                }
            }
        });

        GenerarSudokuAleatori.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                presentacioClasse.setNumeroEntraddes(0);
                presentacioClasse.elimanar();
                try {
                    int res = JOptionPane.showConfirmDialog(new JFrame(), "Perdras el sudoku actual, vols continuar?",
                            "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (res == JOptionPane.YES_OPTION) {
                        controlClasse.canviarTaulell();
                        if (!(nom.equals("Anonim"))) {
                            intentarIntentarSudoku();
                            actualitzarNom();
                            setMenuPersistenica(true);
                        }
                        presentacioClasse.actualitzar();

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                }

            }
        });

        Sudoku0.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(new JFrame(),
                        "Instruccions:\n - Un cop introduit un numero, fer clic ENTER per confirmar"
                                + "\n - En cas d'introduir un 0, la casella no tindra cap valor"
                                + "\n - Per finalitzar la creacio premeu 'Iniciar Sudoku' en el menu");

                setSudokuAleatori(false);
                setMenuPersistenica(false);
                presentacioClasse.setNumeroEntraddes(0);
                CrearSudokuUsuari.setVisible(true);
                presentacioClasse.elimanar();
                try {
                    controlClasse.inciarSudokuBuit();
                    presentacioClasse.actualitzar();
                    if (!(nom.equals("Anonim"))) {
                        intentarIntentarSudoku();
                        actualitzarNom();
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }

            }
        });

        SudokiIniciat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSudokuAleatori(true);
                CrearSudokuUsuari.setVisible(false);
                presentacioClasse.elimanar();
                try {
                    controlClasse.inciarSudokuBuit();
                    controlClasse.iniciarSudokuPredefinit();
                    presentacioClasse.actualitzar();

                    if (!(nom.equals("Anonim"))) {
                        intentarIntentarSudoku();
                        actualitzarNom();
                        setMenuPersistenica(true);
                    }
                } catch (Exception error) {
                    error.getStackTrace();
                }

            }
        });

        GuardarSudokuNou.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku?\n", "TRIA",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (res == JOptionPane.YES_OPTION) {
                        controlClasse.storeSudoku();
                    }
                } catch (Exception e1) {

                    e1.printStackTrace();
                }

            }
        });

    }

    private String getIdSudokuActual() {
        return String.valueOf(controlClasse.getSudoku().getIdSudoku());
    }

    private void actualitzarNom() {
        nomJugadorActual.setText(nomJugadorPerJLbael + " ID: " + getIdSudokuActual());
    }

    public void actionPerformed(ActionEvent e) {

        int res = JOptionPane.showConfirmDialog(new JFrame(), "Vols guardar el sudoku abans de canviar-lo?", "TRIA",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (res == JOptionPane.YES_OPTION) {

            try {
                controlClasse.storeSudoku();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        String agafat = ((((JRadioButton) e.getSource()).getText()));
        String[] parts = agafat.split(" - ");
        try {
            controlClasse.inciarSudokuBuit();
            controlClasse.getSudoku().setIdSudoku(Integer.parseInt(parts[0]));
            presentacioClasse.mostratSudokuRecuperat();
            presentacioClasse.actualitzar();
            actualitzarNom();
        } catch (Exception e1) {

            e1.printStackTrace();
        }

    }

    private void mostratpartidesrecuperades() throws Exception {

        int quinEstaSelecionat = controlClasse.getSudoku().getIdSudoku();

        if (!(nom.equals("Anonim"))) {
            recuperarPArtides.removeAll();
            recuperats = controlClasse.getTimeStamps();

            if (recuperats != null) {
                fontButtons = new JRadioButton[recuperats.size()];
                ButtonGroup fontGroup = new ButtonGroup();

                int i = 0;
                for (Entry<Integer, Date> entry : recuperats.entrySet()) {

                    fontButtons[i] = new JRadioButton();
                    fontButtons[i].setText(entry.getKey() + " - " + entry.getValue());
                    fontButtons[i].addActionListener(this);
                    fontGroup.add(fontButtons[i]);
                    recuperarPArtides.add(fontButtons[i]);
                    i++;
                }
                try {
                    fontButtons[quinEstaSelecionat - 1].setSelected(true);
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }

    public void setMenuPersistenica(boolean estat) {
        menuPersistencia.setEnabled(estat);
    }

    public void setControlClasse(Control control) {
        this.controlClasse = control;

    }

    public void setSudokuAleatori(boolean estat) {
        GenerarSudokuAleatori.setEnabled(estat);
    }

    private void intentarIntentarSudoku() throws Exception {
        try {
            controlClasse.iniciarSudoku();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            controlClasse.setEstatJuagdor();
            System.exit(0);
        }
    }
}