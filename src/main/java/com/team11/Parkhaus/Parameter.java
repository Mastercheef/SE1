package com.team11.Parkhaus;

public class Parameter {
    public int carNummer=0;

    public Parameter(int nummer){
        this.carNummer = nummer;
    }

    private String licensePlate() {
        String s = "";
        int x = (int)(Math.random() * 3 + 1);
        int y = (int)(Math.random() * 2 + 1);
        int z = (int)(Math.random() * 4 + 1);

        for (int i = 0; i < x; i++) s += (char) (Math.random() * 26 + 'A');
        s += "-";
        for (int i = 0; i < y; i++) s += (char) (Math.random() * 26 + 'A');
        s += "-";
        for (int i = 0; i < z; i++) s += (char) (Math.random() * 10 + '0');

        return s;
    }


    private String nummer(){
        String nummer = "";
        nummer = Integer.toString(this.carNummer++);
        return nummer;
    }


    public static void main(String[] args){
        Parameter p = new Parameter(0);


    }

}
