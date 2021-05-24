public class Car implements CarIF {
    boolean isParking;
    String licensePlate;
    String ticketId;
    String color;
    String carType;
    int duration;
    int price;


    Car(String licensePlate, String ticketId, String color, String carType){
        this.isParking = true;
        this.licensePlate = licensePlate;
        this.ticketId = ticketId;
        this.color = color;
        this.carType = carType;
        this.price =  -1;
        this.duration = -1;
    }


    public static String[] durationArray(CarIF[] cars){
        String[] durationArray = new String[cars.length];
        for (int i=0; i<cars.length; i++){
            durationArray[i] = Integer.toString(cars[i].getDuration());
        }
        return durationArray;
    }


    public static int[] priceArray(CarIF[] cars){
        int[] priceArray = new int[cars.length];
        for (int i=0; i<cars.length; i++){
            priceArray[i] = cars[i].getPrice();
        }
        return priceArray;
    }


    public static String[] carTypeArray(CarIF[] cars){
        String[] carTypeArray = new String[cars.length];
        for (int i=0; i<cars.length; i++){
            carTypeArray[i] = cars[i].getCarType();
        }
        return carTypeArray;
    }


    public static String[] ticketIdArray(CarIF[] cars){
        String[] ticketIdArray = new String[cars.length];
        for (int i=0; i<cars.length; i++){
            ticketIdArray[i] = cars[i].getTicketId();
        }
        return ticketIdArray;
    }


    @Override
    public void leave(int duration, int price) {
        this.isParking = false;
        this.duration = duration;
        this.price = price;
    }


    @Override
    public String getCarType() {
        return this.carType;
    }


    @Override
    public int getPrice() {
        return this.price;
    }


    @Override
    public int getDuration() {
        return this.duration;
    }


    @Override
    public String getTicketId() {
        return this.ticketId;
    }
}
