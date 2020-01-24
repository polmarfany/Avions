package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner teclat = new Scanner(System.in);

    public static ArrayList<Avio> arrayListAvions = new ArrayList<>();
    public static double MINDISTANCIASEGURETAT = 2;
    public static double MAXVELOCITATAVIONSCOMERCIALS = 50;
    public static double MAXVELOCITATAVIONSCOMBAT = 100;
    public static int MAXPASSATGERS = 250;
    public static int MAXTRIPULACIO = 40;
    public static double MAXCOORDENADA = 1000;
    public static String LS = System.lineSeparator();

    public static void main(String[] args) {

        System.out.println("Això és el simulador d'un control aeri. Aquest control té una mesura de 1000km3 (cúbics, 1000x1000x1000). Els avions es mouran també en blocks");
        System.out.println("Per començar, hauràs d'indicar un parell de configuracions per a la teva aplicació.");
        System.out.println("Primer de tot, indica la distància de seguretat mínima (recomanem que sigui 2 blocks per a que sigui realista).");
        System.out.println("NOTA: els avions de combat no han de mantenir aquesta distancia, pero tot i així poden estavellar-se:");
        //MINDISTANCIASEGURETAT = teclat.nextDouble();
        System.out.println("Segon, indica quina vols que sigui la velocitat màxima dels avions comercials (recomanem 50).");
        System.out.println("NOTA: els avions de combat tindràn obligatòriament el doble de velocitat, i la velocitat mínima d'ambods tipus serà 1 desena part de la seva VELOCITAT MAXIMA):");

        //MAXVELOCITATAVIONSCOMERCIALS = teclat.nextDouble();
        MAXVELOCITATAVIONSCOMBAT = MAXVELOCITATAVIONSCOMERCIALS* 2;

        while (true) {
            String menu = "Benvingut al Menú." + LS
                    + "1. Gestió de l'espai aeri: Afegir/treure avions, modoficar un avio" + LS
                    + "2. Control dels avions: Controles de manera individual el comportament de cada avió." + LS
                    + "3. Exit program." + LS
                    + "0. Mapa. RECORDA: En qualsevol moment pots escriure el caracter '0' i s'imprimirà tot l'espai aeri, sense interrompre l'activitat anterior. " + LS
                    + "Introdueix l'opció desitjada: ";

            switch ( demanarInt(menu, 3) ) {
                case 1:
                    gestioEspai();
                    break;
                case 2:
                    controlAvions();
                    break;
                case 0:
                    mapa();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("AIXO NO HAURIA DE SORTIR MAI");
                    break;
            }
        }

        /*
        Avio avioOrigen = new Avio("origen");
        Avio avioSec = new Avio("sec");
        AvioComercial avioTerc = new AvioComercial("terc", 50);
        avioSec.setCoordenadesActuals(new Coordenada(52, 52, 53) );
        avioTerc.setCoordenadesActuals(new Coordenada(100, 100, 100) );
        avioOrigen.setCoordenadesDesti(new Coordenada(100, 100, 100) );
        avioOrigen.setVelocitat(500);
        arrayListAvions.add(avioOrigen);
        arrayListAvions.add(avioSec);
        arrayListAvions.add(avioTerc);
        avioOrigen.avansar();

        System.out.println(avioOrigen.getDistanciaDesti() + "distancia desti/" + avioOrigen.getCoordenadesDesti().toString() + "coordenades desti");

        System.out.println(avioOrigen.getCoordenadesActuals().toString() +"coordenades actuals" );

         */

    }


    private static void gestioEspai() {
        boolean stop = false;
        String preg = "Gestió Espai Aeri. Creació i configuració de nous avions, gestionant els existents també." + LS
                + "1. Crear nou avió." + LS
                + "2. Editar un avió existent." + LS
                + "3. Xifrar avions de combat." + LS
                + "4. Desxifrar avions de combat." + LS
                + "5. Torna al menú." + LS
                + "Introdueix l'opció desitjada: ";

        while (!stop ) {
            switch (demanarInt(preg, 2)) {
                case 1:
                    switch (demanarInt("Introdueix el tipus d'avio: " + LS + "1. Comercial " + LS + "2. Combat", 2)) {
                        case 1:
                            nouAvioComercial();
                            break;
                        case 2:
                            nouAvioCombat();
                            break;
                    }
                    break;
                case 2:
                    int idAvioEdit = checkMatriculaListPosicio(demanarString("Introdueix l'identificador de l'avió que vols editar: ") );
                    editarAvio(arrayListAvions.get(idAvioEdit) );
                    break;
                case 3:
                    //xifrarAvions();
                    break;
                case 4:
                    //desxifrarAvions();
                    break;
                case 5:
                    stop = true;
                    break;
            }
        }
    }

    //GESTIOAVIONS
    public static void nouAvioComercial() {
        String identificador = checkMatriculaListString();
        int passatgers = demanarInt("Introdueix el numero de passatgers (màxim "+ MAXPASSATGERS + ")", MAXPASSATGERS);
        arrayListAvions.add(new AvioComercial(identificador, new Coordenada(0,0,0), passatgers) );
        System.out.println("L'avio s'ha creat amb èxit");
    }

    public static void nouAvioCombat() {
        String identificador = checkMatriculaListString();
        int tripulants = demanarInt("Introdueix la quantitat de tripulants (màxim " + MAXTRIPULACIO + ")", MAXTRIPULACIO);
        arrayListAvions.add(new AvioCombat(identificador, new Coordenada(0,0,0), tripulants) );
    }

    private static void editarAvio(Avio avio) {
        String preg;
        if ( avio instanceof AvioComercial ) {
            preg = "Indica que vols modificar:" + LS
                    + "1. Identificador. Actualment és " + avio.getIdentificador() + "." + LS
                    + "2. Passatgers. Actualment és " + ( (AvioComercial) avio).getPassatgers() + "." + LS;

            switch (demanarInt(preg, 2 ) ){
                case 1:
                    avio.setIdentificador(checkMatriculaListString());
                    break;
                case 2:
                    ((AvioComercial) avio).setPassatgers(demanarInt("Introdueix el valor desitjat: ", MAXPASSATGERS) );
                    break;
            }
        }
        else { //edicio d'avio de combat
            preg = "Indica que vols modificar:" + LS
                    + "1. Identificador. Actualment és " + avio.getIdentificador() + "." + LS
                    + "2. Tripulants. Actualment és " + ( (AvioCombat) avio ).getTripulacio() + "." + LS;

            switch (demanarInt(preg, 2) ){
                case 1:
                    avio.setIdentificador(checkMatriculaListString());
                    break;
                case 2:
                    ((AvioCombat) avio).setTripulacio(demanarInt("Introdueix el valor desitjat: ", MAXPASSATGERS) );
                    break;
            }
        }
    }
    //GESTIOAVIONS//

    //CONTROLAVIONS
    private static void controlAvions() {
        boolean stop = false;

        String idAvio = demanarString("Introdueix l'identificador de l'avió que vols controlar: ");

        int index = checkMatriculaListPosicio(idAvio);
        Avio avio = arrayListAvions.get(index);

        String preg;
        int max;


        if (avio.getClass().equals(AvioComercial.class)) {
            preg = "Control d'avio comercial: "+ avio.getIdentificador() + LS
                    + "1. Encendre motor." + LS
                    + "2. Canviar velocitat." + LS
                    + "3. Establir coordenades." + LS
                    + "4. Torna al menú." + LS
                    + "Introdueix l'opció desitjada: ";

            int opcio = demanarInt(preg, 4);

            while (!stop ) {
                switch (opcio) {
                    case 1:
                        avio.motorSwitch();
                        break;
                    case 2:
                        avio.setVelocitat(demanarDouble("Introdueix la velocitat del avió: ", avio.getMaximaVelocitat() ) );
                        break;
                    case 3:

                        Coordenada novaCoord = new Coordenada( demanarDouble("Introdueix X: ", MAXCOORDENADA),
                                demanarDouble("Introdueix Y: ", MAXCOORDENADA),
                                demanarDouble("Introdueix Z: ", MAXCOORDENADA));
                        avio.setCoordenadesActuals(novaCoord);
                        break;
                    case 4:
                        stop = true;
                        break;
                }
            }
        }

        else {
            preg = "Control d'avio de combat: " + avio.getIdentificador() + LS
                    + "1. Encendre motor." + LS
                    + "2. Canviar velocitat." + LS
                    + "3. Establir coordenades." + LS
                    + "4. Torna al menú." + LS
                    + "Introdueix l'opció desitjada: ";

            int opcio = demanarInt(preg, 4);

            while (!stop) {
                switch (opcio) {
                    case 1:
                        avio.motorSwitch();
                        break;
                    case 2:
                        avio.setVelocitat(demanarDouble("Introdueix la velocitat del avió: ", avio.getMaximaVelocitat()));
                        break;
                    case 3:

                        Coordenada novaCoord = new Coordenada(demanarDouble("Introdueix X: ", MAXCOORDENADA),
                                demanarDouble("Introdueix Y: ", MAXCOORDENADA),
                                demanarDouble("Introdueix Z: ", MAXCOORDENADA));
                        avio.setCoordenadesActuals(novaCoord);
                        break;

                    case 4:
                        stop = true;
                        break;
                }
            }
        }
    }

    //CONTROLAVIONS//

    private static void mapa() { //printer
        System.out.println("mapa");

    }

    //eienes del programa
    public static int demanarInt(String pregunta, int max){
        boolean inputCorrecte = false;
        int num = -1;
        while (!inputCorrecte) {
            System.out.println(LS + pregunta);
            if (teclat.hasNextInt() ) {
                num = teclat.nextInt();
                if (num >= 0 && num <= max) {
                    if (num == 0) {
                        mapa();
                    }
                    else {
                        inputCorrecte = true;
                    }
                }
                else {
                    System.out.println("El número ha de ser entre 0 i " + max + ".");
                }
            }
            else {
                System.out.println("Introdueix un numero de 0 al " + max + ".");
                teclat.next();
            }
        }
        return num;
    }

    public static double demanarDouble(String pregunta, double max){
        boolean inputCorrecte = false;
        double num = -1;
        while (!inputCorrecte) {
            System.out.println(LS + pregunta);
            if (teclat.hasNextDouble() ) {
                num = teclat.nextDouble();
                if (num >= 0 && num <= max) {
                    if (num == 0) {
                        mapa();
                    }
                    else {
                        inputCorrecte = true;
                    }
                }
                else {
                    System.out.println("El número ha de ser entre 0 i " + max + ".");
                }
            }
            else {
                System.out.println("Introdueix un numero de 0 al " + max + ".");
                teclat.next();
            }
        }
        return num;
    }

    public static String demanarString(String pregunta){
        String resposta;
        boolean inputCorrecte = false;

        do {
            System.out.println(LS + pregunta);
            resposta = teclat.next();

            if (resposta.length() == 1 && resposta.charAt(0) == '0') {
                mapa();
            }
            else {
                inputCorrecte = true;
            }

        } while (!inputCorrecte);

        return resposta;
    }
    public static String checkMatriculaListString() {
        String id = "";
        boolean boo = true;

        while (boo) {
            id = demanarString("Introdueix la nova matrícula: ");
            
            if (checkMatriculaListPosicio(id) == -1 ) {
                boo = false;
            }
            else {
                System.out.println("La matricula introduida ja existeix");
            }
        }
        return id;
    }
    
    public static int checkMatriculaListPosicio(String id) {
        int index = -1;

        for (Avio avio : arrayListAvions) {
            if (avio.getIdentificador().equals(id) ) {
                index = arrayListAvions.indexOf(avio);
            }
        }        
        return index;
    }
}