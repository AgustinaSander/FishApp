/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.FichaDatos;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Agustina
 */
public class FichaDatosJpaController implements Serializable {

    public FichaDatosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public FichaDatosJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FichaDatos fichaDatos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fichaDatos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FichaDatos fichaDatos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fichaDatos = em.merge(fichaDatos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = fichaDatos.getIdFicha();
                if (findFichaDatos(id) == null) {
                    throw new NonexistentEntityException("The fichaDatos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichaDatos fichaDatos;
            try {
                fichaDatos = em.getReference(FichaDatos.class, id);
                fichaDatos.getIdFicha();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichaDatos with id " + id + " no longer exists.", enfe);
            }
            em.remove(fichaDatos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FichaDatos> findFichaDatosEntities() {
        return findFichaDatosEntities(true, -1, -1);
    }

    public List<FichaDatos> findFichaDatosEntities(int maxResults, int firstResult) {
        return findFichaDatosEntities(false, maxResults, firstResult);
    }

    private List<FichaDatos> findFichaDatosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FichaDatos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FichaDatos findFichaDatos(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FichaDatos.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichaDatosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FichaDatos> rt = cq.from(FichaDatos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
