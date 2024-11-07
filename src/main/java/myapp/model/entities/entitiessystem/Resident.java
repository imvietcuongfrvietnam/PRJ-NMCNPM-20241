package myapp.model.entities.entitiessystem;

public class Resident {
    private int index;
    private String name;
    private String birthday;
    private String hometown;

    public Resident(int index, String name, String birthday, String hometown) {
        this.index = index;
        this.name = name;
        this.birthday = birthday;
        this.hometown = hometown;
    }

    public Resident() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
}
