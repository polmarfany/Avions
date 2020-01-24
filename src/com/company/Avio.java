package com.company;

public abstract class Avio {
    private String identificador;

    private Coordenada coordenadesActuals;
    private Coordenada coordenadesDesti;
    private double velocitat;
    private double seguretatDistancia;
    private double maximaVelocitat;
    private boolean motor;
    
    public Avio(String nom, Coordenada coordenada){
        this.identificador = nom;
        this.coordenadesActuals = coordenada;
        this.seguretatDistancia = Main.MINDISTANCIASEGURETAT;
        this.maximaVelocitat = Main.MAXVELOCITATAVIONSCOMBAT;
        this.motor = false;
    }

    abstract public String toString();

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Coordenada getCoordenadesActuals() {
        return coordenadesActuals;
    }

    public Coordenada getCoordenadesDesti() {
        return coordenadesDesti;
    }

    public double getVelocitat() {
        return velocitat;
    }

    public double getMaximaVelocitat(){
        return this.maximaVelocitat;
    }

    public double getMinVelocitat(){
        return this.maximaVelocitat/10;
    }

    public double getSeguretatDistancia() {
        return seguretatDistancia;
    }

    public void setCoordenadesActuals(Coordenada coordenadesActuals) {
        this.coordenadesActuals = coordenadesActuals;
    }

    public void setCoordenadesDesti(Coordenada coordenadesDesti) {
        this.coordenadesDesti = coordenadesDesti;
    }

    public void motorSwitch(){
        if (this.coordenadesActuals.equals( new Coordenada(0,0,0) )){
            this.motor = !this.motor;
            this.velocitat = 0;
        }
        else {
            System.out.println("Per a poder apagar el motor, l'avió ha de ser a la Coordenada Aeroport (0,0,0).");
        }
    }

    public void setVelocitat(double velocitat) {
        if (velocitat < this.seguretatDistancia ){
            System.out.println("La velocitat indicada no es suficient, torna-ho a intentar.");
        }

        else {
            this.velocitat = velocitat;
        }
    }

    public void avansar() {
        moviment(this.coordenadesDesti);
    }

    public void moviment(Coordenada desti){
        Coordenada origen = this.coordenadesActuals;
        double velocitat = this.velocitat;

        double distanciaFinsDesti = calculDistancia(desti);

        if (distanciaFinsDesti < velocitat ) {
            if (checkColisions(desti, distanciaFinsDesti) ){
                this.setCoordenadesActuals(desti);
            }
        }

        else {//al ser la velocitat menor que la distanciaFinsDesti, la velocitat passa a ser el tram que recorre el avio

            double cOrigenX = origen.getX();
            double cOrigenY = origen.getY();
            double cOrigenZ = origen.getY();
            double cDestiX = desti.getX();
            double cDestiY = desti.getY();
            double cDestiZ = desti.getZ();

            double drp = velocitat/distanciaFinsDesti; //Distancia a Recorrer Percentil (segment, es per a calcular els punts de la nova coordenada)

            double cNovaX = (cOrigenX + cDestiX) * drp;
            double cNovaY = (cOrigenY + cDestiY) * drp;
            double cNovaZ = (cOrigenZ + cDestiZ) * drp;

            Coordenada novaPosicio = new Coordenada(cNovaX, cNovaY, cNovaZ);
            if ( checkColisions(novaPosicio, velocitat) ) {
                this.setCoordenadesActuals(novaPosicio);
            }
        }
    }

    public double calculDistancia(Coordenada desti){
        Coordenada origen = this.coordenadesActuals;
        double distancia;

        double cOrigenX = origen.getX();
        double cOrigenY = origen.getY();
        double cOrigenZ = origen.getY();

        double cDestiX = desti.getX();
        double cDestiY = desti.getY();
        double cDestiZ = desti.getZ();

        distancia = Math.sqrt(Math.pow(cDestiX - cOrigenX, 2) + Math.pow(cDestiY - cOrigenY, 2) + Math.pow(cDestiZ - cOrigenZ, 2) );

        return distancia;
    }
    
