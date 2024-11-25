public class Animal {
    private String species;
    private String eyeColor;
    private boolean hasFur;

    // Конструктор, который принимает Builder
    private Animal(Builder builder) {
        this.species = builder.species;
        this.eyeColor = builder.eyeColor;
        this.hasFur = builder.hasFur;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHasFur(boolean hasFur) {
        this.hasFur = hasFur;
    }

    // Пустой конструктор
    public Animal() {}

    // Статический внутренний класс Builder
    public static class Builder {
        private String species;
        private String eyeColor;
        private boolean hasFur;

        // Метод для задания вида
        public Builder species(String species) {
            this.species = species;
            return this;
        }

        // Метод для задания цвета глаз
        public Builder eyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }

        // Метод для задания наличия шерсти
        public Builder hasFur(boolean hasFur) {
            this.hasFur = hasFur;
            return this;
        }

        // Метод для построения объекта Animal
        public Animal build() {
            // Возвращаем новый объект Animal, не инициализируя параметры
            return new Animal(this);
        }
    }

    // Геттеры для доступа к полям
    public String getSpecies() {
        return species;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public boolean hasFur() {
        return hasFur;
    }
}