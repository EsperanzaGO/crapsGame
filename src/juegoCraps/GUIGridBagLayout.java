package juegoCraps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Component;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIGridBagLayout extends JFrame {
    private static final  String MENSAJE_INICIO = "Bienvenido a Craps \n"
            + "Oprime el botón lanzar para iniciar el juego"
            + "\n Si tu tiro de salida es 7 u 11 ganas con Natural"
            + "\n Si tu tiro de salida es 2, 3  o 12 pierdes con Craps"
            + "\n Si sacas cualquier otro valor establecerás el Punto."
            + "\n Estado en punto podrás seguir lanzando los dados, "
            + "\n pero ganarás si sacas nuevamente el valor del Punto"
            + "\n sin que previamente hayas sacado 7.";

    private Header headerProject;
    private JLabel dado1, dado2;
    private JButton lanzar, salir, ayuda;
    private JPanel panelDados;
    private ImageIcon imageDado;
    private JTextArea resultados, mensajeSalida, resultadosDados;
    private  JSeparator separator;
    private Escucha escucha;
    private ModelCraps modelCraps;
    private Point initialClick;





    public GUIGridBagLayout(){
        initGUI();

        //Default JFrame configuration

        this.setTitle("Juego Craps");
        this.setUndecorated(true);
        this.setBackground(new Color(255, 255, 255, 0));
        this.pack();
        //this.setResizable(true);
        this.setVisible(true);

        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });

    }



    public void initGUI(){
        //Set up JFrame Container's Layout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints  constraints = new GridBagConstraints();

        //Create Listener Object or Control Object
        escucha = new Escucha();
        modelCraps = new ModelCraps();


        //Set up JComponents

        headerProject = new Header("Mesa de Juego Craps", Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        this.add(headerProject,constraints);

        ayuda = new JButton("?");
        ayuda.addActionListener(escucha);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        this.add(ayuda,constraints);

        salir = new JButton("Salir");
        salir.addActionListener(escucha);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        this.add(salir,constraints);

        imageDado = new ImageIcon(getClass().getResource("/resources/dado_6.png"));
        dado1 = new JLabel(imageDado);
        dado2 = new JLabel(imageDado);

        panelDados = new JPanel();
        panelDados.setPreferredSize(new Dimension(450, 300));
        panelDados.setBorder(BorderFactory.createTitledBorder("Tus dados"));
        panelDados.add(dado1);
        panelDados.add(dado2);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        add(panelDados,constraints);

        resultadosDados = new JTextArea(4, 31);

        resultadosDados.setBorder(BorderFactory.createTitledBorder("Resultados: "));
        resultadosDados.setText("Debes lanzar los dados.");
        resultadosDados.setBackground(null);
        resultadosDados.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        add(resultadosDados,constraints);

        lanzar = new JButton("lanzar");
        lanzar.addActionListener(escucha);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        add(lanzar,constraints);

        mensajeSalida = new JTextArea(4, 31);
        mensajeSalida.setText("Usa el botón (?) para ver las instrucciones.");
        mensajeSalida.setBorder(BorderFactory.createTitledBorder("Mensajes: "));
        resultadosDados.setBackground(null);
        mensajeSalida.setEditable(false);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        add(mensajeSalida,constraints);




    }


    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUIGridBagLayout miProjectGUI = new GUIGridBagLayout();
        });
    }


    private class Escucha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
          if(e.getSource() == lanzar){
              modelCraps.calcularTiro();
              int[] caras = modelCraps.getCaras();
              imageDado = new ImageIcon(getClass().getResource("/resources/"+caras[0]+".png"));
              dado1.setIcon(imageDado);
              imageDado = new ImageIcon(getClass().getResource("/resources/"+caras[1]+".png"));
              dado2.setIcon(imageDado);
              modelCraps.determinarJuego();
              resultadosDados.setText(modelCraps.getEstadoToString()[0]);
              mensajeSalida.setText(modelCraps.getEstadoToString()[1]);

          }else{
              if(e.getSource() == ayuda){
                  JOptionPane.showMessageDialog(null, MENSAJE_INICIO);
              }else{
                  System.exit(0);
              }
          }

        }
    }

}