    public boolean checkColisions(Coordenada b, double segment){
        boolean check = true;

        Coordenada a = this.coordenadesActuals;  //PUNT A, avio origen
        //Coordenada desti PUNT B, FORMANT SEGMENT AB->
        Coordenada p; //avio secundari PUNT P
        //SEGMENT AP-> = A - P = Ax - PX, Ay - Py, Az - Pz
        //SEGMENT AB-> = B - A = Bx - Ax, By - Ay, Bz - Az

        // (APx, APy, APz) * (ABx, ABy, ABz) / SQUAREROOT(ABx^2 + ABy^2 + ABz^2)
        // (APx, APy, APz) * (ABx, ABy, ABz) / DISTANCIA SEGMENT

        double distanciaAvioSecAmbRecorregut;
        double distanciaAvioSecAmbAvioOrigen;
        double dividentOP;

        double APx;
        double APy;
        double APz;
        double ABx;
        double ABy;
        double ABz;

        for (Avio avio : Main.arrayListAvions) {
            if (avio != this) {
                p = avio.getCoordenadesActuals();
                APx = p.getX() - a.getX();
                APy = p.getY() - a.getY();
                APz = p.getZ() - a.getZ();
                ABx = b.getX() - a.getX();
                ABy = b.getY() - a.getY();
                ABz = b.getZ() - a.getZ();
              /* System.out.println("APx/APy/APZ/ABx/ABy/ABz"+APx+APy+APz+ABx+ABy+ABz);
                System.out.println("PRIMER POW="+Math.pow( (APy*ABz)-(ABy*APz), 2 ) );
                System.out.println("SEGON POW="+Math.pow( (APx*ABz)-(ABx*APy), 2) );
                System.out.println("TERCER POW="+Math.pow( (APx*ABy)-(ABx*APy), 2) );*/

                dividentOP = Math.pow( (APy*ABz)-(ABy*APz), 2 ) - ( Math.pow( (APx*ABz)-(ABx*APy), 2) ) + Math.pow( (APx*ABy)-(ABx*APy), 2);
                //System.out.println("DIVIDENTOP = "+dividentOP);

                distanciaAvioSecAmbAvioOrigen = Math.sqrt( Math.pow (APx, 2) + Math.pow(APy, 2) + Math.pow(APz, 2) );
                //System.out.println("DISTANCIAAVIOSECAMBAVIOORIGEN= "+distanciaAvioSecAmbAvioOrigen);

                if (dividentOP == 0){ //significa que el AVIOSECUNDARI(P) es situa en la linea (suposant que es infinita) del AVIOORIGINAL(A) fins al DESTI(B).
                                        //el que s'ha de mirar ara es simplement la distancia fins a objectiu i fins a aviosecundari
                    if (distanciaAvioSecAmbAvioOrigen  <= segment ) {
                        check = false;
                        System.out.println("L'avio s'estavellarà directament contra " + avio.getIdentificador() + avio.getCoordenadesActuals().toString() + " a una distància de " + distanciaAvioSecAmbAvioOrigen + "!");
                    }
                    else if (distanciaAvioSecAmbAvioOrigen  <= segment +  this.seguretatDistancia) {
                        check = false;
                        System.out.println("L'avio no mantindra la distancia de seguretat amb l'avio "+ avio.getIdentificador() + avio.getCoordenadesActuals().toString());
                    }
                }

                else if (dividentOP > 0) {

                    distanciaAvioSecAmbRecorregut = Math.sqrt(dividentOP) / segment;

                    if (distanciaAvioSecAmbRecorregut < Main.MINDISTANCIASEGURETAT) {
                        check = false;
                        System.out.println("L'avio no mantindrà la distància de seguretat amb " + avio.getIdentificador() + avio.getCoordenadesActuals().toString() + "a una distància de "+distanciaAvioSecAmbAvioOrigen + "!");
                    }
                }
            }
        }
        return check;
    }
}
