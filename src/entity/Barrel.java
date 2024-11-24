package entity;

public class Barrel<T> implements Comparable<Barrel<T>>{
    private double volume;
    private T materialType;
    private String material;

    public Barrel() {
    }

    private Barrel(Builder<T> builder) {
        this.volume = builder.volume;
        this.materialType = builder.materialType;
        this.material = builder.material;
    }

    public double getVolume() {
        return volume;
    }

    public T getMaterialType() {
        return materialType;
    }

    public String getMaterial() {
        return material;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setmaterialType(T materialType) {
        this.materialType = materialType;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    // Реализация метода compareTo
    @Override
    public int compareTo(Barrel<T> other) {
        if (other == null) {
            throw new NullPointerException("Невозможно сравнить с null-объектомl");
        }
        // Сравнение по объему
        return Double.compare(this.volume, other.volume);
    }

    // Переопределение toString для удобства
    @Override
    public String toString() {
        return "Бочка [объем бочки = " + volume + ", хранимый материал = " + materialType + ", материал бочки = " + material + "]";
    }

    public static class Builder<T> {
        private double volume;
        private T materialType;
        private String material;

        public Builder<T> volume(double volume) {
            this.volume = volume;
            return this;
        }

        public Builder<T> materialType(T materialType) {
            this.materialType = materialType;
            return this;
        }

        public Builder<T> material(String material) {
            this.material = material;
            return this;
        }

        public Barrel<T> build() {
            return new Barrel<>(this);
        }


    }
}
