package models;

public class Animal {
    private String species; // Вид
    private String eyeColor; // Цвет глаз
    private boolean fur; // Шерсть

    private Animal(AnimalBuilder animalBuilder) {
        this.species = animalBuilder.species;
        this.eyeColor = animalBuilder.eyeColor;
        this.fur = animalBuilder.fur;
    }
    public String getSpecies() {
        return species;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public boolean hasFur() {
        return fur;
    }

    public static class AnimalBuilder {
        private String species;
        private String eyeColor;
        private boolean fur;

        public AnimalBuilder setSpecies(String species) {
            this.species = species;
            return this;
        }

        public AnimalBuilder setEyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }

        public AnimalBuilder setFur(boolean fur) {
            this.fur = fur;
            return this;
        }

        public Animal build() {
            return new Animal(this);
        }
    }
}
