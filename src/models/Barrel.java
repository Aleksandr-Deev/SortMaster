package models;

public class Barrel {
    private double volume; // Объем
    private String storedMaterial; // Хранимый материал
    private String material; // Материал из которого изготовлена

    public Barrel(BarrelBuilder barrelBuilder) {
        this.volume = barrelBuilder.volume;
        this.storedMaterial = barrelBuilder.storedMaterial;
        this.material = barrelBuilder.material;
    }
    public double getVolume() {
        return volume;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public String getMaterial() {
        return material;
    }

    public static class BarrelBuilder {
        private double volume;
        private String storedMaterial;
        private String material;

        public BarrelBuilder setVolume(double volume) {
            this.volume = volume;
            return this;
        }

        public BarrelBuilder setStoredMaterial(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }

        public BarrelBuilder setMaterial(String material) {
            this.material = material;
            return this;
        }

        public Barrel build() {
            return new Barrel(this);
        }
    }
}

