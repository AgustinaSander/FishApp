/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.Medicion;
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
public class MedicionJpaController implements Serializable {

    public MedicionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public MedicionJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicion medicion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(medicion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicion medicion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            medicion = em.merge(medicion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = medicion.getIdMedicion();
                if (findMedicion(id) == null) {
                    throw new NonexistentEntityException("The medicion with id " + id + " no longer exists.");
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
            Medicion medicion;
            try {
                medicion = em.getReference(Medicion.class, id);
                medicion.getIdMedicion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicion with id " + id + " no longer exists.", enfe);
            }
            em.remove(medicion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicion> findMedicionEntities() {
        return findMedicionEntities(true, -1, -1);
    }

    public List<Medicion> findMedicionEntities(int maxResults, int firstResult) {
        return findMedicionEntities(false, maxResults, firstResult);
    }

    private List<Medicion> findMedicionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicion.class));
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

    public Medicion findMedicion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicion> rt = cq.from(Medicion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
