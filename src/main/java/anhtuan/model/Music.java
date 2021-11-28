package anhtuan.model;

import javax.persistence.*;

@Entity
@Table
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String single;
    private String category;
    private String audio;

    public Music() {
    }

    public Music(String name, String single, String category, String audio) {
        this.name = name;
        this.single = single;
        this.category = category;
        this.audio = audio;
    }

    public Music(int id, String name, String single, String category, String audio) {
        this.id = id;
        this.name = name;
        this.single = single;
        this.category = category;
        this.audio = audio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
