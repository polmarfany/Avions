package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner teclat = new Scanner(System.in);

    public static ArrayList<Avio> arrayListAvions = new ArrayList<>();
    public static double MAXVELOCITATAVIONSCOMERCIALS = 50;
    public static double MAXVELOCITATAVIONSCOMBAT = 100;
    public static double MINDISTANCIASEGURETATCOMERCIAL = 2;
    public static double MINDISTANCIASEGURETATCOMBAT = 2;
    public static int MAXPASSATGERS = 250;
    public static int MAXTRIPULACIO = 40;
    public static double MAXCOORDENADA = 1000;
    public static String LS = System.lineSeparator();



    public static void main(String[] args) {

        System.out.println("Això és el simulador d'un control aeri. Aquest control té una mesura, per defecte, de 1000blocks^3 (cúbics, 1000x1000x1000). Els avions es mouran també en blocks");

        while (true) {
            String menu = "Benvingut al Menú." + LS
                    + "'0'. Exit program." + LS
                    + "'1'. Gestió de l'espai aeri: Afegir/treure avions, modoficar un avio, modificar parametres" + LS
                    + "'2'. Control dels avions: Controles de manera individual el comportament de cada avió." + LS
                    + "'-1'. Mapa. RECORDA: En qualsevol moment pots escriure el caracter '-1' i s'imprimirà tot l'espai aeri, sense interrompre l'activitat anterior. " + LS
                    + "Introdueix l'opció desitjada: ";

            switch ( demanarInt(menu, 2) ) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    gestioEspai();
                    break;
                case 2:
                    controlAvions();
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
                + "'0.' Torna al menú." + LS
                + "'1.' Crear nou avió." + LS
                + "'2.' Editar un avió existent." + LS
                + "'3.' Xifrar avions de combat." + LS
                + "'4.' Desxifrar avions de combat." + LS
                + "'5.' Canvi de diversos paràmetres del joc. " + LS
                + "Introdueix l'opció desitjada: ";

        while (!stop ) {
            switch (demanarInt(preg, 2)) {
                case 0:
                    stop = true;
                    break;
                case 1:
                    switch (demanarInt("Introdueix el tipus d'avio: " + LS + "'1.' Comercial " + LS + "'2.' Combat", 2)) {
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
                    canviParametres();
                    break;

            }
        }
    }

    //GESTIOAVIONS
    public static void nouAvioComercial() {
        String identificador = checkMatriculaListString();
        int passatgers = demanarInt("Introdueix el numero de passatgers (màxim "+ MAXPASSATGERS + ")", MAXPASSATGERS);
        arrayListAvions.add(new AvioComercial(identificador, new Coordenada(0,0,0), passatgers, MAXVELOCITATAVIONSCOMERCIALS, MINDISTANCIASEGURETATCOMERCIAL) );
        System.out.println("L'avio s'ha creat amb èxit");
    }

    public static void nouAvioCombat() {
        String identificador = checkMatriculaListString();
        int tripulants = demanarInt("Introdueix la quantitat de tripulants (màxim " + MAXTRIPULACIO + ")", MAXTRIPULACIO);
        arrayListAvions.add(new AvioCombat(identificador, new Coordenada(0,0,0), tripulants, MAXVELOCITATAVIONSCOMBAT, MINDISTANCIASEGURETATCOMBAT) );
    }

    private static void editarAvio(Avio avio) {
        int max;
        String preg = "Indica que vols modificar:" + LS
                + "'1.' Identificador. Actualment és " + avio.getIdentificador() + "." + LS
                + "'2.' Velocitat màxima. Actualment és " + avio.getMaximaVelocitat() + "." + LS
                + "'3.' Distancia Seguretat. Actualment és " + avio.getMinDistanciaSeguretat() + "." + LS;

        if ( avio instanceof AvioComercial ) {
            max = 4;
            preg = preg + "'4.' Passatgers. Actualment és " + ( (AvioComercial) avio).getPassatgers() + "." + LS;
        }
        else { //edicio d'avio de combat
            max = 5;
            preg = preg + "'4.' Tripulacio. Actualment és " + ( (AvioCombat) avio).getTripulacio() + "." + LS
                        + "'5. Numero de màxim missils. Actualment és " +  ( (AvioCombat) avio).getMissils().getMaxNumShot() + "." + LS;
        }

        int opcio = (demanarInt(preg, max));
        switch (opcio){
            case 1:
                avio.setIdentificador(checkMatriculaListString());
                break;
        }

        if (avio instanceof AvioComercial) {
            switch (opcio) {
                case 2:
                    avio.setMaximaVelocitat(demanarDouble("Introdueix el valor desitjat(MAX:"+MAXVELOCITATAVIONSCOMERCIALS+"):", MAXVELOCITATAVIONSCOMERCIALS));
                    break;
                case 3:
                    avio.setMinDistanciaSeguretat(demanarDouble("Introdueix el valor desitjat(MAX:"+MINDISTANCIASEGURETATCOMERCIAL+"):", MINDISTANCIASEGURETATCOMERCIAL));
                    break;
                case 4:
                    ((AvioComercial) avio).setPassatgers(demanarInt("Introdueix el valor desitjat: ", MAXPASSATGERS) );
                    break;
            }
        }
        else {
            switch (opcio) {
                case 2:
                    avio.setMaximaVelocitat(demanarDouble("Introdueix el valor desitjat(MAX:"+MAXVELOCITATAVIONSCOMBAT+"):", MAXVELOCITATAVIONSCOMBAT));
                    break;
                case 3:
                    avio.setMinDistanciaSeguretat(demanarDouble("Introdueix el valor desitjat(MAX:"+MINDISTANCIASEGURETATCOMBAT+"):", MINDISTANCIASEGURETATCOMBAT));
                    break;
                case 4:
                    ((AvioCombat) avio).setTripulacio(demanarInt("Introdueix el valor desitjat: ", MAXTRIPULACIO) );
                    break;
                case 5:
                    ((AvioCombat) avio).getMissils().setMaxNumShot(demanarInt("Introdueix el valor desitjat(MAXIM 5)", 5 ));
                    break;
            }
        }
    }

    public static void canviParametres() {
        boolean stop = false;
        int max;
        String preg = "Diversos parametres de diferents elements del programa en el momemnt de la seva creacio. Bàsicament, són els límits del programa." + LS
            + "'0.' Torna al menú." + LS
            + "'1.' P: Minim distanca seguretat de avions Comercal: " + MINDISTANCIASEGURETATCOMERCIAL+ LS
            + "'2.' P: Minim distanca seguretat de avions Combat: " + MINDISTANCIASEGURETATCOMBAT+ LS
            + "'3.' P: MAX Velocitat Avions Comercials: " + MAXVELOCITATAVIONSCOMERCIALS+ LS
            + "'4.' P: MAX Velocitat Avions Combat: " + MAXVELOCITATAVIONSCOMBAT + LS
            + "'5.' P: MAX Passatgers Avions Comercials: " + MAXPASSATGERS + LS
            + "'6.' P: MAX Tripulacio Avions Combat: " + MAXTRIPULACIO + LS
            + "'7.' P: LIMIT GESTIO ESPAI AERI: " + MAXCOORDENADA + LS
            + "Introdueix el parametre que vols cambiar: ";

        while (!stop ) {
            switch (demanarInt(preg, 7)) {
                case 0:
                    stop = true;
                    break;
                case 1:
                    max = 3;
                    MINDISTANCIASEGURETATCOMERCIAL = demanarDouble("Introdueix fins un maxim de " + max, max);
                    break;
                case 2:
                    max = 3;
                    MINDISTANCIASEGURETATCOMBAT = demanarDouble("Introdueix fins un maxim de " + max, max);
                    break;
                case 3:
                    max = 100;
                    MAXVELOCITATAVIONSCOMERCIALS = demanarDouble("Introdueix fins un maxim de " + max, max);
                    break;
                case 4:
                    max = 200;
                    MAXVELOCITATAVIONSCOMBAT = demanarDouble("Introdueix fins un maxim de " + max, max);
                    break;
                case 5:
                    max = 200;
                    MAXPASSATGERS = demanarInt("Introdueix fins un maxim de " + max, max);
                    break;
                case 6:
                    max = 40;
                    MAXPASSATGERS = demanarInt("Introdueix fins un maxim de " + max, max);
                    break;
                case 7:
                    max = 1000;
                    MAXCOORDENADA = demanarDouble("Introdueix fins un maxim de " + max, max);
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
                    + "'0.' Torna al menú." + LS
                    + "'1.' Encendre motor." + LS
                    + "'2.' Canviar velocitat." + LS
                    + "'3.' Establir coordenades." + LS
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
                    + "'0.' Torna al menú." + LS
                    + "'1.' Encendre motor." + LS
                    + "'2.' Canviar velocitat." + LS
                    + "'3.' Establir coordenades." + LS
                    + "'4.' Disparar objectiu." + LS
                    + "Introdueix l'opció desitjada: ";

            int opcio = demanarInt(preg, 4);

            while (!stop) {
                switch (opcio) {
                    case 0:
                        stop = true;
                        break;
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
                        int posicioAvioObjectiu = checkMatriculaListPosicio(demanarString("Introdueix el identificador de l'avio que vols disparar"));
                        Avio avioObjectiu = arrayListAvions.get(posicioAvioObjectiu);
                        if ( ((AvioCombat)avio).disparar(avioObjectiu) ) {
                            arrayListAvions.remove(posicioAvioObjectiu);
                        }
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
                if (num >= -1 && num <= max) {
                    if (num == -1) {
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
                    if (num == -1) {
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

            if (resposta.equals("-1")) {
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