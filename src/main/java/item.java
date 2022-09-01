public class clothes {
    public String clothing_type;
    public String color;
    public String clothing_style;
    public String clothing_size;
    public String gender;
    public int price;

    public clothes(String clothing_type, String color, String clothing_style, String clothing_size, String gender, int price) {
        this.clothing_type = clothing_type;
        this.color = color;
        this.clothing_style = clothing_style;
        this.clothing_size = clothing_size;
        this.gender = gender;
        this.price = price;
    }

    public String getClothing_type() {
        return clothing_type;
    }

    public void setClothing_type(String clothing_type) {
        this.clothing_type = clothing_type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClothing_style() {
        return clothing_style;
    }

    public void setClothing_style(String clothing_style) {
        this.clothing_style = clothing_style;
    }

    public String getClothing_size() {
        return clothing_size;
    }

    public void setClothing_size(String clothing_size) {
        this.clothing_size = clothing_size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "clothes{" +
                "clothing_type='" + clothing_type + '\'' +
                ", color='" + color + '\'' +
                ", clothing_style='" + clothing_style + '\'' +
                ", clothing_size='" + clothing_size + '\'' +
                ", gender='" + gender + '\'' +
                ", price=" + price +
                '}';
    }
}
