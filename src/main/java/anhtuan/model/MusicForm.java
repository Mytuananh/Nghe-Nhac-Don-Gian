package anhtuan.model;

import org.springframework.web.multipart.MultipartFile;

public class MusicForm {
    private int id;
    private String name;
    private String single;
    private String category;
    private MultipartFile audio;

    public MusicForm() {
    }

    public MusicForm(String name, String single, String category, MultipartFile audio) {
        this.name = name;
        this.single = single;
        this.category = category;
        this.audio = audio;
    }

    public MusicForm(int id, String name, String single, String category, MultipartFile audio) {
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

    public MultipartFile getAudio() {
        return audio;
    }

    public void setAudio(MultipartFile audio) {
        this.audio = audio;
    }
}
