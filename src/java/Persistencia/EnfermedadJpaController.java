/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Logica.Enfermedad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.FichaMedica;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Agustina
 */
public class EnfermedadJpaController implements Serializable {

    public EnfermedadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EnfermedadJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enfermedad enfermedad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichaMedica ficha = enfermedad.getFicha();
            if (ficha != null) {
                ficha = em.getReference(ficha.getClass(), ficha.getIdFicha());
                enfermedad.setFicha(ficha);
            }
            em.persist(enfermedad);
            if (ficha != null) {
                ficha.getListaEnfermedades().add(enfermedad);
                ficha = em.merge(ficha);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enfermedad enfermedad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermedad persistentEnfermedad = em.find(Enfermedad.class, enfermedad.getIdEnfermedad());
            FichaMedica fichaOld = persistentEnfermedad.getFicha();
            FichaMedica fichaNew = enfermedad.getFicha();
            if (fichaNew != null) {
                fichaNew = em.getReference(fichaNew.getClass(), fichaNew.getIdFicha());
                enfermedad.setFicha(fichaNew);
            }
            enfermedad = em.merge(enfermedad);
            if (fichaOld != null && !fichaOld.equals(fichaNew)) {
                fichaOld.getListaEnfermedades().remove(enfermedad);
                fichaOld = em.merge(fichaOld);
            }
            if (fichaNew != null && !fichaNew.equals(fichaOld)) {
                fichaNew.getListaEnfermedades().add(enfermedad);
                fichaNew = em.merge(fichaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = enfermedad.getIdEnfermedad();
                if (findEnfermedad(id) == null) {
                    throw new NonexistentEntityException("The enfermedad with id " + id + " no longer exists.");
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
            Enfermedad enfermedad;
            try {
                enfermedad = em.getReference(Enfermedad.class, id);
                enfermedad.getIdEnfermedad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enfermedad with id " + id + " no longer exists.", enfe);
            }
            FichaMedica ficha = enfermedad.getFicha();
            if (ficha != null) {
                ficha.getListaEnfermedades().remove(enfermedad);
                ficha = em.merge(ficha);
            }
            em.remove(enfermedad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enfermedad> findEnfermedadEntities() {
        return findEnfermedadEntities(true, -1, -1);
    }

    public List<Enfermedad> findEnfermedadEntities(int maxResults, int firstResult) {
        return findEnfermedadEntities(false, maxResults, firstResult);
    }

    private List<Enfermedad> findEnfermedadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enfermedad.class));
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

    public Enfermedad findEnfermedad(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enfermedad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnfermedadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enfermedad> rt = cq.from(Enfermedad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
