public class Barrel<T> {
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

    @Override
    public String toString() {
        return "Barrel [volume=" + volume + ", storedMaterial=" + materialType + ", material=" + material + "]";
    }

    public double getVolume() {
        return volume;
    }

    public T getStoredMaterial() {
        return materialType;
    }

    public String getMaterial() {
        return material;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setStoredMaterial(T storedMaterial) {
        this.materialType = storedMaterial;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public static class Builder<T> {
        private double volume;
        private T materialType;
        private String material;

        public Builder<T> volume(double volume) {
            this.volume = volume;
            return this;
        }

        public Builder<T> storedMaterial(T storedMaterial) {
            this.materialType = storedMaterial;
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
