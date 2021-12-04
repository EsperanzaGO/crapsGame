package juegoCraps;

/**
 * ModelCraps apply craps rules.
 * estado = 1 Natural winner
 * estado = 2 Craps Loser
 * estado = 3 Stablish Point
 * estado = 4 Punto Winner
 * estado = 5 Punto Looser
 * @author  Esperanza Olivo
 * @version V1.0.0.0
 * date 2/12/2021
 */
public class ModelCraps {
    private Dado dado1, dado2;
    private int tiro, punto, estado, flag;
    private String estadoToString;
    private  int[] caras;

    /**
     * Class Constructor
     */
    public ModelCraps(){
        dado1 = new Dado();
        dado2 = new Dado();
        caras = new int[2];
        flag = 0;
    }

    /**
     * Establish the tiro according to each dice.
     */
    public void calcularTiro(){
        caras[0] = dado1.getCara();
        caras[1] = dado2.getCara();
        tiro = caras[0] + caras[1];

    }

    /**
     * Establish game state according to estado attribute value
     * estado = 1 Natural winner
     * estado = 2 Craps Loser
     * estado = 3 Stablish Point
     * estado = 4 Punto Winner
     *  estado = 5 Punto Loser
     */
    public void determinarJuego(){
        if (flag == 0){
            if(tiro == 7 || tiro == 11){
                estado = 1;
            }else{
                if(tiro == 3 || tiro == 2 || tiro == 12){
                    estado = 2;
                }else{
                    estado = 3;
                    punto = tiro;
                    flag = 1;
                }
            }

        }else{
            //Ronda punto
            rondaPunto();
        }

    }

    /**
     * Establish game state according to estado attribute value
     * estado = 4 Point Winner
     * estado = 5 Point Loser
     */
    private  void rondaPunto(){
        if(tiro == punto){
            estado = 4;
            flag = 0;
        }
        if(tiro == 7){
            estado = 5;
            flag = 0;
        }
    }

    public int getTiro() {
        return tiro;
    }

    public int getPunto() {
        return punto;
    }

    /**
     * Establish message game state according to estado attribute value.
     * @return Message for the View Class
     */
    public String getEstadoToString() {
        switch (estado){
            case 1: estadoToString = "Has sacado natural. ¡Ganaste!";
                    break;
            case 2: estadoToString = "Has sacado Caps. ¡Perdiste!";
                    break;
            case 3: estadoToString = "Estableciste punto en: "+punto+
                                    " Debes seguir lanzando." +
                                      "\n Si sacas 7 antes que " +punto+ "perderas";
                    break;
            case 4: estadoToString = "Volviste a sacar 7 antes que " +punto+ ". Has ganado";
                break;
            case 5: estadoToString = "Sacaste 7 antes que " +punto+ ". Has perdido";
                    break;

        }
        return estadoToString;
    }

    public int[] getCaras() {
        return caras;
    }
}

