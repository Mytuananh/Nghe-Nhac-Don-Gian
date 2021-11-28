package anhtuan.service;

import anhtuan.model.Music;

import java.util.List;

public interface IMusicService {
    List<Music> findAll();

    Music findOne(int id);

    Music save(Music music);

    Music update(Music music);

    void delete(int id);

}
