package anhtuan.service;

import anhtuan.model.Music;
import anhtuan.model.MusicForm;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
@Service
public class MusicService implements IMusicService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    List<MusicForm> musicFormList = new ArrayList<>();
    List<Music> musicList = new ArrayList<>();

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Music> findAll() {
        String queryStr = "SELECT m FROM Music AS m";
        TypedQuery<Music> query = entityManager.createQuery(queryStr, Music.class);
        return query.getResultList();
    }

    @Override
    public Music findOne(int id) {
        String queryStr = "SELECT m FROM Music AS m WHERE m.id = :id";
        TypedQuery<Music> query = entityManager.createQuery(queryStr, Music.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Music findOneName(String name) {
        String queryStr = "SELECT m FROM Music AS m WHERE m.name = :name";
        TypedQuery<Music> query = entityManager.createQuery(queryStr, Music.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }



    @Override
    public Music save(Music music) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(music);
            musicList.add(music);
            transaction.commit();
            return music;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public void saveForm(MusicForm musicForm) {
        musicFormList.add(musicForm);
    }
    public MusicForm findByFormName(String name) {
        MusicForm musicForm = null;
        for (int i = 0; i < musicFormList.size(); i++) {
            if (musicFormList.get(i).getName().equals(name)) {
                musicForm = musicFormList.get(i);
                break;
            }
        }
        return musicForm;
    }

    public Music findByName(String name) {
        Music music = null;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getName().equals(name)) {
                music = musicList.get(i);
                break;
            }
        }
        return music;
    }

    @Override
    public Music update(Music music) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Music music1 = findOne(music.getId());
            music1.setName(music.getName());
            music1.setSingle(music.getSingle());
            music1.setCategory(music.getCategory());
            music1.setAudio(music.getAudio());
            session.saveOrUpdate(music1);
            transaction.commit();
            return music1;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public Music findById(int id) {
        return musicList.get(id);
    }
    public MusicForm findByFormId(int id) {
        return musicFormList.get(id);
    }


    @Override
    public void delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Music music = findOne(id);
            session.delete(music);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
