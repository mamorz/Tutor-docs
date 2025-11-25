class Car {
    private String manufacturer;
    private String name;
    public String getManufacturer() {
        return this.manufacturer;
    }
    public void setName(String name) {
        this.name = name;
    }
}
class Test {
    Car car = new Car();
    System.out.println("Manufacturer: " + car.getManufacturer());
    car.setName("TT");
}
