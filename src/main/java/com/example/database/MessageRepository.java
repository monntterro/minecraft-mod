package com.example.database;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class MessageRepository {

    public void save(UUID playerUuid, String text) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            MessageEntity entity = new MessageEntity(playerUuid, text);
            session.persist(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}