/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author roseanealves
 */
public class JPAEntityManager {
    private static final String PU_NAME = "RoseBankPU";
    private static EntityManagerFactory emf = null;

    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PU_NAME);
        }
        return emf.createEntityManager();
    }
}
